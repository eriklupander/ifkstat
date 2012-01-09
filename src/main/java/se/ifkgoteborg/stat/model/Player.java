package se.ifkgoteborg.stat.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
	
	private Integer otherCompetitiveGames = 0;
	private Integer otherPracticeGames = 0;
	
	@ManyToOne
	private Position position;
	
	@OneToMany(fetch=FetchType.EAGER)
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

	public Integer getOtherCompetitiveGames() {
		return otherCompetitiveGames;
	}

	public void setOtherCompetitiveGames(Integer otherCompetitiveGames) {
		this.otherCompetitiveGames = otherCompetitiveGames;
	}

	public Integer getOtherPracticeGames() {
		return otherPracticeGames;
	}

	public void setOtherPracticeGames(Integer otherPracticeGames) {
		this.otherPracticeGames = otherPracticeGames;
	}
	
	public String toString() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
