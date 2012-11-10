package se.ifkgoteborg.stat.dto;


import junit.framework.Assert;

import org.junit.Test;

import se.ifkgoteborg.stat.controller.PlayerResultStatDTO;

public class PlayerResultStatDTOTest {

	@Test
	public void testAverage() {
		PlayerResultStatDTO dto = new PlayerResultStatDTO();
		dto.setWins(5);
		dto.setDraws(5);
		dto.setLosses(3);
		
		Assert.assertEquals(1.54f, (float) dto.getAveragePoints());
	}
}
