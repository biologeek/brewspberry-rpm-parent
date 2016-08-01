/*******************************************************************************
 * Using chartjs
 * 
 ******************************************************************************/


	
var rawDataFromServlet = [];

var liveChart;
var chartOptions ={animationSteps: 10};

var initServletAddress = 'http://192.168.0.100:8080/brewspberry-api/rest/initTemperatures';
var updateServletAddress = 'http://192.168.0.100:8080/brewspberry-api/rest/updateTemperatures';
	
var refreshDelay = 5000; // Refreshes every 5 s

var maxPointsNumber = 60;
var XplotTimeRangeInMinutes = 5;
var chartData = {};


var currentLastID = 0;
	// var testData = [{"date": "2016-03-16 18:15:55.0","temp": 16155,"name":
	// "PROBE0","step": 8,"id": 1277,"brew": 7,"uuid":
	// "28-000006ddab6e"},{"date": "2016-03-16 18:15:56.0","temp": 15980,"name":
	// "PROBE0","step": 8,"id": 1277,"brew": 7,"uuid":
	// "28-000006ddab6e"},{"date": "2016-03-16 18:15:57.0","temp": 16187,"name":
	// "PROBE0","step": 8,"id": 1277,"brew": 7,"uuid":
	// "28-000006ddab6e"},{"date": "2016-03-16 18:15:58.0","temp": 16187,"name":
	// "PROBE0","step": 8,"id": 1277,"brew": 7,"uuid":
	// "28-000006ddab6e"},{"date": "2016-03-16 18:15:59.0","temp": 16187,"name":
	// "PROBE0","step": 8,"id": 1277,"brew": 7,"uuid":
	// "28-000006ddab6e"},{"date": "2016-03-16 18:15:00.0","temp": 16187,"name":
	// "PROBE0","step": 8,"id": 1277,"brew": 7,"uuid":
	// "28-000006ddab6e"},{"date": "2016-03-16 18:15:01.0","temp": 16187,"name":
	// "PROBE0","step": 8,"id": 1277,"brew": 7,"uuid":
	// "28-000006ddab6e"},{"date": "2016-03-16 18:15:02.0","temp": 16187,"name":
	// "PROBE0","step": 8,"id": 1277,"brew": 7,"uuid":
	// "28-000006ddab6e"},{"date": "2016-03-16 18:15:03.0","temp": 16187,"name":
	// "PROBE0","step": 8,"id": 1277,"brew": 7,"uuid":
	// "28-000006ddab6e"},{"date": "2016-03-16 18:15:04.0","temp": 16187,"name":
	// "PROBE0","step": 8,"id": 1277,"brew": 7,"uuid": "28-000006ddab6e"}];


// buildGraph(buildDataSetsForChartJS(testData), 'graph');

