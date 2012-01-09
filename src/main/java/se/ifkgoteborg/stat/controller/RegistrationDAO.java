package se.ifkgoteborg.stat.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import se.ifkgoteborg.stat.controller.adapter.SquadPlayer;
import se.ifkgoteborg.stat.model.Club;
import se.ifkgoteborg.stat.model.Formation;
import se.ifkgoteborg.stat.model.FormationPosition;
import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.Ground;
import se.ifkgoteborg.stat.model.Player;
import se.ifkgoteborg.stat.model.Referee;
import se.ifkgoteborg.stat.model.Tournament;
import se.ifkgoteborg.stat.model.TournamentSeason;

public interface RegistrationDAO {

	public Ground getOrCreateGround(String name);

	public Club getOrCreateClub(String name);

	public Player getOrCreatePlayer(String name, Calendar dateOfBirth);

	public List<Player> getAllPlayers();

	public void importPlayers(List<SquadPlayer> players, int year);

	public void persist(Object o);

	public Club getDefaultClub();

	public Map<Integer, Player> loadSquad(int year);

	public List<Game> getGames(int year);

	public Game getGame(Long id);

	public FormationPosition getFormationPosition(String formationName,
			int positionId);

	public List<Tournament> getTournaments();

	public List<TournamentSeason> getTournamentSeasons(Long tournamentId);

	public TournamentSeason getTournamentSeasonByName(String tournamentName,
			int year);

	public Tournament getOrCreateTournamentByName(String tournamentName);

	public List<Game> getGames(Long tournamentId, int year);

	public Formation getFormationByName(String formation);

	public void saveGame(Game g);

	public EntityManager getPersistenceUnit();

	public List<Formation> getFormations();

	public List<Club> getClubs();

	public List<Ground> getGrounds();

	public List<Referee> getReferees();

}