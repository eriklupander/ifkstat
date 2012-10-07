package se.ifkgoteborg.stat.dto;

public class GamePositionStatDTO {

	private String positionName;
	private Integer appearances;
	
	public GamePositionStatDTO(String positionName, Number appearances) {
		this.positionName = positionName;
		this.appearances = appearances.intValue();		
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public Integer getAppearances() {
		return appearances;
	}
	public void setAppearances(Integer appearances) {
		this.appearances = appearances;
	}
	
	
}
