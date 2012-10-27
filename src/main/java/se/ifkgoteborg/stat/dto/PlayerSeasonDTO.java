package se.ifkgoteborg.stat.dto;

public class PlayerSeasonDTO {

	private Long id;
	private String name;
	private Integer squadNr;
	private Integer games;
	private Integer goals;
	
	// TODO perhaps add statistics for player for the season
	
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
	public Integer getSquadNr() {
		return squadNr;
	}
	public void setSquadNr(Integer squadNr) {
		this.squadNr = squadNr;
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
