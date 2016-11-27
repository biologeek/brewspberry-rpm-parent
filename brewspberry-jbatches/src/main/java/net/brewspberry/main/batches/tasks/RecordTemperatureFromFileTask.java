package net.brewspberry.main.batches.tasks;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.batches.beans.TaskParams;
import net.brewspberry.main.batches.exceptions.NotTheGoodNumberOfArgumentsException;
import net.brewspberry.main.batches.util.DS18b20TemperatureMeasurementParser;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.beans.Actioner;
import net.brewspberry.main.business.beans.Brassin;
import net.brewspberry.main.business.beans.ConcreteTemperatureMeasurement;
import net.brewspberry.main.business.beans.Etape;
import net.brewspberry.main.util.ConfigLoader;
import net.brewspberry.main.util.Constants;
import net.brewspberry.main.util.LogManager;

@Component
@Scope("prototype")
public class RecordTemperatureFromFileTask implements Task {
	/**
	 * RecordTemperatureFromFileTask represents 1 temperature record.
	 * 
	 * It can be launched in a separate thread.
	 * 
	 */

	@Autowired
	DS18b20TemperatureMeasurementParser parser;

	List<Path> filesToRead;
	Map<String, Integer> valuesMap = new HashMap<String, Integer>();

	@Value("${batches.files.parent.folder}")
	String parentFolder;

	@Autowired
	@Qualifier("temperatureMeasurementServiceImpl")
	IGenericService<ConcreteTemperatureMeasurement> tmesService;

	String entityToWrite = "ALL";

	TaskParams specificParameters;
	List<ConcreteTemperatureMeasurement> temperatureMeasurement = new ArrayList<ConcreteTemperatureMeasurement>();

	private Logger logger = LogManager.getInstance(DS18b20TemperatureMeasurementParser.class.getName());

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public RecordTemperatureFromFileTask() {
		super();
	}

	public void run() {
		
		System.out.println(parentFolder);
		parser.setBaseFolder(parentFolder);

		try {
			filesToRead = parser.findFilesToOpen();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		logger.info("Thread started. Found " + filesToRead.size() + " files :");
		Iterator<Path> itP = filesToRead.iterator();

		while (itP.hasNext()) {
			String file = Paths.get(itP.next().toUri()).toString();

			if (file != null) {

				Integer temperature = parser.parseTemperature(file);
				String uuid = parser.getProbeUUIDFromFileName(file);

				valuesMap.put(uuid, temperature);
			}
		}

		logger.info("Could find " + valuesMap.size() + " devices");

		try {
			if (checkSpecificParameters(specificParameters)) {

				Iterator<Entry<String, Integer>> entries = valuesMap.entrySet().iterator();

				int i = 0;
				while (entries.hasNext()) {

					ConcreteTemperatureMeasurement tmes = new ConcreteTemperatureMeasurement();
					Entry<String, Integer> entry = entries.next();

					tmes.setTmes_brassin((Brassin) specificParameters.getBrew());
					tmes.setTmes_etape((Etape) specificParameters.getStep());
					tmes.setTmes_actioner((Actioner) specificParameters.getActioner());
					tmes.setTmes_date(new Date());

					tmes.setTmes_probeUI(entry.getKey());
					tmes.setTmes_value(Float.valueOf(entry.getValue()));
					tmes.setTmes_probe_name("PROBE" + i);

					i++;

					temperatureMeasurement.add(tmes);

				}

				if (temperatureMeasurement.size() > 0) {

					if (entityToWrite.equals("ALL") || entityToWrite.equals("FILE")) {
						logger.fine("Saving in File");

						List<String> linesToAddToCSV = this.formatDataForCSVFile(temperatureMeasurement);

						Iterator<String> it2 = linesToAddToCSV.iterator();
						while (it2.hasNext()) {

							this.writeCSV(it2.next());

						}

					}
					if (entityToWrite.equals("ALL") || entityToWrite.equals("SQL")) {
						Iterator<ConcreteTemperatureMeasurement> it = temperatureMeasurement.iterator();

						while (it.hasNext()) {
							ConcreteTemperatureMeasurement tmesToRec = it.next();

							try {
								logger.fine("Saving in DB");
								tmesService.save(tmesToRec);

							} catch (Exception e) {
								logger.severe(
										"Could not record this measurement : UUID=" + tmesToRec.getTmes_probeUI());

								e.printStackTrace();
							}

						}
					}
					temperatureMeasurement = new ArrayList<ConcreteTemperatureMeasurement>();
				}
			}
		} catch (NotTheGoodNumberOfArgumentsException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Checks validity of specific parameters transmitted.
	 * 
	 * @throws NotTheGoodNumberOfArgumentsException
	 */
	public boolean checkSpecificParameters(TaskParams specificParameters2) throws NotTheGoodNumberOfArgumentsException {

		logger.fine("Got this : ");

		if (!specificParameters2.hasNullAttributes()) {

			logger.fine("Parameters : Brew=" + specificParameters2.getBrew() + " Step=" + specificParameters2.getStep()
					+ " Actioner=" + specificParameters2.getActioner());

			return true;

		} else {
			throw new NotTheGoodNumberOfArgumentsException();
		}
	}

	public void setTaskParameters(TaskParams specs) {

		this.specificParameters = specs;
	}

	public List<String> formatDataForCSVFile(List<ConcreteTemperatureMeasurement> tmes) {

		String lineResult = new String();

		List<String> result = new ArrayList<String>();
		logger.finer(tmes.size() + " temperatures to write");

		if (tmes.size() > 0) {
			Iterator<ConcreteTemperatureMeasurement> it = tmes.iterator();

			while (it.hasNext()) {

				ConcreteTemperatureMeasurement tmesU = it.next();

				lineResult = sdf.format(new Date()) + ";" + String.valueOf(tmesU.getTmes_brassin().getBra_id()) + ";"
						+ String.valueOf(tmesU.getTmes_etape().getEtp_id()) + ";"
						+ String.valueOf(tmesU.getTmes_actioner().getAct_id()) + ";"
						+ String.valueOf(tmesU.getTmes_probe_name()) + ";" + String.valueOf(tmesU.getTmes_value());

				result.add(lineResult);
			}
		}
		return result;
	}

	/**
	 * Writes str to Constants.DS18B20_CSV file
	 * 
	 * @param str
	 *            string to write
	 */

	private synchronized void writeCSV(String str) {
		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(

					new FileOutputStream(
							ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "files.measurements.temperature")),
					"utf-8"));

			writer.write(str);

		} catch (Exception e) {
			logger.severe("Could not write line to file "
					+ ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "files.measurements.temperature"));
		} finally {

			try {
				writer.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets where to write temperatures.
	 * 
	 * Can be for example FILE for file writing, SQL for DB record, ALL for both
	 * 
	 * @param entityToWrite
	 */
	public void setWriteParameters(String entityToWrite) {
		if (entityToWrite != null) {
			if (Arrays.asList(Constants.WRITABLE_ENTITIES).contains(entityToWrite)) {
				this.entityToWrite = entityToWrite;
			}
		}
	}

	@Override
	public boolean checkSpecificParameters(Object[] specs) throws NotTheGoodNumberOfArgumentsException {

		for (Object obj : specs) {
			if (obj == null)
				return false;
		}

		return true;
	}
}