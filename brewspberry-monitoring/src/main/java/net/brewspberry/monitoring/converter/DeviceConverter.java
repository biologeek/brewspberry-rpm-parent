package net.brewspberry.monitoring.converter;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.brewspberry.monitoring.api.DeviceDto;
import net.brewspberry.monitoring.api.DeviceDto.ActionerStatus;
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
				.state(DeviceStatusConverter.toApi(result.getPinState()));
	}

	/**
	 * Converts specifics for {@link BinarySwitch}
	 * 
	 * @param sensors
	 * @return
	 */
	public DeviceDto toApi(BinarySwitch result, DeviceDto converted) {
		return converted//
				.state(DeviceStatusConverter.toApi(result.getSwitchStatus()));
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
		switch (dto.getType()) {
		case DS18B20 :
			return toModelTemperatureSensor(dto);
		case ENGINE_RELAY:
		case VALVE :
			return toModelSwitch(dto);
		}
		return null;
	}

	private AbstractDevice toModelSwitch(DeviceDto dto) {
		return new BinarySwitch()//
				.type(DeviceType.RELAY_SWITCH)//
				.uuid(dto.getUuid())//
				.id(dto.getId())
				.creationDate(dto.getCreation())//
				.lastChangedDate(dto.getLastChange())//
				.name(dto.getName())//
				.plugged(dto.isPlugged());
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
