package se.ifkgoteborg.stat.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.GameStatistics;
import se.ifkgoteborg.stat.model.Player;

@Path("/admin")
@RolesAllowed({"user", "admin"})
public interface AdminDataService {
	
	@Path("/test")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String test();
	
	@Path("/player")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Player savePlayer(Player player);
	
	@Path("/game/{id}/details")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Game saveGameDetails(@PathParam("id") Long id, Game game);
	
	@Path("/game/{id}/stats")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	GameStatistics saveGameStats(@PathParam("id") Long id, GameStatistics gameStats);
}
