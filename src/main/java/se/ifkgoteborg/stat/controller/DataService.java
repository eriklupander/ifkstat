package se.ifkgoteborg.stat.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.ifkgoteborg.stat.dto.PlayerStatDTO;
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


@Path("/")
public interface DataService {


		@Path("/player/{id}")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		Player getPlayer(@PathParam("id") Long id);
		
		@Path("/squadseason")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<SquadSeason> getSquadSeasons();
		
		@Path("/tournamentseason")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<TournamentSeason> getTournamentSeasons();
		
		@Path("/tournament")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<Tournament> getTournaments();
		
		@Path("/ground")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<Ground> getGrounds();
		
		@Path("/referee")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<Referee> getReferees();
		
		@Path("/squadseason/{id}/squad")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<Player> getSquadOfSeason(@PathParam("id") Long id);
		
		@Path("/game/{id}")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		Game getGame(@PathParam("id") Long id);
		
		@Path("/game/{id}/participation")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<GameParticipation> getGameParticipation(@PathParam("id") Long id);
		
		@Path("/game/{id}/note")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<GameNote> getGameNotes(@PathParam("id") Long id);
		
		@Path("/game/{id}/events")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<GameEvent> getGameEvents(@PathParam("id") Long id);
		
		
		@Path("/player/{id}/games")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<Game> getGamesOfPlayer(@PathParam("id") Long id);
		
		@Path("/club/{id}/games")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<Game> getGamesVsClub(@PathParam("id") Long id);
		
		@Path("/tournamentseason/{id}/games")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<Game> getGamesOfTournamentSeason(@PathParam("id") Long id);
		
		@Path("/tournament/{id}/seasons")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		List<TournamentSeason> getSeasonsOfTournament(@PathParam("id") Long id);

		@Path("/player/{id}/stats")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
		PlayerStatDTO getPlayerStats(@PathParam("id") Long id);
}
