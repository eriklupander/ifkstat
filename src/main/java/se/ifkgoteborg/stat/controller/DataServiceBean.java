package se.ifkgoteborg.stat.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.GameEvent;
import se.ifkgoteborg.stat.model.GameNote;
import se.ifkgoteborg.stat.model.GameParticipation;
import se.ifkgoteborg.stat.model.Ground;
import se.ifkgoteborg.stat.model.Player;
import se.ifkgoteborg.stat.model.Referee;
import se.ifkgoteborg.stat.model.SquadSeason;
import se.ifkgoteborg.stat.model.Tournament;
import se.ifkgoteborg.stat.model.TournamentSeason;

@Stateless
public class DataServiceBean implements DataService {
	
	@Inject
	EntityManager em;

	@Override
	public Player getPlayer(Long id) {
		return em.find(Player.class, id);
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

	@Override
	public List<Game> getGamesOfPlayer(Long id) {
		return em.createQuery("select g from Game g WHERE g.gameParticipation.player.id = :id")
			.setParameter("id", id)
			.getResultList();
	}

	@Override
	public List<Game> getGamesOfTournamentSeason(Long id) {
		return em.createQuery("select g from Game g WHERE g.tournamentSeason.id = :id")
				.setParameter("id", id)
				.getResultList();
	}

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

}
