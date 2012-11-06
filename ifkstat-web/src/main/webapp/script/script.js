$.urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    return results[1] || 0;
}

function showGamesVsTeam(id) {
	var params = {"id":id};
	var data = DataService.getGamesVsClub(params);
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function createGamesTable(data) {
	$('#example').dataTable( {
    	"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	           
                $('td:eq(0)', nRow).html( '<a href="game.html?id=' + aData.id + '">' + aData.dateOfGame + '</a>' );   
                if(aData.homeTeam.name != 'IFK Göteborg') {
                	$('td:eq(1)', nRow).html( '<a href="gamesVsTeam.html?id=' + aData.homeTeam.id + '">' + aData.homeTeam.name + '</a>');
                }
                if(aData.awayTeam.name != 'IFK Göteborg') {
                	$('td:eq(2)', nRow).html( '<a href="gamesVsTeam.html?id=' + aData.awayTeam.id + '">' + aData.awayTeam.name + '</a>');
                }
                
                // Result                
                $('td:eq(3)', nRow).html(aData.homeGoals + '-' + aData.awayGoals + ' (' + aData.homeGoalsHalftime + '-' + aData.awayGoalsHalftime + ')');
    	},
    	"bProcessing" : true,
        "aaData": data,
        "aoColumns": [
            { "mData": "dateOfGame", "sTitle": "Datum" },
            { "mData": "homeTeam.name", "sTitle": "Hemmalag" },
            { "mData": "awayTeam.name", "sTitle": "Bortalag" },
            { "mData": null, "sTitle": "Resultat" },
            { "mData": "tournamentSeason.tournament.name", "sTitle": "Turnering" },
            { "mData":"formation.name", "sTitle": "Formation" }
        ]
    } );
}

