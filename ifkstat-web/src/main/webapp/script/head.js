function header() {
	$('#header').html('<div class="mainheading">IFK Göteborg statistikdatabas</div>');
	$('#header').append('<div class="menubar" id="menu">' +
		'<a class="menuanchor" href="seasons.html">Säsonger</a>' +
		'<a class="menuanchor" href="tournaments.html">Turneringar</a>' +
		'<a class="menuanchor" href="players.html">Spelare</a>' +
		'<a class="menuanchor" href="games.html">Matcher</a>' +		
		'<a class="menuanchor" href="clubs.html">Klubbar</a>' +	
		'<a class="menuanchor" href="createuser.html">Logga in</a>' +
		'</div>' +
		'<hr/>'
	);
}