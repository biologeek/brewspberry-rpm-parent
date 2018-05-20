package net.brewspberry.monitoring.model;

/**
 * A {@link BinarySwitch} is a device that can be switched ON and OFF, such as a
 * relay
 */
public class BinarySwitch extends AbstractDevice {

	private SwitchStatus switchStatus;

	public SwitchStatus getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(SwitchStatus switchStatus) {
		this.switchStatus = switchStatus;
	}
	
	
	
}
