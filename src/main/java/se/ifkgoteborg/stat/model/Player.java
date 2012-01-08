package se.ifkgoteborg.stat.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="player")
public class Player {
	
	@Id
	@GeneratedValue
	private Long id;

	
	private String name;
	private String fullName;
	
	@Temporal(TemporalType.DATE)
	private Calendar dateOfBirth;
	
	@ManyToOne
	private Country nationality;
	private Integer length;
	private Integer weight;
	
	private Integer squadNumber = -1;
	private String biography;
	
	@ManyToOne
	private Position position;
	
	@OneToMany
	private List<PlayedForClub> clubs = new ArrayList<PlayedForClub>();
	
	@Enumerated
	private Gender gender;
	
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Country getNationality() {
		return nationality;
	}
	public void setNationality(Country nationality) {
		this.nationality = nationality;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	

	public Integer getSquadNumber() {
		if(squadNumber == null) {
			return -1;
		}
		return squadNumber;
	}

	public void setSquadNumber(Integer squadNumber) {
		this.squadNumber = squadNumber;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public List<PlayedForClub> getClubs() {
		return clubs;
	}

	public void setClubs(List<PlayedForClub> clubs) {
		this.clubs = clubs;
	}
	
	
	
}
