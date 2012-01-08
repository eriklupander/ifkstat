package se.ifkgoteborg.stat.ui;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.GameEvent;
import se.ifkgoteborg.stat.model.GameParticipation;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

public class GameDetailsView extends VerticalLayout {	

	private final Game game;
	private final RegistrationDAO dao;

	public GameDetailsView(Game game, RegistrationDAO dao) {
		this.game = game;		
		this.dao = dao;
		this.setStyleName(Runo.PANEL_LIGHT);
		
		addComponent(new Label());
		TabSheet t = new TabSheet();
        t.setHeight("100%");
        t.setWidth("100%");
        t.addTab(getGameDetailsForm(), "Matchfakta");
        t.addTab(getGamePlayersList(), "Laguppställning");
        t.addTab(getEventsList(), "Händelser");
        addComponent(t);
	}

	private Table getGamePlayersList() {
		
		BeanItemContainer<GameParticipation> bic = new BeanItemContainer<GameParticipation>(GameParticipation.class);
		bic.addNestedContainerProperty("player.squadNumber");
		bic.addNestedContainerProperty("player.name");
		bic.addNestedContainerProperty("formationPosition.position.name");
		bic.addNestedContainerProperty("participationType.name");
		
		for(GameParticipation gp : game.getGameParticipation()) {
			bic.addBean(gp);
		}
		
		Table t = new Table();
		t.setContainerDataSource(bic);
		t.setColumnHeader("player.squadNumber", "#");
		t.setColumnHeader("player.name", "Spelare");
        t.setColumnHeader("formationPosition.position.name", "Position");
        t.setColumnHeader("participationType.name", "");

        t.setVisibleColumns(new String[]{"player.squadNumber", "player.name","formationPosition.position.name", "participationType.name"});
		return t;
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
		t.setVisibleColumns(new String[]{"player.name","eventType"});
		return t;
	}

	private Form getGameDetailsForm() {
		final Form form = new Form();
		
		 // Set form caption and description texts 
		form.setCaption(game.getHomeTeam().getName() + " - " + game.getAwayTeam().getName() + " " + game.getResultStr());
		form.setSizeFull();
		form.getLayout().setMargin(true);
		form.setImmediate(true); 
		 // Create a bean item that is bound to the bean. 
		 BeanItem item = new BeanItem(game);
		 
		 // Bind the bean item as the data source for the form. 
		form.setItemDataSource(item);
		
		return form;
	}
}
