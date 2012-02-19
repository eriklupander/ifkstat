package se.ifkgoteborg.stat.ui.form;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.SquadSeason;
import se.ifkgoteborg.stat.model.Tournament;
import se.ifkgoteborg.stat.model.TournamentSeason;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Select;
import com.vaadin.ui.VerticalLayout;

public class GameSeasonForm extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	
	Collection<String> fields = new HashSet<String>();
	private RegistrationDAO dao;
	Select tournamentSelect;
	Select seasonSelect;
	
	public GameSeasonForm(RegistrationDAO dao, BeanItem<Game> gameItem) {
		this.dao = dao;
		init(gameItem);
	}

	
	private void init(BeanItem<Game> gameItem) {
		
		HorizontalLayout hl = new HorizontalLayout();
		tournamentSelect = new Select("Turnering");		
		seasonSelect = new Select("Säsong");
		List<Tournament> tournaments = dao.getTournaments();
		
	    for (int i = 0; i < tournaments.size(); i++) {
	    	tournamentSelect.addItem(tournaments.get(i));
	    }
	
	    tournamentSelect.setFilteringMode(Filtering.FILTERINGMODE_OFF);
	    tournamentSelect.setImmediate(true);
	    tournamentSelect.setNewItemsAllowed(true);
	    tournamentSelect.setNewItemHandler(new NewItemHandler() {

			@Override
			public void addNewItem(String newItemCaption) {
				Tournament t = dao.createTournament(newItemCaption);
				tournamentSelect.addItem(t);
			}		    	
	    });
	    
	    if(gameItem.getBean().getTournamentSeason() != null) {
	    	tournamentSelect.setValue(gameItem.getBean().getTournamentSeason().getTournament());
	    }
	    
	    tournamentSelect.addListener(new ValueChangeListener() {

			@Override
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				Tournament t = (Tournament) event.getProperty().getValue();
				List<TournamentSeason> tournamentSeasons = dao.getTournamentSeasons(t.getId());
				seasonSelect.removeAllItems();
				for(TournamentSeason ts : tournamentSeasons) {
					seasonSelect.addItem(ts.getSeason());
				}
			}	    	
	    });
	    
	    
	    if(gameItem.getBean().getTournamentSeason() != null && gameItem.getBean().getTournamentSeason().getTournament() != null) {
	    	loadTournamentSeasons(gameItem);
	    	
	    	seasonSelect.setValue(gameItem.getBean().getTournamentSeason());
	    }
	    seasonSelect.setImmediate(true);
	    seasonSelect.setNewItemsAllowed(false);
		
		hl.addComponent(tournamentSelect);
		hl.addComponent(seasonSelect);
		addComponent(hl);
	}


	private void loadTournamentSeasons(BeanItem<Game> gameItem) {
//		List<TournamentSeason> tournamentSeasons = dao.getTournamentSeasons(gameItem.getBean().getTournamentSeason().getTournament().getId());
//		seasonSelect.removeAllItems();
//		for(TournamentSeason ts : tournamentSeasons) {
//			seasonSelect.addItem(ts);
//		}
		List<SquadSeason> squadSeasons = dao.getSeasons();
		seasonSelect.removeAllItems();
		for(SquadSeason ss : squadSeasons) {
			seasonSelect.addItem(ss);
		}
	}
	

	
	
}
