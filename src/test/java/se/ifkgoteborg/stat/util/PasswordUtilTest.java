package se.ifkgoteborg.stat.util;

import junit.framework.Assert;

import org.junit.Test;

public class PasswordUtilTest {
	
	@Test
	public void testHashPassword() {
		String passwordHash = PasswordUtil.getPasswordHash("test");
		Assert.assertEquals("CY9rzUYh03PK3k6DJie09g==", passwordHash);
	}
}
