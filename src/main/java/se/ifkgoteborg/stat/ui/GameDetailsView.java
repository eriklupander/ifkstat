package se.ifkgoteborg.stat.ui;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.GameEvent;
import se.ifkgoteborg.stat.model.GameParticipation;
import se.ifkgoteborg.stat.ui.form.GameForm;
import sun.awt.HorizBagLayout;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Runo;

public class GameDetailsView extends VerticalLayout {	

	private final Game game;
	private final RegistrationDAO dao;
	
	private GameForm gameDetailsForm;
	
	public GameDetailsView(Game game, RegistrationDAO dao) {
		this.game = game;		
		this.dao = dao;
		this.setStyleName(Runo.PANEL_LIGHT);
		
		addComponent(new Label(""));
		TabSheet t = new TabSheet();
        t.setHeight("100%");
        t.setWidth("100%");
        gameDetailsForm = getGameDetailsForm();
        t.addTab(gameDetailsForm, "Matchfakta");
        t.addTab(getGamePlayersList(), "Laguppställning");
        t.addTab(getEventsList(), "Händelser");
        addComponent(t);
	}

	private VerticalLayout getGamePlayersList() {
		
		VerticalLayout vl = new VerticalLayout();
		
		
		
		final BeanItemContainer<GameParticipation> bic = new BeanItemContainer<GameParticipation>(GameParticipation.class);
		bic.addNestedContainerProperty("player.squadNumber");
		bic.addNestedContainerProperty("player.name");
		bic.addNestedContainerProperty("formationPosition.position.name");
		bic.addNestedContainerProperty("participationType.name");
		
		for(GameParticipation gp : game.getGameParticipation()) {
			bic.addBean(gp);
		}
		
		final Table t = new Table();
		t.setContainerDataSource(bic);
		t.setColumnHeader("player.squadNumber", "#");
		t.setColumnHeader("player.name", "Spelare");
        t.setColumnHeader("formationPosition.position.name", "Position");
        t.setColumnHeader("participationType.name", "Notering");
        t.setSelectable(true);
        t.setEditable(true);
        t.setVisibleColumns(new String[]{"player.squadNumber", "player.name","formationPosition.position.name", "participationType.name"});
        
        Button addBtn = new Button("Lägg till spelare");
		Button removeBtn = new Button("Ta bort vald spelare");
		
		addBtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
            	bic.addBean(new GameParticipation());
            }
		});
		
		removeBtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
            	bic.removeItem(t.getValue());
            }
		});
        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(addBtn);
        hl.addComponent(removeBtn);
        vl.addComponent(hl);
        vl.addComponent(t);
		return vl;
	}
	
	

	private Table getEventsList() {
		BeanItemContainer<GameEvent> bic = new BeanItemContainer<GameEvent>(GameEvent.class);
		bic.addNestedContainerProperty("player.name");
				
		for(GameEvent ge : game.getEvents()) {
			bic.addBean(ge);
		}
		
		Table t = new Table();
		t.setContainerDataSource(bic);
		t.setColumnHeader("player.name", "Spelare");
		t.setColumnHeader("eventType", "Händelse");
		t.setColumnHeader("gameMinute", "Minut");
		
		t.setVisibleColumns(new String[]{"player.name","eventType","gameMinute"});
		return t;
	}
	
	private GameForm getGameDetailsForm() {
		
		
		BeanItem<Game> item = new BeanItem<Game>(game);
		GameForm form = new GameForm(dao, item);
		
		 // Set form caption and description texts 
		if(game.getHomeTeam() != null && game.getAwayTeam() != null) {
			form.setCaption(game.getHomeTeam().getName() + " - " + game.getAwayTeam().getName() + " " + game.getResultStr());
		} else {
			form.setCaption("Ny match");
		}
		
		return form;
	}

	public GameForm getForm() {
		return gameDetailsForm;
	}
	
	
}
