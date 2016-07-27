package net.brewspberry.business.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificTemperatureMeasurementService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.ConcreteTemperatureMeasurement;
import net.brewspberry.dao.TemperatureMeasurementDaoImpl;
import net.brewspberry.exceptions.ServiceException;
import net.brewspberry.util.ChartPointsCalculator;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;
import net.brewspberry.util.LogManager;

@Service 
@Transactional
public class TemperatureMeasurementServiceImpl implements
		ISpecificTemperatureMeasurementService,
		IGenericService<ConcreteTemperatureMeasurement> {

	private String measurementsCSV = "/home/xavier/ds18b20_raw_measurements.csv";

	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-dd hh:MM:ss.SSSSSS");

	@Autowired
	private ISpecificTemperatureMeasurementService tempDao;
	@Autowired
	@Qualifier("brassinServiceImpl")
	private IGenericService<Brassin> brassinService;

	@Autowired
	@Qualifier("etapeServiceImpl")
	private IGenericService<Etape> etapeService;

	@Autowired
	@Qualifier("temperatureMeasurementDaoImpl")
	private IGenericDao<ConcreteTemperatureMeasurement> tmesDao;
	@Autowired
	private ISpecificTemperatureMeasurementService tmesSpecDao;
	
	static final Logger logger = LogManager.getInstance(TemperatureMeasurementServiceImpl.class.getName());

	@Override
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementByBrassin(
			Brassin bid) {

		return tempDao.getTemperatureMeasurementByBrassin(bid);
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementByEtape(
			Etape etape) {

		return tmesSpecDao.getTemperatureMeasurementByEtape(etape);
	}

	@Override
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementByUUID(
			String uuid) {
		ConcreteTemperatureMeasurement result = new ConcreteTemperatureMeasurement();

		try {
			result = this.tempDao.getLastTemperatureMeasurementByUUID(uuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementByName(
			String name) {
		ConcreteTemperatureMeasurement result = new ConcreteTemperatureMeasurement();

		try {
			result = this.tempDao.getLastTemperatureMeasurementByName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getAllLastTemperatureMeasurements(
			List<String> uuidOrName, Boolean uuid) throws Exception {

		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();
		Iterator<String> it = uuidOrName.iterator();

		ConcreteTemperatureMeasurement toAppend = new ConcreteTemperatureMeasurement();

		// using UUID

		while (it.hasNext()) {

			String next = it.next();

			if (next != null) {

				if (next != "") {

					if (uuid) {

						toAppend = this
								.getLastTemperatureMeasurementByUUID(next);
					} else {
						toAppend = this
								.getLastTemperatureMeasurementByName(next);
					}
				}

			}

			result.add(toAppend);

		}
		if (result.size() <= 0) {

			throw new Exception("Result list is empty !!");

		}

		return result;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getAllLastTemperatureMeasurementsFromCSV(
			List<String> uuidOrName, Boolean uuid) throws Exception {

		BufferedReader f = this.openFile(measurementsCSV);
		String lastLine = "";
		String line = "";
		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();

		while ((line = f.readLine()) != null) {

			lastLine = line;
		}

		System.out.println(lastLine);

		result = this.parseLineAsObjects(lastLine);

		return result;
	}

	@Override
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementsByNameFromCSV(
			String uuidOrName, Boolean uuid) throws Exception {

		BufferedReader f = this.openFile(measurementsCSV);
		String lastLine = "";
		String line = "";
		ConcreteTemperatureMeasurement result = new ConcreteTemperatureMeasurement();

		while ((line = f.readLine()) != null) {

			lastLine = line;
		}
		System.out.println("Found line " + lastLine);

		result = this.parseLineAsObject(lastLine, uuidOrName, uuid);

		return result;

	}

	public BufferedReader openFile(String path) throws FileNotFoundException {

		BufferedReader br = null;

		if (path != null && path != "") {

			try {

				br = new BufferedReader(new FileReader(new File(path)));
			} catch (FileNotFoundException e) {

				System.out.println(path + " not found !!!");
				throw new FileNotFoundException();
			}

		}
		return br;
	}

	public List<ConcreteTemperatureMeasurement> parseLineAsObjects(String line)
			throws Exception {

		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();

		if (line != null && line != "") {

			String[] array = line.split(";");

			int counter = 0;

			for (int i = 3; i < array.length; i += 2) {

				ConcreteTemperatureMeasurement tmes = new ConcreteTemperatureMeasurement();

				tmes.setTmes_date(sdf.parse(array[0]));
				tmes.setTmes_brassin(brassinService.getElementById(Long
						.parseLong(array[1])));
				tmes.setTmes_etape(etapeService.getElementById(Long
						.parseLong(array[2])));
				tmes.setTmes_probe_name("PROBE" + counter);
				tmes.setTmes_probeUI(array[i]);
				tmes.setTmes_value(Float.parseFloat(array[i + 1]));

				result.add(tmes);
				counter += 1;
			}

		}

		if (result.size() == 0) {

			throw new Exception("parseLineAsObjects : result size = 0 !!");
		}

		return result;

	}

	public ConcreteTemperatureMeasurement parseLineAsObject(String line,
			String uuidOrName, Boolean uuid) throws Exception {

		System.out.println("Parsing line as object " + uuidOrName);

		ConcreteTemperatureMeasurement result = new ConcreteTemperatureMeasurement();
		System.out.println("Found line " + line);

		if (line != null && line != "") {

			String[] array = line.split(";");
			int counter = 0;

			for (int i = 3; i < array.length; i += 2) {

				ConcreteTemperatureMeasurement tmes = new ConcreteTemperatureMeasurement();

				tmes.setTmes_date(sdf.parse(array[0]));
				tmes.setTmes_brassin(brassinService.getElementById(Long
						.parseLong(array[1])));
				tmes.setTmes_etape(etapeService.getElementById(Long
						.parseLong(array[2])));

				if (uuid && array[i].matches(uuidOrName)) {

					System.out.println("Found uuid " + uuidOrName);

					tmes.setTmes_probe_name("PROBE" + counter);
					tmes.setTmes_probeUI(array[i]);
					tmes.setTmes_value(Float.parseFloat(array[i + 1]));

					result = tmes;
				} else if (!uuid) {

					int eqi = Integer.parseInt(uuidOrName.substring(uuidOrName
							.length() - 1)) * 2 + 3;

					System.out.println(eqi + " " + i);
					if (eqi == i) {

						System.out.println("Found name " + uuidOrName);

						tmes.setTmes_probe_name("PROBE" + counter);
						tmes.setTmes_probeUI(array[i]);
						tmes.setTmes_value(Float.parseFloat(array[i + 1]));

						result = tmes;
					}
				}

				counter += 1;
			}

		}
		return result;
	}

	@Override
	public ConcreteTemperatureMeasurement save(ConcreteTemperatureMeasurement arg0)
			throws Exception {
		return tmesDao.save(arg0);
	}

	@Override
	public ConcreteTemperatureMeasurement update(ConcreteTemperatureMeasurement arg0) {
		// TODO Auto-generated method stub
		return tmesDao.update(arg0);
	}

	@Override
	public ConcreteTemperatureMeasurement getElementById(long id) {
		// TODO Auto-generated method stub
		return tmesDao.getElementById(id);
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getAllElements() {
		// TODO tme-generated method stub
		return tmesDao.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		tmesDao.deleteElement(id);

	}

	@Override
	public void deleteElement(ConcreteTemperatureMeasurement arg0) {
		// TODO Auto-generated method stub
		tmesDao.deleteElement(arg0);
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return tmesDao.getAllDistinctElements();
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementsAfterIDForStepUUIDAndDelay(Etape etape, 
			String uuid, int numberOfPoints, long tmesID, float delayInSeconds){
		List<ConcreteTemperatureMeasurement> result = tempDao.getTemperatureMeasurementsAfterIDForStepUUIDAndDelay(etape, uuid, numberOfPoints, tmesID, delayInSeconds);
		
		int frequency = ChartPointsCalculator.computePointsFrequencyDisplay(numberOfPoints, result.size(), delayInSeconds);
		
		sortTemperatures(result, frequency);
		
		
		return result;
	}

	/**
	 * Method to keep only 1/frequency points to display
	 * @param result raw list of results from DB
	 * @param frequency of results to keep
	 * @return
	 */
	private List<ConcreteTemperatureMeasurement> sortTemperatures(
			List<ConcreteTemperatureMeasurement> result, int frequency) {
		List<ConcreteTemperatureMeasurement> sorted = new ArrayList<ConcreteTemperatureMeasurement>();
		
		for (int i = 0 ; i<result.size() ; i+=frequency){
			
			sorted.add(result.get(i));
			
		}
		
		return sorted;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getLastTemperatureByStepAndUUID(
			Etape stepID, String uuid) {
		
		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();
		
		
		if (stepID != null && uuid != null){
			if (!uuid.equals("") || !uuid.equalsIgnoreCase("all")){
				
				result = tmesSpecDao.getLastTemperatureByStepAndUUID(stepID, uuid);
						
			} else {
				String[] p = ConfigLoader.getConfigByKey(Constants.DEVICES_PROPERTIES, "device.uuids").split(";");
				
				
				//Beware : if probe was not used recently, temperature might not be accurate
				for (String probe : p){
					
					result = tmesSpecDao.getLastTemperatureByStepAndUUID(stepID, probe);
					
				}
				
				
			}
		}
		return result;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getLastTemperatureMeasurementByStepUUIDNumberOfPointsAndDelay(
			Etape etapeID, String uuid, int numberOfPoints, float delay)
			throws Exception {
		
		
		if (etapeID != null && etapeID.getEtp_id() > 0){
			
			if (uuid.equals("")){
				
				uuid = "all";
				
			}

			if (numberOfPoints < 1){
				
				numberOfPoints = Integer.parseInt(ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "params.chart.maxNumberOfPoints"));
				
			}
			
			if (delay <= 0){
				
				delay = Integer.parseInt(ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "params.chart.chartLengthInMinutes"));
				
			}
			
			
		}
		else {
			
			throw new IllegalArgumentException("Step id "+etapeID.getEtp_id()+" not valid !!");
			
		}
		
		
		List<ConcreteTemperatureMeasurement>  result = tmesSpecDao.getLastTemperatureMeasurementByStepUUIDNumberOfPointsAndDelay(etapeID, uuid, numberOfPoints, delay);
		List<ConcreteTemperatureMeasurement> sortedResult = sortTemperatures(result, 
				ChartPointsCalculator.computePointsFrequencyDisplay(numberOfPoints, result.size(), delay));

		return sortedResult;
	}

	@Override
	public ConcreteTemperatureMeasurement getElementByName(String name) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}