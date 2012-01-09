package se.ifkgoteborg.stat.ui;

import java.util.HashSet;
import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.Tournament;
import se.ifkgoteborg.stat.model.TournamentSeason;
import se.ifkgoteborg.stat.ui.control.ComboBoxFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.VerticalLayout;

public class GameTable extends VerticalLayout {
	
	BeanItemContainer<GameTableWrapper> beans =
		    new BeanItemContainer<GameTableWrapper>(GameTableWrapper.class);

    Table table = new Table("");
    //Panel panel = null;
    GameDetailsView gameDetailsView = null;
    HashSet<Object> markedRows = new HashSet<Object>();
    
    RegistrationDAO dao;
    HorizontalLayout hl = new HorizontalLayout();
    ComboBox seasonComboBox;

    public GameTable(RegistrationDAO dao) {
    	
    	this.dao = dao;    	
    	
    	final ComboBox tournamentComboBox = new ComboBoxFactory(dao).getTournamentComboBox();
    	 
    	
    	tournamentComboBox.addListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				String tournamentName = (String) event.getProperty().getValue();
				final Tournament t = GameTable.this.dao.getOrCreateTournamentByName(tournamentName);
				System.out.println("Loaded tournament " + t.getName());
				if(hl.getComponentCount() > 1) {
					hl.removeComponent(hl.getComponent(1));
				}
				seasonComboBox = new ComboBoxFactory(GameTable.this.dao).getSeasonComboBox(t.getId());
				seasonComboBox.addListener(new ValueChangeListener() {

					@Override
					public void valueChange(ValueChangeEvent event) {
						List<Game> games = GameTable.this.dao.getGames(t.getId(), (Integer) event.getProperty().getValue());
						
						// Populate game list...
						beans.removeAllItems();

						for (Game g : games) {
				           beans.addBean(new GameTableWrapper(g));
				        }
						
						if(getComponentCount() < 2) {
							addComponent(table, 1);
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
        table.setHeight("170px");

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
        table.addListener(new Table.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
               
            	GameTableWrapper gtw = (GameTableWrapper) event.getProperty().getValue();
                
            	Game g = GameTable.this.dao.getGame(gtw.getId());
            	
               if(gameDetailsView != null) {
            	   GameTable.this.removeComponent(gameDetailsView);
               }            	
            	gameDetailsView = new GameDetailsView(g, GameTable.this.dao);
            	addComponent(gameDetailsView);
            }
        });
    }

	public void refresh() {
		
	}

}
