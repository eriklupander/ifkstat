# Lista alla matcher en spelare har spelat.
SELECT p.name, c1.name, c2.name, g.daTEOFGAME, t.name, ts.seasonname   FROM PLAYER p INNER JOIN PLAYER_GAME gp ON gp.player_ID = p.id INNER JOIN game g ON g.id=gp.game_id INNER JOIN CLUB c1 ON c1.id=g.hometeam_id INNER JOIN CLUB c2 ON c2.id=g.awayteam_id INNER JOIN TOURNAMENT_SEASON ts ON ts.id=g.tournamentseason_id INNER JOIN tournament t ON t.id = ts.tournament_id WHERE p.name='Mikael Nilsson'

# Lista alla spelare, med antal matcher i fallande ordning
SELECT p.name, COUNT(gp.id) as aaa FROM player p INNER JOIN player_game gp ON gp.player_id=p.id GROUP BY p.name ORDER BY aaa DESC

# Lista alla spelare, med antal gjorda m�l i fallande ordning
SELECT p.name, COUNT(ge.id) as goals FROM player p INNER JOIN game_event ge ON ge.player_id=p.id WHERE ge.EVENTTYPE ='GOAL' GROUP BY p.name ORDER BY goals DESC

# Flest matcher i Champions League (eller annan turnering)
SELECT p.name, COUNT(ts.id) as games FROM PLAYER p INNER JOIN PLAYER_GAME pg ON pg.player_ID = p.id INNER JOIN game g ON g.id=pg.game_id INNER JOIN TOURNAMENT_SEASON ts ON ts.id=g.tournamentseason_id INNER JOIN TOURNAMENT t ON t.id=ts.tournament_id WHERE t.name='CHAMPIONS LEAGUE' GROUP BY p.name ORDER BY games DESC

# Antal matcher per position för en viss spelare (Erlingmark)
select p.name, pos.name, count(pos.id) as gcount from player p 
inner join player_game pg ON pg.player_id=p.id 
inner join formation_position fp ON pg.formationposition_id=fp.id  
inner join position pos ON pos.id=fp.position_id
where p.name='Magnus Erlingmark' 
GROUP BY p.name, pos.name

# Antal matcher på en viss position
select p.name, pos.name, count(pos.id) as gcount from player p 
inner join player_game pg ON pg.player_id=p.id 
inner join formation_position fp ON pg.formationposition_id=fp.id  
inner join position pos ON pos.id=fp.position_id
WHERE pos.name = 'Innermitt'
GROUP BY p.name, pos.name
ORDER BY gcount DESC

# Lista alla unika spelare som en viss spelare spelat med, hur många gånger, samt första och sista matchdatum
SELECT distinct(p1.name) as name, COUNT(pg1.game_id) as cnt, MIN(g1.dateOfGame) as firstGame, MAX(g1.dateOfGame) as lastGame 
FROM PLAYER_GAME pg1 
INNER JOIN PLAYER p1 ON p1.id=pg1.player_id 
INNER JOIN GAME g1 ON g1.id=pg1.game_id 
WHERE pg1.game_id IN 
	(SELECT pg.game_id FROM GAME g 
	INNER JOIN PLAYER_GAME pg ON pg.game_id=g.id 
	WHERE pg.playER_ID = 38452) 
GROUP BY name ORDER BY name DESC