function showGamesOfPlayer(id) {
	var params = {"id":id};
	var data = DataService.getGamesOfPlayer(params);
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function showGamesOfPlayerInTournament(id, tournamentId) {
	var params = {"id":id, "tournamentId":tournamentId};
	var data = DataService.getGamesOfPlayerInTournament(params);
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function showGamesOfPlayerInTournamentGoalScored(id, tournamentId) {
	var params = {"id":id, "tournamentId":tournamentId};
	var data = DataService.getGamesOfPlayerInTournamentGoalScored(params);
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function showGamesOfPlayerInTournamentSubstIn(id, tournamentId) {
	var params = {"id":id, "tournamentId":tournamentId};
	var data = DataService.getGamesOfPlayerInTournamentSubstIn(params);
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function showGamesOfPlayerInTournamentSubstOut(id, tournamentId) {
	var params = {"id":id, "tournamentId":tournamentId};
	var data = DataService.getGamesOfPlayerInTournamentSubstOut(params);
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function editGameDetails(id) {
	var params = {"id":id};
	var data = DataService.getGame(params);
	var refereesData = DataService.getReferees();
	var referees = toComboBoxFormat(refereesData);
	
	var groundsData = DataService.getGrounds();
	var grounds = toComboBoxFormat(groundsData);
	   
	var clubsData = DataService.getClubs();
	var clubs = toComboBoxFormat(clubsData);
	
	$('#game_result').html(createSplitControl('game_result', data.homeGoals, data.awayGoals));
	$('#game_result_halftime').html(createSplitControl('game_result_halftime', data.homeGoalsHalftime, data.awayGoalsHalftime));
//	$('#game_attendance').html('<input type="text" id="attendance" value="' + data.attendance + '"/>');
//	$('#game_referee').html('<input id="referee_combobox" value="' + (data.referee != null ? data.referee.name : "") + '"/><input type="hidden" id="referee_combobox-id" value="' + (data.referee != null ? data.referee.id : "") + '"/>');
	
	createCombobox('game_opponent', 'club_combobox', clubs, (data.homeTeam.name != 'IFK Göteborg' ? data.homeTeam : data.awayTeam));
	createCombobox('game_ground', 'ground_combobox', grounds, data.ground);
	createCombobox('game_referee', 'referee_combobox', referees, data.referee);
	
	$('#editgamedetailshead').html('<span style="float:right;"><button id="savegamedetails" class="btn">Spara</button><button id="canceldetails" class="btn">Avbryt</button></span>');
	
	$('#savegamedetails').click(function() {
		var params = {};
		params.id = id;
		params.$entity = {
				attendance : 		$('#attendance').val(),
				homeGoals : 		$('#game_result_ht').val(),
				awayGoals : 		$('#game_result_at').val(),
				homeGoalsHalftime : $('#game_result_halftime_ht').val(),
				awayGoalsHalftime : $('#game_result_halftime_at').val(),
				referee : 			{	id:$( "#referee_combobox-id" ).val(),
						  				name:$( "#referee_combobox").val()
						  			},
			    ground : 			{	id:$( "#ground_combobox-id" ).val(),
			    		  				name:$( "#ground_combobox").val()
				  					}
			    
		};
		
		AdminDataService.saveGameDetails(params);
		window.location = "game.html?id=" + id;
	});
	$('#canceldetails').click(function() {
		window.location = "game.html?id=" + id;
	});
}

function editGameStatistics(id) {
	var params = {"id":id};
	var data = DataService.getGame(params);
	stats = data.gameStats;
	$('#gamestats_shots').html(createSplitControl('gamestats_shots', stats.shotsHomeTeam, stats.shotsAwayTeam));
	$('#gamestats_shotsongoal').html(createSplitControl('gamestats_shotsongoal', stats.shotsOnGoalHomeTeam, stats.shotsOnGoalAwayTeam));
	$('#gamestats_shotsoffgoal').html(createSplitControl('gamestats_shotsoffgoal', stats.shotsOffGoalHomeTeam, stats.shotsOffGoalAwayTeam));
	$('#gamestats_offsides').html(createSplitControl('gamestats_offsides', stats.offsidesHomeTeam, stats.offsidesAwayTeam));
	$('#gamestats_corners').html(createSplitControl('gamestats_corners', stats.cornersHomeTeam, stats.cornersAwayTeam));
	$('#gamestats_throwins').html(createSplitControl('gamestats_throwins', stats.throwinsHomeTeam, stats.throwinsAwayTeam));
	$('#gamestats_freekicks').html(createSplitControl('gamestats_freekicks', stats.freekicksHomeTeam, stats.freekicksAwayTeam));
	$('#gamestats_possession').html(createSplitControl('gamestats_possession', stats.possessionHomeTeam, stats.possessionAwayTeam));

	$('#editgamestatshead').html('<span style="float:right;"><button id="savestats" class="btn">Spara</button><button id="cancelstats" class="btn">Avbryt</button></span>');
	
	$('#savestats').click(function() {
		var gs = {
				shotsOnGoalHomeTeam : $('#gamestats_shotsongoal_ht').val(),
				shotsOnGoalAwayTeam : $('#gamestats_shotsongoal_at').val(),
				shotsOffGoalHomeTeam : $('#gamestats_shotsoffgoal_ht').val(),
				shotsOffGoalAwayTeam : $('#gamestats_shotsoffgoal_at').val(),
				offsidesHomeTeam : $('#gamestats_offsides_ht').val(),
				offsidesAwayTeam : $('#gamestats_offsides_at').val(),
				cornersHomeTeam : $('#gamestats_corners_ht').val(),
				cornersAwayTeam : $('#gamestats_corners_at').val(),
				throwinsHomeTeam : $('#gamestats_throwins_ht').val(),
				throwinsAwayTeam : $('#gamestats_throwins_at').val(),
				freekicksHomeTeam : $('#gamestats_freekicks_ht').val(),
				freekicksAwayTeam : $('#gamestats_freekicks_at').val(),
				possessionHomeTeam : $('#gamestats_possession_ht').val(),
				possessionAwayTeam : $('#gamestats_possession_at').val()
		};
		
		var params = {};
		params.id = id;
		params.$entity = gs;
		AdminDataService.saveGameStats(params);
		window.location = "game.html?id=" + id;
	});
	
	$('#cancelstats').click(function() {
		window.location = "game.html?id=" + id;
	});
}

function toComboBoxFormat(arr) {
	var outArr = new Array();
	for(var a = 0; a < arr.length; a++) {
		var item = arr[a];
		outArr.push({
	        value: item.id,
	        label: item.name
	    });
	}
	return outArr;
}

function createCombobox(parentId, id, data, selectedObject) {
	
	$('#' + parentId).html('<input id="' + id + '" value="' + (selectedObject != null ? selectedObject.name : "") + '"/><input type="hidden" id="' + id + '-id" value="' + (selectedObject != null ? selectedObject.id : "") + '"/>');
	
	
	$( "#" + id).autocomplete({
        minLength: 0,
        source: data,
        focus: function( event, ui ) {
            $("#" + id).val( ui.item.label );
            return false;
        },
        select: function( event, ui ) {
            $("#" + id).val( ui.item.label );
            $("#" + id + "-id" ).val( ui.item.value );

            return false;
        }
    }).data( "autocomplete" )._renderItem = function( ul, item ) {
        return $( "<li>" )
        .data( "item.autocomplete", item )
        .append( "<a>" + item.label + "</a>" )
        .appendTo( ul );
	};
}

function createSplitControl(id, ht, at) {
	return '<input type="text" size="2" id="' + id + '_ht" value="' + ht + '"></input> - ' +
			'<input type="text" size="2" id="' + id + '_at" value="' + at + '"></input>';
}

function editPlayerDetails(id) {
	
	var params = {"id":id};
	var data = DataService.getPlayer(params);
	
	$('#playerdetails').css('display', 'block');
	$('#playerdetails').html('<table class="bordered">' + 
		'<thead><tr><th colspan="2">' + data.name + '<span style="float:right;"><button id="saveplayer" class="btn">Spara</button><button id="cancel" class="btn">Avbryt</button></span></th></tr></thead>' +
		'<tbody>' +
		'<tr><td>Namn</td><td><input type="text" id="name"/></td></tr>' + 
		'<tr><td>Född</td><td><input type="text" id="dateOfBirth"/></td></tr>' + 
		'<tr><td>Moderklubb</td><td><input type="text" id="motherClub"/></td></tr>' + 
		'<tr><td>Övriga klubbar</td><td><input type="text" id="playedForClubs"/></td></tr>' + 
		'<tr><td>Längd</td><td><input type="text" id="length"/></td></tr>' +
		'<tr><td>Vikt</td><td><input type="text" id="weight"/></td></tr>' +
		'<tr><td>Nationalitet</td><td><select id="nationality"/></td></tr>' +
		'<tr><td>Position</td><td><select id="positionType"/></td></tr>' +
		'<tr><td>Biografi</td><td><textarea id="biography"></textarea></td></tr>' +
		'</tbody></table>' + 
		'<form method="POST" action="/ifkstat-server/upload" enctype="multipart/form-data" >' + 
	    'Bildfil:' + 
	    '<input type="file" name="file" id="file" /> <br/>' + 
	    '<input type="hidden" name="playerId" id="playerId" value="' + id + '"/> <br/>' + 
	    '<input type="submit" value="Ladda upp" name="upload" id="upload" />' + 
	    '</form>');
	
	// Populate
	$('#name').val(data.name);
	$('#dateOfBirth').val(data.dateOfBirth);
	$('#motherClub').val(data.motherClub);
	$('#playedForClubs').val(data.playedForClubs);
	$('#length').val(data.length);
	$('#weight').val(data.weight);
	$('#biography').val(data.biography);
	
	var positions = DataService.getPositionTypes();
	for(var a = 0; a < positions.length; a++) {
		var isSelected = (data.positionType != null && positions[a].id == data.positionType.id);
		$('#positionType').append('<option ' + (isSelected ? 'selected' : '') + ' value="' + positions[a].id + '">' + positions[a].name + '</option>');
	}
	
	var countries = DataService.getCountries();
	var hasSelection = false;
	for(var a = 0; a < countries.length; a++) {
		var isSelected = (data.nationality != null && countries[a].id == data.nationality.id);
		$('#nationality').append('<option ' + (isSelected ? 'selected' : '') + ' value="' + countries[a].id + '">' + countries[a].name + '</option>');
		if(isSelected) {
			hasSelection = true;
		}
	}
	
	// Select sweden if no country is pre-selected. Otherwise, I think we'll end up with a lot of Albanian players
	if(!hasSelection) {
		var text1 = 'Sverige';
		$("#nationality option").filter(function() {
		    //may want to use $.trim in here
		    return $(this).text() == text1; 
		}).attr('selected', true);

	}
	
	$('#saveplayer').click(function() {
		// Save to backend
		data.name = $('#name').val();
		data.dateOfBirth = $('#dateOfBirth').val();
		data.motherClub = $('#motherClub').val();
		data.playedForClubs = $('#playedForClubs').val();
		data.length = $('#length').val();
		data.weight = $('#weight').val();
		data.biography = $('#biography').val();
		if(data.positionType == null) {
			data.positionType = {};
		}
		data.positionType.id = $('#positionType').val();
		if(data.nationality == null) {
			data.nationality = {};
		}
		data.nationality.id = $('#nationality').val();
		var playerParam = {};
		playerParam.$entity = data;
		data = AdminDataService.savePlayer(playerParam);
		showPlayerDetails(id);
	});
	$('#cancel').click(function() {
		showPlayerDetails(id);
	});
	
}

function showClubs() {
	$('#content').html( 
	'<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="clubs">' +
	'<thead>' +
	'<tr><th></th><th colspan="5">Totalt</th><th colspan="5">Hemma</th><th colspan="5">Borta</th></tr>' +
	'<tr><th>Klubb</th><th>M</th><th>V</th><th>O</th><th>F</th><th>Mål</th><th>M</th><th>V</th><th>O</th><th>F</th><th>Mål</th><th>M</th><th>V</th><th>O</th><th>F</th><th>Mål</th></tr>' +
	'</thead>' +
	'</table>');

	var clubs = DataService.getAllClubStatistics();
	
	$('#clubs').dataTable( {	 
		"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	  
			$('td:eq(0)', nRow).html( '<a href="gamesVsTeam.html?id=' + aData.clubId + '">' + aData.clubName + '</a>');
			$('td:eq(5)', nRow).html( aData.goalsScored + '-' + aData.goalsConceded );   
			$('td:eq(10)', nRow).html( aData.homeGoalsScored + '-' + aData.homeGoalsConceded );   
			$('td:eq(15)', nRow).html( aData.awayGoalsScored + '-' + aData.awayGoalsConceded );   
	    },
		"bProcessing" : true,
		"aaData": clubs,
		"bPaginate": true,
	    "bLengthChange": false,
	    "bFilter": true,
	    "bSort": true,
	    "bInfo": false,
	    "aoColumns": [		    
	      		    { "mData": "clubName"},
	      		    { "mData": "games"},
	      		    { "mData": "wins"},
	      		    { "mData": "draws"},
	      		    { "mData": "losses"},
	      		    { "mData": "games" },
	      		    { "mData": "homeGames"},
	      		    { "mData": "homeWins"},
	      		    { "mData": "homeDraws"},
	      		    { "mData": "homeLosses"},
	      		    { "mData": "games"},
	      		    { "mData": "awayGames"},
	      		    { "mData": "awayWins"},
	      		    { "mData": "awayDraws"},
	      		    { "mData": "awayLosses"},
	      		    { "mData": "games"}
	      		    ]
	    });
}

function showPlayers() {

	$('#content').html( 
		'<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="players"></table>');
	
	var players = DataService.getPlayerSummaries();

	$('#players').dataTable( {	 
	"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	           
        $('td:eq(0)', nRow).html( '<a href="player.html?id=' + aData.id + '">' + aData.name + '</a>' );   
        $('td:eq(1)', nRow).html( '<a href="gamesOfPlayer.html?id=' + aData.id + '">' + nstr(aData.games) + '</a>' ); 
        $('td:eq(3)', nRow).html( '<a href="gamesOfDate.html?date=' + formatDate(new Date(aData.firstGame)) + '">' + formatDate(new Date(aData.firstGame)) + '</a>');
        $('td:eq(4)', nRow).html( '<a href="gamesOfDate.html?date=' + formatDate(new Date(aData.lastGame)) + '">' + formatDate(new Date(aData.lastGame)) + '</a>');
	},
	"bProcessing" : true,
	"aaData": players,
	"bPaginate": true,
    "bLengthChange": false,
    "bFilter": true,
    "bSort": true,
    "bInfo": false,
	"aoColumns": [		    
	    { "mData": "name", "sTitle": "Spelare" },
	    { "mData": "games", "sTitle": "Matcher" },
	    { "mData": "goals", "sTitle": "Mål" },
	    { "mData": "firstGame", "sTitle": "Första match" },
	    { "mData": "lastGame", "sTitle": "Sista match" }
	]
} );

}

function showPlayerDetails(id) {

	var params = {"id":id};
	var data = DataService.getPlayer(params);
	$('#playerdetails').css('display', 'block');
	$('#playerdetails').html('<table class="bordered">' + 
		'<thead><tr><th colspan="2">' + data.name + '<span style="float:right;"><button id="editplayer" class="btn">Redigera</button></span></th></tr></thead>' +
		'<tbody>' +
		'<tr><td>Antal matcher</td><td><a href="gamesOfPlayer.html?id=' + data.id + '">' + nstr(data.numberOfGames) + '</a></td></tr>' + 
		'<tr><td>Född</td><td>' + nstr(data.dateOfBirth) + '</td></tr>' + 
		'<tr><td>Moderklubb</td><td>' + nstr(data.motherClub) + '</td></tr>' + 
		'<tr><td>Övriga klubbar</td><td>' + nstr(data.playedForClubs) + '</td></tr>' + 
		'<tr><td>Längd</td><td>' + nstr(data.length) + '</td></tr>' + 
		'<tr><td>Vikt</td><td>' + nstr(data.weight) + '</td></tr>' + 
		'<tr><td>Nationalitet</td><td>' + (data.nationality != null ? data.nationality.name : '') + '</td></tr>' + 
		'<tr><td>Position</td><td>' + (data.positionType != null ? data.positionType.name : '' ) + '</td></tr>' + 
		'<tr><td>Biografi</td><td>' + nstr(data.biography) + '</td></tr>' +
			'</tbody></table>');
	
	$('#editplayer').click(function() {
		editPlayerDetails(id);
	});
	
	$('#playerstats').css('display', 'block');
	$('#playerstats').html('<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="player_stats"></table>');
	
	var statdata = DataService.getPlayerStats(params);
	$('#player_stats').dataTable( {	 
			"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	           
                $('td:eq(1)', nRow).html( '<a href="gamesOfPlayerInTournament.html?id=' + id + '&tournament_id=' + aData.tournamentId + '">' + aData.totalGames + '</a>' );
                $('td:eq(2)', nRow).html( '<a href="gamesOfPlayerInTournamentGoalScored.html?id=' + id + '&tournament_id=' + aData.tournamentId + '">' + aData.goals + '</a>' );
                $('td:eq(3)', nRow).html( '<a href="gamesOfPlayerInTournamentSubstIn.html?id=' + id + '&tournament_id=' + aData.tournamentId + '">' + aData.gamesAsSubstituteIn + '</a>' );
                $('td:eq(4)', nRow).html( '<a href="gamesOfPlayerInTournamentSubstOut.html?id=' + id + '&tournament_id=' + aData.tournamentId + '">' + aData.gamesAsSubstituteOut + '</a>' );
                
			},
			"bProcessing" : true,
			"aaData": statdata.averagesPerTournament,
			"bPaginate": false,
	        "bLengthChange": false,
	        "bFilter": false,
	        "bSort": true,
	        "bInfo": false,
			"aoColumns": [		    
			    { "mData": "tournamentName", "sTitle": "Turnering" },
			    { "mData": "totalGames", "sTitle": "Matcher" },
			    { "mData": "goals", "sTitle": "Mål" },
			    { "mData": "gamesAsSubstituteIn", "sTitle": "Inbytt" },
			    { "mData": "gamesAsSubstituteOut", "sTitle": "Utbytt" },
			    { "mData": "goalsAsSubstituteIn", "sTitle": "Mål som inhoppare" },
			    { "mData": "goalsAsSubstituteOut", "sTitle": "Mål, sedan utbytt" }
			]
		} );

}






function showGameDetails(id) {

	var params = {"id":id};
	var data = DataService.getGame(params);
	var events = DataService.getGameEvents(params);
	var notes = DataService.getGameNotes(params);
	var participants = DataService.getGameParticipation(params);
	

	$('#game_opponent').html(data.homeTeam.name == 'IFK Göteborg' ? data.awayTeam.name : data.homeTeam.name);
	$('#game_result').html(data.homeGoals + '-' + data.awayGoals);
	$('#game_result_halftime').html(data.homeGoalsHalftime + '-' + data.awayGoalsHalftime);
	$('#game_tournament').html(data.tournamentSeason.tournament.name);
	$('#game_season').html(data.tournamentSeason.start);
	$('#game_homeaway').html(data.homeTeam.name == 'IFK Göteborg' ? 'Hemmamatch' : 'Bortamatch');
	$('#game_dateOfGame').html(data.dateOfGame);
	$('#game_ground').html(data.ground.name + ', ' + data.ground.city);
	$('#game_attendance').html(data.attendance);
	$('#game_referee').html(data.referee != null ? data.referee.name : 'I.u.');

	
	// events
	$('#gameheader').html( 
		'<h2>' + data.homeTeam.name + '-' + data.awayTeam.name + ' ' + data.homeGoals + '-' + data.awayGoals + ' (' + data.homeGoalsHalftime + '-' + data.awayGoalsHalftime + ')</h2>');
		
	
		for(var a = 0; a < participants.length; a++) {
			var prt = participants[a];
			if(prt.participationType == 'STARTER' || prt.participationType == 'SUBSTITUTE_OUT' ) {
				$('#gamelineup').append('<div style="' + getPositionOffset(prt.formationPosition.position) + '" class="lineup_player"><div class="player_icon"></div><div><a href="player.html?id=' + prt.player.id + '">' + prt.player.name + '</a></div></div>');
			}
		}

		
		$('#gameevents_table').dataTable( {	   
			"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	  
				var str = '';
				if(aData.eventType == 'GOAL') {
					str = 'Mål';
				} else if(aData.eventType == 'SUBSTITUTION_IN') {
					str = 'Inbytt';
				} else if(aData.eventType == 'SUBSTITUTION_OUT') {
					str = 'Utbytt';
				}
              $('td:eq(0)', nRow).html(str);   
  		},
	    	"bProcessing" : true,
	        "aaData": events,
			"bPaginate": false,
	        "bLengthChange": false,
	        "bFilter": false,
	        "bSort": true,
	        "bInfo": false,
	        "aoColumns": [
	            { "mData": "eventType", "sTitle": "Händelse" },
	            { "mData": "player.name", "sTitle": "Spelare" }
	        ]
	    } );
		
	    $('#gamenotes_table').dataTable( {	    	
	    	"bProcessing" : true,
	        "aaData": notes,
		    "bPaginate": false,
	        "bLengthChange": false,
	        "bFilter": false,
	        "bSort": true,
	        "bInfo": false,
	        "aoColumns": [
	            { "mData": "text", "sTitle": "Notering" }
	        ]
	    } );
	    
	    $('#gamestats_shots').html(data.gameStats.shots);
		$('#gamestats_shotsongoal').html(data.gameStats.shotsOnGoal);
		$('#gamestats_shotsoffgoal').html(data.gameStats.shotsOffGoal);
		$('#gamestats_offsides').html(data.gameStats.offsides);
		$('#gamestats_corners').html(data.gameStats.corners);
		$('#gamestats_throwins').html(data.gameStats.throwins);
		$('#gamestats_freekicks').html(data.gameStats.freekicks);
		$('#gamestats_possession').html(data.gameStats.possession);
		
		
		
	    $('#content').css('width', '900px');
}

function showSeasons() {
	var data = DataService.getSeasons();
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createSeasonsTable(data);
}

function createSeasonsTable(data) {
	$('#example').dataTable( {
    	"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	           
                $('td:eq(0)', nRow).html( '<a href="season.html?id='+ aData.id + '">' + aData.name + '</a>' );
    	},
    	"bProcessing" : true,
        "aaData": data,
        "aoColumns": [
            { "mData": "name", "sTitle": "Säsong" }
        ]
    } );	
}

