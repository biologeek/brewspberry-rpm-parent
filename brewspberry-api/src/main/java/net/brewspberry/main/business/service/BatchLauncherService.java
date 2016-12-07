package net.brewspberry.main.business.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

import net.brewspberry.main.adapter.RelayAdapter;
import net.brewspberry.main.batches.beans.BatchParams;
import net.brewspberry.main.batches.launchers.Batch;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificActionerLauncherService;
import net.brewspberry.main.business.ISpecificActionerService;
import net.brewspberry.main.business.beans.Actioner;
import net.brewspberry.main.business.beans.DurationBO;
import net.brewspberry.main.business.beans.Etape;
import net.brewspberry.main.business.beans.GenericActionner.ActionerStatus;
import net.brewspberry.main.util.ConfigLoader;
import net.brewspberry.main.util.Constants;
import net.brewspberry.main.util.LogManager;
import net.brewspberry.main.util.OSUtils;

@Service
@Transactional
public class BatchLauncherService implements ISpecificActionerLauncherService {

	private Logger logger = LogManager.getInstance(BatchLauncherService.class.getName());

	@Autowired
	private ISpecificActionerService actionerService;

	@Autowired
	@Qualifier("actionerServiceImpl")
	IGenericService<Actioner> genActionerService;

	@Autowired
	Batch temperatureBatch;

	Thread recordTemperatureThread;

	String commandLineRegexp = "/home/pi/batches/bchrectemp.py [0-9]{0,5} [0-9]{0,5}";

	Pattern pattern = Pattern.compile(commandLineRegexp);

	String getTemperatureRunningGrep = "ps -ef | grep bchrectemp.py";

	private RelayAdapter relayAdapter = RelayAdapter.getInstance();

	private GpioController gpioController;
	@Value("${param.batches.length.coef}")
	String durationCoef;
	
	public BatchLauncherService() {
		super();
		if (OSUtils.isRaspbian()) {
			gpioController = GpioFactory.getInstance();
		} else {
			gpioController = null;
		}
	}

	/**
	 * startAction starts device for selected Actioner - ds18b20 : launches Java
	 * batch that collects and stores temperatures measured by ds18b20s - relays
	 * : starting action means setting PinState to HIGH thus running the device
	 * 
	 * It also stores the Actioner in database
	 */
	@Override
	public Actioner startAction(Actioner actioner) throws Exception {

		// TODO : fix this part !!!!

		Etape currentStep = null;
		String duration = "";
		BatchParams arguments = new BatchParams();

		if (actioner != null) {

			logger.fine(actioner.getAct_etape() + " " + actioner.getAct_brassin());
			switch (actioner.getAct_generic().getGact_type()) {

			case DS18B20:
				// DS18B20

				/*
				 * When loading ds18b20, it executes in a separate thread a time
				 * limited batch to record temperatures
				 * 
				 * Requires act_type, act_brassin, act_etape, name, uuid
				 */

				logger.info("It's a DS18B20 :");

				logger.fine("Duree : " + actioner.getAct_etape().getEtp_duree());
				try {
					if (actioner.getAct_brassin() != null && actioner.getAct_etape() != null) {

						currentStep = actioner.getAct_etape();

						/*
						 * Duration of step converted in Minutes * Coefficient =
						 * recommended time In case thread is not finished at
						 * the end of real step, a mechanism is to
						 * interrupt it.
						 * 
						 * Is implemented in
						 * 
						 * @see startActionInDatabase
						 */

						actioner = actionerService.startActionInDatabase(actioner);

						duration = calculateDuration(currentStep, durationCoef);

						arguments = assignArgumentsToBatch(actioner, duration);

						logger.fine("Launching batch thread for " + duration + " " + arguments.getLaunchType());

						temperatureBatch.setBatchParams(arguments);

						recordTemperatureThread = new Thread((Runnable) temperatureBatch);

						recordTemperatureThread.start();

					} else {

						logger.warning("TemperatureMeasurement not available without Step and Brew...");
					}

				} catch (Exception e) {

					e.printStackTrace();
					// TODO: handle exception
				}

				break;

			case ENGINE_RELAY:
			case PUMP_RELAY:
				GpioPinDigitalOutput gpio = gpioController.provisionDigitalOutputPin(
						Constants.BREW_GPIO.get(actioner.getAct_generic().getGact_raspi_pin()));
				// Relay
				logger.info("It's a relay !");

				if (actioner.getAct_generic().getGact_raspi_pin() != "") {

					logger.info("Provisionning pin " + actioner.getAct_generic().getGact_raspi_pin() + " "
							+ Constants.BREW_GPIO.get(actioner.getAct_generic().getGact_raspi_pin()));
					try {

						// Turning ON or OFF the pin
						PinState state = relayAdapter.changePinState(gpio);

						actioner.getAct_generic().setGact_status(ActionerStatus.STARTED);
						logger.info("Actioner at pin " + actioner.getAct_generic().getGact_raspi_pin()
								+ " changed state to " + actioner.getAct_generic().getGact_status());

					} catch (Exception e) {

						logger.severe("Couldn't change Pin state..." + actioner.getAct_generic().getGact_raspi_pin()
								+ ", setting status to FAILED !");
						actioner.getAct_generic().setGact_status(ActionerStatus.FAILED);

					}
					actioner = actionerService.startActionInDatabase(actioner);

				} else {
					throw new Exception("Empty Pin !!");
				}

				gpioController.unprovisionPin(gpio);

				break;

			}

		}
		return actioner;
	}

