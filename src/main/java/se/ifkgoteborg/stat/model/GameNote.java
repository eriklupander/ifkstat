package se.ifkgoteborg.stat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="game_note")
public class GameNote {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="note_text", length=1024, nullable=true)
	private String text;
	
	private GameNote() {}

	public GameNote(String text) {		
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
