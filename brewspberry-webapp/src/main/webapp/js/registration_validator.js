


function validateForm (){

	var groups = $(".group");
	
	jQuery.each(groups, function(index, value){
	
		var writtenField = value.find("input");
		var writtenSelect = value.find("select");
		var divSelect = value.find("div");
		 
		if (writtenField == null && writtenSelect == null && divSelect == null){
		
			$(".errormessages").append("<div class="alert alert-danger"> \
			    <a class="close" data-dismiss="alert" href="#">&times;</a> \
			    <strong>Error!</strong> No type found for field "+value.find("label").text()+". \
			</div>");
		} else if (writtenField.length == 1){
			
			var toValidate = {id:writtenField.attr('id'),data:writtenField.val};
			
			validateField(toValidate, changevalidatorToGreen(toValidate.id));
			
		} else if (writtenSelect.length == 1){
			
			var toValidate = {id:writtenSelect.attr('id'),data:writtenSelect.val};
			
			validateField(toValidate, changevalidatorToGreen(toValidate.id));
			
			}); 
			
		} else if (divSelect.length == 1){
			
			var toValidate = {id:divSelect.attr('id'),data:writtenSelect.datePicker('getDate')};
			
			validateField(toValidate, changevalidatorToGreen(toValidate.id));
			
		}
		
		
	
	});
	

}

/**
 * Empties div next to field and 
 * adds checkOK image
 *
 * id : the id of validator field
 */ 
function changevalidatorToGreen(id){


	var element = $("#v_"+id);
	
	if (element.length){
		
		element.empty();
		element.prepend($('<img>',{id:'checkOK', src:'images/checkOK.png'}));
		
	}


}



function validateField(toValidate, callback){

	var id = toValidate.id;

	switch (id){
	
		case "username" :
		
		case "first_name" :
		
		case "last_name" :
		
		
		break;
		
		
		case "password" :
		
		break;
	
	
	}

	

}
