package se.ifkgoteborg.stat.controller;

public class PlayerPositionStatsDTO {
	private Long playerId;
	private String positionName;
	private String formationName;
	private Integer games;
	private Integer goals;
	
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getFormationName() {
		return formationName;
	}
	public void setFormationName(String formationName) {
		this.formationName = formationName;
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
	
	
}
