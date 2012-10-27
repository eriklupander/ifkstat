package se.ifkgoteborg.stat.dto;

import java.io.Serializable;
import java.util.Date;

public class SquadSeasonDTO implements Serializable {
	private Long id;
	private String name;
	private Date startYear;
	private Date endYear;
	
	public SquadSeasonDTO() {}
	
	public SquadSeasonDTO(Long id, String name, Date startYear, Date endYear) {
		super();
		this.id = id;
		this.name = name;
		this.startYear = startYear;
		this.endYear = endYear;
	}
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
	
	
}
