package net.brewspberry.monitoring.converter;

import java.util.Set;
import java.util.stream.Collectors;

import net.brewspberry.monitoring.api.ActionnerDto;
import net.brewspberry.monitoring.model.TemperatureSensor;

public class Converter {

	public Set<ActionnerDto> toActionerApi(Set<TemperatureSensor> sensors) {
		return sensors.stream().map(this::toActionerApi).collect(Collectors.toSet());
	}

	public ActionnerDto toActionerApi(TemperatureSensor result) {
		return new ActionnerDto()//
				.uuid(result.getUuid())//
				.id(result.getId())
				.pin(result.getPin());
	}

}
