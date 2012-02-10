package se.ifkgoteborg.stat.ui;

import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Ground;
import se.ifkgoteborg.stat.model.Player;
import se.ifkgoteborg.stat.ui.editor.ArenaEditor;
import se.ifkgoteborg.stat.ui.editor.BaseEditor.EditorSavedEvent;
import se.ifkgoteborg.stat.ui.editor.BaseEditor.EditorSavedListener;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ArenaView extends VerticalLayout {
	BeanItemContainer<Ground> bic = new BeanItemContainer<Ground>(Ground.class);	

    Table table = new Table("Arenor");
    private static final String[] fields = new String[]{"name", "city", "maxCapacity"};

	private RegistrationDAO dao;
    
    public ArenaView(RegistrationDAO dao) {
    	this.dao = dao;
    	
    	addComponent(table);
    	loadArenas();
    	table.setSelectable(true);
    	table.setContainerDataSource(bic);
    	//bic.addNestedContainerProperty("country.name");
    	
    	table.setColumnHeader("name", "Namn");
        table.setColumnHeader("city", "Stad");
       // table.setColumnHeader("country.name", "Land");
        table.setColumnHeader("maxCapacity", "Publikkapacitet");
    	table.setVisibleColumns(fields);
                
        
        // turn on column reordering and collapsing
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        
        table.addListener(new ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    table.select(event.getItemId());
                    
                    ArenaEditor arenaEditor = new ArenaEditor(ArenaView.this.dao, (BeanItem) event.getItem());
                    arenaEditor.addListener(new EditorSavedListener() {
                        @Override
                        public void editorSaved(EditorSavedEvent event) {
                            //bic.addBean(newPersonItem.getBean());
                        	ArenaView.this.dao.updateGround((Ground) ((BeanItem) event.getSavedItem()).getBean());
                        }
                    });
                    getApplication().getMainWindow().addWindow(arenaEditor);
                }
            }
        });
    }

	private void loadArenas() {
		List<Ground> grounds = dao.getGrounds();
		bic.addAll(grounds);
	}

	public void refresh() {
		bic.removeAllItems();
		loadArenas();
		
	}
}
