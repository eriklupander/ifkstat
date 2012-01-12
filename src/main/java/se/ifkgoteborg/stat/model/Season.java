package se.ifkgoteborg.stat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="season")
public class Season {

	@Id
	@GeneratedValue
	private Long id;
	
	private int startYear;
	
	private int endYear;
	
	private String name;
	
	private Season() {}
	
	public Season(String name) {
		System.out.println("creating new season based on name: " + name);
		this.name = name;
		if(name.indexOf("/") > -1) {
			String[] parts = name.split("/");
			if(parts.length > 0)
				if(parts[0].trim().length() == 2) {
					parts[0] = "19" + parts[0].trim();
				}
				
				startYear = Integer.parseInt(parts[0].trim());
			if(parts.length > 1) {
				endYear = Integer.parseInt(parts[0].substring(0, 2) + "" + parts[1].trim());
			} else {
				endYear = startYear;
			}
		} else {
			startYear = Integer.parseInt(name.trim());
			endYear = startYear;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
