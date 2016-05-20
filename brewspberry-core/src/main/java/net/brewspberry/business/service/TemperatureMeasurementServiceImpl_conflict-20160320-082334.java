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

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificTemperatureMeasurementService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.TemperatureMeasurement;
import net.brewspberry.dao.TemperatureMeasurementDaoImpl;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;
import net.brewspberry.util.LogManager;

public class TemperatureMeasurementServiceImpl implements
		ISpecificTemperatureMeasurementService,
		IGenericService<TemperatureMeasurement> {

	private String measurementsCSV = "/home/xavier/ds18b20_raw_measurements.csv";

	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-dd hh:MM:ss.SSSSSS");

	private ISpecificTemperatureMeasurementService tempDao = new TemperatureMeasurementDaoImpl();
	private IGenericService<Brassin> brassinService = new BrassinServiceImpl();

	private IGenericService<Etape> etapeService = new EtapeServiceImpl();

	private IGenericDao<TemperatureMeasurement> tmesDao = new TemperatureMeasurementDaoImpl();
	private ISpecificTemperatureMeasurementService tmesSpecDao = new TemperatureMeasurementDaoImpl();
	
	static final Logger logger = LogManager.getInstance(TemperatureMeasurementServiceImpl.class.getName());

	@Override
	public List<TemperatureMeasurement> getTemperatureMeasurementByBrassin(
			Brassin bid) {

		return tempDao.getTemperatureMeasurementByBrassin(bid);
	}

	@Override
	public List<TemperatureMeasurement> getTemperatureMeasurementByEtape(
			Etape etape) {

		return tmesSpecDao.getTemperatureMeasurementByEtape(etape);
	}

	@Override
	public TemperatureMeasurement getLastTemperatureMeasurementByUUID(
			String uuid) {
		TemperatureMeasurement result = new TemperatureMeasurement();

		try {
			result = this.tempDao.getLastTemperatureMeasurementByUUID(uuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public TemperatureMeasurement getLastTemperatureMeasurementByName(
			String name) {
		TemperatureMeasurement result = new TemperatureMeasurement();

		try {
			result = this.tempDao.getLastTemperatureMeasurementByName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<TemperatureMeasurement> getAllLastTemperatureMeasurements(
			List<String> uuidOrName, Boolean uuid) throws Exception {

		List<TemperatureMeasurement> result = new ArrayList<TemperatureMeasurement>();
		Iterator<String> it = uuidOrName.iterator();

		TemperatureMeasurement toAppend = new TemperatureMeasurement();

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
	public List<TemperatureMeasurement> getAllLastTemperatureMeasurementsFromCSV(
			List<String> uuidOrName, Boolean uuid) throws Exception {

		BufferedReader f = this.openFile(measurementsCSV);
		String lastLine = "";
		String line = "";
		List<TemperatureMeasurement> result = new ArrayList<TemperatureMeasurement>();

		while ((line = f.readLine()) != null) {

			lastLine = line;
		}

		System.out.println(lastLine);

		result = this.parseLineAsObjects(lastLine);

		return result;
	}

	@Override
	public TemperatureMeasurement getLastTemperatureMeasurementsByNameFromCSV(
			String uuidOrName, Boolean uuid) throws Exception {

		BufferedReader f = this.openFile(measurementsCSV);
		String lastLine = "";
		String line = "";
		TemperatureMeasurement result = new TemperatureMeasurement();

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

	public List<TemperatureMeasurement> parseLineAsObjects(String line)
			throws Exception {

		List<TemperatureMeasurement> result = new ArrayList<TemperatureMeasurement>();

		if (line != null && line != "") {

			String[] array = line.split(";");

			int counter = 0;

			for (int i = 3; i < array.length; i += 2) {

				TemperatureMeasurement tmes = new TemperatureMeasurement();

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

	public TemperatureMeasurement parseLineAsObject(String line,
			String uuidOrName, Boolean uuid) throws Exception {

		System.out.println("Parsing line as object " + uuidOrName);

		TemperatureMeasurement result = new TemperatureMeasurement();
		System.out.println("Found line " + line);

		if (line != null && line != "") {

			String[] array = line.split(";");
			int counter = 0;

			for (int i = 3; i < array.length; i += 2) {

				TemperatureMeasurement tmes = new TemperatureMeasurement();

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
	public TemperatureMeasurement save(TemperatureMeasurement arg0)
			throws DAOException {
		return tmesDao.save(arg0);
	}

	@Override
	public TemperatureMeasurement update(TemperatureMeasurement arg0) {
		// TODO Auto-generated method stub
		return tmesDao.update(arg0);
	}

	@Override
	public TemperatureMeasurement getElementById(long id) {
		// TODO Auto-generated method stub
		return tmesDao.getElementById(id);
	}

	@Override
	public List<TemperatureMeasurement> getAllElements() {
		// TODO tme-generated method stub
		return tmesDao.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		tmesDao.deleteElement(id);

	}

	@Override
	public void deleteElement(TemperatureMeasurement arg0) {
		// TODO Auto-generated method stub
		tmesDao.deleteElement(arg0);
	}

	@Override
	public List<TemperatureMeasurement> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return tmesDao.getAllDistinctElements();
	}

	@Override
	public List<TemperatureMeasurement> getTemperatureMeasurementsAfterID(
			Etape etape, String uuid, long tmesID, int range) {

		/*
		 * In service, modulo is time range in minutes. 
		 * It will be transformed in modulo depending on 
		 * time range and record delay
		 */
		int delay = Integer.parseInt(ConfigLoader.getConfigByKey(Constants.BATCHES_PROPERTIES, "brewspberry.batches.threads.delay"));
		int maxPointsOnChart = Integer.parseInt(ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "param.chart.maxPointsOnChart"));
		
		/*
		 * 
		 * Calculates the number of points that will be fetched from database 
		 * - range is the range that the user wants to be displayed in minutes
		 * 
		 *  
		 */
		float nbRec = 60000/delay*range;
		int modulo = 1;
		
		
		logger.fine("delay : "+delay+" range : "+range);
		
		if (nbRec > maxPointsOnChart){
			
			modulo = (int) (nbRec/maxPointsOnChart);
			
		}
		logger.fine("NbRec : "+nbRec+" maxPoints : "+maxPointsOnChart+" modulo : "+modulo);
		
		List<TemperatureMeasurement> result = tmesSpecDao.getTemperatureMeasurementsAfterID(etape, uuid, tmesID, modulo);
		
		logger.fine("Retrieved "+result.size()+" results");
		
		return result;
	}

	@Override
	public List<TemperatureMeasurement> getLastTemperatureByStepAndUUID(
			Etape stepID, String uuid) {
		
		List<TemperatureMeasurement> result = new ArrayList<TemperatureMeasurement>();
		
		
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
}
