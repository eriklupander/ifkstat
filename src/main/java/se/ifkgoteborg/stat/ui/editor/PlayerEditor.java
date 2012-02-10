package se.ifkgoteborg.stat.ui.editor;

import java.io.Serializable;
import java.lang.reflect.Method;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.ui.form.PlayerForm;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class PlayerEditor extends BaseEditor  {

	

	public PlayerEditor(Item player, RegistrationDAO dao) {
		super(dao);
		PlayerForm form = new PlayerForm(dao, player);
		setWidth(700, UNITS_PIXELS);
		setCaption((String) player.getItemProperty("name").getValue());
		
		addComponent(form);		
	}

	


}
