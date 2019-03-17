package net.brewspberry.monitoring.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import net.brewspberry.monitoring.api.RaspberryPinDto;
import net.brewspberry.monitoring.model.RaspberryPin;

@Component
public class PinConverter {

	public List<RaspberryPinDto> toDto(List<RaspberryPin> allPins) {
		return allPins.stream()//
				.map(t -> toDto(t))//
				.collect(Collectors.toList());
	}

	public RaspberryPinDto toDto(RaspberryPin pin) {
		RaspberryPinDto dto = new RaspberryPinDto();
		dto.setId(pin.getId());
		dto.setName(pin.getPinName());
		dto.setNumber(pin.getPinNumber());
		return dto;
	}

	public RaspberryPin toModel(RaspberryPinDto pin) {
		// TODO Auto-generated method stub
		return null;
	}

}
