package se.ifkgoteborg.stat.dto;

import java.util.Date;
import java.util.List;

public class FullSquadSeasonDTO extends SquadSeasonDTO {
	private List<PlayerSeasonDTO> squad;
	private List<TournamentSeasonDTO> tournamentSeasons;

	public FullSquadSeasonDTO() {}
	
	public FullSquadSeasonDTO(Long id, String name, Date startYear, Date endYear) {
		super(id, name, startYear, endYear);
	}
	public List<PlayerSeasonDTO> getSquad() {
		return squad;
	}
	public void setSquad(List<PlayerSeasonDTO> squad) {
		this.squad = squad;
	}
	public List<TournamentSeasonDTO> getTournamentSeasons() {
		return tournamentSeasons;
	}
	public void setTournamentSeasons(List<TournamentSeasonDTO> tournamentSeasons) {
		this.tournamentSeasons = tournamentSeasons;
	}
	
	
}
