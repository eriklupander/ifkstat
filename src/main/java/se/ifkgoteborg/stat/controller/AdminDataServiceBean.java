package se.ifkgoteborg.stat.controller;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import se.ifkgoteborg.stat.model.Country;
import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.GameStatistics;
import se.ifkgoteborg.stat.model.Ground;
import se.ifkgoteborg.stat.model.Player;
import se.ifkgoteborg.stat.model.PositionType;
import se.ifkgoteborg.stat.model.Referee;

@Stateless
@RolesAllowed("user")
public class AdminDataServiceBean implements AdminDataService {
	
	@Inject
	EntityManager em;
	
	@Override
	public String test() {
		return "Hello World!";
	}

	@Override
	public Player savePlayer(Player player) {
		Player dbPlayer = em.find(Player.class, player.getId());
		dbPlayer.setBiography(player.getBiography());
		dbPlayer.setDateOfBirth(player.getDateOfBirth());
		dbPlayer.setLength(player.getLength());
		dbPlayer.setWeight(player.getWeight());
		dbPlayer.setMotherClub(player.getMotherClub());
		dbPlayer.setPlayedForClubs(player.getPlayedForClubs());
		dbPlayer.setName(player.getName());
		dbPlayer.setSquadNumber(player.getSquadNumber());
		
		if(player.getPositionType() != null) {
			PositionType pt = em.find(PositionType.class, player.getPositionType().getId());
			dbPlayer.setPositionType(pt);
		}
		
		if(player.getNationality() != null) {
			Country c = em.find(Country.class, player.getNationality().getId());
			dbPlayer.setNationality(c);
		}
		
		return em.merge(dbPlayer);
	}

	@Override
	public GameStatistics saveGameStats(Long id, GameStatistics gs) {
		Game g = em.find(Game.class, id);
		GameStatistics dbGs = g.getGameStats();
		dbGs.setCornersAwayTeam(gs.getCornersAwayTeam());
		dbGs.setCornersHomeTeam(gs.getCornersHomeTeam());
		dbGs.setFreekicksAwayTeam(gs.getFreekicksAwayTeam());
		dbGs.setFreekicksHomeTeam(gs.getFreekicksHomeTeam());
		dbGs.setOffsidesAwayTeam(gs.getOffsidesAwayTeam());
		dbGs.setOffsidesHomeTeam(gs.getOffsidesHomeTeam());
		dbGs.setPossessionAwayTeam(gs.getPossessionAwayTeam());
		dbGs.setPossessionHomeTeam(gs.getPossessionHomeTeam());
		dbGs.setShotsOffGoalAwayTeam(gs.getShotsOffGoalAwayTeam());
		dbGs.setShotsOffGoalHomeTeam(gs.getShotsOffGoalHomeTeam());
		dbGs.setShotsOnGoalAwayTeam(gs.getShotsOnGoalAwayTeam());
		dbGs.setShotsOnGoalHomeTeam(gs.getShotsOnGoalHomeTeam());
		dbGs.setThrowinsAwayTeam(gs.getThrowinsAwayTeam());
		dbGs.setThrowinsHomeTeam(gs.getThrowinsHomeTeam());
		return em.merge(dbGs);
	}

	@Override
	public Game saveGameDetails(Long id, Game game) {
		Game db = em.find(Game.class, id);
		if(game.getAttendance() != null) {
			db.setAttendance(game.getAttendance());
		}
		if(game.getAwayGoals() != null) {
			db.setAwayGoals(game.getAwayGoals());
		}
		
		if(game.getAwayGoalsHalftime() != null) {
			db.setAwayGoalsHalftime(game.getAwayGoalsHalftime());
		}
		
		if(game.getGameSummary() != null) {
			db.setGameSummary(game.getGameSummary());
		}
		
		if(game.getHomeGoals() != null) {
			db.setHomeGoals(game.getHomeGoals());
		}
		
		if(game.getHomeGoalsHalftime() != null) {
			db.setHomeGoalsHalftime(game.getHomeGoalsHalftime());
		}
		
		// Entities...
		if(game.getGround() != null) {
			if(game.getGround().getId() != null) {
				Ground ground = em.find(Ground.class, game.getGround().getId());
				
				if(ground != null) {
					// If same name
					if(game.getGround().getName().equals(ground.getName())) {
						db.setGround(ground);
					} else {
						ground = new Ground();
						ground.setName(game.getGround().getName());
						ground = em.merge(ground);
						db.setGround(ground);
					}
					
				}
			} else {
				Ground Ground = new Ground();
				Ground.setName(game.getGround().getName());
				Ground = em.merge(Ground);
				db.setGround(Ground);
			}
			
		}
		
		if(game.getReferee() != null) {
			if(game.getReferee().getId() != null) {
				Referee referee = em.find(Referee.class, game.getReferee().getId());
				
				if(referee != null) {
					// If same name
					if(game.getReferee().getName().equals(referee.getName())) {
						db.setReferee(referee);
					} else {
						referee = new Referee();
						referee.setName(game.getReferee().getName());
						referee = em.merge(referee);
						db.setReferee(referee);
					}
					
				}
			} else {
				Referee referee = new Referee();
				referee.setName(game.getReferee().getName());
				referee = em.merge(referee);
				db.setReferee(referee);
			}
			
		}
		
		
		return em.merge(db);
	}
}
