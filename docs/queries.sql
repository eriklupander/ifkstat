# Lista alla matcher en spelare har spelat.
SELECT p.name, c1.name, c2.name, g.daTEOFGAME, t.name, ts.seasonname   FROM PLAYER p INNER JOIN PLAYER_GAME gp ON gp.player_ID = p.id INNER JOIN game g ON g.id=gp.game_id INNER JOIN CLUB c1 ON c1.id=g.hometeam_id INNER JOIN CLUB c2 ON c2.id=g.awayteam_id INNER JOIN TOURNAMENT_SEASON ts ON ts.id=g.tournamentseason_id INNER JOIN tournament t ON t.id = ts.tournament_id WHERE p.name='Mikael Nilsson'

# Lista alla matcher en spelare har spelat, sorterat per turnering och �r
SELECT p.name, c1.name, c2.name, g.daTEOFGAME, t.name, ts.seasonname, ts.start   FROM PLAYER p INNER JOIN PLAYER_GAME gp ON gp.player_ID = p.id INNER JOIN game g ON g.id=gp.game_id INNER JOIN CLUB c1 ON c1.id=g.hometeam_id INNER JOIN CLUB c2 ON c2.id=g.awayteam_id INNER JOIN TOURNAMENT_SEASON ts ON ts.id=g.tournamentseason_id INNER JOIN tournament t ON t.id = ts.tournament_id WHERE p.name='Mikael Nilsson' ORDER BY ts.start DESC, t.name ASC


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
where p.name='Jakob Johansson' 
GROUP BY p.name, pos.name

# Antal matcher på viss position, med formation och antal mål
select pos.name, f.name, count(pos.id) as gcount, count(ge.id) as goals from player p 
inner join player_game pg ON pg.player_id=p.id 
inner join formation_position fp ON pg.formationposition_id=fp.id  
inner join formation f ON f.id=fp.formation_id
inner join position pos ON pos.id=fp.position_id
inner join game g ON g.id=pg.game_id
left outer join game_event ge ON ge.game_id=g.id AND ge.player_id=p.id AND ge.eventtype='GOAL'
where p.name='Nicklas Bärkroth' 
GROUP BY pos.name, f.name
ORDER BY gcount DESC

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
	WHERE pg.playER_ID = 29526) 
GROUP BY name ORDER BY name DESC

# Lista alla matcher d�r IFK legat under med 0-3 i paus
select ht.name hemmlag, awt.name bortalag, g.name, dateofgame, homegoals, awaygoals, homegoalshalftime, awaygoalshalftime from game i
nner join ground g ON g.id=ground_id inner join club ht ON ht.id = hometeam_id inner join club awt ON awt.id=awayteam_id where homegoalshalf
time = 0 and awaygoalshalftime = 3 ORDER BY dateOfGame;

# Lista alla matcher som slutat 3-3 eller mer...
select ht.name hemmlag, awt.name bortalag, g.name, dateofgame, homegoals, awaygoals from game inner join ground g ON g.id=ground_id inner join club ht ON ht.id = hometeam_id inner join club awt ON awt.id=awayteam_id where homegoals >= 0 and awaygoals >= 3 ORDER BY dateOfGame;

# Alla matcher f�r en viss spelare p� en viss position
select p.name, pos.name, g.dateofgame, ht.name, at.name, pg.participationType from player p 
inner join player_game pg ON pg.player_id=p.id
inner join game g ON pg.game_id=g.id
inner join club ht ON g.hometeam_id=ht.id
inner join club at ON g.awayteam_id=at.id
inner join formation_position fp ON pg.formationposition_id=fp.id  
inner join position pos ON pos.id=fp.position_id
WHERE pos.name like 'V%nsterytter' AND p.name = 'Jakob Johansson'

# M�l av Torbj�rn Nilsson f�rdelat per turnering
select count(ge.id) as goals, t.name from player p 
inner join player_game pg ON pg.player_id=p.id
inner join game g ON pg.game_id=g.id
inner join game_event ge ON ge.game_id=g.id
inner join player p2 ON p2.id = ge.player_id AND p2.id = p.id
inner join tournament_season ts ON ts.id = g.tournamentseason_id
inner join tournament t ON t.id = ts.tournament_id
WHERE p.name like 'Torbj%rn Nilsson' AND ge.eventType = 'GOAL'
GROUP BY t.name

# Lista m�l och antal matcher per turnering av spelare
SELECT t.name, COUNT(ge.id) as goals FROM player p 
INNER JOIN game_event ge ON ge.player_id=p.id 
INNER JOIN game g ON g.id = ge.game_id
INNER JOIN tournament_season ts ON ts.id = g.tournamentseason_id
INNER JOIN tournament t ON t.id = ts.tournament_id
WHERE ge.EVENTTYPE ='GOAL' AND p.id=54533 GROUP BY t.name ORDER BY goals DESC


