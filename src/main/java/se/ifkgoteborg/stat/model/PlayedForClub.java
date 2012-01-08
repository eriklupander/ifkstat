package se.ifkgoteborg.stat.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="player_club")
public class PlayedForClub {
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Player player;
	
	@ManyToOne
	private Club club;
	
	@Temporal(TemporalType.DATE)
	private Calendar fromDate;
	
	@Temporal(TemporalType.DATE)
	private Calendar toDate;
	
	private Integer squadNr;
	private Integer importIndex;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Club getClub() {
		return club;
	}
	public void setClub(Club club) {
		this.club = club;
	}
	public Calendar getFromDate() {
		return fromDate;
	}
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}
	public Calendar getToDate() {
		return toDate;
	}
	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}

	public Integer getSquadNr() {
		return squadNr;
	}

	public void setSquadNr(Integer squadNr) {
		this.squadNr = squadNr;
	}

	public Integer getImportIndex() {
		return importIndex;
	}

	public void setImportIndex(Integer importIndex) {
		this.importIndex = importIndex;
	}
	
	
}
