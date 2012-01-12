package se.ifkgoteborg.stat.model;

import java.util.ArrayList;
import java.util.Calendar;
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
	private Calendar start;
	
	@OneToMany
	private List<Game> games = new ArrayList<Game>();
	
	// Can be either a "pure" year such as 1993, or a cross-season string such as 1992/1993.
	@ManyToOne
	private Season season;
		
	public TournamentSeason() {}
	
	public TournamentSeason(Tournament tournament, Calendar date) {
		this.tournament = tournament;
		this.start = date;
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

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
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
	
	
}
