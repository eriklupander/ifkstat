package se.ifkgoteborg.stat.controller;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.spi.DecimalFormatSymbolsProvider;
import java.util.Locale;



public class PlayerResultStatDTO {
	private Integer wins;
	private Integer draws;
	private Integer losses;
	
	private String participationType;
	
	private String season;
	private String tournament;

	
	
	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getTournament() {
		return tournament;
	}

	public void setTournament(String tournament) {
		this.tournament = tournament;
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public Integer getDraws() {
		return draws;
	}

	public void setDraws(Integer draws) {
		this.draws = draws;
	}

	public Integer getLosses() {
		return losses;
	}

	public void setLosses(Integer losses) {
		this.losses = losses;
	}

	public String getParticipationType() {
		return participationType;
	}

	public void setParticipationType(String participationType) {
		this.participationType = participationType;
	}
	
	public Integer getGames() {
		return wins+draws+losses;
	}
	
	static DecimalFormat nf = new DecimalFormat("#.##");
	
	static {
		nf.setMaximumFractionDigits(2);
		nf.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
	}
	
	public Float getAveragePoints() {
		if(getGames() == 0) {
			return 0.0f;
		}
		return Float.parseFloat(nf.format((float) ((float) ((wins*3)+(draws)) / getGames()))); 
	}
}
