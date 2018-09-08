package net.brewspberry.monitoring.model;

import javax.persistence.Entity;

/**
 * A {@link BinarySwitch} is a device that can be switched ON and OFF, such as a
 * relay that can support many devices : pump, solenoid valve, ...
 */
@Entity
public class BinarySwitch extends AbstractDevice {

	private SwitchStatus switchStatus;

	public SwitchStatus getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(SwitchStatus switchStatus) {
		this.switchStatus = switchStatus;
	}
	
	
	
}