function showSeasonDetails(id) {
	var params = {"id":id};
	var data = DataService.getSeason(params);

	$('#seasonheading').html('Säsongen ' + data.name);
	
	$('#tournaments').html('<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="tournaments_table"></table>');
	$('#tournaments_table').dataTable( {
    	"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	           
                $('td:eq(0)', nRow).html( '<a href="gamesOfSeason.html?id='+aData.id + '">' + aData.name + '</a>' );
    	},
    	"bProcessing" : true,
        "aaData": data.tournamentSeasons,
        "aoColumns": [
            { "mData": "name", "sTitle": "Namn" },
            { "mData": "games", "sTitle": "Matcher" },
            { "mData": "goalsScored", "sTitle": "Gjorda mål" },
            { "mData": "goalsConceded", "sTitle": "Insläppta mål" }
        ]
    } );
	
	$('#squad').html('<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="squad_table"></table>');
	$('#squad_table').dataTable( {
    	"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	           
    		$('td:eq(0)', nRow).html( '<a href="player.html?id='+aData.id + '">' + aData.name + '</a>' );    
    		$('td:eq(1)', nRow).html( aData.squadNr != '-1' ? aDatam.squadNr : 'i.u.');
    	},
    	"bProcessing" : true,
        "aaData": data.squad,
        "aoColumns": [
            { "mData": "name", "sTitle": "Namn" },
            { "mData": "squadNr", "sTitle": "Tröjnummer" },
            { "mData": "games", "sTitle": "Matcher" },
            { "mData": "goals", "sTitle": "Mål" }
        ]
    } );
}

