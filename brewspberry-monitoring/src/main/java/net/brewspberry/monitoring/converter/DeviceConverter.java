package net.brewspberry.monitoring.converter;

import java.util.Set;
import java.util.stream.Collectors;

import net.brewspberry.monitoring.api.DeviceDto;
import net.brewspberry.monitoring.api.DeviceDto.ActionerStatus;
import net.brewspberry.monitoring.model.AbstractDevice;
import net.brewspberry.monitoring.model.BinarySwitch;
import net.brewspberry.monitoring.model.DeviceStatus;
import net.brewspberry.monitoring.model.SwitchStatus;
import net.brewspberry.monitoring.model.TemperatureSensor;

public class DeviceConverter {

	public Set<DeviceDto> toApiFromSensors(Set<TemperatureSensor> sensors) {
		return sensors.stream().map(this::toApi).collect(Collectors.toSet());
	}

	public Set<DeviceDto> toApi(Set<AbstractDevice> sensors) {
		return sensors.stream().map(this::toApi).collect(Collectors.toSet());
	}

	public DeviceDto toApi(TemperatureSensor result) {
		return new DeviceDto()//
				.uuid(result.getUuid())//
				.id(result.getId())//
				.pin(result.getPin())//
				.state(DeviceStatusConverter.toApi(result.getPinState()))//
				.name(result.getName());
	}
	public DeviceDto toApi(BinarySwitch result) {
		return new DeviceDto()//
				.uuid(result.getUuid())//
				.id(result.getId())//
				.pin(result.getPin())//
				.state(DeviceStatusConverter.toApi(result.getSwitchStatus()))//
				.name(result.getName());
	}

	public DeviceDto toApi(AbstractDevice result) {
		if (result instanceof TemperatureSensor)
			return toApi((TemperatureSensor) result);
		else if (result instanceof BinarySwitch)
			return toApi((BinarySwitch) result);
		else 
			throw new IllegalStateException();
	}

	
	public static class DeviceStatusConverter{
		public static ActionerStatus toApi(DeviceStatus status) {
			switch (status) {
			case PLUGGED : 
				return ActionerStatus.IDLE;
			case UP : 
				return ActionerStatus.STARTED;
			case PENDING : 
				return ActionerStatus.PAUSED;
			case UNPLUGGED:
				return ActionerStatus.STOPPED;
			}
			return null;
		}
		public static ActionerStatus toApi(SwitchStatus status) {
			switch (status) {
			case UP : 
				return ActionerStatus.UP;
			case DOWN : 
				return ActionerStatus.DOWN;
			case NULL : 
				return ActionerStatus.IDLE;
			}
			return null;
		}
	}
}