	private BatchParams assignArgumentsToBatch(Actioner actioner, String duration) {
		String[] args = new String[6];
		args[0] = "MINUTE";
		args[1] = String.valueOf(duration);
		args[2] = String.valueOf(actioner.getAct_brassin().getBra_id());
		args[3] = String.valueOf(actioner.getAct_etape().getEtp_id());
		args[4] = String.valueOf(actioner.getAct_id());
		args[5] = String.valueOf(actioner.getAct_generic().getGact_uuid());

		return new BatchParams.BatchParamsBuilder().buildBatchParams(args);

	}

	/**
	 * Calculates measurement duration using step REAL beginning date and
	 * theoretical end date.
	 * 
	 * @param currentStep
	 * @param durationCoef
	 * @return
	 */
	private String calculateDuration(Etape currentStep, String durationCoef) {
		Calendar beginning = Calendar.getInstance();
		
		double durationCoefDigit;
		double numberOfSeconds;
		
		beginning.setTime(currentStep.getEtp_debut_reel());	
		
		if (currentStep.getEtp_debut_reel() == null){
			logger.warning("Not using real beginning date as it is null. Degraded mode using theoretical beginning");
			if (currentStep.getEtp_debut() == null && currentStep.getEtp_fin() == null)
				throw new IllegalArgumentException("step.beginning.end.mandatory");
			
			beginning.setTime(currentStep.getEtp_debut());
		}
		
		if (currentStep.getEtp_duree() == null){
			throw new IllegalArgumentException("step.duration.mandatory");
		} else {
			numberOfSeconds = convertToSeconds(currentStep.getEtp_duree());
		}
		
		if (durationCoef == null)
			durationCoefDigit = 1.0;
		else 
			durationCoefDigit = Double.parseDouble(durationCoef);
		
		
		Double finalDuration = durationCoefDigit * numberOfSeconds;
		
	
		return finalDuration.toString();
	}

