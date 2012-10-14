$.urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    return results[1] || 0;
}

function showGamesVsTeam(id) {
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'block');
	var params = {"id":id};
	var data = DataService.getGamesVsClub(params);
    $('#demo').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
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
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'block');
	var params = {"id":id};
	var data = DataService.getGamesOfPlayer(params);
    $('#demo').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function showGamesOfPlayerInTournament(id, tournamentId) {
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'block');
	var params = {"id":id, "tournamentId":tournamentId};
	var data = DataService.getGamesOfPlayerInTournament(params);
    $('#demo').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function showGamesOfPlayerInTournamentGoalScored(id, tournamentId) {
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'block');
	var params = {"id":id, "tournamentId":tournamentId};
	var data = DataService.getGamesOfPlayerInTournamentGoalScored(params);
    $('#demo').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function showGamesOfPlayerInTournamentSubstIn(id, tournamentId) {
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'block');
	var params = {"id":id, "tournamentId":tournamentId};
	var data = DataService.getGamesOfPlayerInTournamentSubstIn(params);
    $('#demo').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function showGamesOfPlayerInTournamentSubstOut(id, tournamentId) {
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'block');
	var params = {"id":id, "tournamentId":tournamentId};
	var data = DataService.getGamesOfPlayerInTournamentSubstOut(params);
    $('#demo').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}

function editPlayerDetails(id) {
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'none');
	
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
		'</tbody></table>');
	
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
	for(var a = 0; a < countries.length; a++) {
		var isSelected = (data.nationality != null && countries[a].id == data.nationality.id);
		$('#nationality').append('<option ' + (isSelected ? 'selected' : '') + ' value="' + countries[a].id + '">' + countries[a].name + '</option>');
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

function showPlayerDetails(id) {
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'none');
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
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'block');
	var params = {"id":id};
	var data = DataService.getGame(params);
	var events = DataService.getGameEvents(params);
	var notes = DataService.getGameNotes(params);
	var participants = DataService.getGameParticipation(params);
	
	$('#gamedetails').css('display', 'block');
	$('#gamedetails').html('<table class="bordered"><tr><td>' + data.homeTeam.name + '-' + data.awayTeam.name + ' ' + data.homeGoals + '-' + data.awayGoals + ' (' + data.homeGoalsHalftime + '-' + data.awayGoalsHalftime + ')</td></tr></table>');
	
	// events
	$('#demo').html( 
		'<table><tr><td valign="top"><table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="players"></table></td>' +
		'<td valign="top"><table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="events"></table></td>' +
		'<td valign="top"><table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="notes"></table></td></tr></table>');
		$('#players').dataTable( {	 
			"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	           
                $('td:eq(0)', nRow).html( '<a href="player.html?id=' + aData.player.id + '">' + aData.player.name + '</a>' );   
//                $('td:eq(0)', nRow).click(function() {
//                	showPlayerDetails(aData.player.id);
//                });                
    		},
			"bProcessing" : true,
			"aaData": participants,
			"bPaginate": false,
	        "bLengthChange": false,
	        "bFilter": false,
	        "bSort": true,
	        "bInfo": false,
			"aoColumns": [		    
			    { "mData": "player.name", "sTitle": "Spelare" },
			    { "mData": "formationPosition.position.name", "sTitle": "Position" }
			]
		} );
		
		$('#events').dataTable( {	    	
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
		
	    $('#notes').dataTable( {	    	
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
	    
	    $('#demo').css('width', '900px');
}

function showGamesOfSeason(id) {
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'block');
	var params = {"id":id};
	var data = DataService.getGamesOfTournamentSeason(params);
    $('#demo').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    createGamesTable(data);
}


function showTournamentSeasons(id) {
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'block');
	var params = {"id":id};
	var data = DataService.getSeasonsOfTournament(params);
    $('#demo').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    $('#example').dataTable( {
    	"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	           
                $('td:eq(0)', nRow).html( '<a href="gamesOfSeason.html?id='+aData.id + '">' + aData.tournament.name + '</a>' );   
//                $('td:eq(0)', nRow).click(function() {
//                	showGamesOfSeason(aData.id);
//                });
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
	$('.infopanel').css('display', 'none');
	$('.infotables').css('display', 'block');
	
	var data = DataService.getTournaments();
    $('#demo').html( '<table class="bordered" cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    $('#example').dataTable( {
    	"fnRowCallback": function( nRow, aData, iDisplayIndex ) {	           
                $('td:eq(0)', nRow).html( '<a href="tournamentSeasons.html?id=' + aData.id + '">' + aData.name + '</a>' );   
//                $('td:eq(0)', nRow).click(function() {
//                	showTournamentSeasons(aData.id);
//                });
    	},
    	"bProcessing" : true,
        "aaData": data,
        "aoColumns": [
            { "mData": "name", "sTitle": "Namn" }
        ]
    } );
}

function nstr(str) {
	if(str == null) {
		return '';
	} else {
		return str;
	}
}