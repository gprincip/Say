$(document).ready(function() {
	console.log("ready!");

	loadTagsFromDBForAutocomplete("api/tag/findAll");

});

function addTag(val) {
	console.log(val);
}

function loadTagsFromDBForAutocomplete(apiUrl) {
	$.ajax({
		url : apiUrl,
		success : function(data) {
			
			$("#tagAutocomplete").autocomplete({
				source : function(request, response) {
			        var results = $.ui.autocomplete.filter(
			        		data.map(function(elem){return elem.name}), request.term);
			        
			        response(results.slice(0, 10));
			    }
			});
			
		}
	
	});
}
