package se.ifkgoteborg.stat.dto;

import java.util.Date;

public class PlayerSummaryDTO {
	private Long id;
	private String name;
	private Integer games;
	private Integer goals;
	
	private Date firstGame;
	private Date lastGame;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGames() {
		return games;
	}
	public void setGames(Integer games) {
		this.games = games;
	}
	public Integer getGoals() {
		return goals;
	}
	public void setGoals(Integer goals) {
		this.goals = goals;
	}
	public Date getFirstGame() {
		return firstGame;
	}
	public void setFirstGame(Date firstGame) {
		this.firstGame = firstGame;
	}
	public Date getLastGame() {
		return lastGame;
	}
	public void setLastGame(Date lastGame) {
		this.lastGame = lastGame;
	}
	
}
