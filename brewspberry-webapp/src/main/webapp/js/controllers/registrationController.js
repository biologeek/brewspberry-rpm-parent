/************************************************************************
*	registration_validator.js											*
*-----------------------------------------------------------------------*	
*	@author biologeek      												*
*																		*
*	Script used for validating form 									*
*																		*
*																		*
*************************************************************************/
(function(){
'use strict';

angular.module('brewspberry').controller('RegistrationController', RegistrationController);


RegistrationController.$inject = ['$scope', 'UserService', 'CONSTANTS'];


function RegistrationController ($scope, UserService, CONSTANTS){

	var vm = this;

	vm.showSuccess = false;
	vm.showErrors = false;
	/**
	 *
	 */
	console.log("handling form")


	vm.test = function(form){

		console.log (form.bla);

	}

	vm.handleClickRegisterForm = function (form){

		console.log("handling form")
		if (form.$dirty && form.$valid){

			return submitNewUser();

		}
	}

	function submitNewUser(){


		UserService.createUser(vm.user,

		function (response){


			vm.showSuccess = true;
			vm.submissionSuccessMessage = 'new user created successfuly !'
			console.log('request success')
		},

		function (response){

			vm.submissionFailureMessage = 'Failed to post new user. Error : '+ response.statusText + '. '+response.data;
			vm.showErrors = true;
			console.log('request failed')

		});
	}

	//UserService.createUser().then();

}
})();

/*
function triggerFormValidation(){

	var typingTimer;                //timer identifier
	var doneTypingInterval = 5000;  //time in ms, 5 second for example

	
	//on keyup, start the countdown
	$input.on('keyup', function () {
	  clearTimeout(typingTimer);
	  typingTimer = setTimeout(validateForm(), doneTypingInterval);
	});

}


function validateForm (){

	var groups = $(".group");
	
	var totalFields = 0;
	var validatedFields = 0;
	
	jQuery.each(groups, function(index, value){
	
		totalFields += 1;
		
		var writtenField = value.find("input");
		var writtenSelect = value.find("select");
		var divSelect = value.find("div");
		 
		if (writtenField == null && writtenSelect == null && divSelect == null){
		
			$(".errormessages").append("<div class=\"alert alert-danger\"> \
			    <a class=\"close\" data-dismiss=\"alert\" href=\"#\">&times;</a> \
			    <strong>Error!</strong> No type found for field "+value.find("label").text()+". \
			</div>");
			
		} else if (writtenField.length == 1){
			
			var toValidate = {id:writtenField.attr('id'),data:writtenField.val};
			
			validateField(toValidate, function(){
				
					changevalidatorToGreen(toValidate.id)
					validatedFields += 1;
					
				}, function(){
							
				blockSubmitButton()
			
			});
			
		} else if (writtenSelect.length == 1){
			
			var toValidate = {id:writtenSelect.attr('id'),data:writtenSelect.val};
			
			validateField(toValidate, function(){
				
					changevalidatorToGreen(toValidate.id)
					validatedFields += 1;
					
				}, function(){
							
				blockSubmitButton()
						
			}); 
			
		} else if (divSelect.length == 1){
			
			var toValidate = {id:divSelect.attr('id'),data:writtenSelect.datePicker('getDate')};
			
			validateField(toValidate, function(){
				
					changevalidatorToGreen(toValidate.id)
					validatedFields += 1;
					
				}, function(){
							
				blockSubmitButton()
			
			});
			
		}
		
	});
	
}

/**
 * Empties div next to field and 
 * adds checkOK image
 *
 * id : the id of validator field
 *
function changevalidatorToGreen(id){


	var element = $("#v_"+id);
	
	if (element.length){
		
		element.empty();
		element.prepend($('<img>',{id:'checkOK', src:'images/checkOK.png'}));
		
	}
}

function blockSubmitButton(){

	var submit = curElement.closest('form').find(':submit');

	if (!submit.is(':disabled')){
		submit.attr('disabled', true);
	}
}


/**
 * Validates single field against regex
 *
function validateField(toValidate, callbackSuccess, callbackFailure){

	var id = toValidate.id;

	var nameRegex = '[a-zA-Z]{3,15}';
	var loginRegex = '[a-zA-Z0-9]{3,15}';
	var emailRegex = '/^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i';
	var passwordRegex = '(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/)';
	
	
	switch (id){
		case "username" :
		
			if (toValidate.val.match(loginRegex).length > 0){
				callbackSuccess();
			} else {
				callbackFailure();
			}
			break;
			
			case "first_name" :
			
			case "last_name" :
					
			if (toValidate.val.match(nameRegex).length > 0){
				callbackSuccess();
			} else {
				callbackFailure();
			}
		break;
		
		
		case "password" :
			
			if (toValidate.val.match(emailRegex).length > 0){
				callbackSuccess();
			} else {
				callbackFailure();
			}
		break;
		
		case "email" :
				
			if (toValidate.val.match(emailRegex).length > 0){
				callbackSuccess();
			} else {
				callbackFailure();
			}
		break;
	
	
	}

	

}
*/