package se.ifkgoteborg.stat.importer.ejb;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.controller.adapter.SquadPlayer;
import se.ifkgoteborg.stat.importer.GameImporter;
import se.ifkgoteborg.stat.importer.PlayerImporter;
import se.ifkgoteborg.stat.model.SquadSeason;
import se.ifkgoteborg.stat.util.StringUtil;

@Stateless
public class SeasonImporterBean implements SeasonImporter {

	@Inject
	RegistrationDAO dao;
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void importSeason(String playerData, String season, String data) {
		List<SquadPlayer> players = new PlayerImporter().importPlayers(playerData);
        
        SquadSeason squadSeason = dao.importPlayers(players, season);
     
        String gamesData = StringUtil.getLines(data, 3);
        
        // Split by ####
        String[] tournaments = gamesData.split("####");
        for(String dataSet : tournaments) {
        	if(dataSet.trim().length() == 0) {
        		continue;
        	}
        	// First row tournament name
        	String tournamentNameAndStartingYear = StringUtil.getLines(dataSet, 0, 1);
        	
        	
        	String [] parts = tournamentNameAndStartingYear.split("\t");
        	String tournamentName = parts[0];
        	
        	// Clean everything after digits from tournament name.
        	tournamentName = cleanTournamentName(tournamentName);
        	
        	String tournamentSeason = null;
        	if(parts.length == 1) {
        		tournamentSeason = "" + season;
        	} else {
        		if(parts[1].indexOf("(") > -1) {
        			tournamentSeason = parts[1].substring(0, parts[1].indexOf("("));
        		} else {
        			tournamentSeason = parts[1];
        		}
        	}
        	 
        	String tournamentData = StringUtil.getLines(dataSet, 1);
        	new GameImporter(dao).importTournamentSeason(tournamentData, tournamentSeason.trim(), dao.loadSquad(season), tournamentName.trim(), squadSeason);          
        }

	}
	
	
	String cleanTournamentName(String tn) {
		try {
			Pattern p = Pattern.compile("[\\d]{2}");
			Matcher matcher = p.matcher(tn);
			if(matcher.find()) {
				int index = matcher.start();
				return tn.substring(0, index - 1);
			}
			return tn;
		} catch (Exception e) {
			System.err.println("Error parsing tournament name: " + tn + ". Message: " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

}
