package se.ifkgoteborg.stat.ui.form;

import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Club;
import se.ifkgoteborg.stat.model.Formation;
import se.ifkgoteborg.stat.model.Ground;
import se.ifkgoteborg.stat.model.Referee;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class GameFormFieldFactory implements FormFieldFactory {

	private static final long serialVersionUID = 1L;
	
	private final RegistrationDAO dao;

	public GameFormFieldFactory(RegistrationDAO dao) {
		super();
		this.dao = dao;		
	}
	
    public Field createField(Item item, Object propertyId,
                             Component uiContext) {
        // Identify the fields by their Property ID.
    	System.out.println("PropertyID: " + propertyId.toString());
        String pid = (String) propertyId;
        if ("attendance".equals(pid)) {
            return new TextField("Publiksiffra");
        } else if ("homeGoals".equals(pid)) {
            return new TextField("Hemmamål");
        } else if ("awayGoals".equals(pid)) {
            return new TextField("Bortamål");
        } else if("dateOfGame".equals(pid)) {
        	Property itemProperty = item.getItemProperty(propertyId);

        	PopupDateField datetime = new PopupDateField("Matchdatum");
        	datetime.setResolution(PopupDateField.RESOLUTION_DAY);
        	datetime.setValue(item.getItemProperty(propertyId).getValue());
        	return datetime;
        } else if("gameSummary".equals(pid)) {
        	TextArea editor = new com.vaadin.ui.TextArea(null, item.getItemProperty(propertyId));
            editor.setRows(10);
            editor.setColumns(20);
            return editor;
        } else if ("formation".equals(pid)) {
            Select select = new Select("Formation");
            List<Formation> formations = dao.getFormations();
            for(Formation formation : formations) {
            	select.addItem(formation);
            }
            select.setRequired(true);
            select.setNullSelectionAllowed(false);
            select.setValue(item.getItemProperty(propertyId));
            select.setNewItemsAllowed(false);
            return select;
        } else if("homeTeam".equals(pid) || "awayTeam".equals(pid)) {
        	Select select = new Select("homeTeam".equals(pid) ? "Hemmalag" : "Bortalag");
        	List<Club> clubs = dao.getClubs();
            for(Club club : clubs) {
            	select.addItem(club);
            }
            select.setRequired(true);
            select.setNullSelectionAllowed(false);
            select.setValue(item.getItemProperty(propertyId));
            select.setNewItemsAllowed(false);
            return select;
        } else if("ground".equals(pid)) {
        	Select select = new Select("Spelplats");
        	List<Ground> grounds = dao.getGrounds();
            for(Ground ground : grounds) {
            	select.addItem(ground);
            }
            select.setRequired(true);
            select.setNullSelectionAllowed(false);
            select.setValue(item.getItemProperty(propertyId));
            select.setNewItemsAllowed(false);
            return select;
        } else if("referee".equals(pid)) {
        	final ComboBox select = new ComboBox("Domare");
        	List<Referee> referees = dao.getReferees();
        	final IndexedContainer container = new IndexedContainer();
            for(Referee ref : referees) {
            	container.addItem(ref);
            }
            select.setRequired(false);
            select.setNullSelectionAllowed(false);
            select.setValue(item.getItemProperty(propertyId));
            select.setNewItemsAllowed(true);            
			select.setNewItemHandler(new NewItemHandler() {

				private static final long serialVersionUID = 1L;

				@Override
				public void addNewItem(String newItemCaption) {
					container.addItem(newItemCaption);
		            select.setValue(newItemCaption);
				}
				
			});
			select.setContainerDataSource(container);
            return select;
        }
        
        return null; //new TextField((String) propertyId); // Invalid field (property) name.
    }
}
