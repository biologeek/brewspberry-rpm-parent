package net.brewspberry.monitoring.daemons;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.junit.Assert;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

import net.brewspberry.monitoring.model.BinarySwitch;
import net.brewspberry.monitoring.model.DeviceStatus;
import net.brewspberry.monitoring.model.SwitchStatus;
import net.brewspberry.monitoring.model.ThreadState;
import net.brewspberry.monitoring.model.ThreadWitness;
import net.brewspberry.monitoring.repositories.BinarySwitchRepository;
import net.brewspberry.monitoring.services.ThreadStateServices;
import net.brewspberry.monitoring.services.ThreadWitnessCheckServices;

/**
 * this class is used to set a binary switch up for a certain time
 *
 */
public class BinarySwitchDaemonThread implements Runnable {

	public static final String DURATION_PRM = "duration";
	public static final String DEVICE_PRM = "device";

	public static final int LONG_CHECK_TIMEOUT = 12;
	public static final int SHORT_CHECK_TIMEOUT = 1;
	private BinarySwitchRepository binarySwitchRepository;
	private GpioController gpioController;
	private ThreadStateServices threadServices;
	private ThreadWitnessCheckServices witnessServices;
	private Map<String, Object> parameters;

	public BinarySwitchDaemonThread(BinarySwitchRepository repository, //
			GpioController gpioController, //
			ThreadStateServices threadServices, //
			ThreadWitnessCheckServices witnessServices) {
		this.binarySwitchRepository = repository;
		this.gpioController = gpioController;
		this.threadServices = threadServices;
		this.setWitnessServices(witnessServices);
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Thread must stay alive during given time parameter (in seconds)
	 */
	@Override
	public void run() {

		checkParameters();
		BinarySwitch device = this.binarySwitchRepository.findByUuid((String) this.parameters.get(DEVICE_PRM));

		LocalDateTime endLocalDate = LocalDateTime.now().plus(Duration.ofSeconds((long) parameters.get(DURATION_PRM)));

		int sleepTime = 10000;
		while (LocalDateTime.now().isBefore(endLocalDate)) {
			GpioPinDigitalOutput outputPin = gpioController.provisionDigitalOutputPin(device.getPin().getPin());

			if (outputPin.getState() == PinState.HIGH)
				outputPin.setState(PinState.LOW);
			ThreadState state = this.threadServices.readState(device.getUuid());

			if (state == null) {
				gpioController.unprovisionPin(outputPin);
				interruptCurrentRun();
				
			}
			sleepTime = getSleepTime(endLocalDate);
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				gpioController.unprovisionPin(outputPin);
			}
		}

		postRun();

		interruptCurrentRun();

	}

	private void interruptCurrentRun() {
		
		
		Thread.currentThread().interrupt();
	}

	/**
	 * Executed after run cycle completion. Free {@link ThreadWitness} and updates
	 * device status
	 */
	private void postRun() {
		BinarySwitch device = this.binarySwitchRepository.findByUuid((String) this.parameters.get(DEVICE_PRM));

		device.setSwitchStatus(SwitchStatus.DOWN);
		device.setPinState(DeviceStatus.STOPPED);
		device.setLastStateChangeDate(LocalDateTime.now());
		binarySwitchRepository.save(device);

		threadServices.cleanState(device.getUuid());
	}

	private int getSleepTime(LocalDateTime endLocalDate) {

		long numberOfSecndsBetweenEndAndNow = Duration.between(LocalDateTime.now(), endLocalDate)
				.get(ChronoUnit.SECONDS);

		if (numberOfSecndsBetweenEndAndNow <= LONG_CHECK_TIMEOUT) {
			return 1000;
		} else {
			return 10000;
		}
	}

	private void checkParameters() {
		Assert.assertNotNull(parameters.get(DURATION_PRM));
		Assert.assertNotNull(parameters.get(DEVICE_PRM));
	}

	public ThreadWitnessCheckServices getWitnessServices() {
		return witnessServices;
	}

	public void setWitnessServices(ThreadWitnessCheckServices witnessServices) {
		this.witnessServices = witnessServices;
	}

}
