package se.ifkgoteborg.stat.ui;

import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.util.DateFactory;


public class GameTableWrapper {
	private Long id;
	private String fixture;
	private String result;
	private String date;
	private Integer attendance;
	
	public GameTableWrapper() {}
	
	public GameTableWrapper(Game g) {
		setId(g.getId());
		String homeTeam = g.getHomeTeam() != null ? g.getHomeTeam().getName() : "X";
		String awayTeam =  g.getAwayTeam() != null ? g.getAwayTeam().getName() : "Y";
		setFixture(homeTeam + "-" + awayTeam);
		setResult(g.getHomeGoals() + "-" + g.getAwayGoals() + " (" + g.getHomeGoalsHalftime() + "-" + g.getAwayGoalsHalftime() + ")");
		if(g.getDateOfGame() != null) {
			setDate(DateFactory.format(g.getDateOfGame().getTime()));
		}
		setAttendance(g.getAttendance());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFixture() {
		return fixture;
	}

	public void setFixture(String fixture) {
		this.fixture = fixture;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getAttendance() {
		return attendance;
	}
	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}
	
	
}
