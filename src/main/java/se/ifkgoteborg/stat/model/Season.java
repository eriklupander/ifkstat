package se.ifkgoteborg.stat.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import se.ifkgoteborg.stat.util.DateFactory;

@Entity
@Table(name="season")
public class Season {

	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(value=TemporalType.DATE)
	private Date startYear;
	
	@Temporal(value=TemporalType.DATE)
	private Date endYear;
	
	private String name;
	
	@OneToMany(mappedBy="season")
	private List<PlayedForClub> squad = new ArrayList<PlayedForClub>();
	
	private Season() {}
	
	public Season(String name, int startYearInt, int endYearInt) {
		if(startYearInt < 1900 || endYearInt < 1900) {
			throw new IllegalArgumentException("Cannot create Season instance, supplied year was < 1900. Season name: " + name + ". Start year: " + startYear + " endYear: " + endYear);
		}
		System.out.println("creating new season based on name: " + name);
		this.name = name;
		
		setStartYear(DateFactory.get(startYearInt, 0, 1));
		setEndYear(DateFactory.get(endYearInt, 11, 31));

	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Date getStartYear() {
		return startYear;
	}

	public void setStartYear(Date startYear) {
		this.startYear = startYear;
	}

	public Date getEndYear() {
		return endYear;
	}

	public void setEndYear(Date endYear) {
		this.endYear = endYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PlayedForClub> getSquad() {
		return squad;
	}

	public void setSquad(List<PlayedForClub> squad) {
		this.squad = squad;
	}

	public String toString() {
		return this.name;
	}
}
