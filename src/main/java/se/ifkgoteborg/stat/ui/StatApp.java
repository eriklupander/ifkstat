package se.ifkgoteborg.stat.ui;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import se.ifkgoteborg.stat.controller.RegistrationDAO;

import com.vaadin.Application;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
@SessionScoped
public class StatApp extends Application {
	
	@Inject
    RegistrationDAO dao;
	
	private static final ThemeResource icon1 = new ThemeResource(
            "../sampler/icons/action_save.gif");
    private static final ThemeResource icon2 = new ThemeResource(
            "../sampler/icons/comment_yellow.gif");
    private static final ThemeResource icon3 = new ThemeResource(
            "../sampler/icons/icon_info.gif");
    
    PlayerTable playerTableView = null;    
    GameTable gameTableView = null;
	
	public void init() { 
        Window main = new Window("IFK-statistik"); 
        setMainWindow(main);
                
        playerTableView = new PlayerTable(dao);
        gameTableView = new GameTable(dao);
        
        VerticalLayout l1 = new VerticalLayout();
        l1.setMargin(true);
        l1.addComponent(playerTableView);
        // Tab 2 content
        VerticalLayout l2 = new VerticalLayout();
        l2.setMargin(true);
        l2.addComponent(gameTableView);
        // Tab 3 content
        VerticalLayout l3 = new VerticalLayout();
        l3.setMargin(true);
        l3.addComponent(new ImportView(dao));
        

        TabSheet t = new TabSheet();
        t.setHeight("100%");
        t.setWidth("100%");
        t.addTab(l1, "Spelare", icon1);
        t.addTab(l2, "Matcher", icon2);
        t.addTab(l3, "Import", icon3);
      
        t.addListener(new SelectedTabChangeListener()  {
			
        	public void selectedTabChange(SelectedTabChangeEvent event) {
                TabSheet tabsheet = event.getTabSheet();
                Tab tab = tabsheet.getTab(tabsheet.getSelectedTab());
                if (tab != null) {
                    getMainWindow().showNotification("Selected tab: " + tab.getCaption());
                    
                    if(tab.getCaption().equals("Spelare")) {
                    	playerTableView.loadPlayers();
                    }
                    if(tab.getCaption().equals("Matcher")) {
                    	gameTableView.refresh();
                    }
                }
            }
		});
        
        main.addComponent(t); 
    }
}
