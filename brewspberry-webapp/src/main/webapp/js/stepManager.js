
var restServiceBaseURL = "http://localhost:8080/brew";


function startStep(stepID){
	
	
	var restService = restServiceBaseURL+"/start";
	
	if (stepID != undefined && stepID > 0){
		
		restService = restService+"/"+stepID;
		
		jQuery.ajax (serviceAddress,{
			
			success : function (result){
				console.log('Call success');
		
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
				rawDataFromServlet = new Array();
			}	
			
		});
	
	}
	
	
}