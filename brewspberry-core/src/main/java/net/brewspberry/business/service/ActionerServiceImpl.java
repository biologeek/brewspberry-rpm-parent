package net.brewspberry.business.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.adapter.RelayAdapter;
import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificActionerDao;
import net.brewspberry.business.ISpecificActionerService;
import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.exceptions.DAOException;
import net.brewspberry.business.exceptions.NotAppropriateStatusException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.dao.ActionerDaoImpl;
import net.brewspberry.util.Constants;
import net.brewspberry.util.LogManager;


@Service 
@Transactional
public class ActionerServiceImpl implements IGenericService<Actioner>,
		ISpecificActionerService {

	public static final Logger logger = LogManager
			.getInstance(ActionerServiceImpl.class.toString());

	String commandLineRegexp = "/home/pi/batches/bchrectemp.py [0-9]{0,5} [0-9]{0,5}";

	Pattern pattern = Pattern.compile(commandLineRegexp);

	@Autowired
	IGenericDao<Actioner> actionerDao;
	@Autowired
	ISpecificActionerDao actionerSpecDao;


	String getTemperatureRunningGrep = "ps -ef | grep bchrectemp.py";

	RelayAdapter relayAdapter = RelayAdapter.getInstance();

	public ActionerServiceImpl() {
		super();
		
	}

	@Override
	public Actioner save(Actioner arg0) throws Exception {

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

	
	/**
	 * Deprecated if using java batches
	 * 
	 * @param which
	 * @param uuid
	 * @param brassin
	 * @param etape
	 * @return
	 * @throws IOException
	 * @throws ServiceException
	 */
	@Deprecated
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

	/**
	 * Starts the action and saves it in database.
	 * If an actioner already exists for this step, brew type and UUID, no need to recreate it, just update previous one 
	 * @param arg0
	 * @return
	 * @throws ServiceException
	 * @throws NotAppropriateStatusException
	 */
	public Actioner startActionInDatabase(Actioner arg0)
			throws ServiceException, NotAppropriateStatusException {

		boolean isAlreadyStored = false;
	
		Actioner result = actionerSpecDao.getActionerByFullCharacteristics(arg0);
		
		
		if (result != null){
			isAlreadyStored = true;
		}
		
		if (arg0.getAct_date_debut() == null) {
			arg0.setAct_date_debut(new Date());
			arg0.setAct_status(Constants.ACT_RUNNING);
		}

		if (arg0.getAct_activated() == false && arg0.getAct_used() == false) {

			arg0.setAct_activated(true);
			
		} else
			throw new NotAppropriateStatusException();
		try {
			logger.info("Situation : is alread stored ? " + isAlreadyStored+" Actionner ID : "+arg0.getAct_id());
			if(isAlreadyStored && arg0.getAct_id() > 0) {
				
				result = this.update(arg0);
				
			} else {
				
				try {
					result = this.save(arg0);
				} catch (Exception e) {
					throw new ServiceException("Couldn't update Actioner "
							+ arg0.toString());
				}
				
			}
		} catch (Exception e) {
			
			throw new ServiceException("Couldn't update Actioner "
					+ arg0.toString());
		}
		return result;
	}

	public Actioner stopActionInDatabase(Actioner arg0)
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
	@Deprecated
	public Actioner startAction(Actioner actioner) throws Exception {

		return actioner;

	}

	/**
	 * 
	 * @see BatchLauncherService
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
	@Deprecated
	public Actioner stopAction(Actioner actioner) throws Exception {
		
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
	@Override
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

		return result;
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

	@Override
	public Actioner getElementByName(String name) throws ServiceException {
		
		return null;
	}

}