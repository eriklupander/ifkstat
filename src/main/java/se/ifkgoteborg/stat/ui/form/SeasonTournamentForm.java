package se.ifkgoteborg.stat.ui.form;

import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Tournament;
import se.ifkgoteborg.stat.model.TournamentSeason;

import com.vaadin.data.Item;
import com.vaadin.data.util.AbstractContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.Select;

public class SeasonTournamentForm extends BaseForm<TournamentSeason> implements FormFieldFactory{

	private static final long serialVersionUID = 1L;
	private final AbstractContainer ac;

	public SeasonTournamentForm(RegistrationDAO dao, BeanItem<TournamentSeason> item, AbstractContainer ac) {
		super(dao, item);
		this.ac = ac;
		fields.add("tournament");
		//fields.add("season");
		setCaption("Lägg till turnering");
		// FieldFactory for customizing the fields and adding validators
		setFormFieldFactory(this);
		setItemDataSource(item); // bind to POJO via BeanItem

		// Determines which properties are shown, and in which order:
		setVisibleItemProperties(fields);		
		super.init();
	}

	@Override
	protected ClickListener getSaveListener() {
		return new Button.ClickListener() {

            @SuppressWarnings("unchecked")
			@Override
            public void buttonClick(ClickEvent event) {               
            	commit();
            	TournamentSeason ts = SeasonTournamentForm.this.dao.saveTournamentSeason( (TournamentSeason) ((BeanItem<TournamentSeason>) getItemDataSource()).getBean());
            	ac.addItem(item);  
            	System.out.println("Added BeanItem<TournamentSeason> to abstract container");
            	getWindow().getParent().removeWindow(getWindow());
            }
        };
	}

	@SuppressWarnings("unchecked")
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		if("tournament".equals(propertyId)) {
			Select select = new Select("Turnering");
	        List<Tournament> list = dao.getTournaments();
	        for(Tournament t : list) {
	        	select.addItem(t);
	        }
	        select.setRequired(false);
	        select.setNullSelectionAllowed(false);
	        select.setValue(item.getItemProperty(propertyId));
	        select.setNewItemsAllowed(false);
	       
	        return select;
		}
		return null;
	}

}
