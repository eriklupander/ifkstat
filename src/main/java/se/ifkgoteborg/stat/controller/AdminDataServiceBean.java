package se.ifkgoteborg.stat.controller;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import se.ifkgoteborg.stat.model.Country;
import se.ifkgoteborg.stat.model.Player;
import se.ifkgoteborg.stat.model.PositionType;

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
}
