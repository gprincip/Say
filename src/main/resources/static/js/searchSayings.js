
var lastSearchTerm = ""; //last search term that was used to generate results from backend
var fetchQuantity = 5; // default search results limit (move to external config maybe?)

function search(searchTerm, searchType, fetchQuantity){
	
	jQuery.get({
		
		url:"test/search/" + searchType + "?searchTerm=" + searchTerm + "?fetchQuantity=" + fetchQuantity,
		success: function(searchResult){
			var jsonResult = JSON.parse(searchResult);
			console.log(jsonResult);
			lastSearchTerm = JSON.parse(searchResult).searchTerm;
			autocomplete($("#searchTerm"));
		}
		
	});
	
}

function getLabelsForAutocomplete(resultsJson){
	
	var labels = new Array();
	for(var i=0; i<resultsJson.length; i++){
		labels.push(resultsJson[i].label);
	}
	return labels;
	
}

/*setInterval(function() {
	var searchTerm = $("#searchTerm").val();
	if(searchTerm !== lastSearchTerm){
		search(searchTerm, "sText");
	}
}, 3000);*/

function autocomplete(input){
	
	//, getLabelsForAutocomplete(jsonResult.jsonData.results)
	
	var currentFocus;
	var values;
	var searchTerm; 
	
	input[0].addEventListener("input", function(e){
		
		if(input[0].value !== ''){
			$("#loader").show();
		}else{
			$("#loader").hide(); //sometimes loader can remain active so hide it just in case
		}
		
		jQuery.get({
			
			url:"test/search/" + "sText" + "?searchTerm=" + $("#searchTerm").val() + "&fetchQuantity=" + fetchQuantity,
			success: function(searchResult){
				searchTerm = JSON.parse(searchResult).searchTerm;
				
				/*if(searchTerm !== $("#searchTerm")){
					autocomplete(input);
				}*/
				
				values = getLabelsForAutocomplete(JSON.parse(searchResult).jsonData.results);
				
				var a, b, i, val = input[0].value;
				closeAllLists();
				if(!val){
					return false;
				}
				currentFocus = -1;
				
				a = document.createElement("DIV");
				a.setAttribute("id", this.id + "autocomplete-list");
				a.setAttribute("class", "autocomplete-items");
				input[0].parentNode.appendChild(a);
				
				for(i=0; i<values.length; i++){
					
					var startIndexOfMatchedText;
					if((startIndexOfMatchedText = values[i].toUpperCase().indexOf(val.toUpperCase())) != -1){
						var displayValue = formatListItem(values[i]);
						var searchResultJson = JSON.parse(searchResult);
						
						b = document.createElement("DIV");
						b.innerHTML = displayValue.substring(0, startIndexOfMatchedText); //from beginning until the start of a matched text
						b.innerHTML += "<strong>" + displayValue.substring(startIndexOfMatchedText, startIndexOfMatchedText + val.length) + "</strong>"; //matched text is bolded
						b.innerHTML += displayValue.substring(startIndexOfMatchedText + val.length); //from the end of matched text until end
						b.innerHTML += "<input type='hidden' value='" + displayValue + "'>";
						b.innerHTML += "<input type='hidden' value='" + values[i] + "'>"; //real value (not shortened or formatted)
						b.innerHTML += "<input type='hidden' value='" + searchResultJson.jsonData.results[i].id + "'>"; // id
						
						b.addEventListener("click", function(e){
							
							input[0].value = this.getElementsByTagName("input")[1].value; //input[1] - real value (not shortened or formatted)
							
							var id = this.getElementsByTagName("input")[2].value; //input[2] - id
							var searchType = searchResultJson.searchType;
							showFoundData(id, searchType);
							
							closeAllLists();
							
						});
						a.appendChild(b);
					}
					
				}
				$("#loader").hide();

				//not working currently. Seems like we cannot define function like this in asynchronous call
				input[0].addEventListener("keydown", function(e){

					var x = document.getElementById(this.id + "autocomplete-list");
					if(x) x = x.getElementsByTagName("div");
					if(e.keyCode == 40){ // down key
						currentFocus++;
						addActive(x);
					}else if(e.keyCode == 38){ // up key
						currentFocus--;
						addActive(x);
					}else if(e.keyCode == 13){ // return
						e.preventDefault(); // prevent form from submitting
						if(currentFocus > -1){
							if(x) x[currentFocus].click();
						}
					}
					
				});
				
				function addActive(x){
					
					if (!x) return false;
					removeActive(x);
					if(currentFocus >= x.length){
						currentFocus = 0;
					}
					if(currentFocus < 0){
						currentFocus = (x.length - 1);
					}
					x[currentFocus].classList.add("autocomplete-active");
					
				}
				
				function closeAllLists(element){
					var x = document.getElementsByClassName("autocomplete-items");
					for(var i=0; i< x.length; i++){
						if(element != x[i] && element != input){
							x[i].parentNode.removeChild(x[i]);
						}
					}
				}
				
				document.addEventListener("click", function(e){
					closeAllLists(e.target);
				});
				
				function removeActive(x) {
				    for (var i = 0; i < x.length; i++) {
				      x[i].classList.remove("autocomplete-active");
				    }
				}
			}
		});
	})
			
				
}
			

//additional processing on found item
function formatListItem(item){
	if(item.length > 15){
		return item.substring(0,14) + "...";
	}
	return item;
}

function showFoundData(id, searchType){
	
	if(searchType === "sText"){
		window.location.href = "sayings/" + id;
	}
	
}

