package se.ifkgoteborg.stat.dto;

public class PlayerSeasonDTO {

	private Long id;
	private String name;
	private Integer squadNr;
	
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
	
	
	
}
