package se.ifkgoteborg.stat.controller;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import se.ifkgoteborg.stat.model.Club;
import se.ifkgoteborg.stat.model.Country;
import se.ifkgoteborg.stat.model.Formation;
import se.ifkgoteborg.stat.model.FormationPosition;
import se.ifkgoteborg.stat.model.Ground;
import se.ifkgoteborg.stat.model.Position;
import se.ifkgoteborg.stat.model.Setting;
import se.ifkgoteborg.stat.model.Tournament;
import se.ifkgoteborg.stat.model.enums.PositionType;
import se.ifkgoteborg.stat.model.enums.Side;

@Startup
@Singleton
public class StatStartup {

	private static final String INIT_DATA_RUN = "init.data.run";
	@Inject
	EntityManager em;	
	
	@PostConstruct
	public void init() {
		
		System.out.println("ENTER - init() of Stat");
		
		boolean runInitData = true;
		
		try {
			Setting s = (Setting) em.createQuery("select s from Setting s WHERE s.key=:key").setParameter("key", INIT_DATA_RUN).getSingleResult();
			if("1".equals(s.getValue())) {
				runInitData = false;
			}
		} catch (NoResultException e) {
			
		}
		
		if(!runInitData)
			return;
	
		
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
		//ifkgbg.setFoundedDate(Date.get)
		
		ifkgbg = em.merge(ifkgbg);
		
		Ground gullevi = new Ground();
		gullevi.setName("Gamla Ullevi");
		gullevi.setMaxCapacity(18500);
		
		gullevi = em.merge(gullevi);
//		
//		
//		Player pl = new Player();
//		pl.setFullName("Mamadou Diallo");
//		pl.setName("Big Mama");
//		pl.setGender(Gender.MALE);
//		pl.setLength(191);
//		pl.setWeight(100);
//		pl.setNationality(senegal);
//		
//		PlayedForClub pfc = new PlayedForClub();
//		pfc.setClub(ifkgbg);
//		pfc.setFromDate(DateFactory.get(2003,3,25));
//		pfc.setToDate(DateFactory.get(2003,11,31));
//		
//		pfc = em.merge(pfc);
//		
//		pl.getClubs().add(pfc);
//		
//		pl = em.merge(pl);
//		
//		Player pl2 = new Player();
//		pl2.setFullName("Marino");
//		pl2.setName("Rahmberg");
//		pl2.setGender(Gender.MALE);
//		pl2.setLength(179);
//		pl2.setWeight(71);
//		pl2.setNationality(sweden);
//		
//		PlayedForClub pfc2 = new PlayedForClub();
//		pfc2.setClub(ifkgbg);
//		pfc2.setFromDate(DateFactory.get(2002,7,25));
//		pfc2.setToDate(DateFactory.get(2003,11,31));
//		
//		pfc2 = em.merge(pfc2);
//		
//		pl2.getClubs().add(pfc2);
//		
//		pl2 = em.merge(pl2);
//		
		// Positioner f�r 4-4-2
		Position mv = em.merge(new Position("Målvakt", "MV", Side.CENTRAL, PositionType.GOALKEEPER));
		Position hb = em.merge(new Position("Högerback", "HB", Side.RIGHT, PositionType.DEFENDER));
		Position mb1 = em.merge(new Position("Mittback", "MB", Side.RIGHT, PositionType.DEFENDER));
		Position mb2 = em.merge(new Position("Mittback", "MB", Side.LEFT, PositionType.DEFENDER));
		Position vb = em.merge(new Position("Vänsterback", "VB", Side.LEFT, PositionType.DEFENDER));
		Position hy = em.merge(new Position("Högerytter", "HY", Side.RIGHT, PositionType.MIDFIELD));
		Position im1 = em.merge(new Position("Innermitt", "IM", Side.RIGHT, PositionType.MIDFIELD));
		Position im2 = em.merge(new Position("Innermitt", "IM", Side.LEFT, PositionType.MIDFIELD));
		Position im1_c = em.merge(new Position("Innermitt", "IM", Side.CENTRAL, PositionType.MIDFIELD));
		
		Position im1d_r = em.merge(new Position("Innermitt (def)", "IM (D)", Side.RIGHT, PositionType.MIDFIELD));
		Position im2d_l = em.merge(new Position("Innermitt (def)", "IM (D)", Side.LEFT, PositionType.MIDFIELD));
		Position im1d_c = em.merge(new Position("Innermitt (def)", "IM (D)", Side.CENTRAL, PositionType.MIDFIELD));
		
		Position im1o = em.merge(new Position("Innermitt (off)", "IM (O)", Side.CENTRAL, PositionType.MIDFIELD));
		
		Position vy = em.merge(new Position("Vänsterytter", "VY", Side.LEFT, PositionType.MIDFIELD));
		
		Position fw1 = em.merge(new Position("Anfallare", "FW", Side.RIGHT, PositionType.FORWARD));
		Position fw2 = em.merge(new Position("Anfallare", "FW", Side.LEFT, PositionType.FORWARD));
		
		Position fw1_c = em.merge(new Position("Anfallare", "FW", Side.CENTRAL, PositionType.FORWARD));
		
		Position p11 = em.merge(new Position("Avbytare", "AV", Side.CENTRAL, PositionType.SUBSTITUTE));
		Position p12 = em.merge(new Position("Innermitt", "IM", Side.CENTRAL, PositionType.MIDFIELD));
		
		
		Position hh = em.merge(new Position("Högerhalv", "HH", Side.RIGHT, PositionType.MIDFIELD));
		Position ch = em.merge(new Position("Centerhalv", "CH", Side.CENTRAL, PositionType.MIDFIELD));
		Position vh = em.merge(new Position("Vänsterhalv", "VH", Side.LEFT, PositionType.MIDFIELD));
		
		Position hi = em.merge(new Position("Högerinner", "HI", Side.RIGHT, PositionType.FORWARD));
		Position c = em.merge(new Position("Center", "CE", Side.CENTRAL, PositionType.FORWARD));
		Position vi = em.merge(new Position("Vänsterinner", "VI", Side.LEFT, PositionType.FORWARD));
			

		
		Formation f442 = new Formation("4-4-2");
		f442 = em.merge(f442);
		
		Formation f4231 = new Formation("4-2-3-1");
		f4231 = em.merge(f4231);
		
		Formation f4141 = new Formation("4-1-4-1");
		f4141 = em.merge(f4141);
		
		Formation f451 = new Formation("4-5-1");
		f451 = em.merge(f451);
		
		Formation f352 = new Formation("3-5-2");
		f352 = em.merge(f352);
		
		Formation f235 = new Formation("2-3-5");
		f235 = em.merge(f235);
		
		em.persist(new FormationPosition(1, f442, mv));
		em.persist(new FormationPosition(2, f442, hb));
		em.persist(new FormationPosition(3, f442, mb1));
		em.persist(new FormationPosition(4, f442, mb2));
		em.persist(new FormationPosition(5, f442, vb));
		em.persist(new FormationPosition(6, f442, hy));
		em.persist(new FormationPosition(7, f442, im1));
		em.persist(new FormationPosition(8, f442, im2));
		em.persist(new FormationPosition(9, f442, vy));
		em.persist(new FormationPosition(10, f442, fw1));
		em.persist(new FormationPosition(11, f442, fw2));
		
		em.persist(new FormationPosition(1, f4231, mv));
		em.persist(new FormationPosition(2, f4231, hb));
		em.persist(new FormationPosition(3, f4231, mb1));
		em.persist(new FormationPosition(4, f4231, mb2));
		em.persist(new FormationPosition(5, f4231, vb));
		em.persist(new FormationPosition(6, f4231, im1d_r));
		em.persist(new FormationPosition(7, f4231, im2d_l));
		em.persist(new FormationPosition(8, f4231, hy));
		em.persist(new FormationPosition(9, f4231, im1o));
		em.persist(new FormationPosition(10, f4231, vy));
		em.persist(new FormationPosition(11, f4231, fw1_c));
		
		em.persist(new FormationPosition(1, f4141, mv));
		em.persist(new FormationPosition(2, f4141, hb));
		em.persist(new FormationPosition(3, f4141, mb1));
		em.persist(new FormationPosition(4, f4141, mb2));
		em.persist(new FormationPosition(5, f4141, vb));
		em.persist(new FormationPosition(6, f4141, im1d_c));
		em.persist(new FormationPosition(7, f4141, hy));
		em.persist(new FormationPosition(8, f4141, im1));
		em.persist(new FormationPosition(9, f4141, vy));
		em.persist(new FormationPosition(10, f4141, im1o));
		em.persist(new FormationPosition(11, f4141, fw1_c));
		
		em.persist(new FormationPosition(1, f451, mv));
		em.persist(new FormationPosition(2, f451, hb));
		em.persist(new FormationPosition(3, f451, mb1));
		em.persist(new FormationPosition(4, f451, mb2));
		em.persist(new FormationPosition(5, f451, vb));
		em.persist(new FormationPosition(6, f451, hy));
		em.persist(new FormationPosition(7, f451, im1));
		em.persist(new FormationPosition(8, f451, im1_c));
		em.persist(new FormationPosition(9, f451, im2));
		em.persist(new FormationPosition(10, f451, vy));
		em.persist(new FormationPosition(11, f451, fw1_c));
		
		em.persist(new FormationPosition(1, f352, mv));
		em.persist(new FormationPosition(2, f352, hb));
		em.persist(new FormationPosition(3, f352, mb1));
		em.persist(new FormationPosition(4, f352, vb));
		em.persist(new FormationPosition(5, f352, hy));
		em.persist(new FormationPosition(6, f352, im1));
		em.persist(new FormationPosition(7, f352, im1_c));
		em.persist(new FormationPosition(8, f352, im2));
		em.persist(new FormationPosition(9, f352, vy));
		em.persist(new FormationPosition(10, f352, fw1));
		em.persist(new FormationPosition(11, f352, fw2));
		

		em.persist(new FormationPosition(1, f235, mv));
		em.persist(new FormationPosition(2, f235, hb));
		em.persist(new FormationPosition(3, f235, vb));
		em.persist(new FormationPosition(4, f235, hh));
		em.persist(new FormationPosition(5, f235, ch));
		em.persist(new FormationPosition(6, f235, vh));
		em.persist(new FormationPosition(7, f235, hy));
		em.persist(new FormationPosition(8, f235, hi));
		em.persist(new FormationPosition(9, f235, c));
		em.persist(new FormationPosition(10, f235, vi));
		em.persist(new FormationPosition(11, f235, vy));
		
		// Write setting flag
		Setting settingsInitialized = new Setting(INIT_DATA_RUN, "1");
		em.persist(settingsInitialized);
		
		System.out.println("FINISHED setting up core data.");
	}
	
}
