package se.ifkgoteborg.stat.ui.form;

import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Player;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

public class PlayerSeasonFormFieldFactory implements FormFieldFactory {
private static final long serialVersionUID = 1L;
	
	private final RegistrationDAO dao;

	public PlayerSeasonFormFieldFactory(RegistrationDAO dao) {
		super();
		this.dao = dao;		
	}
	
    public Field createField(Item item, Object propertyId,
                             Component uiContext) {
    	if("player".equals(propertyId)) {
			Select select = new Select("Namn");
	        List<Player> players = dao.getAllPlayersShallow();
	        for(Player pos : players) {
	        	select.addItem(pos);
	        }
	        select.setRequired(true);
	        select.setNullSelectionAllowed(false);
	        select.setValue(item.getItemProperty(propertyId));
	        select.setNewItemsAllowed(false);
	        return select;
		}
		Field field = DefaultFieldFactory.get().createField(item, propertyId,
				uiContext);
		
		if (field instanceof TextField) {
			field.setCaption(getCaption(propertyId));
			((TextField) field).setNullRepresentation("");
		}
		return field;
    }

	private String getCaption(Object propertyId) {
		if("player".equals(propertyId)) {
			return "Namn";
		}		
		if("squadNr".equals(propertyId)) {
			return "Tröjnummer denna säsong";
		}
		if("importIndex".equals(propertyId)) {
			return "Indexplats i importfil";
		}
		return "";
	}
}