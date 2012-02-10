package se.ifkgoteborg.stat.ui.editor;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Ground;
import se.ifkgoteborg.stat.ui.form.ArenaForm;

import com.vaadin.data.util.BeanItem;

public class ArenaEditor extends BaseEditor {
	public ArenaEditor(RegistrationDAO dao, BeanItem<Ground> item) {
		super(dao);
		ArenaForm form = new ArenaForm(dao, item);
		
		setCaption((String) item.getItemProperty("name").getValue());
		
		addComponent(form);
		addComponent(form.getButtons());
	}
}
