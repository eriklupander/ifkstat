package se.ifkgoteborg.stat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Keeps track of statistics for a single game in a structured manner
 * @author Erik
 *
 */
@Entity
@Table(name="game_stats")
public class GameStatistics {
	
	@Id
	@GeneratedValue
	private Long id;
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
