package se.ifkgoteborg.stat.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ground")
public class Ground {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private Integer maxCapacity;
	
	@Temporal(TemporalType.DATE)
	private Calendar dateOfConstruction;
	
	@OneToMany
	private List<Club> homeTeams = new ArrayList<Club>();
	
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

	public Integer getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public Calendar getDateOfConstruction() {
		return dateOfConstruction;
	}

	public void setDateOfConstruction(Calendar dateOfConstruction) {
		this.dateOfConstruction = dateOfConstruction;
	}

	public List<Club> getHomeTeams() {
		return homeTeams;
	}

	public void setHomeTeams(List<Club> homeTeams) {
		this.homeTeams = homeTeams;
	}

	
}
