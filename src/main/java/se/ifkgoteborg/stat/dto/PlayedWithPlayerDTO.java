package se.ifkgoteborg.stat.dto;

public class PlayedWithPlayerDTO {
	private Long id;
	private String name;
	private Integer gamesWithPlayer;
	
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
	public Integer getGamesWithPlayer() {
		return gamesWithPlayer;
	}
	public void setGamesWithPlayer(Integer gamesWithPlayer) {
		this.gamesWithPlayer = gamesWithPlayer;
	}
	
	
}
