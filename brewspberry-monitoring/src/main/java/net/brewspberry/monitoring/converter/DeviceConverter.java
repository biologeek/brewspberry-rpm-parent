package net.brewspberry.monitoring.converter;

import java.util.Set;
import java.util.stream.Collectors;

import net.brewspberry.monitoring.api.DeviceDto;
import net.brewspberry.monitoring.model.TemperatureSensor;

public class DeviceConverter {

	public Set<DeviceDto> toApi(Set<TemperatureSensor> sensors) {
		return sensors.stream().map(this::toApi).collect(Collectors.toSet());
	}

	public DeviceDto toApi(TemperatureSensor result) {
		return new DeviceDto()//
				.uuid(result.getUuid())//
				.id(result.getId())
				.pin(result.getPin());
	}

}
