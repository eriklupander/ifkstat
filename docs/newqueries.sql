SELECT c.name, j1.g1, j2.g2, j1.hg, j1.ag, j2.hg, j2.ag, j1.wins, j1.draws, j1.losses, j2.wins, j2.draws, j2.losses FROM club c
LEFT OUTER JOIN
(
SELECT c1.id as j1id, count(g.id) as g1, sum(g.homeGoals) as hg, sum(g.awayGoals) as ag, COUNT(g2.id) as wins, COUNT(g3.id) as draws, COUNT(g4.id) as losses 
FROM game g
LEFT OUTER JOIN club c1 ON c1.id=g.hometeam_id
LEFT OUTER JOIN game g2 ON g2.id=g.id AND g2.homegoals > g2.awaygoals
LEFT OUTER JOIN game g3 ON g3.id=g.id AND g3.homegoals = g3.awaygoals
LEFT OUTER JOIN game g4 ON g4.id=g.id AND g4.homegoals < g4.awaygoals
WHERE c1.id <> 110
GROUP BY c1.id
) as j1
ON j1id=c.id

LEFT OUTER JOIN

(SELECT c2.id as j2id, count(g.id) as g2, sum(g.homeGoals) as hg, sum(g.awayGoals) as ag, COUNT(g2.id) as wins, COUNT(g3.id) as draws, COUNT(g4.id) as losses  
FROM game g
LEFT OUTER JOIN club c2 ON c2.id=g.awayteam_id
LEFT OUTER JOIN game g2 ON g2.id=g.id AND g2.homegoals > g2.awaygoals
LEFT OUTER JOIN game g3 ON g3.id=g.id AND g3.homegoals = g3.awaygoals
LEFT OUTER JOIN game g4 ON g4.id=g.id AND g4.homegoals < g4.awaygoals
WHERE c2.id <> 110
GROUP BY c2.id
) j2
ON j2.j2id = c.id