function showGames() {
	var data = DataService.getGames();
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function showGamesOfSeason(id) {

	var params = {"id":id};
	var data = DataService.getGamesOfTournamentSeason(params);
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function showGamesOfDate(date) {

	var params = {"date":date};
	var data = DataService.getGamesOfDate(params);
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}


function showTournamentSeasons(id) {

	var params = {"id":id};
	var data = DataService.getSeasonsOfTournament(params);
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    $('#example').dataTable( {
    	"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	           
                $('td:eq(0)', nRow).html( '<a href="gamesOfSeason.html?id='+aData.id + '">' + aData.tournament.name + '</a>' );
    	},
    	"bProcessing" : true,
        "aaData": data,
        "aoColumns": [
            { "mData": "tournament.name", "sTitle": "Namn" },
            { "mData": "seasonName", "sTitle": "Säsong" }
        ]
    } );
}

function showTournaments() {
		
	var data = DataService.getTournaments();
    $('#content').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    $('#example').dataTable( {
    	"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	           
                $('td:eq(0)', nRow).html( '<a href="tournamentSeasons.html?id=' + aData.id + '">' + aData.name + '</a>' );
    	},
    	"bProcessing" : true,
        "aaData": data,
        "aoColumns": [
            { "mData": "name", "sTitle": "Namn" }
        ]
    } );
}

