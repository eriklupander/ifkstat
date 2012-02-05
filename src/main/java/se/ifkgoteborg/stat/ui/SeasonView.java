package se.ifkgoteborg.stat.ui;

import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.PlayedForClub;
import se.ifkgoteborg.stat.model.SquadSeason;
import se.ifkgoteborg.stat.ui.editor.PlayerEditor.EditorSavedEvent;
import se.ifkgoteborg.stat.ui.editor.PlayerEditor.EditorSavedListener;
import se.ifkgoteborg.stat.ui.editor.PlayerSeasonEditor;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class SeasonView extends VerticalLayout {

	ComboBox seasonCombo = new ComboBox("Säsonger");
	Table table = new Table("Spelare vald säsong");
	
	Button addBtn = new Button("Lägg till spelare");
	Button editBtn = new Button("Redigera vald spelare");
	Button removeBtn = new Button("Ta bort spelare");
	Button saveBtn = new Button("Spara");
	
	BeanItemContainer<SquadSeason> bic = new BeanItemContainer<SquadSeason>(SquadSeason.class);
	BeanItemContainer<PlayedForClub> tableBic = new BeanItemContainer<PlayedForClub>(PlayedForClub.class);
	
	private final RegistrationDAO dao;
	
	private String[] cols = new String[]{"player.name", "squadNr", "importIndex"};
	protected PlayedForClub selectedItem;
	protected SquadSeason selectedSeason;
	
	public SeasonView(RegistrationDAO dao) {
		this.dao = dao;
		setupButtons();
		loadSeasons();
		
		
		table.setContainerDataSource(tableBic);

		tableBic.addNestedContainerProperty("player.name");
		table.setColumnHeader("player.name", "Namn");   
		table.setColumnHeader("squadNr", "Spelarnummer");
		table.setColumnHeader("importIndex", "Indexplats");

		
		table.setVisibleColumns(cols);
		table.setSelectable(true);
		table.setImmediate(true);
		
		table.addListener(new Property.ValueChangeListener() {
		    public void valueChange(ValueChangeEvent event) {
		    	selectedItem = (PlayedForClub) table.getValue();
				removeBtn.setEnabled(true);
				editBtn.setEnabled(true);
		    }
		});
		
		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.addComponent(addBtn);
		toolBar.addComponent(editBtn);
		toolBar.addComponent(removeBtn);
		
		addComponent(seasonCombo);
		addComponent(toolBar);
		
		
		HorizontalLayout footerBar = new HorizontalLayout();
		footerBar.addComponent(saveBtn);
		addComponent(footerBar);
		
		seasonCombo.addListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				Object value = event.getProperty().getValue();
				if(value instanceof SquadSeason) {
					selectedSeason = (SquadSeason) value;
					System.out.println("Selected Season: " + value.toString());
					addComponent(table);
					loadSeasonIntoTable(((SquadSeason) value).getSquad());
				}
			}
		});
		
		seasonCombo.setContainerDataSource(bic);
	}

	private void setupButtons() {
		addBtn.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// tableBic.addBean(new PlayedForClub());
				// open "new" window.
                
                PlayerSeasonEditor psEditor = new PlayerSeasonEditor(new PlayedForClub(), selectedSeason, SeasonView.this.dao, SeasonView.this);
                getApplication().getMainWindow().addWindow(psEditor);
			}
		});
		
		editBtn.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {				
				// open "edit" window.
				if(selectedSeason == null || selectedItem == null) {
					return;
				}
				PlayerSeasonEditor psEditor = new PlayerSeasonEditor((PlayedForClub) selectedItem, selectedSeason, SeasonView.this.dao, SeasonView.this);
				getApplication().getMainWindow().addWindow(psEditor);
			}
		});
		editBtn.setEnabled(false);
		
		removeBtn.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(selectedSeason == null || selectedItem == null) {
					return;
				}
				tableBic.removeItem(selectedItem);
				dao.removePlayedForClub(selectedItem);
				selectedItem = null;	
				table.setValue(null);
				table.commit();
			}
		});
		removeBtn.setEnabled(false);
	}

	private void loadSeasonIntoTable(List<PlayedForClub> squad) {
		tableBic.removeAllItems();
		int i = 0;
		
		for(PlayedForClub pfc : squad) {
		
			tableBic.addBean(pfc);	
			i++;
		}
	}
	
	private void loadSeasons() {
		List<SquadSeason> seasons = dao.getSeasons();
		
		bic.removeAllItems();
		for(SquadSeason s : seasons) {
			bic.addBean(s);
		}
	}

	public void refresh() {
		tableBic.removeAllItems();
		loadSeasons();
		System.out.println("Refreshed SeasonView");
	}

	public void reloadTable() {	
		
		// E.g. reload from database.
		selectedSeason = dao.getSquadSeason(selectedSeason.getId());
		
		tableBic.removeAllItems();
		loadSeasonIntoTable(selectedSeason.getSquad());
	}
	
}
