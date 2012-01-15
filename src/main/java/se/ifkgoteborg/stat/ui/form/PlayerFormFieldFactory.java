package se.ifkgoteborg.stat.ui.form;

import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.enums.PositionType;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class PlayerFormFieldFactory implements FormFieldFactory {
private static final long serialVersionUID = 1L;
	
	private final RegistrationDAO dao;

	public PlayerFormFieldFactory(RegistrationDAO dao) {
		super();
		this.dao = dao;		
	}
	
    public Field createField(Item item, Object propertyId,
                             Component uiContext) {
    	if("positionType".equals(propertyId)) {
			Select select = new Select("Position");
	        List<PositionType> positions = dao.getPositionTypes();
	        for(PositionType pos : positions) {
	        	select.addItem(pos);
	        }
	        select.setRequired(false);
	        select.setNullSelectionAllowed(false);
	        select.setValue(item.getItemProperty(propertyId));
	        select.setNewItemsAllowed(false);
	        return select;
		} else if("biography".equals(propertyId)) {
        	TextArea editor = new com.vaadin.ui.TextArea("Spelarbiografi", item.getItemProperty(propertyId));
            editor.setRows(10);
            editor.setColumns(20);
            return editor;
        } else if("dateOfBirth".equals(propertyId)) {
        	Property itemProperty = item.getItemProperty(propertyId);
        	
        	PopupDateField datetime = new PopupDateField("Födelsedatum");
        	datetime.setResolution(PopupDateField.RESOLUTION_DAY);
        	datetime.setValue(item.getItemProperty(propertyId).getValue());
        	datetime.setRequired(false);
        	
        	return datetime;
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
		if("name".equals(propertyId)) {
			return "Namn";
		}
		if("fullName".equals(propertyId)) {
			return "Fullständigt namn";
		}
		if("length".equals(propertyId)) {
			return "Längd";
		}
		if("weight".equals(propertyId)) {
			return "Vikt";
		}
		if("motherClub".equals(propertyId)) {
			return "Moderklubb";
		}
		if("playedForClubs".equals(propertyId)) {
			return "Övriga klubbar";
		}
		if("otherCompetitiveGames".equals(propertyId)) {
			return "Övriga tävlingsmatcher";
		}
		if("otherPracticeGames".equals(propertyId)) {
			return "Övriga vänskapsmatcher";
		}
		return "";
	}
}
