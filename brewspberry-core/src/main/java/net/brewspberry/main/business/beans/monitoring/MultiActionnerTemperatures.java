package net.brewspberry.main.business.beans.monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultiActionnerTemperatures {
	
	Map<String, List<ConcreteTemperatureMeasurement>> temperatures;

	public MultiActionnerTemperatures (){
		temperatures = new HashMap<>();
	}
	public Map<String, List<ConcreteTemperatureMeasurement>> getTemperatures() {
		return temperatures;
	}

	public void setTemperatures(Map<String, List<ConcreteTemperatureMeasurement>> temperatures) {
		this.temperatures = temperatures;
	} 
	
	public boolean isAlreadySet(String uuid){
		Set<String> keys = temperatures.keySet();
		
		if (keys.contains(uuid))
			return true;
		else 
			return false;
	}
	
	
	public void createMapEntry(String uuid){
		temperatures.put(uuid, new ArrayList<>());
	}
}
