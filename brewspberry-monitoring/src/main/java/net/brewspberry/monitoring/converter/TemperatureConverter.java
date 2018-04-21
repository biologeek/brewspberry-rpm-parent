package net.brewspberry.monitoring.converter;

import java.util.List;
import java.util.stream.Collectors;

import net.brewspberry.monitoring.api.Temperature;
import net.brewspberry.monitoring.model.TemperatureMeasurement;

/**
 * Converts model to DTO and contrary
 *
 */
public class TemperatureConverter {

	public List<Temperature> toApi(List<TemperatureMeasurement> measured) {
		return measured.stream()//
				.map(this::toApi)//
				.collect(Collectors.toList());
	}
	public Temperature toApi(TemperatureMeasurement measured) {
		return new Temperature()//
				.sensor(new DeviceConverter().toApi(measured.getSensor()))//
				.date(measured.getDate())//
				.temperature(measured.getTemperature());
	}

}
