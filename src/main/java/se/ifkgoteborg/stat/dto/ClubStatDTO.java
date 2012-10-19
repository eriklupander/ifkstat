package se.ifkgoteborg.stat.dto;

public class ClubStatDTO {
	private Long clubId;
	private String clubName;

	private Integer homeGames = 0;
	private Integer awayGames = 0;

	private Integer homeGoalsScored = 0;
	private Integer awayGoalsScored = 0;
	
	private Integer homeGoalsConceded = 0;
	private Integer awayGoalsConceded = 0;
	
	private Integer homeWins = 0;
	private Integer homeDraws = 0;
	private Integer homeLosses = 0;
	
	private Integer awayWins = 0;
	private Integer awayDraws = 0;
	private Integer awayLosses = 0;
	
	
	public Long getClubId() {
		return clubId;
	}
	public void setClubId(Long clubId) {
		this.clubId = clubId;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public Integer getHomeGames() {
		return homeGames;
	}
	public void setHomeGames(Integer homeGames) {
		this.homeGames = homeGames;
	}
	public Integer getAwayGames() {
		return awayGames;
	}
	public void setAwayGames(Integer awayGames) {
		this.awayGames = awayGames;
	}
	
	public Integer getHomeGoalsScored() {
		return homeGoalsScored;
	}
	public void setHomeGoalsScored(Integer homeGoalsScored) {
		this.homeGoalsScored = homeGoalsScored;
	}
	public Integer getAwayGoalsScored() {
		return awayGoalsScored;
	}
	public void setAwayGoalsScored(Integer awayGoalsScored) {
		this.awayGoalsScored = awayGoalsScored;
	}
	public Integer getHomeGoalsConceded() {
		return homeGoalsConceded;
	}
	public void setHomeGoalsConceded(Integer homeGoalsConceded) {
		this.homeGoalsConceded = homeGoalsConceded;
	}
	public Integer getAwayGoalsConceded() {
		return awayGoalsConceded;
	}
	public void setAwayGoalsConceded(Integer awayGoalsConceded) {
		this.awayGoalsConceded = awayGoalsConceded;
	}
	public Integer getHomeWins() {
		return homeWins;
	}
	public void setHomeWins(Integer homeWins) {
		this.homeWins = homeWins;
	}
	public Integer getHomeDraws() {
		return homeDraws;
	}
	public void setHomeDraws(Integer homeDraws) {
		this.homeDraws = homeDraws;
	}
	public Integer getHomeLosses() {
		return homeLosses;
	}
	public void setHomeLosses(Integer homeLosses) {
		this.homeLosses = homeLosses;
	}
	public Integer getAwayWins() {
		return awayWins;
	}
	public void setAwayWins(Integer awayWins) {
		this.awayWins = awayWins;
	}
	public Integer getAwayDraws() {
		return awayDraws;
	}
	public void setAwayDraws(Integer awayDraws) {
		this.awayDraws = awayDraws;
	}
	public Integer getAwayLosses() {
		return awayLosses;
	}
	public void setAwayLosses(Integer awayLosses) {
		this.awayLosses = awayLosses;
	}
	
	public Integer getGames() {
		return this.homeGames + this.awayGames;
	}
	
	public Integer getWins() {
		return this.homeWins + this.awayWins;
	}
	public Integer getDraws() {
		return this.homeDraws + this.awayDraws;
	}
	public Integer getLosses() {
		return this.homeLosses + this.awayLosses;
	}
	
	public Integer getGoalsScored() {
		return this.homeGoalsScored + this.awayGoalsScored;
	}
	
	public Integer getGoalsConceded() {
		return this.homeGoalsConceded + this.awayGoalsConceded;
	}
}
