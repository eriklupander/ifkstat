package se.ifkgoteborg.stat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Goal {
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Game game;
	
	@ManyToOne
	private Player scoredBy;
	
	@ManyToOne
	private Player assistedBy;
	
	private Integer gameMinute;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public Player getScoredBy() {
		return scoredBy;
	}
	public void setScoredBy(Player scoredBy) {
		this.scoredBy = scoredBy;
	}
	public Player getAssistedBy() {
		return assistedBy;
	}
	public void setAssistedBy(Player assistedBy) {
		this.assistedBy = assistedBy;
	}
	public Integer getGameMinute() {
		return gameMinute;
	}
	public void setGameMinute(Integer gameMinute) {
		this.gameMinute = gameMinute;
	}
	
	
}
