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
		System.out.println(pin);
		if (pin == null)
			return null;
		RaspberryPinDto dto = new RaspberryPinDto();
		dto.setId(pin.getId());
		dto.setName(pin.getPinName());
		dto.setNumber(pin.getPinNumber());
		return dto;
	}

	public RaspberryPin toModel(RaspberryPinDto pin) {
		if (pin == null)
			return null;
		RaspberryPin model = new RaspberryPin();
		model.setId(pin.getId());
		model.setPinName(pin.getName());
		model.setPinNumber(pin.getNumber());
		return model;
	}

}
