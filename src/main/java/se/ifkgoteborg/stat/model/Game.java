package se.ifkgoteborg.stat.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Game {
	
	@Id
	@GeneratedValue
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	private Calendar dateOfGame;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private TournamentSeason tournamentSeason;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Club homeTeam;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Club awayTeam;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Ground ground;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Referee referee;
	
//	@OneToMany
//	private List<Goal> homeGoals;
//	
//	@OneToMany
//	private List<Goal> awayGoals;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<GameEvent> events = new ArrayList<GameEvent>();
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<GameParticipation> gameParticipation = new ArrayList<GameParticipation>();
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Formation formation;
	
	private Integer homeFreekicks;
	private Integer awayFreekicks;
	
	private Integer homeCorners;
	private Integer awayCorners;
	
	private Integer attendance;
	
	private String gameSummary;
	private Integer homeGoals;
	private Integer awayGoals;
	private Integer homeGoalsHalftime;
	private Integer awayGoalsHalftime;
	
	public Calendar getDateOfGame() {
		return dateOfGame;
	}
	public Date getDateOfGameAsDate() {
		return dateOfGame.getTime();
	}
	public void setDateOfGame(Calendar dateOfGame) {
		this.dateOfGame = dateOfGame;
	}
	public Club getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(Club homeTeam) {
		this.homeTeam = homeTeam;
	}
	public Club getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(Club awayTeam) {
		this.awayTeam = awayTeam;
	}
	public Referee getReferee() {
		return referee;
	}
	public void setReferee(Referee referee) {
		this.referee = referee;
	}
	public List<GameEvent> getEvents() {
		return events;
	}
	public void setEvents(List<GameEvent> events) {
		this.events = events;
	}
	public Integer getHomeFreekicks() {
		return homeFreekicks;
	}
	public void setHomeFreekicks(Integer homeFreekicks) {
		this.homeFreekicks = homeFreekicks;
	}
	public Integer getAwayFreekicks() {
		return awayFreekicks;
	}
	public void setAwayFreekicks(Integer awayFreekicks) {
		this.awayFreekicks = awayFreekicks;
	}
	public Integer getHomeCorners() {
		return homeCorners;
	}
	public void setHomeCorners(Integer homeCorners) {
		this.homeCorners = homeCorners;
	}
	public Integer getAwayCorners() {
		return awayCorners;
	}
	public void setAwayCorners(Integer awayCorners) {
		this.awayCorners = awayCorners;
	}
	public Ground getGround() {
		return ground;
	}
	public void setGround(Ground ground) {
		this.ground = ground;
	}
	public Integer getAttendance() {
		return attendance;
	}
	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}
	public String getGameSummary() {
		return gameSummary;
	}
	public void setGameSummary(String gameSummary) {
		this.gameSummary = gameSummary;
	}

	public Integer getHomeGoals() {
		return homeGoals;
	}

	public void setHomeGoals(Integer homeGoals) {
		this.homeGoals = homeGoals;
	}

	public Integer getAwayGoals() {
		return awayGoals;
	}

	public void setAwayGoals(Integer awayGoals) {
		this.awayGoals = awayGoals;
	}

	public Integer getHomeGoalsHalftime() {
		return homeGoalsHalftime;
	}

	public void setHomeGoalsHalftime(Integer homeGoalsHalftime) {
		this.homeGoalsHalftime = homeGoalsHalftime != null ? homeGoalsHalftime : 0;
	}

	public Integer getAwayGoalsHalftime() {
		return awayGoalsHalftime != null ? awayGoalsHalftime : 0;
	}

	public void setAwayGoalsHalftime(Integer awayGoalsHalftime) {
		this.awayGoalsHalftime = awayGoalsHalftime;
	}

	public List<GameParticipation> getGameParticipation() {
		return gameParticipation;
	}

	public void setGameParticipation(List<GameParticipation> gameParticipation) {
		this.gameParticipation = gameParticipation;
	}

	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	public TournamentSeason getTournamentSeason() {
		return tournamentSeason;
	}

	public void setTournamentSeason(TournamentSeason tournamentSeason) {
		this.tournamentSeason = tournamentSeason;
	}
	
	

	@Transient
	public String getResultStr() {
		return getHomeGoals() + "-" + getAwayGoals() + " (" + getHomeGoalsHalftime() + "-" + getAwayGoalsHalftime() + ")";
	}
	
	
}
