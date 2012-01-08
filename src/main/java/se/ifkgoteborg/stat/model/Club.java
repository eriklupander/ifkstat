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
@Table(name="club")
public class Club {
	
	@Id
	@GeneratedValue
	private Long id;

	
	private String name;
	private String city;
	
	private Boolean defaultClub = false;
	
	@ManyToOne
	private Country country;
	
	@Temporal(value=TemporalType.DATE)
	private Calendar foundedDate;
	
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Calendar getFoundedDate() {
		return foundedDate;
	}
	public void setFoundedDate(Calendar foundedDate) {
		this.foundedDate = foundedDate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Boolean getDefaultClub() {
		return defaultClub;
	}

	public void setDefaultClub(Boolean defaultClub) {
		this.defaultClub = defaultClub;
	}
	
	
	
	
}