	private double convertToSeconds(DurationBO duration) {
		
		double result = 0;
		switch (duration.getUnit()){
		
		case Calendar.SECOND :
			result = duration.getDuration();
			break;
			
		case Calendar.MINUTE:
			result = duration.getDuration() * 60;
			break;
			
		case Calendar.HOUR :
			result = duration.getDuration() * 60 * 60;
			break;
			
		case Calendar.DATE :
			result = duration.getDuration() * 60 * 60 * 24;
			break;
			
		case Calendar.MONTH :
			result = duration.getDuration() * 60 * 60 * 24 * 30;
			break;
			
		case Calendar.YEAR :
			result = duration.getDuration() * (60 * 60 * 24 * 30 * 12 + 5);
			break;
		}
		
		return result;
		
	}

	/**
	 * 
	 * @see BatchLauncherService Whenever user stops an actioner (for example
	 *      switch off a relay), this method must be called !
	 * 
	 *      It stops configured devices. for the moment : - relays (type 2) -
	 *      ds18b20 temperature sensors (type 1). For these ones in fact it
	 *      stops the job collecting temperatures.
	 * 
	 * @param Actioner
	 *            that has to be stopped. Actioner must have ID
	 * @return
	 * @throws Exception
	 * 
	 */
	public Actioner stopAction(Actioner actioner) throws Exception {
		if (actioner != null && actioner.getAct_id() != 0) {

			if (ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "param.batches.language").equals("java")) {

				if (recordTemperatureThread != null) {

					if (recordTemperatureThread.isAlive()) {

						recordTemperatureThread.interrupt();

						actionerService.stopActionInDatabase(actioner);

						// Wait for it to end
						recordTemperatureThread.join();

					} else {
						logger.fine("Thread is dead");

					}

				} else {
					logger.severe("Thread is null !!");
				}

			} else if (ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "param.batches.language")
					.equals("python")) {
				switch (actioner.getAct_generic().getGact_type()) {

				case DS18B20:

					// ds18b20

					// First, checks if a process is currently running
					Process proc = null;
					try {
						proc = Runtime.getRuntime().exec(getTemperatureRunningGrep);
					} catch (IOException e) {
						e.printStackTrace();
					}

					BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));

					String line = null;

					int counter = 0;
					while ((line = br.readLine()) != null) {

						// Checks if it is really the running batch
						if (line.matches(commandLineRegexp)) {

							System.out.println("Matching process found !");

							/*
							 * Returns PID of program using bra_id, etape_id and
							 * act_id So no way we kill the wrong process
							 */
							String pid = actionerService.getPIDFromPs("bchrectemp.py "
									+ actioner.getAct_brassin().getBra_id() + " " + actioner.getAct_etape().getEtp_id())
									+ " " + actioner.getAct_id();

							if (pid != "") {

								// Kills the process !
								Runtime.getRuntime().exec("kill -SIGTERM " + pid);
								counter++;
							}

							// records in DB

							actioner = actionerService.stopActionInDatabase(actioner);
						}
					}

					break;

				case ENGINE_RELAY:

					// Relay

					GpioPinDigitalOutput gpio = null;
					if (actioner.getAct_generic().getGact_raspi_pin() != null) {
						gpio = gpioController.provisionDigitalOutputPin(
								Constants.BREW_GPIO.get(actioner.getAct_generic().getGact_raspi_pin()));

						if (gpio.getState().equals(PinState.HIGH)) {

							relayAdapter.changePinState(gpio);

							// Not recording relay actions as therre may be more
							// than one
							// actioner = this.stopActionInDatabase(actioner);
						}

					}

					relayAdapter.setShutdownOptionsandShutdown(gpioController, gpio, PinState.LOW, true);
					break;

				}

			} else {

				logger.severe("Actioner ID is null, can't stop null actioner !!");
				throw new Exception();
			}
		} else {

			logger.warning("Unknown language");

		}
		return actioner;

	}

	@Override
	public Actioner startAction(Long actioner) throws Exception {

		Actioner actionner = genActionerService.getElementById(actioner);

		return this.startAction(actionner);
	}

	@Override
	public Actioner stopAction(Long actioner) throws Exception {

		Actioner actionner = genActionerService.getElementById(actioner);

		return this.stopAction(actionner);
	}

}