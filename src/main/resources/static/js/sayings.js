//tags added to the saying
var tags = new Set();

$(document).ready(function() {
	console.log("ready!");
	loadTagsFromDBForAutocomplete("/say/api/tag/findAll");
	tags.clear();
	autocomplete($("#searchTerm"), "search/sText", 5);
	$("#loader").hide();
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

			$("#tagAutocomplete").autocomplete(
					{
						source : function(request, response) {
							var results = $.ui.autocomplete.filter(data
									.map(function(elem) {
										return elem.name
									}), request.term);

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
			formattedTags += "<div class='tag'>" + tagList[i] + " <a href='#' onClick='removeTag(`" + tagList[i].toString() + "`)'><img src='/say/images/removeTag.png' style='width:10px; height:10px;'></a></div>";
		}else{
			formattedTags += ", <div class='tag'>" + tagList[i] + " <a href='#' onClick='removeTag(`" + tagList[i].toString() + "`)'><img src='/say/images/removeTag.png' style='width:10px; height:10px;'></a></div>";
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
		url : "/say/api/saying/validateAndSaveSaying",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(saying),
		success : function(status) {
			console.log(status);
			if(status != ''){
				afterErrors(status);
			}else{
				afterSuccess(saying);
				
			}
			
		}
	
	});
	
}

function generateSayingCreatedEvent(saying){
	
	saying.id = 0; //dummy id
	jQuery.post({
		url : "/say/api/rest/v1/generateSayingCreatedEvent",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(saying)
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

function afterSuccess(saying){
	clearFields();
	$("#success").text("Successfuly posted!");
	
	generateSayingCreatedEvent(saying);
	
	//remove tags when saying is submitted successfully
	tags.clear();
}

function afterErrors(status){
	$("#warning").text(status);
	$("#success").text("");
}

function removeTag(tagName){
	
	tags.delete(tagName);
	$("#addedTagsList").html(formatTagsForDisplay(tags));
	
}









