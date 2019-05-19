package net.brewspberry.monitoring.services;

import java.time.LocalDateTime;
import java.util.List;

import net.brewspberry.monitoring.exceptions.ElementNotFoundException;
import net.brewspberry.monitoring.exceptions.StateChangeException;
import net.brewspberry.monitoring.model.BinarySwitch;
import net.brewspberry.monitoring.model.SwitchStatus;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.model.SwitchState;

/**
 * Handles behaviour of a binary switch. Used for switches, relays, ...
 *
 */
public interface BinarySwitchService extends DeviceService<BinarySwitch> {

	public BinarySwitch setSwitchUp(BinarySwitch device) throws StateChangeException;

	public BinarySwitch setSwitchDown(BinarySwitch device) throws StateChangeException;
	public BinarySwitch toggleSwitch(Long id) throws StateChangeException;

	public SwitchStatus getDeviceStatus(BinarySwitch device);

	public List<SwitchState> getSwitchStatesForDates(String uuid, LocalDateTime begin, LocalDateTime end) throws ElementNotFoundException;
	
}
