package se.ifkgoteborg.stat.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import se.ifkgoteborg.stat.model.Club;
import se.ifkgoteborg.stat.model.Formation;
import se.ifkgoteborg.stat.model.FormationPosition;
import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.GameEvent;
import se.ifkgoteborg.stat.model.GameEvent.EventType;
import se.ifkgoteborg.stat.model.GameParticipation;
import se.ifkgoteborg.stat.model.Player;
import se.ifkgoteborg.stat.model.TournamentSeason;
import se.ifkgoteborg.stat.model.enums.ParticipationType;
import se.ifkgoteborg.stat.util.StringUtil;



public class GameImporter {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/dd/MM");

	private static final SimpleDateFormat sdf2 = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static final String GOAL_TOKEN = "•";
	private static final String SUBST_TOKEN = "[";
	private static final String SUBST_TOKEN_ALT = "(";

	private static final int OFFSET = 8;

	RegistrationDAO dao;

	public GameImporter(RegistrationDAO dao) {
		this.dao = dao;
	}

	/**
	 * 
	 * @param data
	 * @param year
	 * @param players
	 *            Integer is INDEX during this season.
	 * @param tournamentName 
	 */
	public void importSeason(String data, int year, Map<Integer, Player> players, String tournamentName) {
		String[] games = data.split("\n");
		TournamentSeason ts = dao.getTournamentSeasonByName(tournamentName, year);
		for (String game : games) {
			importGame(game, year, players, ts);
		}
	}

	private void importGame(String dataRow, int year,
			Map<Integer, Player> players, TournamentSeason ts) {
		String[] cells = dataRow.split("\t");

		Game g = new Game();
		g.setTournamentSeason(ts);
		ts.getGames().add(g);
		
		Club opponentClub = null;
		boolean homegame = true;

		List<Integer> substitutedPositions = new ArrayList();
		
		for (int a = 0; a < cells.length; a++) {
			System.out.println("INDEX: " + a + " DATA: " + cells[a]);
			switch (a) {

			// Date
			case 0:
				try {
					Date parsedDate = sdf.parse(year + "/" + cells[a].trim());
					Calendar c = new GregorianCalendar();
					c.setTime(parsedDate);
					g.setDateOfGame(c);
					System.out.println(sdf2.format(parsedDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;

			// Opponent
			case 1:
				// Create opponent club if not exists
				opponentClub = dao.getOrCreateClub(cells[a]);
				break;

			// Home or Away (H/B)
			case 2:
				homegame = "H".equals(cells[a].trim());
				break;
			// Arena / ground
			case 3:
				g.setGround(dao.getOrCreateGround(cells[a]));
				break;

			// Result
			case 4:
				String result = cells[a];
				if(result == null || result.trim().length() == 0) {
					break;
				}
				System.out.println("Result text: " + result);
				result = result.replaceAll("[^\\d]", " ");
				String[] parts = result.split(" ");
				g.setHomeGoals(Integer.parseInt(parts[0]));
				g.setAwayGoals(Integer.parseInt(parts[1]));
				break;
			// Halftime result
			case 5:
				String htResult = cells[a];
				if(htResult == null || htResult.trim().length() == 0) {
					break;
				}
				htResult = htResult.replaceAll("[^\\d]", " ");
				String[] htParts = htResult.trim().split(" ");
				g.setHomeGoalsHalftime(htParts[0] != null ? Integer.parseInt(htParts[0].replaceAll("[^\\d]",
						"")) : 0);
				g.setAwayGoalsHalftime(htParts[1] != null ?Integer.parseInt(htParts[1].replaceAll("[^\\d]",
						"")) : 0);
				break;
			// attendance
			case 6:
				g.setAttendance(Integer.parseInt(cells[a].replaceAll("[^\\d]",
						"")));
				break;

			// default team formation
			case 7:
				String formation = cells[a].trim();
				Formation f = dao.getFormationByName(formation);				
				g.setFormation(f);
				//f.getUsedInGames().add(g);
				
				break;

			// The rest are always player position in game
			default:
				// If empty, player at index (a) did not participate in game
				if (cells[a] == null || cells[a].trim().length() == 0) {
					break;
				}
				// OK, player did participate. Get player of current index,
				// start building GameParticipation list.
				String data = cells[a].trim();
				
				int positionId = Integer.parseInt(data.replaceAll("[^\\d]", ""));
				int goalsScored = 0;
				
				// Some years use a "raised" integer after the number instead of the * to mark goals.
				if(positionId > 11) {
					String s = new String("" + positionId);
					goalsScored = Integer.parseInt(s.substring(s.length() - 1));
					
					
					positionId = Integer.parseInt(s.substring(0, s.length() - 1));
				}

				Player player = players.get(new Integer(a - OFFSET));

				// Add a participation
				GameParticipation gp = new GameParticipation();
				gp.setPlayer(player);
				gp.setGame(g);
				gp.setPositionId(positionId);
				//gp.setPlayerNumber(player.getSquadNumber());
				gp.setFormationPosition(getFormationPosition("4-4-2", positionId));
				g.getGameParticipation().add(gp);
				
				// Check for special characters
				if (data.indexOf(GOAL_TOKEN) > -1) {
					// How many?
					int numberOfGoals = StringUtil.countOccurrences(data,
							GOAL_TOKEN);
					for (int i = 0; i < numberOfGoals; i++) {
						// Add a GameEvent instance for each goal.
						GameEvent event = new GameEvent();
						event.setEventType(EventType.GOAL);
						event.setPlayer(player);
						g.getEvents().add(event);
					}
				} else if(goalsScored > 0) {
					for (int i = 0; i < goalsScored; i++) {
						// Add a GameEvent instance for each goal.
						GameEvent event = new GameEvent();
						event.setEventType(EventType.GOAL);
						event.setPlayer(player);
						g.getEvents().add(event);
					}
				}
				if (data.indexOf(SUBST_TOKEN) > -1 || data.indexOf(SUBST_TOKEN_ALT) > -1) {
					// For a subst, check which POSITION the number refers to
					// and add a subst IN event
					GameEvent event = new GameEvent();
					event.setPlayer(player);
					event.setEventType(EventType.SUBSTITUTION_IN);
					g.getEvents().add(event);
					
					gp.setParticipationType(ParticipationType.SUBSTITUTE_IN);
					
					// Add ID to list so we can post-process and set the SUBST_OUT properly
					substitutedPositions.add(positionId);
					
				} else {
					gp.setParticipationType(ParticipationType.STARTER);
				}
				break;

			}

		}

		if (homegame) {
			g.setAwayTeam(opponentClub);
			g.setHomeTeam(dao.getDefaultClub());
		} else {
			g.setAwayTeam(dao.getDefaultClub());
			g.setHomeTeam(opponentClub);
		}
		
		// Find the players that were substituted out
		for(Integer posId : substitutedPositions) {
			for(GameParticipation gp : g.getGameParticipation()) {
				if(gp.getPositionId().equals(posId) && gp.getParticipationType() == ParticipationType.STARTER) {
					gp.setParticipationType(ParticipationType.SUBSTITUTE_OUT);
					GameEvent ge = new GameEvent();
					ge.setEventType(EventType.SUBSTITUTION_OUT);
					ge.setPlayer(gp.getPlayer());
					g.getEvents().add(ge);
					break;
				}
			}
		}
		
		dao.saveGame(g);
		

		System.out.println("Imported game vs " + opponentClub.getName());
	}

	private FormationPosition getFormationPosition(String formationName, int positionId) {
		return dao.getFormationPosition(formationName, positionId);
	}

	class PGame {
		public int index;
		public int positionIndex;
	}

}
