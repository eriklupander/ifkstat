package se.ifkgoteborg.stat.ui;

import java.util.HashSet;

import javax.persistence.EntityManager;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Player;
import se.ifkgoteborg.stat.ui.editor.PlayerEditor;
import se.ifkgoteborg.stat.ui.editor.PlayerEditor.EditorSavedEvent;
import se.ifkgoteborg.stat.ui.editor.PlayerEditor.EditorSavedListener;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.provider.MutableLocalEntityProvider;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PlayerTable extends VerticalLayout {
	
	JPAContainer<Player> players;	

    Table table = new Table("ISO-3166 Country Codes and flags");

    HashSet<Object> markedRows = new HashSet<Object>();
    
    RegistrationDAO dao;

    private Button newButton;
    private Button deleteButton;
    private Button editButton; 
    private TextField searchField;
    
    private String textFilter;

	private EntityManager em;
    
    

    public PlayerTable(RegistrationDAO dao, EntityManager em) {
    	
    	this.dao = dao;
		this.em = em;
    	
    	players = new JPAContainer(Player.class); 
    	MutableLocalEntityProvider<Player> ep = new MutableLocalEntityProvider<Player>(Player.class, em);
    	ep.setEntitiesDetached(false);
    	players.setEntityProvider(ep);
    	
    	addComponent(createToolbar());
        addComponent(table);

        // Label to indicate current selection
        final Label selected = new Label("No selection");
        addComponent(selected);

        // set a style name, so we can style rows and cells
        table.setStyleName("iso3166");

        // size
        table.setWidth("100%");
        table.setHeight("170px");

        // selectable
        table.setSelectable(true);
        table.setMultiSelect(true);
        table.setImmediate(true); // react at once when something is selected

        // connect data source
        //table.setContainerDataSource(ExampleUtil.getISO3166Container());      
        
        loadPlayers();
        table.setContainerDataSource(players);
        
        // turn on column reordering and collapsing
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);

        // set column headers
        //table.setColumnHeaders(new String[] { "#", "Name", "Full name", "Country", "Height", "Weight" });

        // Icons for column headers
//        table.setColumnIcon(ExampleUtil.iso3166_PROPERTY_FLAG,
//                new ThemeResource("../sampler/icons/action_save.gif"));
//        table.setColumnIcon(ExampleUtil.iso3166_PROPERTY_NAME,
//                new ThemeResource("../sampler/icons/icon_get_world.gif"));
//        table.setColumnIcon(ExampleUtil.iso3166_PROPERTY_SHORT,
//                new ThemeResource("../sampler/icons/page_code.gif"));

        // Column alignment
        table.setColumnAlignment("Name",
                Table.ALIGN_CENTER);

        // Column width
        table.setColumnExpandRatio("Name", 1);
        table.setColumnWidth("Name", 270);

        // Collapse one column - the user can make it visible again
        //table.setColumnCollapsed(ExampleUtil.iso3166_PROPERTY_FLAG, true);

        // show row header w/ icon
        table.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
       // table.setItemIconPropertyId(ExampleUtil.iso3166_PROPERTY_FLAG);

       

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

        // listen for valueChange, a.k.a 'select' and update the label
//        table.addListener(new Table.ValueChangeListener() {
//            public void valueChange(ValueChangeEvent event) {
//                // in multiselect mode, a Set of itemIds is returned,
//                // in singleselect mode the itemId is returned directly
//                Set<?> value = (Set<?>) event.getProperty().getValue();
//                if (null == value || value.size() == 0) {
//                    selected.setValue("No selection");
//                } else {
//                    selected.setValue("Selected: " + table.getValue());
//                }
//            }
//        });
        table.addListener(new ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    table.select(event.getItemId());
                }
            }
        });
    }
    
    private static final String[] fields = new String[]{"squadNumber", "name", "fullName", "country", "height", "weight"};

	public void loadPlayers() {
//		List<Player> players = dao.getAllPlayers();
//		
//		ic.removeAllItems();
//		
//		for (String p : fields) {
//            ic.addContainerProperty(p, String.class, "");
//        }
//
//		for (Player p : players) {
//            try {
//				Object id = ic.addItem();
//				ic.getContainerProperty(id, "squadNumber").setValue("#" + (p.getSquadNumber()  != null ? p.getSquadNumber() : ""));
//				ic.getContainerProperty(id, "name").setValue(p.getName());
//				ic.getContainerProperty(id, "fullName").setValue(p.getFullName());
//				ic.getContainerProperty(id, "country").setValue(p.getNationality().getName());
//				ic.getContainerProperty(id, "height").setValue(p.getLength() + " cm");
//				ic.getContainerProperty(id, "weight").setValue(p.getWeight() + " kg");
//			} catch (Exception e) {
//				System.out.println("Error reading player: " + e.getMessage());
//			}
//        }
//		
	}


	private Component createToolbar() {
		HorizontalLayout toolbar = new HorizontalLayout();
        newButton = new Button("Add");
        newButton.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                final BeanItem<Player> newPersonItem = new BeanItem<Player>(
                        new Player());
                PlayerEditor personEditor = new PlayerEditor(newPersonItem);
                personEditor.addListener(new EditorSavedListener() {
                    @Override
                    public void editorSaved(EditorSavedEvent event) {
                        players.addEntity(newPersonItem.getBean());
                    }
                });
                getApplication().getMainWindow().addWindow(personEditor);
            }
        });

        deleteButton = new Button("Delete");
        deleteButton.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                players.removeItem(table.getValue());
            }
        });
        deleteButton.setEnabled(false);

        editButton = new Button("Edit");
        editButton.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                getApplication().getMainWindow().addWindow(
                        new PlayerEditor(table.getItem(table
                                .getValue())));
            }
        });
        editButton.setEnabled(false);

        searchField = new TextField();
        searchField.setInputPrompt("Search by name");
        searchField.addListener(new TextChangeListener() {

            @Override
            public void textChange(TextChangeEvent event) {
                textFilter = event.getText();
                updateFilters();
            }
        });

        toolbar.addComponent(newButton);
        toolbar.addComponent(deleteButton);
        toolbar.addComponent(editButton);
        toolbar.addComponent(searchField);
        toolbar.setWidth("100%");
        toolbar.setExpandRatio(searchField, 1);
        toolbar.setComponentAlignment(searchField, Alignment.TOP_RIGHT);
        
        return toolbar;
	}
	
	private void updateFilters() {
        players.setApplyFiltersImmediately(false);
        players.removeAllContainerFilters();
       
        if (textFilter != null && !textFilter.equals("")) {
            Or or = new Or(new Like("name", textFilter + "%", false),
                    new Like("fullName", textFilter + "%", false));
            players.addContainerFilter(or);
        }
        players.applyFilters();
    }
}
