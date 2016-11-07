


(function(){
	'use strict';

	angular.module('brewspberry').filter('unitFilter',function(){
		
		return function(key, units){
			for (var unit in units){
				if (units[unit].obj == key) {
					return unit.trad;
				}
			}
		};
	});
	
})();

