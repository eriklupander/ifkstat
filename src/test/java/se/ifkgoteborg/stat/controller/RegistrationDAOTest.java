package se.ifkgoteborg.stat.controller;

import org.junit.Assert;
import org.junit.Test;

import se.ifkgoteborg.stat.model.SquadSeason;

public class RegistrationDAOTest {

	@Test
	public void testFullSeason() {
		String seasonName = "1992/1993";
		SquadSeason s = new RegistrationDAOBean().createSeasonFromString(seasonName);
		Assert.assertEquals(seasonName, s.getName());
		Assert.assertEquals(1992, s.getStartYear().getYear() + 1900);
		Assert.assertEquals(1993, s.getEndYear().getYear() + 1900);
	}

	@Test
	public void testFullSeasonHalfEndYear() {
		String seasonName = "1992/93";
		SquadSeason s = new RegistrationDAOBean().createSeasonFromString(seasonName);
		Assert.assertEquals(seasonName, s.getName());
		Assert.assertEquals(1992, s.getStartYear().getYear() + 1900);
		Assert.assertEquals(1993, s.getEndYear().getYear() + 1900);
	}
	
	@Test
	public void testHalfFirstHalfEndYear() {
		String seasonName = "92/93";
		SquadSeason s = new RegistrationDAOBean().createSeasonFromString(seasonName);
		Assert.assertEquals(seasonName, s.getName());
		Assert.assertEquals(1992, s.getStartYear().getYear() + 1900);
		Assert.assertEquals(1993, s.getEndYear().getYear() + 1900);
	}
	
	@Test
	public void testFullFirstYearHalfSecond21thCentury() {
		String seasonName = "2003/04";
		SquadSeason s = new RegistrationDAOBean().createSeasonFromString(seasonName);
		Assert.assertEquals(seasonName, s.getName());
		Assert.assertEquals(2003, s.getStartYear().getYear() + 1900);
		Assert.assertEquals(2004, s.getEndYear().getYear() + 1900);
	}
	
	@Test
	public void testFullFirstYearFullSecond21thCentury() {
		String seasonName = "2003/2004";
		SquadSeason s = new RegistrationDAOBean().createSeasonFromString(seasonName);
		Assert.assertEquals(seasonName, s.getName());
		Assert.assertEquals(2003, s.getStartYear().getYear() + 1900);
		Assert.assertEquals(2004, s.getEndYear().getYear() + 1900);
	}

}
