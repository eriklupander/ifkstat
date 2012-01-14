package se.ifkgoteborg.stat.importer;

import junit.framework.Assert;

import org.junit.Test;

public class ImporterTest {

	@Test
	public void testCleanTournamentName() {
		String name = "SVENSKA CUPEN 02/03";
		String cleanedName = new MasterImporter(null).cleanTournamentName(name);
		
		Assert.assertEquals("SVENSKA CUPEN", cleanedName);
	}
	
	@Test
	public void testCleanTournamentNameWithOneDigit() {
		String name = "DIVISION 1 NORRA 1972/1973";
		String cleanedName = new MasterImporter(null).cleanTournamentName(name);
		
		Assert.assertEquals("DIVISION 1 NORRA", cleanedName);
	}

}
