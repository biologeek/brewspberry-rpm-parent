


function validateForm (){

	var groups = $(".group");
	
	jQuery.each(groups, function(index, value){
	
		var writtenField = value.find("input");
		var writtenSelect = value.find("select");
		 
		if (writtenField == null && writtenSelect == null){
		
			$(".errormessages").append("<div class="alert alert-danger"> \
			    <a class="close" data-dismiss="alert" href="#">&times;</a> \
			    <strong>Error!</strong> No type found for field "+value.find("label").text()+". \
			</div>");
		}
	
	});
	

}


function changevalidatorToGreen(id){


}