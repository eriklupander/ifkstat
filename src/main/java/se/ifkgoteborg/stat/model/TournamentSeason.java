package se.ifkgoteborg.stat.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tournament_season")
public class TournamentSeason {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Tournament tournament;
	
	@Temporal(value=TemporalType.DATE)
	private Date start;
	
	@OneToMany(mappedBy="tournamentSeason")
	private List<Game> games = new ArrayList<Game>();
	
	// Can be either a "pure" year such as 1993, or a cross-season string such as 1992/1993.
	@ManyToOne
	private Season season;
	
	/**
	 * This should be just the year or year/year combo. Eg. 1993 or 1992/1993.
	 */
	private String seasonName;
		
	private TournamentSeason() {}
	
	public TournamentSeason(Tournament tournament, Date startDate, Date endDate) {
		this.tournament = tournament;
		this.start = startDate;
		
		int sYear = startDate.getYear()+1900;
		int eYear = endDate.getYear()+1900;
		if(sYear == eYear) {
			setSeasonName("" + sYear);
		} else {
			setSeasonName(sYear + "/" + eYear);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}
	
	public String toString() {
		return this.seasonName;
	}
}
