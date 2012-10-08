package se.ifkgoteborg.stat.controller;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.ifkgoteborg.stat.model.Player;

@Path("/admin")
public interface AdminDataService {
	
	@Path("/player")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
	Player savePlayer(Player player);
}