function execute (htmlID, step, probe){
	
	maxPointsNumber = $('#defaultmaxPtsValue').value;
	delay = $('#delay').value;
	
	// Initiating chart
	getDataFromServlet(step, probe, true, 0, maxPoints, delay, function(){
			buildDataSetsForChartJS(function(){
	
				buildGraph(chartData, htmlID);		
			});
		
			isDataSetAtMaximumSize ();
	});
	
	// Updating
	setInterval (function (){
		
		getDataFromServlet (step, probe, false, currentLastID, maxPts, delay, function (){
			updateChartWithNewData(rawDataFromServlet);

		});
		
	}, refreshDelay);
	
	
}
	
	/**
	 * probe is a list of probes Receiving data formatted as such :
	 * 
	 * probe;date;temperature
	 * 
	 * from servlet.
	 * 
	 * Uses ajax GET Request to retrieve data
	 */
	function getDataFromServlet(step, probe, init, lastID, maxPts, delay, callback) {
		/* if probe is not null retrieves data for this probe */
	
		
		var address = '';
		/*
		 * If it's not an init, only updates with last temperatures
		 */
		if (init){
			address = initServletAddress;
		} else {
			address = updateServletAddress;
		}

		
		if (typeof probe == "string"){
				
				address +='/uuid/'+probe+'';
		} else {
			
			alert ('UUID is not a string')
		}


		if (typeof step == "string" ){
				
				address +='/sid/'+step+'';
		} else {
			
			alert ('step is not a string')
		}
		

		if (init){
			if (maxPts > 0){
				
				maxPointsNumber = maxPts;
				
			}
			address+='/maxPts/'+maxPointsNumber;

		}

		
		if (!init){
			if (delay > 0){
				XplotTimeRangeInMinutes = delay;
			}
			
			address += '/delay/'+XplotTimeRangeInMinutes;
			
		}
		
		if (!init && lastID > 0){
			
			address+= '/lastID/'+lastID;
			
		} else if (!init){
			
			console.log ('OK I\'m cool, setting ID to 1');
			address+= '/lastID/1';
		}

			/* if null => all probes */
		
		// console.log('Calling '+address);
		rawDataFromServlet = null;
		// If query is OK setting rawDataFromServlet
		jQuery.ajax (address,{
			
			success : function (result){
				// console.log('Call success');
		
				rawDataFromServlet = result;
			

				callback();
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
		}
				
		);	
	}
	

	
	function buildGraph(dataSets, divID) {
	
		var ctx = document.getElementById(divID).getContext("2d");
		liveChart = new Chart(ctx).Line(dataSets, chartOptions);
	
		
	}
	
	/**
	 * Receiving data as a list of "uuid":[{probe;date;temperature}, ...]
	 * converting them to ChartJS datasets. Example :
	 * 
	 * [{"date":"2016-03-16
	 * 18:15:55.0","temp":16187,"name":"PROBE0","step":8,"id":1277,"brew":7,"uuid":"28-000006ddab6e"},...]
	 * 
	 * @param data
	 * @returns
	 */
	function buildDataSetsForChartJS (callback){
		
		var xLabels = [];
		var yValues = {};
		var datasets = {};
		var data = [];
		
		// console.log (rawDataFromServlet);

		if (typeof rawDataFromServlet == "string"){
			
			data = jQuery.parseJSON(rawDataFromServlet);
		} else {
			
			data = rawDataFromServlet;
		}
		
		/*
		 * Starting with concreteTemperatureMeasurements, For each item (uuid: [
		 * temperature, ...]), puts temperature value in yValues
		 */

		jQuery.each (data.ConcreteTemperatureMeasurement, function (i, item){
			
			
			if (xLabels.indexOf(formatDateFromJavaToJS(item.date)) == -1){
				xLabels.push(formatDateFromJavaToJS(item.date));
			}
		
			var itemName = item.name;

			if (!yValues.hasOwnProperty(itemName)){
							
				yValues[itemName] = [];

			}
			yValues[itemName].push(item.temp);
			currentLastID = item.id;			
		});

		/*
		 * Now working on Theoretical values. Usually, there is only one
		 * theoretical temperature but in case there is more than one, keeps
		 * looping over object
		 * 
		 */
		jQuery.each (data.TheoreticalTemperatureMeasurement, function (i, item){
			
			
			if (xLabels.indexOf(formatDateFromJavaToJS(item.date)) == -1){
				xLabels.push(formatDateFromJavaToJS(item.date));
			}
		
			var itemName = item.name;

			if (!yValues.hasOwnProperty(itemName)){
							
				yValues[itemName] = [];

			}
			yValues[itemName].push(item.temp);
			currentLastID = item.id;			
		});


		// Building final data for ChartJS

		chartData.labels = xLabels;
		chartData.datasets = [];
		

		if (yValues.PROBE0.length > 0){

			jQuery.each (yValues, function(i, item){
				// For each UUID, creating a dataset
				// For Theoretical temperature sets, uuid = null, null1, ...
				if (yValues.contains("null") != -1){
					chartData.datasets.push (
						{
		
						    label: i,
						    fillColor: "rgba(255, 0, 0, 0.2)",
						    strokeColor: "rgba(255, 0, 0, 1)",
						    pointColor: "rgba(255, 0, 0, 1)",
						    pointStrokeColor: "#fff",
						    pointHighlightFill: "#fff",
						    pointHighlightStroke: "rgba(255, 0, 0, 1)",
						    data: item
						}					
				);

			});

		}
		callback();
		
	}
	
	/**
	 * Updates chart using data from service
	 */
	function updateChartWithNewData (data){
		

		if (typeof data == "string"){
			
			data = jQuery.parseJSON(data);
		}
		
		var array=[];

		jQuery.each (data, function (i, item){
			// console.log(formatDateFromJavaToJS(item.date)+ ' '+array[0]);
			// console.log(array);
			if (typeof array[formatDateFromJavaToJS(item.date)] != 'undefined'){
				array[formatDateFromJavaToJS(item.date)].push(item.temp);
			} else {
				array[formatDateFromJavaToJS(item.date)] = [item.temp];
			}
						// console.log(array);

			currentLastID = item.id;
			
		});
		isDataSetAtMaximumSize();

		for (a in array){
			liveChart.addData(array[a], a);

		}
	}
	
	
	/**
	 * Calculates color of line for the dataset with ID id
	 * 
	 * Chooses 2 random numbers, third is 0
	 * 
	 * Returns RGB color code "xxx,yyy,zzz"
	 * 
	 */
	function detemineColorForSet(){


		colorTable = [Math.floor((Math.random() * 255) + 1), Math.floor((Math.random() * 255) + 1), Math.floor((Math.random() * 255) + 1)];

		zeroIS = Math.floor((Math.random() * 2) + 1)
		
		colorTable [zeroIS] = 0;
		
		return colorTable.toString();
		
	} 
	
	function formatDateFromJavaToJS (javaDate){
		
		var date = new Date (javaDate);
		
		
		var newDate = moment(date).format('LTS');
		
		return newDate;
	}




	function isDataSetAtMaximumSize (){
			
				if (liveChart.datasets.length > 0){
					var length = liveChart.datasets.length;
				} else {
					var length = 0;
				}
				
				// console.log(length);
		
				for (var i = 0; i < length; i++){
					var remainingToRemove = liveChart.datasets[i].points.length - maxPointsNumber;
					for (var j = 0; j < remainingToRemove ; j++){
					// console.log('Removing data : '+
					// liveChart.datasets[i].points.length-maxPointsNumber);
						liveChart.removeData();	
					}
				}

	}

	

