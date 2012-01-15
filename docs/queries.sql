# Lista alla matcher en spelare har spelat.
SELECT p.name, c1.name, c2.name, g.daTEOFGAME, t.name, ts.seasonname   FROM PLAYER p INNER JOIN PLAYER_GAME gp ON gp.player_ID = p.id INNER JOIN game g ON g.id=gp.game_id INNER JOIN CLUB c1 ON c1.id=g.hometeam_id INNER JOIN CLUB c2 ON c2.id=g.awayteam_id INNER JOIN TOURNAMENT_SEASON ts ON ts.id=g.tournamentseason_id INNER JOIN tournament t ON t.id = ts.tournament_id WHERE p.name='Mikael Nilsson'

# Lista alla spelare, med antal matcher i fallande ordning
SELECT p.name, COUNT(gp.id) as aaa FROM player p INNER JOIN player_game gp ON gp.player_id=p.id GROUP BY p.name ORDER BY aaa DESC

# Lista alla spelare, med antal gjorda mål i fallande ordning
SELECT p.name, COUNT(ge.id) as goals FROM player p INNER JOIN game_event ge ON ge.player_id=p.id WHERE ge.EVENTTYPE ='GOAL' GROUP BY p.name ORDER BY goals DESC

# Flest matcher i Champions League (eller annan turnering)
SELECT p.name, COUNT(ts.id) as games FROM PLAYER p INNER JOIN PLAYER_GAME pg ON pg.player_ID = p.id INNER JOIN game g ON g.id=pg.game_id INNER JOIN TOURNAMENT_SEASON ts ON ts.id=g.tournamentseason_id INNER JOIN TOURNAMENT t ON t.id=ts.tournament_id WHERE t.name='CHAMPIONS LEAGUE' GROUP BY p.name ORDER BY games DESC 