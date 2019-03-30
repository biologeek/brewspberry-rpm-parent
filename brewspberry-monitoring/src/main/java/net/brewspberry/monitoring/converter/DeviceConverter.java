package net.brewspberry.monitoring.converter;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.brewspberry.monitoring.api.DeviceDto;
import net.brewspberry.monitoring.api.DeviceDto.ActionerStatus;
import net.brewspberry.monitoring.api.DeviceDto.ActionerType;
import net.brewspberry.monitoring.model.AbstractDevice;
import net.brewspberry.monitoring.model.BinarySwitch;
import net.brewspberry.monitoring.model.DeviceStatus;
import net.brewspberry.monitoring.model.DeviceType;
import net.brewspberry.monitoring.model.SwitchStatus;
import net.brewspberry.monitoring.model.TemperatureSensor;

@Component
public class DeviceConverter {
	
	@Autowired
	private PinConverter pinConverter;

	public Set<DeviceDto> toApiFromSensors(Set<TemperatureSensor> sensors) {
		return sensors.stream().map(this::toApi).collect(Collectors.toSet());
	}

	public Set<DeviceDto> toApi(Set<AbstractDevice> sensors) {
		return sensors.stream().map(this::toApi).collect(Collectors.toSet());
	}

	/**
	 * Converts specifics for {@link TemperatureSensor}
	 * 
	 * @param sensors
	 * @return
	 */
	public DeviceDto toApi(TemperatureSensor result, DeviceDto converted) {
		return converted//
				.state(DeviceStatusConverter.toApi(result.getPinState()))
				.type(ActionerType.THERMOMETER);
	}

	/**
	 * Converts specifics for {@link BinarySwitch}
	 * 
	 * @param sensors
	 * @return
	 */
	public DeviceDto toApi(BinarySwitch result, DeviceDto converted) {
		return converted//
				.state(DeviceStatusConverter.toApi(result.getSwitchStatus()))
				.type(toDtoType(result.getType()));
	}

	private ActionerType toDtoType(DeviceType type) {
		switch (type) {
		case ENGINE_RELAY:
			return ActionerType.ENGINE;
		case PUMP :
			return ActionerType.PUMP;
		case TEMPERATURE_SENSOR :
			return ActionerType.THERMOMETER;
		}
		return null;
	}

	/**
	 * Converts non-specific properties of devices
	 * 
	 * @param sensors
	 * @return
	 */
	public DeviceDto toApi(AbstractDevice result) {
		DeviceDto converted = new DeviceDto()//
				.uuid(result.getUuid())//
				.id(result.getId())//
				.pin(pinConverter.toDto(result.getPin()))//
				.name(result.getName())//
				.isPlugged(result.isPlugged());
		if (result instanceof TemperatureSensor)
			converted = toApi((TemperatureSensor) result, converted);
		else if (result instanceof BinarySwitch)
			converted = toApi((BinarySwitch) result, converted);
		else
			throw new IllegalStateException();
		return converted;
	}

	public static class DeviceStatusConverter {
		public static ActionerStatus toApi(DeviceStatus status) {
			if (status != null) {
				switch (status) {
				case PLUGGED:
					return ActionerStatus.IDLE;
				case UP:
					return ActionerStatus.UP;
				case PAUSED:
					return ActionerStatus.PAUSED;
				case STOPPED:
					return ActionerStatus.STOPPED;
				case UNPLUGGED:
					return ActionerStatus.DOWN;
				case RUNNING:
					return ActionerStatus.STARTED;
				default:
					break;
				}
			}
			return null;
		}

		public static ActionerStatus toApi(SwitchStatus status) {
			switch (status) {
			case UP:
				return ActionerStatus.UP;
			case DOWN:
				return ActionerStatus.DOWN;
			case NULL:
				return ActionerStatus.IDLE;
			}
			return null;
		}
	}
	
	public AbstractDevice toModel(DeviceDto dto) {
		if (dto == null)
			return null;
		switch (dto.getType()) {
		case THERMOMETER :
			return toModelTemperatureSensor(dto);
		case ENGINE:
		case PUMP :
			return toModelSwitch(dto);
		}
		return null;
	}

	private AbstractDevice toModelSwitch(DeviceDto dto) {
		return new BinarySwitch()//
				.type(toModelType(dto.getType()))//
				.uuid(dto.getUuid())//
				.id(dto.getId())
				.creationDate(dto.getCreation())//
				.lastChangedDate(dto.getLastChange())//
				.name(dto.getName())//
				.plugged(dto.isPlugged());
	}

	private DeviceType toModelType(ActionerType type) {
		switch (type) {
		case ENGINE:
			return DeviceType.ENGINE_RELAY;
		case PUMP :
			return DeviceType.PUMP;
		case THERMOMETER :
			return DeviceType.TEMPERATURE_SENSOR;
		}
		return null;
	}

	public TemperatureSensor toModelTemperatureSensor(DeviceDto dto) {
		return (TemperatureSensor) new TemperatureSensor()//
				.creationDate(dto.getCreation())//
				.lastChangedDate(dto.getLastChange())//
				.id(dto.getId())//
				.name(dto.getName())//
				.pin(pinConverter.toModel(dto.getPin()))//
				.uuid(dto.getUuid());
	}
}
