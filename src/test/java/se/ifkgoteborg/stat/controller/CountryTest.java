package se.ifkgoteborg.stat.controller;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

public class CountryTest {

	@Test
	public void test() {
		 Locale[] locales = Locale.getAvailableLocales();
		    for (Locale locale : locales) {
		      String iso = locale.getISO3Country();
		      String code = locale.getCountry();
		      String name = locale.getDisplayCountry();
		      System.out.println("ISO: " + iso + " Code: " + code + " Name: " + name);
		    }
	}

}
