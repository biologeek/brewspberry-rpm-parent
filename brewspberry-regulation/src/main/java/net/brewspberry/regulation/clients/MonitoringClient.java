package net.brewspberry.regulation.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import net.brewspberry.monitoring.api.Temperature;

@Component
public class MonitoringClient {

	@Autowired
	TemperatureMonitoringClient temperatureMonitoringClient;
	
	public TemperatureMonitoringClient temperature() {
		return this.temperatureMonitoringClient;
	}
	
	
	
	@Component
	public class TemperatureMonitoringClient{
		
		@Value("${params.monitoring.address}")
		private String monitoringAddress;
		
		private String TEMPERATURE_PATH = "/temperature";
		
		
		RestTemplate template;

		public TemperatureMonitoringClient() {
			this.template = new RestTemplate();
		}
		
		public List<Temperature> getTemperaturesForSensors(List<String> uuids) {
			return (List<Temperature>) this.template.getForEntity(monitoringAddress+TEMPERATURE_PATH+"/sensors/"+String.join(",", uuids), List.class);
		}
		
	}
	
	
	

}
