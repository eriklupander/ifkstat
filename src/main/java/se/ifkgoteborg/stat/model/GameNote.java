package se.ifkgoteborg.stat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="game_note")
public class GameNote {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Game game;
	
	@Column(name="note_text", length=1024, nullable=true)
	private String text;
	
	private GameNote() {}

	public GameNote(Game game, String text) {
		this.game = game;		
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
