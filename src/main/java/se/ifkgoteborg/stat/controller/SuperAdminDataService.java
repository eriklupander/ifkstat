package se.ifkgoteborg.stat.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.ifkgoteborg.stat.model.User;

@Path("/admin/superadmin")
@RolesAllowed("admin")
public interface SuperAdminDataService {

	@POST
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User createUser(User user);
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean login();
	
	@POST
	@Path("/bulk")
	@Consumes(MediaType.APPLICATION_JSON)
	public void bulkUploadGameData(String data);
	
	@POST
	@Path("/notes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void bulkUploadNotes(String data);

	@POST
	@Path("/clean/{password}")
	public void cleanDatabase(@PathParam("password") String password);

	@POST
	@Path("/reseed/{password}")
	public void reseedInitData(@PathParam("password") String password);
}
