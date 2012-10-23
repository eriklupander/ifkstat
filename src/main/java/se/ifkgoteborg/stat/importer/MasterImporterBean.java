package se.ifkgoteborg.stat.importer;

import javax.ejb.Stateless;
import javax.inject.Inject;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.importer.ejb.SeasonImporter;
import se.ifkgoteborg.stat.util.StringUtil;

@Stateless
public class MasterImporterBean implements MasterImporter {
	
	@Inject
	SeasonImporter seasonImporter;
	
	@Override
	public void importMasterFile(String data) {
		
		long start = System.currentTimeMillis();
		
		// Split per season:
		String[] seasons = data.split("\\$\\$\\$\\$");
		System.out.println("Number of seasons: " + seasons.length);
		for(String seasonData : seasons) {
			//System.out.println("Season data:\n" + season);
			if(seasonData.trim().length() > 0) {
				parseSeason(seasonData);
			}
		}
		
		System.out.println("Import finished in " + (System.currentTimeMillis() - start) + " ms.");
		System.out.println("Goals imported: " + GameImporter.goalsImported);
		System.out.println("Subst in imported: " + GameImporter.subInImported);
		System.out.println("Subst out imported: " + GameImporter.subOutImported);
	}

	
	public void parseSeason(String data) {
		String season = null;
		try {
			season = StringUtil.getLines(data, 0, 1).trim();
			season = season.replaceAll(" ", "/").trim();
		} catch (NumberFormatException e) {
			System.err.println("Error parsing " + StringUtil.getLines(data, 0, 1) + " into number");
			throw e;
		}
        String playerData = StringUtil.getLines(data, 1, 3);
        
        seasonImporter.importSeason(playerData, season, data);
        
//        
//        List<SquadPlayer> players = new PlayerImporter().importPlayers(playerData);
//        
//        SquadSeason squadSeason = dao.importPlayers(players, season);
//     
//        String gamesData = StringUtil.getLines(data, 3);
//        
//        // Split by ####
//        String[] tournaments = gamesData.split("####");
//        for(String dataSet : tournaments) {
//        	if(dataSet.trim().length() == 0) {
//        		continue;
//        	}
//        	// First row tournament name
//        	String tournamentNameAndStartingYear = StringUtil.getLines(dataSet, 0, 1);
//        	
//        	
//        	String [] parts = tournamentNameAndStartingYear.split("\t");
//        	String tournamentName = parts[0];
//        	
//        	// Clean everything after digits from tournament name.
//        	tournamentName = cleanTournamentName(tournamentName);
//        	
//        	String tournamentSeason = null;
//        	if(parts.length == 1) {
//        		tournamentSeason = "" + season;
//        	} else {
//        		if(parts[1].indexOf("(") > -1) {
//        			tournamentSeason = parts[1].substring(0, parts[1].indexOf("("));
//        		} else {
//        			tournamentSeason = parts[1];
//        		}
//        	}
//        	 
//        	String tournamentData = StringUtil.getLines(dataSet, 1);
//        	new GameImporter(dao).importTournamentSeason(tournamentData, tournamentSeason.trim(), dao.loadSquad(season), tournamentName.trim(), squadSeason);          
//        }
        
        
	}

	

}
