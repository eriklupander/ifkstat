package se.ifkgoteborg.stat.dto;

public class TournamentSeasonDTO {
	private Long id;
	private String name;
	private Integer games;
	
	private Integer goalsScored;
	private Integer goalsConceded;
	
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
	public Integer getGoalsScored() {
		return goalsScored;
	}
	public void setGoalsScored(Integer goalsScored) {
		this.goalsScored = goalsScored;
	}
	public Integer getGoalsConceded() {
		return goalsConceded;
	}
	public void setGoalsConceded(Integer goalsConceded) {
		this.goalsConceded = goalsConceded;
	}
	
}
