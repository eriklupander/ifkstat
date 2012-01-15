package se.ifkgoteborg.stat.util;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testParseFullSeasonStringToStartYear() {
		String seasonName = "1911";
		int year = StringUtil.parseSeasonStringToStartYear(seasonName);
		Assert.assertEquals(1911, year);
	}
	
	@Test
	public void testParseFullSplitSeasonStringToStartYearWithSlash() {
		String seasonName = "1911/1912";
		int year = StringUtil.parseSeasonStringToStartYear(seasonName);
		Assert.assertEquals(1911, year);
	}
	
	@Test
	public void testParseFullSplitSeasonStringToStartYearWithSpace() {
		String seasonName = "1911 1912";
		int year = StringUtil.parseSeasonStringToStartYear(seasonName);
		Assert.assertEquals(1911, year);
	}
	
	@Test
	public void testParseHalfSplitSeasonStringToStartYearWithSpace() {
		String seasonName = "11 12";
		int year = StringUtil.parseSeasonStringToStartYear(seasonName);
		Assert.assertEquals(1911, year);
	}

	@Test
	public void testParseHalfSplitSeasonStringToStartYearWithSlash() {
		String seasonName = "11/12";
		int year = StringUtil.parseSeasonStringToStartYear(seasonName);
		Assert.assertEquals(1911, year);
	}
	
	
}
