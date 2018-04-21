package net.brewspberry.monitoring.services.tech;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import net.brewspberry.monitoring.converter.TemperatureConverter;
import net.brewspberry.monitoring.model.TemperatureMeasurement;

@Service
/**
 * Technical service that handles JMS sending
 */
public class TemperatureMeasurementJmsService {

	@Autowired
	private JmsTemplate template;
	/**
	 * Converts given object to DTO and sends JMS
	 */
	public void send(TemperatureMeasurement measured) {
		template.convertAndSend(new	TemperatureConverter().toApi(measured));
	}
	/**
	 * Converts given list to DTO and sends JMS
	 */
	public void send(List<TemperatureMeasurement> measured) {
		template.convertAndSend(new	TemperatureConverter().toApi(measured));
	}

}
