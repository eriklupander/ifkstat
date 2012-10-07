package se.ifkgoteborg.stat.dto;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatDTO {
	private List<GoalsPerTournamentDTO> goalsPerTournament = new ArrayList<GoalsPerTournamentDTO>();
	private List<GamePositionStatDTO> gamesPerPosition = new ArrayList<GamePositionStatDTO>();
	private List<PlayedWithPlayerDTO> playedWithPlayer = new ArrayList<PlayedWithPlayerDTO>();
	
	public List<GoalsPerTournamentDTO> getGoalsPerTournament() {
		return goalsPerTournament;
	}
	public void setGoalsPerTournament(List<GoalsPerTournamentDTO> goalsPerTournament) {
		this.goalsPerTournament = goalsPerTournament;
	}
	public List<GamePositionStatDTO> getGamesPerPosition() {
		return gamesPerPosition;
	}
	public void setGamesPerPosition(List<GamePositionStatDTO> gamesPerPosition) {
		this.gamesPerPosition = gamesPerPosition;
	}
	public List<PlayedWithPlayerDTO> getPlayedWithPlayer() {
		return playedWithPlayer;
	}
	public void setPlayedWithPlayer(List<PlayedWithPlayerDTO> playedWithPlayer) {
		this.playedWithPlayer = playedWithPlayer;
	}
	
	
}
