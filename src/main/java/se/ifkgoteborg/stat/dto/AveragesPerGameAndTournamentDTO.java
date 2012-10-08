package se.ifkgoteborg.stat.dto;

public class AveragesPerGameAndTournamentDTO {
	private String tournamentName;
	private Long tournamentId;
	private Integer totalGames;
	private Integer gamesAsSubstituteIn;
	private Integer gamesAsSubstituteOut;
	private Integer points;
	private Integer goals;
	private Integer goalsAsSubstituteIn;
	private Integer goalsAsSubstituteOut;
	
	public String getTournamentName() {
		return tournamentName;
	}
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
	public Long getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}
	public Integer getTotalGames() {
		return totalGames;
	}
	public void setTotalGames(Integer totalGames) {
		this.totalGames = totalGames;
	}
	public Integer getGamesAsSubstituteIn() {
		return gamesAsSubstituteIn;
	}
	public void setGamesAsSubstituteIn(Integer gamesAsSubstituteIn) {
		this.gamesAsSubstituteIn = gamesAsSubstituteIn;
	}
	public Integer getGamesAsSubstituteOut() {
		return gamesAsSubstituteOut;
	}
	public void setGamesAsSubstituteOut(Integer gamesAsSubstituteOut) {
		this.gamesAsSubstituteOut = gamesAsSubstituteOut;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Integer getGoals() {
		return goals;
	}
	public void setGoals(Integer goals) {
		this.goals = goals;
	}
	public Integer getGoalsAsSubstituteIn() {
		return goalsAsSubstituteIn;
	}
	public void setGoalsAsSubstituteIn(Integer goalsAsSubstituteIn) {
		this.goalsAsSubstituteIn = goalsAsSubstituteIn;
	}
	public Integer getGoalsAsSubstituteOut() {
		return goalsAsSubstituteOut;
	}
	public void setGoalsAsSubstituteOut(Integer goalsAsSubstituteOut) {
		this.goalsAsSubstituteOut = goalsAsSubstituteOut;
	}
	
	
}
