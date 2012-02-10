package se.ifkgoteborg.stat.ui;


import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import se.ifkgoteborg.stat.controller.RegistrationDAO;

import com.vaadin.Application;
import com.vaadin.terminal.ThemeResource;
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
    private static final ThemeResource icon4 = new ThemeResource(
            "../sampler/icons/icon_info.gif");
        
   
    PlayerView playerTableView = null;    
    GameView gameTableView = null;
    SeasonView seasonView = null;
    ArenaView arenaView = null;
	
	public void init() { 
        Window main = new Window("IFK-statistik"); 
        setMainWindow(main);
                
        playerTableView = new PlayerView(dao);
        gameTableView = new GameView(dao);
        
        VerticalLayout playerTab = new VerticalLayout();
        playerTab.setMargin(true);
        playerTab.addComponent(playerTableView);
        // Tab 2 content
        VerticalLayout gamesTab = new VerticalLayout();
        gamesTab.setMargin(true);
        gamesTab.addComponent(gameTableView);        
        // Tab 3 content
        VerticalLayout importTab = new VerticalLayout();
        importTab.setMargin(true);
        importTab.addComponent(new ImportView(dao));
        // Season content
        seasonView = new SeasonView(dao);
        VerticalLayout seasonTab = new VerticalLayout();
        seasonTab.setMargin(true);
        seasonTab.addComponent(seasonView);
        
        // Arena content
        VerticalLayout arenaTab = new VerticalLayout();
        arenaTab.setMargin(true);
        arenaView = new ArenaView(dao);
        arenaTab.addComponent(arenaView);
        

        TabSheet t = new TabSheet();
        t.setHeight("100%");
        t.setWidth("100%");
        t.addTab(playerTab, "Spelare", icon1);
        t.addTab(gamesTab, "Matcher", icon2);
        t.addTab(seasonTab, "Säsonger", icon4);
        t.addTab(arenaTab, "Arenor", icon3);
        t.addTab(importTab, "Import", icon3);
      
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
                    if(tab.getCaption().equals("Säsonger")) {
                    	seasonView.refresh();
                    }
                    if(tab.getCaption().equals("Arenor")) {
                    	arenaView.refresh();
                    }
                }
            }
		});
      
        main.addComponent(t); 
    }
}
