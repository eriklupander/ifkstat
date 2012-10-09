package se.ifkgoteborg.stat.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.ifkgoteborg.stat.model.Player;

@Path("/admin")
@RolesAllowed("admin")
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
}
