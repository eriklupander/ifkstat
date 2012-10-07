# Lista alla matcher en spelare har spelat.
SELECT p.name, c1.name, c2.name, g.daTEOFGAME, t.name, ts.seasonname   FROM PLAYER p INNER JOIN PLAYER_GAME gp ON gp.player_ID = p.id INNER JOIN game g ON g.id=gp.game_id INNER JOIN CLUB c1 ON c1.id=g.hometeam_id INNER JOIN CLUB c2 ON c2.id=g.awayteam_id INNER JOIN TOURNAMENT_SEASON ts ON ts.id=g.tournamentseason_id INNER JOIN tournament t ON t.id = ts.tournament_id WHERE p.name='Mikael Nilsson'

# Lista alla matcher en spelare har spelat, sorterat per turnering och år
SELECT p.name, c1.name, c2.name, g.daTEOFGAME, t.name, ts.seasonname, ts.start   FROM PLAYER p INNER JOIN PLAYER_GAME gp ON gp.player_ID = p.id INNER JOIN game g ON g.id=gp.game_id INNER JOIN CLUB c1 ON c1.id=g.hometeam_id INNER JOIN CLUB c2 ON c2.id=g.awayteam_id INNER JOIN TOURNAMENT_SEASON ts ON ts.id=g.tournamentseason_id INNER JOIN tournament t ON t.id = ts.tournament_id WHERE p.name='Mikael Nilsson' ORDER BY ts.start DESC, t.name ASC


# Lista alla spelare, med antal matcher i fallande ordning
SELECT p.name, COUNT(gp.id) as aaa FROM player p INNER JOIN player_game gp ON gp.player_id=p.id GROUP BY p.name ORDER BY aaa DESC

# Lista alla spelare, med antal gjorda mï¿½l i fallande ordning
SELECT p.name, COUNT(ge.id) as goals FROM player p INNER JOIN game_event ge ON ge.player_id=p.id WHERE ge.EVENTTYPE ='GOAL' GROUP BY p.name ORDER BY goals DESC

# Flest matcher i Champions League (eller annan turnering)
SELECT p.name, COUNT(ts.id) as games FROM PLAYER p INNER JOIN PLAYER_GAME pg ON pg.player_ID = p.id INNER JOIN game g ON g.id=pg.game_id INNER JOIN TOURNAMENT_SEASON ts ON ts.id=g.tournamentseason_id INNER JOIN TOURNAMENT t ON t.id=ts.tournament_id WHERE t.name='CHAMPIONS LEAGUE' GROUP BY p.name ORDER BY games DESC

# Antal matcher per position fÃ¶r en viss spelare (Erlingmark)
select p.name, pos.name, count(pos.id) as gcount from player p 
inner join player_game pg ON pg.player_id=p.id 
inner join formation_position fp ON pg.formationposition_id=fp.id  
inner join position pos ON pos.id=fp.position_id
where p.name='Jakob Johansson' 
GROUP BY p.name, pos.name

# Antal matcher pÃ¥ en viss position
select p.name, pos.name, count(pos.id) as gcount from player p 
inner join player_game pg ON pg.player_id=p.id 
inner join formation_position fp ON pg.formationposition_id=fp.id  
inner join position pos ON pos.id=fp.position_id
WHERE pos.name = 'Innermitt'
GROUP BY p.name, pos.name
ORDER BY gcount DESC

# Lista alla unika spelare som en viss spelare spelat med, hur mÃ¥nga gÃ¥nger, samt fÃ¶rsta och sista matchdatum
SELECT distinct(p1.name) as name, COUNT(pg1.game_id) as cnt, MIN(g1.dateOfGame) as firstGame, MAX(g1.dateOfGame) as lastGame 
FROM PLAYER_GAME pg1 
INNER JOIN PLAYER p1 ON p1.id=pg1.player_id 
INNER JOIN GAME g1 ON g1.id=pg1.game_id 
WHERE pg1.game_id IN 
	(SELECT pg.game_id FROM GAME g 
	INNER JOIN PLAYER_GAME pg ON pg.game_id=g.id 
	WHERE pg.playER_ID = 29526) 
GROUP BY name ORDER BY name DESC

# Lista alla matcher där IFK legat under med 0-3 i paus
select ht.name hemmlag, awt.name bortalag, g.name, dateofgame, homegoals, awaygoals, homegoalshalftime, awaygoalshalftime from game i
nner join ground g ON g.id=ground_id inner join club ht ON ht.id = hometeam_id inner join club awt ON awt.id=awayteam_id where homegoalshalf
time = 0 and awaygoalshalftime = 3 ORDER BY dateOfGame;

# Lista alla matcher som slutat 3-3 eller mer...
select ht.name hemmlag, awt.name bortalag, g.name, dateofgame, homegoals, awaygoals from game inner join ground g ON g.id=ground_id inner join club ht ON ht.id = hometeam_id inner join club awt ON awt.id=awayteam_id where homegoals >= 0 and awaygoals >= 3 ORDER BY dateOfGame;

# Alla matcher för en viss spelare på en viss position
select p.name, pos.name, g.dateofgame, ht.name, at.name, pg.participationType from player p 
inner join player_game pg ON pg.player_id=p.id
inner join game g ON pg.game_id=g.id
inner join club ht ON g.hometeam_id=ht.id
inner join club at ON g.awayteam_id=at.id
inner join formation_position fp ON pg.formationposition_id=fp.id  
inner join position pos ON pos.id=fp.position_id
WHERE pos.name like 'V%nsterytter' AND p.name = 'Jakob Johansson'

# Mål av Torbjörn Nilsson fördelat per turnering
select count(ge.id) as goals, t.name from player p 
inner join player_game pg ON pg.player_id=p.id
inner join game g ON pg.game_id=g.id
inner join game_event ge ON ge.game_id=g.id
inner join player p2 ON p2.id = ge.player_id AND p2.id = p.id
inner join tournament_season ts ON ts.id = g.tournamentseason_id
inner join tournament t ON t.id = ts.tournament_id
WHERE p.name like 'Torbj%rn Nilsson' AND ge.eventType = 'GOAL'
GROUP BY t.name

# Lista mål per turnering av spelare
SELECT t.name, COUNT(ge.id) as goals FROM player p 
INNER JOIN game_event ge ON ge.player_id=p.id 
INNER JOIN tournament_season ts ON ts.id = g.tournamentseason_id
INNER JOIN tournament t ON t.id = ts.tournament_id
WHERE ge.EVENTTYPE ='GOAL' GROUP BY t.name ORDER BY goals DESC
