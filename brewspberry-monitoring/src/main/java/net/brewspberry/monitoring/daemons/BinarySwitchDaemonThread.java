package net.brewspberry.monitoring.daemons;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Map;

import org.junit.Assert;

import com.pi4j.io.w1.W1Master;

import net.brewspberry.monitoring.model.BinarySwitch;
import net.brewspberry.monitoring.model.DeviceStatus;
import net.brewspberry.monitoring.model.SwitchStatus;
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
	public static final String UUID_PRM = "uuid";

	public static final int LONG_CHECK_TIMEOUT = 12;
	public static final int SHORT_CHECK_TIMEOUT = 1;
	private BinarySwitchRepository binarySwitchRepository;
	private W1Master oneWireMaster;
	private ThreadStateServices threadServices;
	private ThreadWitnessCheckServices witnessServices;
	private Map<String, Object> parameters;

	public BinarySwitchDaemonThread(BinarySwitchRepository repository, //
			W1Master oneWireMaster, //
			ThreadStateServices threadServices, //
			ThreadWitnessCheckServices witnessServices) {
		this.binarySwitchRepository = repository;
		this.oneWireMaster = oneWireMaster;
		this.threadServices = threadServices;
		this.witnessServices = witnessServices;
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

		LocalDateTime endLocalDate = LocalDateTime.now().plus(Duration.ofSeconds((long) parameters.get(DURATION_PRM)));

		int sleepTime = 10000;
		while (LocalDateTime.now().isBefore(endLocalDate)) {
			sleepTime = getSleepTime(endLocalDate);
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {

			}
		}

		postRun();
		
		Thread.currentThread().interrupt();

	}

	/**
	 * Executed after run cycle completion. Free {@link ThreadWitness} and updates
	 * device status
	 */
	private void postRun() {
		BinarySwitch device = this.binarySwitchRepository.findByUuid((String) this.parameters.get(UUID_PRM));
		
		device.setSwitchStatus(SwitchStatus.DOWN);
		device.setPinState(DeviceStatus.STOPPED);
		device.setLastStateChangeDate(new Date());
		binarySwitchRepository.save(device);
		
		threadServices.cleanState((String) parameters.get(UUID_PRM));
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
	}

}
