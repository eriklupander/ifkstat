package se.ifkgoteborg.stat.ui.form;

import java.util.Collection;
import java.util.HashSet;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Game;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Button.ClickEvent;

public class GameForm extends Form {

	Collection<String> fields = new HashSet<String>();

	private RegistrationDAO dao;
	private GridLayout ourLayout;

	private Button saveButton;
	
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
		fields.add("dateOfGameAsDate");
		fields.add("gameSummary");
	
		setCaption("Personal details");

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
		 saveButton = new Button("Spara");
	        saveButton.addListener(new Button.ClickListener() {

	            @Override
	            public void buttonClick(ClickEvent event) {
	                // Save...
	            	System.out.println("Saved clicked...");
	            	commit();
	            	// Get item from form.	        
	            	Item item = getItemDataSource();	            	
	            	BeanItem<Game> bi = (BeanItem<Game>) item;	            	
	            	Game g = bi.getBean();	            	
	            	dao.updateGame(g);
	            }
	        });
		ourLayout.addComponent(saveButton, 1, 6);
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
        else if (propertyId.equals("dateOfGameAsDate")) {
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
