package net.brewspberry.business.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.engine.jdbc.batch.internal.BatchBuilderImpl;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

import net.brewspberry.adapter.RelayAdapter;
import net.brewspberry.batches.launchers.Batch;
import net.brewspberry.batches.launchers.BatchRecordTemperatures;
import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificActionerDao;
import net.brewspberry.business.ISpecificActionerService;
import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.exceptions.NotAppropriateStatusException;
import net.brewspberry.dao.ActionerDaoImpl;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.exceptions.ServiceException;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;
import net.brewspberry.util.LogManager;

public class ActionerServiceImpl implements IGenericService<Actioner>,
		ISpecificActionerService {

	public static final Logger logger = LogManager
			.getInstance(ActionerServiceImpl.class.toString());

	String commandLineRegexp = "/home/pi/batches/bchrectemp.py [0-9]{0,5} [0-9]{0,5}";

	Pattern pattern = Pattern.compile(commandLineRegexp);

	IGenericDao<Actioner> actionerDao = new ActionerDaoImpl();
	ISpecificActionerDao actionerSpecDao = new ActionerDaoImpl();

	Batch temperatureBatch;
	Thread recordTemperatureBatch;

	String getTemperatureRunningGrep = "ps -ef | grep bchrectemp.py";

	final GpioController gpioController = GpioFactory.getInstance();
	RelayAdapter relayAdapter = RelayAdapter.getInstance();

	public ActionerServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Actioner save(Actioner arg0) throws DAOException {

		return actionerDao.save(arg0);
	}

	@Override
	public Actioner update(Actioner arg0) {


		return actionerDao.update (arg0);
	}

	@Override
	public Actioner getElementById(long id) {
		return actionerDao.getElementById(id);
	}

	@Override
	public List<Actioner> getAllElements() {

		return actionerDao.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		
		actionerDao.deleteElement(id);
	}

	@Override
	public void deleteElement(Actioner arg0) {
		actionerDao.deleteElement(arg0);

	}

	@Override
	public List<Actioner> getAllDistinctElements() {

		return actionerDao.getAllDistinctElements();
	}

	@Override
	public List<Actioner> getActionerByBrassin(Brassin brassin) {

		return actionerSpecDao.getActionerByBrassin(brassin);
	}

	@Override
	public List<Actioner> getActionnerByEtape(Etape etape) {
		return actionerSpecDao.getActionnerByEtape(etape);
	}

	public List<Actioner> getRealTimeActionersByName(List<String> which,
			Boolean uuid, Brassin brassin, Etape etape) throws IOException,
			ServiceException {

		/*
		 * command format : BCHRECTEMP.py ActionerName ActionerUUID
		 */

		List<Actioner> result = new ArrayList<Actioner>();
		Process commandResult = null;

		for (String actName : which) {
			try {
				commandResult = Runtime.getRuntime().exec(
						getTemperatureRunningGrep + "| grep " + which);
			} catch (IOException e) {
				System.out.println(getTemperatureRunningGrep + "| grep "
						+ which + " threw Exception");
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					commandResult.getInputStream()));
			String s = "";
			while ((s = reader.readLine()) != null) {

				Matcher matcher = pattern.matcher(s);
				Actioner curAct = new Actioner();
				String[] actionerCommand = new String[3];
				String actionerFileScriptPath = "";
				String actionerProbeName = "";
				String actionerProbeUUID = "";
				Actioner actionerDoesExists = new Actioner();

				if (matcher.find()) {

					if (matcher.group(0).split(" ").length == 3) {
						actionerCommand = matcher.group(0).split(" ");

						actionerFileScriptPath = actionerCommand[0];
						actionerProbeName = actionerCommand[1];
						actionerProbeUUID = actionerCommand[2];

						curAct.setAct_type(Constants.ACT_DS18B20);
						curAct.setAct_nom(actionerProbeName);
						curAct.setAct_uuid(actionerProbeUUID);
						curAct.setAct_brassin(brassin);
						curAct.setAct_etape(etape);

						actionerDoesExists = actionerSpecDao
								.getActionerByFullCharacteristics(curAct);

						if (actionerDoesExists.getAct_date_debut() != null) {

							curAct = actionerDoesExists;
							result.add(curAct);
						} else {
							throw new ServiceException(
									"Actioner does not exist ! "
											+ curAct.toString());
						}

					}
				}
			}
		}

		return result;
	}

	public List<Actioner> getAllRealTimeActioners() {
		List<Actioner> result = new ArrayList<Actioner>();

		return result;
	}

	private Actioner startActionInDatabase(Actioner arg0)
			throws ServiceException, NotAppropriateStatusException {

		/*
		 * Starts the action and saves it in database.
		 */

		Actioner result = null;
		if (arg0.getAct_date_debut() == null) {
			arg0.setAct_date_debut(new Date());
			arg0.setAct_status(Constants.ACT_RUNNING);
		}

		if (arg0.getAct_activated() == false && arg0.getAct_used() == false) {

			arg0.setAct_activated(true);
		} else
			throw new NotAppropriateStatusException();
		try {
			result = this.save(arg0);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("Couldn't save Actioner "
					+ arg0.toString());
		}
		return result;
	}

	private Actioner stopActionInDatabase(Actioner arg0)
			throws ServiceException, NotAppropriateStatusException {

		/*
		 * When clicking stop for example, adds end date and saves the action
		 * setting Actioner status to STOPPED
		 */

		Actioner result = null;

		if (arg0.getAct_id() == 0) {
			// Searching by uuid, brassin, etape, and type
			arg0 = actionerSpecDao.getActionerByFullCharacteristics(arg0);
		}

		if (arg0.getAct_date_fin() == null) {
			arg0.setAct_date_fin(new Date());
			arg0.setAct_status(Constants.ACT_STOPPED);
		}
		if (arg0.getAct_activated() == true && arg0.getAct_used() == false) {

			arg0.setAct_used(true);

		} else
			throw new NotAppropriateStatusException();

		try {
			result = actionerDao.update(arg0);
		} catch (Exception e) {
			throw new ServiceException("Couldn't update Actioner :"
					+ arg0.toString());
		}
		return result;
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

		Etape currentStep = null;
		String duration = "";
		String[] args = new String[5];

		if (actioner != null) {

			logger.fine(actioner.getAct_etape() + " "
					+ actioner.getAct_brassin());
			switch (actioner.getAct_type()) {

			case "1":
				// DS18B20

				/*
				 * When loading ds18b20, it executes in a separate thread a time
				 * limited batch to record temperatures
				 * 
				 * Requires act_type, act_brassin, act_etape, name, uuid
				 */

				String durationCoef = ConfigLoader.getConfigByKey(
						Constants.CONFIG_PROPERTIES,
						"param.batches.length.coef");

				logger.info("It's a DS18B20 :");


				logger.fine("Duree : " + actioner.getAct_etape().getEtp_duree());
				logger.fine("Duree");
				try {
					if (actioner.getAct_brassin() != null
							&& actioner.getAct_etape() != null) {

						currentStep = actioner.getAct_etape();

						/*
						 * Duration of step converted in Minutes * Coefficient =
						 * recommended time In case thread is not finished at
						 * the end of real step, a security mechanism is to
						 * interrupt it.
						 * 
						 * Is implemented in
						 * 
						 * @see stopActionInDatabase
						 */

						actioner = this.startActionInDatabase(actioner);

						duration = String.valueOf((double) currentStep
								.getEtp_duree().getMinute()
								* (Double.parseDouble(durationCoef)));

						args[0] = "MINUTE";
						args[1] = String.valueOf(duration);
						args[2] = String.valueOf(actioner.getAct_brassin().getBra_id());
						args[3] = String.valueOf(actioner.getAct_etape().getEtp_id());
						args[4] = String.valueOf(actioner.getAct_id());

						
						logger.info(String.join(";", args));
						temperatureBatch = new BatchRecordTemperatures(args);

						recordTemperatureBatch = new Thread(
								(Runnable) temperatureBatch);

						recordTemperatureBatch.start();

					} else {

						logger.warning("TemperatureMeasurement not available without Step and Brew...");
					}

				} catch (Throwable e) {

					e.printStackTrace();
					// TODO: handle exception
				}

				break;

			case "2":

				// Relay
				logger.info("It's a relay !");

				if (actioner.getAct_raspi_pin() != "") {

					logger.info("Provisionning pin "
							+ actioner.getAct_raspi_pin()
							+ " "
							+ Constants.BREW_GPIO.get(actioner
									.getAct_raspi_pin()));
					try {

						// Turning ON or OFF the pin
						relayAdapter.changePinState(Constants.BREW_GPIO
								.get(actioner.getAct_raspi_pin()),
								PinState.HIGH);

						actioner.setAct_status(Constants.ACT_RUNNING);
						logger.fine("Actioner at pin "
								+ actioner.getAct_raspi_pin()
								+ " changed state to "
								+ actioner.getAct_status());

					} catch (Exception e) {

						logger.severe("Couldn't change Pin state..."
								+ actioner.getAct_raspi_pin()
								+ ", setting status to FAILED !");
						actioner.setAct_status(Constants.ACT_FAILED);

					}
					actioner = this.startActionInDatabase(actioner);

				} else {
					throw new Exception("Empty Pin !!");
				}

				if (relayAdapter.getStateAsString(Constants.BREW_GPIO
						.get(actioner.getAct_raspi_pin())) != "HIGH") {

					throw new Exception(
							"PinState not high, State change failed !");
				}

				break;

			}

		}
		return actioner;

	}

	/**
	 * Whenever user stops an actioner (for example switch off a relay), this
	 * method must be called !
	 * 
	 * It stops configured devices. for the moment : - relays (type 2) - ds18b20
	 * temperature sensors (type 1). For these ones in fact it stops the job
	 * collecting temperatures.
	 * 
	 * @param Actioner
	 *            that has to be stopped. Actioner must have ID
	 * @return
	 * @throws Exception
	 * 
	 */
	@Override
	public Actioner stopAction(Actioner actioner) throws Exception {
		if (actioner != null && actioner.getAct_id() != 0) {

			if (ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES,
					"param.batches.language") == "java") {

				if (recordTemperatureBatch != null) {

					if (recordTemperatureBatch.isAlive()) {

						recordTemperatureBatch.interrupt();

						this.stopActionInDatabase(actioner);

						// Wait for it to end
						recordTemperatureBatch.join();

					} else {
						logger.info("Thread is dead");

					}

				} else {
					logger.severe("Thread is null !!");
				}

			} else if (ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES,
					"param.batches.language") == "python") {
				switch (actioner.getAct_type()) {

				case "1":

					// ds18b20

					// First, checks if a process is currently running
					Process proc = null;
					try {
						proc = Runtime.getRuntime().exec(
								getTemperatureRunningGrep);
					} catch (IOException e) {
						e.printStackTrace();
					}

					BufferedReader br = new BufferedReader(
							new InputStreamReader(proc.getInputStream()));

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
							String pid = this
									.getPIDFromPs("bchrectemp.py "
											+ actioner.getAct_brassin()
													.getBra_id()
											+ " "
											+ actioner.getAct_etape()
													.getEtp_id())
									+ " " + actioner.getAct_id();

							if (pid != "") {

								// Kills the process !
								Runtime.getRuntime().exec(
										"kill -SIGTERM " + pid);
								counter++;
							}

							// records in DB

							actioner = this.stopActionInDatabase(actioner);
						}
					}

					break;

				case "2":

					// Relay

					if (actioner.getAct_raspi_pin() != null) {

						GpioPinDigitalOutput gpio = gpioController
								.provisionDigitalOutputPin(Constants.BREW_GPIO
										.get(actioner.getAct_raspi_pin()));

						if (gpio.getState() == PinState.HIGH) {

							gpio.setState(PinState.LOW);

							// Not recording relay actions as therre may be more
							// than one
							// actioner = this.stopActionInDatabase(actioner);
						}

					}
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

	/**
	 * This method returns PIDs for line command Greps over line parameter,
	 * deletes grep search process and display PID (2nd column)
	 * 
	 * @param line
	 * @return If the process exists, return PID as String, else empty String
	 * @throws IOException
	 */
	public String getPIDFromPs(String line) throws IOException {

		String result = null;
		Process proc = Runtime.getRuntime().exec(
				"/bin/ps -fe | grep \"" + line
						+ "\" | grep -v \"grep\" | awk \'{print $2}\'");

		BufferedReader br = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		String grepLine = null;

		if ((grepLine = br.readLine()) != null) {

			result = line;

		} else {
			result = new String();
			System.out.println("No results found !!");
		}

		return result;
	}

	/**
	 * This method checks in DB whether Actioner is already activated or not.
	 *
	 * searches on brew, step, uuid and type
	 * 
	 * 
	 * @param arg0
	 * @return stored Actioner if exists, arg0 else
	 */
	public Actioner isAlreadyStoredAndActivated(Actioner arg0) {
		Actioner result = null;

		if (arg0 != null) {

			result = actionerSpecDao.getActionerByFullCharacteristics(arg0);

		}

		return arg0;
	}

	/**
	 * Joins threads
	 * 
	 * @throws InterruptedException
	 */
	public void joinThreads() throws InterruptedException {

		if (recordTemperatureBatch != null) {
			recordTemperatureBatch.join();
		}

	}

	public String getCommandLineRegexp() {
		return commandLineRegexp;
	}

	public void setCommandLineRegexp(String commandLineRegexp) {
		this.commandLineRegexp = commandLineRegexp;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public IGenericDao<Actioner> getActionerDao() {
		return actionerDao;
	}

	public void setActionerDao(IGenericDao<Actioner> actionerDao) {
		this.actionerDao = actionerDao;
	}

	public ISpecificActionerDao getActionerSpecDao() {
		return actionerSpecDao;
	}

	public void setActionerSpecDao(ISpecificActionerDao actionerSpecDao) {
		this.actionerSpecDao = actionerSpecDao;
	}

	public Batch getTemperatureBatch() {
		return temperatureBatch;
	}

	public void setTemperatureBatch(Batch temperatureBatch) {
		this.temperatureBatch = temperatureBatch;
	}

	public Thread getRecordTemperatureBatch() {
		return recordTemperatureBatch;
	}

	public void setRecordTemperatureBatch(Thread recordTemperatureBatch) {
		this.recordTemperatureBatch = recordTemperatureBatch;
	}

	public String getGetTemperatureRunningGrep() {
		return getTemperatureRunningGrep;
	}

	public void setGetTemperatureRunningGrep(String getTemperatureRunningGrep) {
		this.getTemperatureRunningGrep = getTemperatureRunningGrep;
	}

	public RelayAdapter getRelayAdapter() {
		return relayAdapter;
	}

	public void setRelayAdapter(RelayAdapter relayAdapter) {
		this.relayAdapter = relayAdapter;
	}

	public static Logger getLogger() {
		return logger;
	}

	public GpioController getGpioController() {
		return gpioController;
	}
}
