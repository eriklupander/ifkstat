package se.ifkgoteborg.stat.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import se.ifkgoteborg.stat.dto.AveragesPerGameAndTournamentDTO;
import se.ifkgoteborg.stat.dto.GamePositionStatDTO;
import se.ifkgoteborg.stat.dto.GoalsPerTournamentDTO;
import se.ifkgoteborg.stat.dto.PlayerStatDTO;
import se.ifkgoteborg.stat.dto.PlayerSummaryDTO;
import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.GameEvent;
import se.ifkgoteborg.stat.model.GameNote;
import se.ifkgoteborg.stat.model.GameParticipation;
import se.ifkgoteborg.stat.model.Ground;
import se.ifkgoteborg.stat.model.Player;
import se.ifkgoteborg.stat.model.PositionType;
import se.ifkgoteborg.stat.model.Referee;
import se.ifkgoteborg.stat.model.SquadSeason;
import se.ifkgoteborg.stat.model.Tournament;
import se.ifkgoteborg.stat.model.TournamentSeason;

@Stateless
@PermitAll
public class DataServiceBean implements DataService {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String GAMES_PER_PLAYER_TOURNAMENT =
			"SELECT t.name, COUNT(pg.id) as appearances, " + 
			"	COUNT(ge2.id) as inbytt, COUNT(ge3.id) as utbytt, t.id  " +  
			"	FROM player p   " + 
			"	INNER JOIN player_game pg ON pg.player_id=p.id  " + 
			"	INNER JOIN game g ON g.id = pg.game_id  " + 
			"	INNER JOIN tournament_season ts ON ts.id = g.tournamentseason_id  " + 
			"	INNER JOIN tournament t ON t.id = ts.tournament_id  " + 
			"	LEFT OUTER JOIN game_event ge2 ON ge2.player_id=p.id AND ge2.game_id=g.id AND ge2.EVENTTYPE ='SUBSTITUTION_IN'  " + 
			"	LEFT OUTER JOIN game_event ge3 ON ge3.player_id=p.id AND ge3.game_id=g.id AND ge3.EVENTTYPE ='SUBSTITUTION_OUT'  " + 
			"	WHERE p.id = :id " + 
			"	GROUP BY t.name ORDER BY t.name;";
	
	private static final String GOALS_PER_PLAYER_TOURNAMENT = 
			"SELECT t.name, COUNT(ge.id) as goals, COUNT(ge4.id) as goals_as_subst " +
			"	, COUNT(ge5.id) as goals_as_subst_out " +
			"	FROM player p   " +
			"	INNER JOIN player_game pg ON pg.player_id=p.id  " +
			"	INNER JOIN game g ON g.id = pg.game_id  " +
			"	INNER JOIN tournament_season ts ON ts.id = g.tournamentseason_id  " +
			"	INNER JOIN tournament t ON t.id = ts.tournament_id  " +
			"	LEFT OUTER JOIN game_event ge ON ge.player_id=p.id AND ge.game_id=g.id AND ge.EVENTTYPE ='GOAL'  " +
			"	LEFT OUTER JOIN game_event ge4 ON ge4.player_id=p.id AND ge.player_id=p.id AND ge4.game_id=g.id AND (ge4.EVENTTYPE ='SUBSTITUTION_IN' AND ge.EVENTTYPE ='GOAL')  " +
			"	LEFT OUTER JOIN game_event ge5 ON ge5.player_id=p.id AND ge.player_id=p.id AND ge5.game_id=g.id AND (ge5.EVENTTYPE ='SUBSTITUTION_OUT' AND ge.EVENTTYPE ='GOAL')  " +
			"	WHERE p.id=:id " +
			"	GROUP BY t.name ORDER BY t.name";
	
	@Inject
	EntityManager em;

	@Override
	public Player getPlayer(Long id) {
		Player p = em.find(Player.class, id);
		
		// Lazy loading fix. I don't like it at all.
		p.getGames().size();
		
		return p;
	}

	@Override
	public List<Player> getSquadOfSeason(Long id) {
		return em.createQuery("select p from Player p WHERE p.clubs.season.id = :id")
				.setParameter("id", id)
				.getResultList();
	}

