package net.brewspberry.monitoring.controller.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.monitoring.api.DeviceDto;
import net.brewspberry.monitoring.converter.DeviceConverter;
import net.brewspberry.monitoring.model.AbstractDevice;
import net.brewspberry.monitoring.services.DeviceService;

@RequestMapping("/devices")
@RestController
public class DevicesController {
	
	@Autowired
	private DeviceService<AbstractDevice> deviceServices;
	
	@RequestMapping(path="/", method=RequestMethod.GET)
	public Set<DeviceDto> getAllDevices(){
		return new DeviceConverter().toApi(deviceServices.listAllDevices());
	}

}
