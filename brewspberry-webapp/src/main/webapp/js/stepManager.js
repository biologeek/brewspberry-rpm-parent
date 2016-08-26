
var restServiceBaseURL = "/brew";
var rawDataFromService = "";

function startStep(stepID){
	
	
	var startButtonID = 'start'+stepID;
	var stopButtonID = 'stop'+stepID;
	
	if (stepID != undefined && stepID > 0){
		
		getDataFromService(stepID, true, changeButtonState(startButtonID));
	
	}
	
	/* If step has been started, button stop is disabled, it should be activated */
	
	if ( ! $( "#"+stopButtonID).is(':disabled') ){
		
		changeButtonState(stopButtonID);
		
	}
	
}
	
	
function stopStep(stepID){
	var startButtonID = 'start'+stepID;
	var stopButtonID = 'stop'+stepID;

	if (stepID != undefined && stepID > 0){
		
		getDataFromService(stepID, false, changeButtonState(stopButtonID));
	
	}
	
}

/**
 * Calls service and feeds rawDataFronServlet with updated step
 */
function getDataFromService(stepID, startBoolean, callback){
	
	var restService;
	
	if (startBoolean){
		restService = restServiceBaseURL+"/start";
	} else {
		restService = restServiceBaseURL+"/stop";
	}
	restService = restService+"/"+stepID;
	
	jQuery.ajax (restService,{
		
		success : function (result){
			console.log('Call success');
	
			/*
			 * Will retrieve whole step 
			 */
			rawDataFromService = result;
		

			callback(rawDataFromService);
		},
		error : function (request, status, error) {
			
			/*
			 * Will be error message displayed in jsp
			 * 
			 */
			

			console.log ('** Error when calling API !!');
			console.log ('** Status : '+status);
			console.log ('** Error : '+error);
			rawDataFromServlet = new Array();
		}	
		
	});	
	
}


function changeButtonState(buttonID){
	
	
	if ($( "#"+buttonID ).is(':disabled')){
		$( "#"+buttonID ).prop( 'disabled', false);
	} else {
		$( "#"+buttonID ).prop( 'disabled', true);
	}
}



