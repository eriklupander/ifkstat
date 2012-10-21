package se.ifkgoteborg.stat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	private Integer shotsOnGoalHomeTeam = 0;
	private Integer shotsOnGoalAwayTeam = 0;
	private Integer shotsOffGoalHomeTeam = 0;
	private Integer shotsOffGoalAwayTeam = 0;
	private Integer offsidesHomeTeam = 0;
	private Integer offsidesAwayTeam = 0;
	private Integer cornersHomeTeam = 0;
	private Integer cornersAwayTeam = 0;
	private Integer freekicksHomeTeam = 0;
	private Integer freekicksAwayTeam = 0;
	private Integer throwinsHomeTeam = 0;
	private Integer throwinsAwayTeam = 0;
	private Integer possessionHomeTeam = 0;
	private Integer possessionAwayTeam = 0;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public Integer getShotsHomeTeam() {
		return shotsOnGoalHomeTeam + shotsOffGoalHomeTeam;
	}
	
	@Transient
	public Integer getShotsAwayTeam() {
		return shotsOnGoalAwayTeam + shotsOffGoalAwayTeam;
	}
	
	public Integer getShotsOnGoalHomeTeam() {
		return shotsOnGoalHomeTeam;
	}

	public void setShotsOnGoalHomeTeam(Integer shotsOnGoalHomeTeam) {
		this.shotsOnGoalHomeTeam = shotsOnGoalHomeTeam;
	}

	public Integer getShotsOnGoalAwayTeam() {
		return shotsOnGoalAwayTeam;
	}

	public void setShotsOnGoalAwayTeam(Integer shotsOnGoalAwayTeam) {
		this.shotsOnGoalAwayTeam = shotsOnGoalAwayTeam;
	}

	public Integer getShotsOffGoalHomeTeam() {
		return shotsOffGoalHomeTeam;
	}

	public void setShotsOffGoalHomeTeam(Integer shotsOffGoalHomeTeam) {
		this.shotsOffGoalHomeTeam = shotsOffGoalHomeTeam;
	}

	public Integer getShotsOffGoalAwayTeam() {
		return shotsOffGoalAwayTeam;
	}

	public void setShotsOffGoalAwayTeam(Integer shotsOffGoalAwayTeam) {
		this.shotsOffGoalAwayTeam = shotsOffGoalAwayTeam;
	}

	public Integer getOffsidesHomeTeam() {
		return offsidesHomeTeam;
	}

	public void setOffsidesHomeTeam(Integer offsidesHomeTeam) {
		this.offsidesHomeTeam = offsidesHomeTeam;
	}

	public Integer getOffsidesAwayTeam() {
		return offsidesAwayTeam;
	}

	public void setOffsidesAwayTeam(Integer offsidesAwayTeam) {
		this.offsidesAwayTeam = offsidesAwayTeam;
	}

	public Integer getCornersHomeTeam() {
		return cornersHomeTeam;
	}

	public void setCornersHomeTeam(Integer cornersHomeTeam) {
		this.cornersHomeTeam = cornersHomeTeam;
	}

	public Integer getCornersAwayTeam() {
		return cornersAwayTeam;
	}

	public void setCornersAwayTeam(Integer cornersAwayTeam) {
		this.cornersAwayTeam = cornersAwayTeam;
	}

	public Integer getFreekicksHomeTeam() {
		return freekicksHomeTeam;
	}

	public void setFreekicksHomeTeam(Integer freekicksHomeTeam) {
		this.freekicksHomeTeam = freekicksHomeTeam;
	}

	public Integer getFreekicksAwayTeam() {
		return freekicksAwayTeam;
	}

	public void setFreekicksAwayTeam(Integer freekicksAwayTeam) {
		this.freekicksAwayTeam = freekicksAwayTeam;
	}

	public Integer getThrowinsHomeTeam() {
		return throwinsHomeTeam;
	}

	public void setThrowinsHomeTeam(Integer throwinsHomeTeam) {
		this.throwinsHomeTeam = throwinsHomeTeam;
	}

	public Integer getThrowinsAwayTeam() {
		return throwinsAwayTeam;
	}

	public void setThrowinsAwayTeam(Integer throwinsAwayTeam) {
		this.throwinsAwayTeam = throwinsAwayTeam;
	}

	public Integer getPossessionHomeTeam() {
		return possessionHomeTeam;
	}

	public void setPossessionHomeTeam(Integer possessionHomeTeam) {
		this.possessionHomeTeam = possessionHomeTeam;
	}

	public Integer getPossessionAwayTeam() {
		return possessionAwayTeam;
	}

	public void setPossessionAwayTeam(Integer possessionAwayTeam) {
		this.possessionAwayTeam = possessionAwayTeam;
	}
	
	
}