	@Override
	public Game getGame(Long id) {
		return em.find(Game.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Game> getGamesOfPlayer(Long id) {
		return em.createQuery("select g from Game g join g.gameParticipation gp join gp.player p WHERE p.id = :id")
			.setParameter("id", id)
			.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Game> getGamesOfPlayerInTournament(Long id, Long tournamentId) {
		return em.createQuery("select g from Game g join g.gameParticipation gp join gp.player p WHERE p.id = :id AND g.tournamentSeason.tournament.id = :tournamentId")
				.setParameter("id", id)
				.setParameter("tournamentId", tournamentId)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Game> getGamesOfPlayerInTournamentSubstIn(Long id, Long tournamentId) {
		return em.createQuery("select DISTINCT(g) from Game g join g.gameParticipation gp " +
				"join gp.player p " +
				"join g.events ge " +                                                                      
				"WHERE p.id = :id AND g.tournamentSeason.tournament.id = :tournamentId AND ge.eventType = 'SUBSTITUTION_IN' and ge.player.id=:id")
					.setParameter("id", id)
					.setParameter("tournamentId", tournamentId)
					.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Game> getGamesOfPlayerInTournamentSubstOut(Long id, Long tournamentId) {
		return em.createQuery("select DISTINCT(g) from Game g join g.gameParticipation gp " +
				"join gp.player p " +
				"join g.events ge " +
				"WHERE p.id = :id AND g.tournamentSeason.tournament.id = :tournamentId AND ge.eventType = 'SUBSTITUTION_OUT' and ge.player.id=:id")
					.setParameter("id", id)
					.setParameter("tournamentId", tournamentId)
					.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Game> getGamesOfPlayerInTournamentGoalScored(Long id,
			Long tournamentId) {
		return em.createQuery("select DISTINCT(g) from Game g join g.gameParticipation gp " +
			"join gp.player p " +
			"join g.events ge " +
			"WHERE p.id = :id AND g.tournamentSeason.tournament.id = :tournamentId AND ge.eventType = 'GOAL' and ge.player.id=:id")
				.setParameter("id", id)
				.setParameter("tournamentId", tournamentId)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Game> getGamesOfTournamentSeason(Long id) {
		return em.createQuery("select g from Game g WHERE g.tournamentSeason.id = :id")
				.setParameter("id", id)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SquadSeason> getSquadSeasons() {
		return em.createQuery("select ss from SquadSeason ss").getResultList();
	}

	@Override
	public List<TournamentSeason> getTournamentSeasons() {
		return em.createQuery("select ts from TournamentSeason ts").getResultList();
	}

	@Override
	public List<Tournament> getTournaments() {
		return em.createQuery("select t from Tournament t").getResultList();
	}

	@Override
	public List<Ground> getGrounds() {
		return em.createQuery("select g from Ground g").getResultList();
	}

	@Override
	public List<Referee> getReferees() {
		return em.createQuery("select r from Referee r").getResultList();
	}

	@Override
	public List<TournamentSeason> getSeasonsOfTournament(Long id) {
		return em.createQuery("select ts from TournamentSeason ts WHERE ts.tournament.id = :id")
				.setParameter("id", id)
				.getResultList();
	}

	@Override
	public List<GameParticipation> getGameParticipation(Long id) {
		return em.createQuery("select gp from GameParticipation gp WHERE gp.game.id = :id")
				.setParameter("id", id)
				.getResultList();
	}

	@Override
	public List<GameNote> getGameNotes(Long id) {
		return em.createQuery("select gn from GameNote gn WHERE gn.game.id = :id")
				.setParameter("id", id)
				.getResultList();
	}

	@Override
	public List<GameEvent> getGameEvents(Long id) {
		return em.createQuery("select ge from GameEvent ge WHERE ge.game.id = :id")
				.setParameter("id", id)
				.getResultList();
	}

	@Override
	public List<Game> getGamesVsClub(Long id) {
		return em.createQuery("select g from Game g WHERE (g.homeTeam.id = :id OR g.awayTeam.id = :id)")
				.setParameter("id", id)
				.getResultList();
	}
	
	@Override
	public PlayerStatDTO getPlayerStats(Long id) {
		PlayerStatDTO dto = new PlayerStatDTO();
		
		Query q1 = em.createNativeQuery(
			"SELECT t.name, COUNT(ge.id) as goals FROM player p " +
			"INNER JOIN game_event ge ON ge.player_id=p.id  " +
			"INNER JOIN game g ON g.id = ge.game_id " +
			"INNER JOIN tournament_season ts ON ts.id = g.tournamentseason_id " +
			"INNER JOIN tournament t ON t.id = ts.tournament_id " +
			"WHERE ge.EVENTTYPE ='GOAL' AND p.id=" + id + " GROUP BY t.name ORDER BY goals DESC");
		List<Object[]> res1 = q1.getResultList();
		for(Object[] row : res1) {
			dto.getGoalsPerTournament().add(new GoalsPerTournamentDTO((String) row[0], (Number) row[1]));
		}
		
		Query q2 = em.createNativeQuery(
				"select pos.name, count(pos.id) as gcount from player p " +
				"inner join player_game pg ON pg.player_id=p.id  " +
				"inner join formation_position fp ON pg.formationposition_id=fp.id " +
				"inner join position pos ON pos.id=fp.position_id " +
				"WHERE p.id = " + id + " " +
				"GROUP BY p.name, pos.name " +
				"ORDER BY gcount DESC");
			List<Object[]> res2 = q2.getResultList();
			for(Object[] row : res2) {
				dto.getGamesPerPosition().add(new GamePositionStatDTO((String) row[0], (Number) row[1]));
			}
		
			Query q3 = em.createNativeQuery(GAMES_PER_PLAYER_TOURNAMENT)
					.setParameter("id", id);
			List<Object[]> res3 = q3.getResultList();
			
			Query q4 = em.createNativeQuery(GOALS_PER_PLAYER_TOURNAMENT)
					.setParameter("id", id);
			List<Object[]> res4 = q4.getResultList();
			
			for(int a = 0; a < res3.size(); a++) {
				
				Object[] row3 = res3.get(a);
				Object[] row4 = res4.get(a);
				
				AveragesPerGameAndTournamentDTO avDto = new AveragesPerGameAndTournamentDTO();
				avDto.setTournamentName((String) row3[0]);
				avDto.setGoals( ((Number) row4[1]).intValue());
				avDto.setTotalGames( ((Number) row3[1]).intValue());
				avDto.setGamesAsSubstituteIn( ((Number) row3[2]).intValue());
				avDto.setGamesAsSubstituteOut( ((Number) row3[3]).intValue());
				avDto.setGoalsAsSubstituteIn( ((Number) row4[2]).intValue());
				avDto.setGoalsAsSubstituteOut( ((Number) row4[3]).intValue());
				avDto.setTournamentId( ((Number) row3[4]).longValue());
				dto.getAveragesPerTournament().add(avDto);
			}
			
		return dto;
	}

	@Override
	public List<PositionType> getPositionTypes() {
		return em.createQuery("select pt from PositionType pt").getResultList();
	}

	@Override
	public List<PositionType> getCountries() {
		return em.createQuery("select c from Country c ORDER BY c.name").getResultList();
	}

	@Override
	public List<Player> getPlayers() {
		return em.createQuery("select p from Player p ORDER BY p.name").getResultList();
	}

	@Override
	public List<Game> getGames() {
		return em.createQuery("select g from Game g ORDER BY g.dateOfGame DESC").getResultList();
	}

	@Override
	public Collection<PlayerSummaryDTO> getPlayerSummaries() {
		String sql = "select p.id, p.name, COUNT(pg.id) as games, MIN(g.dateOfGame) as firstGame, MAX(g.dateOfGame) as lastGame FROM player p " +
				"INNER JOIN player_game pg ON pg.player_id=p.id " +
				"INNER JOIN game g ON pg.game_id=g.id " +
				"GROUP BY p.name ORDER BY p.name";
		
		Query q1 = em.createNativeQuery(sql);
		List<Object[]> res1 = q1.getResultList();
		Map<Long, PlayerSummaryDTO> map = new HashMap<Long, PlayerSummaryDTO>();
		for(Object[] row : res1) {
			PlayerSummaryDTO dto = new PlayerSummaryDTO();
			dto.setId( ((Number) row[0]).longValue());
			dto.setName((String) row[1]);
			dto.setGames( ((Number) row[2]).intValue());
			dto.setFirstGame(new Date(((java.sql.Date) row[3]).getTime()));
			dto.setLastGame(new Date(((java.sql.Date) row[4]).getTime()));
			map.put(dto.getId(), dto);
		}
		
		// Get the goals in a separate query...
		String sql2 = "select p.id, COUNT(ge.id) as goals FROM player p " +	
					"LEFT OUTER JOIN game_event ge ON ge.player_id=p.id AND ge.eventType = 'GOAL' " + 
					"GROUP BY p.id";
		
		Query q2 = em.createNativeQuery(sql2);
		List<Object[]> res2 = q2.getResultList();
		
		// Fugly, we assume the rows are matching the result from above. Reason for this:
		// Doing the goals thing in a sub-select makes the query take many seconds instead of
		// milliseconds. This is much faster. And more fugly.
		for(Object[] row : res2) {
			Long id = ((Number) row[0]).longValue();
			Integer goals = ((Number) row[1]).intValue();
			
			if(map.containsKey(id)) {
				map.get(id).setGoals(goals);
			} else {
				// WARN!
				System.out.println("Could not find playerId in map: " + id);
			}
			
		}
		
		return map.values();
	}

	
	public List<Game> getGamesOfDate(String date) {
		try {
			Date dateOfGame = sdf.parse(date);
			
			return em.createQuery("SELECT g FROM Game g WHERE g.dateOfGame = :dateOfGame")
					.setParameter("dateOfGame", dateOfGame)
					.getResultList();
		} catch (Exception e) {
			return new ArrayList<Game>();
		}
		
	}
	

}
