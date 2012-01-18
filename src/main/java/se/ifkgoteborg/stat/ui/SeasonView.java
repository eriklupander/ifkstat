package se.ifkgoteborg.stat.ui;

import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.PlayedForClub;
import se.ifkgoteborg.stat.model.Season;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
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
	
	Button addBtn = new Button("Lägg till");
	Button removeBtn = new Button("Ta bort");
	Button saveBtn = new Button("Spara");
	
	BeanItemContainer<Season> bic = new BeanItemContainer<Season>(Season.class);
	BeanItemContainer<PlayedForClub> tableBic = new BeanItemContainer<PlayedForClub>(PlayedForClub.class);
	
	private final RegistrationDAO dao;
	
	private String[] cols = new String[]{"player.name", "squadNr", "importIndex"};
	protected PlayedForClub selectedItem;
	
	public SeasonView(RegistrationDAO dao) {
		this.dao = dao;
		setupButtons();
		loadSeasons();
		seasonCombo.setContainerDataSource(bic);
		seasonCombo.addListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				Object value = event.getProperty().getValue();
				if(value instanceof Season) {
					loadSeasonIntoTable(((Season) value).getSquad());
				}
			}

			
		});
		table.setContainerDataSource(tableBic);
//		table.setTableFieldFactory(new TableFieldFactory() {
//
//            @Override
//            public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
//                System.out.println(container.toString() + itemId.toString() + propertyId.toString() + uiContext.toString());
//                String pid = (String) propertyId;
//                if("squadNr".equals(pid)) {
//                	return DefaultFieldFactory.get().createField(container, itemId, propertyId, uiContext);
//                }
//                return null;
//            }
//        });
		tableBic.addNestedContainerProperty("player.name");
		table.setColumnHeader("player.name", "Namn");   
		table.setColumnHeader("squadNr", "Spelarnummer");
		table.setColumnHeader("importIndex", "Indexplats");
		//table.setColumnHeader("btn", "");
		table.setEditable(true);
		table.setVisibleColumns(cols);
		table.setSelectable(true);
		table.setImmediate(true);
		
		table.addListener(new Property.ValueChangeListener() {
		    public void valueChange(ValueChangeEvent event) {
		    	selectedItem = (PlayedForClub) table.getValue();
				removeBtn.setEnabled(true);
		    }
		});
		
		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.addComponent(addBtn);
		toolBar.addComponent(removeBtn);
		
		addComponent(seasonCombo);
		addComponent(toolBar);
		addComponent(table);
		
		HorizontalLayout footerBar = new HorizontalLayout();
		footerBar.addComponent(saveBtn);
		addComponent(footerBar);
	}

	private void setupButtons() {
		addBtn.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				tableBic.addBean(new PlayedForClub());
			}
		});
		
		removeBtn.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				tableBic.removeItem(selectedItem);
				dao.removePlayedForClub(selectedItem);
				selectedItem = null;	
				table.setValue(null);
				table.commit();
			}
		});
	}

	private void loadSeasonIntoTable(List<PlayedForClub> squad) {
		tableBic.removeAllItems();
		int i = 0;
		
		for(PlayedForClub pfc : squad) {
			
//			Integer itemId = new Integer(i);
//			BeanItem<PlayedForClub> bi = new BeanItem<PlayedForClub>(pfc);
//			Button buttonField = new Button("Ändra");
//		    buttonField.setData(itemId);
//		    buttonField.addListener(new Button.ClickListener() {
//		        public void buttonClick(ClickEvent event) {
//		            // Get the item identifier from the user-defined data.
//		            Integer itemId = (Integer)event.getButton().getData();
//		            getWindow().showNotification("Link "+
//		                                   itemId.intValue()+" clicked.");
//		        } 
//		    });
//		    buttonField.addStyleName("link");
//			bi.addItemProperty("btn", buttonField);
//						
			tableBic.addBean(pfc);	
			i++;
		}	
		//tableBic.addNestedContainerProperty("btn");
	}
	
	private void loadSeasons() {
		List<Season> seasons = dao.getSeasons();
		
		bic.removeAllItems();
		for(Season s : seasons) {
			bic.addBean(s);
		}
	}
	
}
