package net.brewspberry.monitoring.converter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import net.brewspberry.monitoring.api.Temperature;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.model.SwitchState;
import net.brewspberry.monitoring.model.SwitchStatus;

/**
 * Converts model to DTO and contrary
 *
 */
@Component
public class TemperatureConverter {

	public List<Temperature> toApi(List<TemperatureMeasurement> measured) {
		return measured.stream()//
				.map(this::toApi)//
				.collect(Collectors.toList());
	}

	public Temperature toApi(TemperatureMeasurement measured) {
		return new Temperature()//
				.sensor(measured.getSensor().getId())//
				.date(measured.getDate())//
				.temperature(measured.getTemperature());
	}

	public List<Temperature> toApiSwitch(List<SwitchState> switchStatesForDates) {
		List<Temperature> result = new ArrayList<>();
		if (switchStatesForDates == null || switchStatesForDates.isEmpty())
			return result;
		SwitchState lastState = null;
		for (SwitchState state : switchStatesForDates) {
			Temperature before = new Temperature()//
					.date(state.getDate().minus(Duration.ofSeconds(1)))//
					.sensor(state.getDevice().getId())//
					.temperature(state.getStatusTo() == SwitchStatus.UP ? 0F : 1F);
			Temperature after = new Temperature()//
					.date(state.getDate().minus(Duration.ofSeconds(1)))//
					.sensor(state.getDevice().getId())//
					.temperature(state.getStatusTo() == SwitchStatus.UP ? 1F : 0F);
			result.add(before);
			result.add(after);
			
			lastState = state;
		}
		Temperature last = new Temperature()//
				.date(LocalDateTime.now())//
				.sensor(lastState.getDevice().getId())//
				.temperature(lastState.getStatusTo() == SwitchStatus.UP ? 1F : 0F);
		result.add(last);
		
		return result;
	}

}
