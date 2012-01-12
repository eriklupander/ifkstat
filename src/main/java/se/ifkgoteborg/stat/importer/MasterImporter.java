package se.ifkgoteborg.stat.importer;

import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.controller.adapter.SquadPlayer;
import se.ifkgoteborg.stat.util.StringUtil;

public class MasterImporter {
	
	private final RegistrationDAO dao;

	public MasterImporter(RegistrationDAO dao) {
		this.dao = dao;		
	}

	public void importMasterFile(String data) {
		
		// Split per season:
		String[] seasons = data.split("\\$\\$\\$\\$");
		System.out.println("Number of seasons: " + seasons.length);
		for(String seasonData : seasons) {
			//System.out.println("Season data:\n" + season);
			if(seasonData.trim().length() > 0) {
				parseSeason(seasonData);
			}
		}
		
		
	}

	private void parseSeason(String data) {
		String season = null;
		try {
			season = StringUtil.getLines(data, 0, 1).trim();
			season = season.replaceAll(" ", "/").trim();
		} catch (NumberFormatException e) {
			System.err.println("Error parsing " + StringUtil.getLines(data, 0, 1) + " into number");
			throw e;
		}
        String playerData = StringUtil.getLines(data, 1, 3);
        
        List<SquadPlayer> players = new PlayerImporter().importPlayers(playerData);
        
        dao.importPlayers(players, season);
     
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
        	new GameImporter(dao).importTournamentSeason(tournamentData, tournamentSeason, dao.loadSquad(season), tournamentName);          
        }
	}

}
