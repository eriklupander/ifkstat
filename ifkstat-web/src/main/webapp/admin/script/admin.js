
function savePlayer(player) {
	
	var params = {};
	params.$entity = player;

	AdminDataService.savePlayer(params);
}


function nstr(str) {
	if(str == null) {
		return '';
	} else {
		return str;
	}
}