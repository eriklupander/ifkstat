package se.ifkgoteborg.stat.ui.control;

import java.util.Calendar;
import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Tournament;
import se.ifkgoteborg.stat.model.TournamentSeason;

import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;

public class ComboBoxFactory {
	
	private final RegistrationDAO dao;


	public ComboBoxFactory(RegistrationDAO dao) {
		this.dao = dao;		
	}
	
	
	public ComboBox getTournamentComboBox() {
		ComboBox l = new ComboBox("Välj turnering");
		
		List<Tournament> tournaments = dao.getTournaments();
		
	    for (int i = 0; i < tournaments.size(); i++) {
	        l.addItem(tournaments.get(i).getName());
	    }
	
	    l.setFilteringMode(Filtering.FILTERINGMODE_OFF);
	    l.setImmediate(true);	    
	    
	    return l;
	}


	public ComboBox getSeasonComboBox() {
		ComboBox l = new ComboBox("Välj säsong");
		Calendar c = Calendar.getInstance();
		
		for (int i = 1904; i < c.get(Calendar.YEAR); i++) {
            l.addItem(new String("" + i));
        }
		
		l.setFilteringMode(Filtering.FILTERINGMODE_OFF);
        l.setImmediate(true);
        
        return l;
	}
	
	public ComboBox getSeasonComboBox(Long tournamentId) {
		ComboBox l = new ComboBox("Välj säsong");
		
		List<TournamentSeason> tournamentSeasons = dao.getTournamentSeasons(tournamentId);
		
		for (TournamentSeason ts : tournamentSeasons) {
            l.addItem(ts.getStart().get(Calendar.YEAR));
        }
		
		l.setFilteringMode(Filtering.FILTERINGMODE_OFF);
        l.setImmediate(true);
        
        return l;
	}
}
