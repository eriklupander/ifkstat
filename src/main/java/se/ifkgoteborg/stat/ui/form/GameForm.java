package se.ifkgoteborg.stat.ui.form;

import java.util.Collection;
import java.util.HashSet;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Game;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;

public class GameForm extends Form {

	Collection<String> fields = new HashSet<String>();

	private RegistrationDAO dao;
	private GridLayout ourLayout;
	
	public GameForm(RegistrationDAO dao, BeanItem<Game> gameItem) {
		this.dao = dao;
		init(gameItem);
	}

	
	private void init(BeanItem<Game> gameItem) {
		fields.add("homeTeam");
		fields.add("awayTeam");
		fields.add("attendance");
		fields.add("homeGoals");
		fields.add("awayGoals");
		fields.add("formation");
		fields.add("ground");
		fields.add("referee");
		fields.add("dateOfGame");
		fields.add("gameSummary");
	
		setCaption("Matchfakta");

		// Create our layout (3x3 GridLayout)
		ourLayout = new GridLayout(2, 8);

		// Use top-left margin and spacing
		ourLayout.setMargin(true, false, false, true);
		ourLayout.setSpacing(true);

		setLayout(ourLayout);

		// Set up buffering
		setWriteThrough(false); // we want explicit 'apply'
		setInvalidCommitted(false); // no invalid values in datamodel

		// FieldFactory for customizing the fields and adding validators
		setFormFieldFactory(new GameFormFieldFactory(dao));
		setItemDataSource(gameItem); // bind to POJO via BeanItem

		// Determines which properties are shown, and in which order:
		setVisibleItemProperties(fields);		
	}
	
	public Game getEntityForSave() {
		BeanItem<Game> item = (BeanItem<Game>) this.getItemDataSource();
		commit();
		return item.getBean();
	}
	
	/*
     * Override to get control over where fields are placed.
     */
    @Override
    protected void attachField(Object propertyId, Field field) {
        if (propertyId.equals("homeTeam")) {
            ourLayout.addComponent(field, 0, 0);
        } 
        else if (propertyId.equals("awayTeam")) {
            ourLayout.addComponent(field, 1, 0);
        } 
        else if (propertyId.equals("homeGoals")) {
            ourLayout.addComponent(field, 0, 1);
        } 
        else if (propertyId.equals("awayGoals")) {
            ourLayout.addComponent(field, 1, 1);
        }
        else if (propertyId.equals("formation")) {
            ourLayout.addComponent(field, 0, 2);
        }
        else if (propertyId.equals("dateOfGame")) {
            ourLayout.addComponent(field, 1, 2);
        }
        else if (propertyId.equals("ground")) {
            ourLayout.addComponent(field, 0, 3);
        }
        else if (propertyId.equals("attendance")) {
            ourLayout.addComponent(field, 1, 3);
        }
        else if (propertyId.equals("referee")) {
            ourLayout.addComponent(field, 0, 4);
        }
        
        else if (propertyId.equals("gameSummary")) {
            ourLayout.addComponent(field, 0, 5, 1, 5);
        }
    }

    
}
