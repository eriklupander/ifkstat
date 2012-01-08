package se.ifkgoteborg.stat.controller;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import se.ifkgoteborg.stat.model.Club;
import se.ifkgoteborg.stat.model.Country;
import se.ifkgoteborg.stat.model.Formation;
import se.ifkgoteborg.stat.model.FormationPosition;
import se.ifkgoteborg.stat.model.Gender;
import se.ifkgoteborg.stat.model.Ground;
import se.ifkgoteborg.stat.model.PlayedForClub;
import se.ifkgoteborg.stat.model.Player;
import se.ifkgoteborg.stat.model.Position;
import se.ifkgoteborg.stat.model.Tournament;
import se.ifkgoteborg.stat.model.enums.PositionType;
import se.ifkgoteborg.stat.model.enums.Side;
import se.ifkgoteborg.stat.util.DateFactory;

@Startup
@Singleton
public class StatStartup {

	@Inject
	EntityManager em;
	
	
	@PostConstruct
	public void init() {
		
		System.out.println("ENTER - init() of Stat");
		
		Tournament t1 = em.merge(new Tournament("Allsvenskan", false));
		Tournament t2 = em.merge(new Tournament("Svenska Cupen", false));
		Tournament t3 = em.merge(new Tournament("Champions League", true));
		Tournament t4 = em.merge(new Tournament("UEFA cupen", true));
		Tournament t5 = em.merge(new Tournament("Cupvinnarcupen", true));
		Tournament t6 = em.merge(new Tournament("UEFA Euro League", true));
		Tournament t7 = em.merge(new Tournament("Royal League", true));
		Tournament t8 = em.merge(new Tournament("Träningsmatch", false));
		
		// TODO add "is init" check
	
		Country sweden = new Country();
		sweden.setName("Sweden");
		sweden.setIsoCode("se");		
		sweden = em.merge(sweden);
		
		Country senegal = new Country();
		senegal.setName("Senegal");
		senegal.setIsoCode("sn");		
		senegal = em.merge(senegal);
		
		Club ifkgbg = new Club();
		ifkgbg.setName("IFK Göteborg");
		ifkgbg.setCity("Göteborg");
		ifkgbg.setCountry(sweden);
		ifkgbg.setDefaultClub(true);
		//ifkgbg.setFoundedDate(Calendar.get)
		
		ifkgbg = em.merge(ifkgbg);
		
		Ground gullevi = new Ground();
		gullevi.setName("Gamla Ullevi");
		gullevi.setMaxCapacity(18500);
		gullevi.getHomeTeams().add(ifkgbg);
		
		gullevi = em.merge(gullevi);
		
		
		Player pl = new Player();
		pl.setFullName("Mamadou Diallo");
		pl.setName("Big Mama");
		pl.setGender(Gender.MALE);
		pl.setLength(191);
		pl.setWeight(100);
		pl.setNationality(senegal);
		
		PlayedForClub pfc = new PlayedForClub();
		pfc.setClub(ifkgbg);
		pfc.setFromDate(DateFactory.get(2003,3,25));
		pfc.setToDate(DateFactory.get(2003,11,31));
		
		pfc = em.merge(pfc);
		
		pl.getClubs().add(pfc);
		
		pl = em.merge(pl);
		
		Player pl2 = new Player();
		pl2.setFullName("Marino");
		pl2.setName("Rahmberg");
		pl2.setGender(Gender.MALE);
		pl2.setLength(179);
		pl2.setWeight(71);
		pl2.setNationality(sweden);
		
		PlayedForClub pfc2 = new PlayedForClub();
		pfc2.setClub(ifkgbg);
		pfc2.setFromDate(DateFactory.get(2002,7,25));
		pfc2.setToDate(DateFactory.get(2003,11,31));
		
		pfc2 = em.merge(pfc2);
		
		pl2.getClubs().add(pfc2);
		
		pl2 = em.merge(pl2);
		
		// Positioner f�r 4-4-2
		Position p0 = em.merge(new Position("Målvakt", "MV", Side.CENTRAL, PositionType.GOALKEEPER));
		Position p1 = em.merge(new Position("Högerback", "HB", Side.RIGHT, PositionType.DEFENDER));
		Position p2 = em.merge(new Position("Mittback", "MB", Side.RIGHT, PositionType.DEFENDER));
		Position p3 = em.merge(new Position("Mittback", "MB", Side.LEFT, PositionType.DEFENDER));
		Position p4 = em.merge(new Position("Vänsterback", "VB", Side.LEFT, PositionType.DEFENDER));
		Position p5 = em.merge(new Position("Högerytter", "HY", Side.RIGHT, PositionType.MIDFIELD));
		Position p6 = em.merge(new Position("Innermitt", "IM", Side.RIGHT, PositionType.MIDFIELD));
		Position p7 = em.merge(new Position("Innermitt", "IM", Side.LEFT, PositionType.MIDFIELD));
		Position p8 = em.merge(new Position("Vänsterytter", "VY", Side.LEFT, PositionType.MIDFIELD));
		Position p9 = em.merge(new Position("Anfallare", "FW", Side.RIGHT, PositionType.FORWARD));
		Position p10 = em.merge(new Position("Anfallare", "FW", Side.LEFT, PositionType.FORWARD));		
		Position p11 = em.merge(new Position("Avbytare", "AV", Side.CENTRAL, PositionType.SUBSTITUTE));
		
		Formation f442 = new Formation();
		f442.setName("4-4-2");
		f442 = em.merge(f442);
		
		em.persist(new FormationPosition(1, f442, p0));
		em.persist(new FormationPosition(2, f442, p1));
		em.persist(new FormationPosition(3, f442, p2));
		em.persist(new FormationPosition(4, f442, p3));
		em.persist(new FormationPosition(5, f442, p4));
		em.persist(new FormationPosition(6, f442, p5));
		em.persist(new FormationPosition(7, f442, p6));
		em.persist(new FormationPosition(8, f442, p7));
		em.persist(new FormationPosition(9, f442, p8));
		em.persist(new FormationPosition(10, f442, p9));
		em.persist(new FormationPosition(11, f442, p10));
		
		System.out.println("FINISHED setting up core data.");
	}
	
}
