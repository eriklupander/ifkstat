package se.ifkgoteborg.stat.ui.form;

import java.util.Collection;
import java.util.HashSet;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.PlayedForClub;
import se.ifkgoteborg.stat.model.Player;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class PlayerSeasonForm extends Form {
	
	Collection<String> fields = new HashSet<String>();

	private final RegistrationDAO dao;
	
	Button saveBtn;
	Button saveCloseBtn;
	Button closeBtn;

	public PlayerSeasonForm(RegistrationDAO dao, PlayedForClub pfc) {
		this.dao = dao;	
		
		fields.add("player");
		fields.add("squadNr");
		fields.add("importIndex");
		
		// Set up buffering
		setWriteThrough(false); // we want explicit 'apply'
		setInvalidCommitted(false); // no invalid values in datamodel

		// Determines which properties are shown, and in which order:
		
		
		// FieldFactory for customizing the fields and adding validators
		setFormFieldFactory(new PlayerSeasonFormFieldFactory(dao));
		setItemDataSource(new BeanItem(pfc)); // bind to POJO via BeanItem
		setVisibleItemProperties(fields);
		
		HorizontalLayout hl = new HorizontalLayout();
		addButtons(hl);
		getLayout().addComponent(hl);
	}

	private void addButtons(HorizontalLayout hl) {
		saveBtn = new Button("Spara");
		saveBtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                // Save...	            	
            	commit();
            	PlayerSeasonForm.this.dao.updatePlayerSeason( (PlayedForClub) ((BeanItem) getItemDataSource()).getBean());
            }
        });
		
		
		saveCloseBtn = new Button("Spara och stäng");
		
		saveCloseBtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                // Save... and close	            	
            	commit();
            	PlayerSeasonForm.this.dao.updatePlayerSeason( (PlayedForClub) ((BeanItem) getItemDataSource()).getBean());
            	getWindow().getParent().removeWindow(getWindow());
            }
        });
		
		closeBtn = new Button("Avbryt");
		closeBtn.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {	 
            	getWindow().getParent().removeWindow(getWindow());
            }
        });
		
		hl.addComponent(saveBtn);
		hl.addComponent(saveCloseBtn);
		hl.addComponent(closeBtn);
	}

}
