package net.brewspberry.monitoring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.monitoring.model.RaspberryPin;
import net.brewspberry.monitoring.repositories.RaspberryPinRepository;
import net.brewspberry.monitoring.services.PinServices;

@Service
public class DefaultPinServices implements PinServices {

	@Autowired
	private RaspberryPinRepository raspberryPinRepository;

	@Override
	public List<RaspberryPin> getAllPins() {
		return this.raspberryPinRepository.findAll();
	}

}