/**
 * Should return a css position string such as 'left:50px;top:100px;'
 * 
 * "position":
 * 	{"id":128,"name":"Innermitt (off)","code":"IM (O)","side":"CENTRAL","positionType":
 * 		{"id":114,"name":"Mittfältare","alignment":"MF"},
 * "minorVerticalAlignment":"OFFENSIVE","minorHorizontalAlignment":"NEUTRAL"}}
 * @param position
 */
function getPositionOffset(position) {
	// First, get base vertical position (GK, DEF, MF, FW)
	var top = 0;
	var left = 0;
	switch(position.positionType.alignment) {
	case 'GK':
		top = 40;		
		break;
	case 'DEF':
		top = 120;
		break;
	case 'MF':
		top = 230;
		break;
	case 'FW':
		top = 330;
		break;
	}
	
	switch(position.side) {
	case 'RIGHT':
		left = 100;		
		break;
	case 'CENTRAL':
		left = 320;
		break;
	case 'LEFT':
		left = 550;
		break;
	}
	
	switch(position.minorVerticalAlignment) {
	case 'DEFENSIVE':
		top -= 30;		
		break;
	case 'OFFENSIVE':
		top += 40;
		break;
	}
	
	switch(position.minorHorizontalAlignment) {
	case 'LEFT':
		left += 90;		
		break;
	case 'RIGHT':
		left -= 90;
		break;
	}
	
	return 'left:' + (left-40) + 'px;top:' + top + 'px;';
}

function nstr(str) {
	if(str == null) {
		return '';
	} else {
		return str;
	}
}

function formatDate(d) {
	return $.datepicker.formatDate('yy-mm-dd', d);
}