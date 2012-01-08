package se.ifkgoteborg.stat.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="formation")
public class Formation {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
//	@OneToMany(cascade=CascadeType.ALL)
//	private List<Game> usedInGames = new ArrayList<Game>();
	
	public Formation() {}

	public Formation(String name) {
		this.name = name;
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

//	public List<Game> getUsedInGames() {
//		return usedInGames;
//	}
//
//	public void setUsedInGames(List<Game> usedInGames) {
//		this.usedInGames = usedInGames;
//	}
}
