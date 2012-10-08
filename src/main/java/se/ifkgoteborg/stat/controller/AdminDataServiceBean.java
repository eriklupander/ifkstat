package se.ifkgoteborg.stat.controller;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import se.ifkgoteborg.stat.model.Player;

@Stateless
public class AdminDataServiceBean implements AdminDataService {
	
	@Inject
	EntityManager em;

	@Override
	public Player savePlayer(Player player) {
		Player dbPlayer = em.find(Player.class, player.getId());
		dbPlayer.setBiography(player.getBiography());
		dbPlayer.setDateOfBirth(player.getDateOfBirth());
		dbPlayer.setLength(player.getLength());
		dbPlayer.setWeight(player.getWeight());
		dbPlayer.setMotherClub(player.getMotherClub());
		dbPlayer.setName(player.getName());
		dbPlayer.setSquadNumber(player.getSquadNumber());
		return em.merge(dbPlayer);
	}
}
