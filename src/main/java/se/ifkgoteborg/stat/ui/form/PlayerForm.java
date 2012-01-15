package se.ifkgoteborg.stat.ui.form;

import java.util.Collection;
import java.util.HashSet;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Player;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;

public class PlayerForm extends Form {
	
	Collection<String> fields = new HashSet<String>();
	
	private Button saveButton;
	private final RegistrationDAO dao;
	private GridLayout ourLayout;

	public PlayerForm(RegistrationDAO dao, Item playerItem) {
		this.dao = dao;
		
		fields.add("name");
		fields.add("fullName");
		fields.add("imageUrl");
		fields.add("length");
		fields.add("weight");
		fields.add("positionType");
		fields.add("dateOfBirth");
		fields.add("biography");
		fields.add("motherClub");
		fields.add("playedForClubs");
		fields.add("otherCompetitiveGames");
		fields.add("otherPracticeGames");
		
		// Create our layout (3x3 GridLayout)
		ourLayout = new GridLayout(3, 8);

		// Use top-left margin and spacing
		ourLayout.setMargin(true, false, false, true);
		ourLayout.setSpacing(true);

		setLayout(ourLayout);
		
		// Set up buffering
		setWriteThrough(false); // we want explicit 'apply'
		setInvalidCommitted(false); // no invalid values in datamodel

		// FieldFactory for customizing the fields and adding validators
		setFormFieldFactory(new PlayerFormFieldFactory(dao));
		setItemDataSource(playerItem); // bind to POJO via BeanItem

		// Determines which properties are shown, and in which order:
		setVisibleItemProperties(fields);
		saveButton = new Button("Spara");
	        saveButton.addListener(new Button.ClickListener() {

	            @Override
	            public void buttonClick(ClickEvent event) {
	                // Save...
	            	System.out.println("Saved clicked...");
	            	commit();
	            	PlayerForm.this.dao.updatePlayer( (Player) ((BeanItem) getItemDataSource()).getBean());
	            }
	        });
		ourLayout.addComponent(saveButton, 1, 6);
		ourLayout.addComponent(
				new Embedded("Spelarporträtt", new ExternalResource("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc4/161504_687872513_758152_n.jpg"))
				, 2, 0, 2, 2);
	}

	/*
     * Override to get control over where fields are placed.
     */
    @Override
    protected void attachField(Object propertyId, Field field) {
        if (propertyId.equals("name")) {
            ourLayout.addComponent(field, 0, 0);
        } 
        else if (propertyId.equals("fullName")) {
            ourLayout.addComponent(field, 1, 0);
        } 
        else if (propertyId.equals("length")) {
            ourLayout.addComponent(field, 0, 1);
        } 
        else if (propertyId.equals("weight")) {
            ourLayout.addComponent(field, 1, 1);
        }
        else if (propertyId.equals("positionType")) {
            ourLayout.addComponent(field, 0, 2);
        }
        else if (propertyId.equals("dateOfBirth")) {
            ourLayout.addComponent(field, 1, 2);
        }
        else if (propertyId.equals("biography")) {
            ourLayout.addComponent(field, 0, 3, 1, 3);
        }
        else if (propertyId.equals("motherClub")) {
            ourLayout.addComponent(field, 0, 4);
        } 
        else if (propertyId.equals("playedForClubs")) {
            ourLayout.addComponent(field, 1, 4);
        }
        else if (propertyId.equals("otherCompetitiveGames")) {
            ourLayout.addComponent(field, 0, 5);
        } 
        else if (propertyId.equals("otherPracticeGames")) {
            ourLayout.addComponent(field, 1, 5);
        }
    }

}
