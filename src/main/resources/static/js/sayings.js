//tags added to the saying
var tags = new Set();

$(document).ready(function() {
	console.log("ready!");
	loadTagsFromDBForAutocomplete("api/tag/findAll");
	tags.clear();
});

function addTag() {
	var tag = $("#tagAutocomplete").val();
	tags.add(tag);
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

function validateAndSave(){
	
	var saying = {
			"id":null,
			"text":$("#saying").val(),
			"author":"testAuthor",
			"clientIp":null,
			"date":null,
			"tags":tagsToJson(),
			"score":"0"
	}
	
	jQuery.post({
		url : "api/saying/validateAndSaveSaying",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(saying),
		success : function(status) {
			console.log(status);
			if(status != ''){
				afterErrors(status);
			}else{
				afterSuccess();
				
			}
			
		}
	
	});
	
}

function tagsToJson(){

	var tagsArr = [];
	var tagList = Array.from(tags);
	for(var i = 0; i < tagList.length; i++){
		var tag = {};
		tag["id"] = null;
		tag["name"] = tagList[i];
		tagsArr.push(tag);
	}
	
	return tagsArr;
}

function clearFields(){
	
	$("#saying").val("");
	$("#tagAutocomplete").val("");
	$("#warning").text("");
	$("#addedTagsList").text("");
	
}

function afterSuccess(){
	clearFields();
	$("#success").text("Successfuly posted!");
	//remove tags when saying is submitted successfully
	tags.clear();
}

function afterErrors(status){
	$("#warning").text(status);
	$("#success").text("");
}


