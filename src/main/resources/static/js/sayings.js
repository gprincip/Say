//tags added to the saying
var tags = new Set();

$(document).ready(function() {
	console.log("ready!");
	loadTagsFromDBForAutocomplete("api/tag/findAll");
	tags.clear();
});

function addTag(val) {
	tags.add(val);
	$("#addedTagsList").html(formatTagsForDisplay(tags));
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

function beforeSubmit(){
	$("#tagSet").val(Array.from(tags)); //add tags to hidden input form filed
}

//format tag set for displaying
function formatTagsForDisplay(tags){
	
	var formattedTags = "";
	var tagList = Array.from(tags);
	
	for(var i = 0; i<tagList.length; i++){
		if(formattedTags == ""){
			formattedTags += tagList[i];
		}else{
			formattedTags += ", " + tagList[i];
		}
	}
	return formattedTags;
	
}










