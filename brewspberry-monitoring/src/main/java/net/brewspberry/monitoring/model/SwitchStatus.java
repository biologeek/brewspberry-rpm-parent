package net.brewspberry.monitoring.model;
/**
 * Switch status represents the state of the device. When device is activated/up/whatever positive it is UP
 * If it's off, it's DOWN.
 * <br><br>
 * For every other case, it can be set to NULL
 */
public enum SwitchStatus {
	
	UP, DOWN, NULL;

}
