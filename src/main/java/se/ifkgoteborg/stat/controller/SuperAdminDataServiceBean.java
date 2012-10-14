package se.ifkgoteborg.stat.controller;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import se.ifkgoteborg.stat.importer.MasterImporter;
import se.ifkgoteborg.stat.model.User;
import se.ifkgoteborg.stat.model.Userrole;
import se.ifkgoteborg.stat.util.PasswordUtil;
import se.ifkgoteborg.stat.util.StringUtil;

@Stateless
@RolesAllowed("admin")
public class SuperAdminDataServiceBean implements SuperAdminDataService {
	
	@Inject
	EntityManager em;
	
	@Inject
	RegistrationDAO registrationDao;

	@Override
	public User createUser(User user) {
		validateUser(user);
		user.setPasswd(hashPassword(user.getPasswd()));
		user.setFirstName(StringUtil.capitalize(user.getFirstName()));
		user.setLastName(StringUtil.capitalize(user.getLastName()));
		
		Userrole ur = new Userrole();
		ur.setUsername(user.getUsername());
		if(user.getRoles() != null) {			
			ur.setUserRoles(user.getRoles()); // comma-separated string
		} else {
			ur.setUserRoles("user");
		}
		
		user = em.merge(user);
		ur = em.merge(ur);
		
		return user;
		
	}

	private String hashPassword(String passwd) {
		return PasswordUtil.getPasswordHash(passwd);
	}

	private void validateUser(User user) {
		if(isEmpty(user.getUsername())) {
			throw new IllegalArgumentException("Username is required");
		}
		if(isEmpty(user.getPasswd())) {
			throw new IllegalArgumentException("Password is required");
		}
	}

	private boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	@Override
	public Boolean login() {
		return true;
	}

	@Override
	public void bulkUpload(String data) {
		new MasterImporter(registrationDao).importMasterFile(data);
	}

}
