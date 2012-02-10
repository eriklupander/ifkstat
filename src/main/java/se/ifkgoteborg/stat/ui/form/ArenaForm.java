package se.ifkgoteborg.stat.ui.form;

import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Country;
import se.ifkgoteborg.stat.model.Ground;
import se.ifkgoteborg.stat.model.Player;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

public class ArenaForm extends BaseForm<Ground> implements FormFieldFactory {
	
	public ArenaForm(RegistrationDAO dao, BeanItem<Ground> item) {
		super(dao, item);
		init();
	}
	
	@Override
	protected void init() {
		super.init();
		fields.add("name");
		fields.add("address");
		fields.add("city");
		fields.add("country");
		fields.add("maxCapacity");
			
		setCaption("Arenainformation");
		// FieldFactory for customizing the fields and adding validators
		setFormFieldFactory(this);
		setItemDataSource(item); // bind to POJO via BeanItem

		// Determines which properties are shown, and in which order:
		setVisibleItemProperties(fields);		
	}

	@Override
	public Field createField(Item item, Object pid, Component uiContext) {
		if("country".equals(pid)) {
			Select select = new Select("Land");
	        List<Country> country = dao.getCountries();
	        for(Country c : country) {
	        	select.addItem(c);
	        }
	        select.setRequired(false);
	        select.setNullSelectionAllowed(false);
	        select.setValue(item.getItemProperty(pid));
	        select.setNewItemsAllowed(false);
	        return select;
		}
		if ("name".equals(pid)) {
            return new TextField("Namn");
        } else if ("address".equals(pid)) {
            return new TextField("Adress");
        } else if ("city".equals(pid)) {
            return new TextField("Stad");
        } else if ("maxCapacity".equals(pid)) {
            return new TextField("Publikkapacitet");
        }
		return null;
	}

	@Override
	protected ClickListener getSaveListener() {
		return new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {               
            	commit();
            	ArenaForm.this.dao.updateGround( (Ground) ((BeanItem) getItemDataSource()).getBean());
            	getWindow().getParent().removeWindow(getWindow());
            }
        };
	}
}
