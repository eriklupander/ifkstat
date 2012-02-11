package se.ifkgoteborg.stat.ui;

import java.util.HashSet;
import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.Tournament;
import se.ifkgoteborg.stat.model.TournamentSeason;
import se.ifkgoteborg.stat.ui.control.ComboBoxFactory;
import se.ifkgoteborg.stat.ui.editor.GameEditor;
import se.ifkgoteborg.stat.ui.wrapper.GameTableWrapper;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.VerticalLayout;

public class GameView extends VerticalLayout {
	
	BeanItemContainer<GameTableWrapper> beans =
		    new BeanItemContainer<GameTableWrapper>(GameTableWrapper.class);

    Table table = new Table("");
    HashSet<Object> markedRows = new HashSet<Object>();
    
    RegistrationDAO dao;
    HorizontalLayout hl = new HorizontalLayout();
   // HorizontalLayout buttons = new HorizontalLayout();
    ComboBox seasonComboBox;
    ComboBox tournamentComboBox;
    
    TournamentSeason ts = null;
    
    public GameView(RegistrationDAO dao) {
    	
    	this.dao = dao;    	
    	
    	tournamentComboBox = new ComboBoxFactory(dao).getTournamentComboBox();
    	tournamentComboBox.addListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				String tournamentName = (String) event.getProperty().getValue();
				if(tournamentName == null)
					return;
				
				final Tournament t = GameView.this.dao.getOrCreateTournamentByName(tournamentName);
				System.out.println("Loaded tournament " + t.getName());
				if(hl.getComponentCount() > 1) {
					hl.removeComponent(hl.getComponent(1));
				}
				seasonComboBox = new ComboBoxFactory(GameView.this.dao).getSeasonComboBox(t.getId());
				seasonComboBox.addListener(new ValueChangeListener() {

					@Override
					public void valueChange(ValueChangeEvent event) {
						ts = GameView.this.dao.getTournamentSeason(t.getId(), event.getProperty().getValue().toString());
						List<Game> games = GameView.this.dao.getGames(t.getId(),  event.getProperty().getValue().toString());
						
						// Populate game list...
						beans.removeAllItems();

						for (Game g : games) {
				           beans.addBean(new GameTableWrapper(g));
				        }
						
						if(getComponentCount() < 2) {
							HorizontalLayout buttons = new HorizontalLayout();
							Button addBtn = new Button("Lägg till match");
							Button removeBtn = new Button("Ta bort match");
							buttons.addComponent(addBtn);
							addBtn.addListener(new Button.ClickListener() {
								
								@Override
								public void buttonClick(ClickEvent event) {
									GameEditor ge = new GameEditor(new Game(), GameView.this.dao, ts);
						            getApplication().getMainWindow().addWindow(ge);
								}
							});
							
							removeBtn.addListener(new Button.ClickListener() {
								
								@Override
								public void buttonClick(ClickEvent event) {
									beans.removeItem(table.getValue());									
								}
							});
							
							buttons.removeComponent(removeBtn);
							VerticalLayout tableAndBtns = new VerticalLayout();
							tableAndBtns.addComponent(buttons);
							tableAndBtns.addComponent(table);
							addComponent(tableAndBtns, 1);
						}
					}
					
				});
				hl.addComponent(seasonComboBox, 1);
				
			}
		});
    	
    	
    	
    	hl.addComponent(tournamentComboBox, 0);
    	
    	addComponent(hl, 0);
        
        // set a style name, so we can style rows and cells
        table.setStyleName("iso3166");

        // size
        table.setWidth("100%");
        table.setHeight("400px");

        // selectable
        table.setSelectable(true);
        table.setMultiSelect(false);
        table.setImmediate(true); // react at once when something is selected

        // connect data source
        
        table.setContainerDataSource(beans);
        
        // turn on column reordering and collapsing
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);

        // set column headers
        table.setColumnHeader("fixture", "Match");
        table.setColumnHeader("date", "Datum");
        table.setColumnHeader("result", "Resultat");
        table.setColumnHeader("attendance", "Publiksiffra");
        table.setVisibleColumns(new String[]{"date","fixture","result","attendance"});
  
        // Column alignment
        table.setColumnAlignment("Name",
                Table.ALIGN_CENTER);

        // Column width
        table.setColumnExpandRatio("Name", 1);
        table.setColumnWidth("Name", 270);

      

        // show row header w/ icon
        table.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
     
        // style generator
        table.setCellStyleGenerator(new CellStyleGenerator() {
            public String getStyle(Object itemId, Object propertyId) {
                if (propertyId == null) {
                    // no propertyId, styling row
                    return (markedRows.contains(itemId) ? "marked" : null);
                } else if ("Name".equals(propertyId)) {
                    return "bold";
                } else {
                    // no style
                    return null;
                }
            }

            
        });
        
        // listen for valueChange, a.k.a 'select'
        table.addListener(new ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    table.select(event.getItemId());
               
	            	BeanItem<GameTableWrapper> gtw = (BeanItem<GameTableWrapper>) event.getItem();
	                
	            	Game g = GameView.this.dao.getGame(gtw.getBean().getId());
	            	                      	
	               GameEditor ge = new GameEditor(g, GameView.this.dao, ts);
	               getApplication().getMainWindow().addWindow(ge);
	            }
            }
        });
    }

	public void refresh() {
		if(tournamentComboBox != null)
			tournamentComboBox.setContainerDataSource(new ComboBoxFactory(dao).getTournamentDataSource());
		if(seasonComboBox != null)
			seasonComboBox.setVisible(false);
	}

}