# Lista snittm�l, snittpo�ng f�r spelare i turneringar
SELECT t.name, COUNT(ge.id) as goals, COUNT(pg.id) as appearances, 
COUNT(ge2.id) as inbytt, COUNT(ge3.id) as utbytt, COUNT(ge4.id) as goals_as_subst
, COUNT(ge5.id) as goals_as_subst_out
FROM player p 
INNER JOIN player_game pg ON pg.player_id=p.id
INNER JOIN game g ON g.id = pg.game_id
INNER JOIN tournament_season ts ON ts.id = g.tournamentseason_id
INNER JOIN tournament t ON t.id = ts.tournament_id
LEFT OUTER JOIN game_event ge ON ge.player_id=p.id AND ge.game_id=g.id AND ge.EVENTTYPE ='GOAL'
LEFT OUTER JOIN game_event ge2 ON ge2.player_id=p.id AND ge2.game_id=g.id AND ge2.EVENTTYPE ='SUBSTITUTION_IN'
LEFT OUTER JOIN game_event ge3 ON ge3.player_id=p.id AND ge3.game_id=g.id AND ge3.EVENTTYPE ='SUBSTITUTION_OUT'
LEFT OUTER JOIN game_event ge4 ON ge4.player_id=p.id AND ge4.game_id=g.id AND (ge4.EVENTTYPE ='SUBSTITUTION_IN' AND ge.EVENTTYPE ='GOAL')
LEFT OUTER JOIN game_event ge5 ON ge5.player_id=p.id AND ge5.game_id=g.id AND (ge5.EVENTTYPE ='SUBSTITUTION_OUT' AND ge.EVENTTYPE ='GOAL')
WHERE p.id=44829 
GROUP BY t.name ORDER BY goals DESC;



#Matcher per position, mål.
SELECT p.name, pos.name, f.name, j1.games, j2.goals FROM
player p 
inner join player_game pg ON pg.player_id=p.id
INNER JOIN formation_position fp ON pg.formationposition_id=fp.id
INNER JOIN formation f ON f.id=fp.formation_id
INNER JOIN position pos ON pos.id=fp.position_id
LEFT OUTER JOIN
				(select  p.id as playerId, pos.name as posname, count(pg.id) as games from player p 
inner join player_game pg ON pg.player_id=p.id
inner join formation_position fp ON pg.formationposition_id=fp.id  
inner join formation f ON f.id=fp.formation_id
inner join position pos ON pos.id=fp.position_id
WHERE p.id=43816
GROUP BY (p.id, pos.name)
			 	) j1 
			 	ON j1.playerId=43816 AND j1.posname = pos.name

LEFT OUTER JOIN
				(select  p.id as playerId, pos.name as posname, count(ge.id) as goals from player p 
inner join player_game pg ON pg.player_id=p.id
inner join formation_position fp ON pg.formationposition_id=fp.id  
inner join formation f ON f.id=fp.formation_id
inner join position pos ON pos.id=fp.position_id
inner join game g ON g.id=pg.game_id
left outer join game_event ge ON ge.player_id=p.id AND ge.game_id=g.id
WHERE p.id=43816  AND ge.eventtype='GOAL'
GROUP BY (p.id, pos.name)
			 	) j2 
			 	ON j2.playerId=43816 AND j2.posname = pos.name
WHERE p.id=43816 
GROUP BY (p.name, pos.name, f.name, j1.games)


# Resultat i matcher där spelaren EJ deltog, men fanns med i truppen det året.
SELECT 'NO_PART',COUNT(g2.id)  + COUNT(g5.id) wins, COUNT(g3.id)  + COUNT(g4.id) losses, COUNT(g6.id)  + COUNT(g7.id) draws FROM game g 
INNER JOIN tournament_season ts ON ts.id=g.tournamentseason_id
LEFT OUTER JOIN game g2 ON g2.id=g.id AND g2.homeGoals > g2.awayGoals AND g2.hometeam_id=110 
LEFT OUTER JOIN game g3 ON g3.id=g.id AND g3.homeGoals < g3.awayGoals AND g3.hometeam_id=110 
LEFT OUTER JOIN game g6 ON g6.id=g.id AND g6.homeGoals = g6.awayGoals AND g6.hometeam_id=110 
LEFT OUTER JOIN game g4 ON g4.id=g.id AND g4.homeGoals > g4.awayGoals AND g4.awayteam_id=110 
LEFT OUTER JOIN game g5 ON g5.id=g.id AND g5.homeGoals < g5.awayGoals AND g5.awayteam_id=110 
LEFT OUTER JOIN game g7 ON g7.id=g.id AND g7.homeGoals = g7.awayGoals AND g7.awayteam_id=110
WHERE g.id NOT IN (
SELECT g.id FROM player_game pg INNER JOIN game g ON g.id=pg.game_id WHERE pg.player_id=36837
)
AND ts.id IN 
(
SELECT DISTINCT(ts.id)  FROM game g 
INNER JOIN tournament_season ts ON ts.id=g.tournamentseason_id
INNER JOIN player_game pg ON pg.game_id=g.id
WHERE pg.player_id=36837 
GROUP BY ts.id
HAVING COUNT(pg.id) > 5
)