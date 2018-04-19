package net.brewspberry.monitoring.services.tech;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import net.brewspberry.monitoring.model.TemperatureMeasurement;

@Service
/**
 * Technical service that handles JMS sending
 */
public class TemperatureMeasurementJmsService {

	@Autowired
	private JmsTemplate template;
	
	public void send(List<TemperatureMeasurement> measured) {
		template.convertAndSend(measured);
	}

}
