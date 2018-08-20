package net.brewspberry.monitoring.services;

import net.brewspberry.monitoring.exceptions.StateChangeException;
import net.brewspberry.monitoring.model.BinarySwitch;
import net.brewspberry.monitoring.model.SwitchStatus;

/**
 * Handles behaviour of a binary switch. Used for switches, relays, ...
 *
 */
public interface BinarySwitchService extends DeviceService<BinarySwitch> {

	public BinarySwitch setSwitchUp(BinarySwitch device) throws StateChangeException;

	public BinarySwitch setSwitchDown(BinarySwitch device) throws StateChangeException;

	public SwitchStatus getDeviceStatus(BinarySwitch device);
	
}
