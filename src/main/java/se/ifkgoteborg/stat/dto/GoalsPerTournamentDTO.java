package se.ifkgoteborg.stat.dto;

public class GoalsPerTournamentDTO {
	private Integer goals;
	private String tournamentName;
	
	public GoalsPerTournamentDTO(String tournamentName, Number goals) {
		this.tournamentName = tournamentName;
		this.goals = goals.intValue();
	}
	public Integer getGoals() {
		return goals;
	}
	public void setGoals(Integer goals) {
		this.goals = goals;
	}
	public String getTournamentName() {
		return tournamentName;
	}
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
	
	
}
