function setupAdminTabs() {
	// First, do some "is logged in" check
	var successful = SuperAdminDataService.login();
	
	$('#import_gamedata_btn').click(function() {
		var params = {};
		params.$entity = $('#import_gamedata').val();
		SuperAdminDataService.bulkUploadGameData(params);
		
		alert('Uppladdning av matchdata klar!');
	});
	$('#import_notes_btn').click(function() {
		var params = {};
		params.$entity = $('#import_notes').val();
		SuperAdminDataService.bulkUploadNotes(params);
		alert('Uppladdning av noter färdig!');
	});
	$('#cleanDb').click(function() {
		var params = {};
		params.password = $('#cleandb_password').val();
		SuperAdminDataService.cleanDatabase(params);
		SuperAdminDataService.reseedInitData(params);
		alert('Databas rensad och grunddata byggd!');
	});
	
}


function showCreateUserForm() {
	
	// First, do some "is logged in" check
	var successful = SuperAdminDataService.login();
	
	$('#newuserform').css('display', 'block');
	$('#newuserform').html('<table class="bordered">' + 
		'<thead><tr><th colspan="2">Skapa användare<span style="float:right;"><button id="saveuser" class="btn">Spara</button><button id="cancel" class="btn">Avbryt</button></span></th></tr></thead>' +
		'<tbody>' +
		
		'<tr><td>Användarnamn</td><td><input type="text" id="username"/></td></tr>' + 
		'<tr><td>Lösenord</td><td><input type="password" id="passwd"/></td></tr>' + 
		'<tr><td>Förnamn</td><td><input type="text" id="firstName"/></td></tr>' + 
		'<tr><td>Efternamn</td><td><input type="text" id="lastName"/></td></tr>' + 
		'<tr><td>E-post</td><td><input type="text" id="email"/></td></tr>' +
		
		'</tbody></table>');
	
	$('#saveuser').click(function() {
		
		var data = {};
		// Save to backend
		data.username = $('#username').val();
		data.passwd = $('#passwd').val();
		data.firstName = $('#firstName').val();
		data.lastName = $('#lastName').val();
		data.email = $('#email').val();
		
		var userParam = {};
		userParam.$entity = data;
		data = SuperAdminDataService.createUser(userParam);
		window.location = "index.html";
	});
	$('#cancel').click(function() {
		window.location = "index.html";
	});
	
}