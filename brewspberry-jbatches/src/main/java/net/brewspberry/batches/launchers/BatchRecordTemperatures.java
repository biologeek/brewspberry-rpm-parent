package net.brewspberry.batches.launchers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import net.brewspberry.batches.beans.BatchParams;
import net.brewspberry.batches.beans.TaskParams;
import net.brewspberry.batches.tasks.RecordTemperatureFromFileTask;
import net.brewspberry.batches.tasks.Task;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;
import net.brewspberry.util.LogManager;

@Service
public class BatchRecordTemperatures implements Batch, Runnable {

	Logger logger = LogManager.getInstance(BatchRecordTemperatures.class.getName());
	Task currentTask = null;
	@Autowired
	@Qualifier("brassinServiceImpl")
	IGenericService<Brassin> brassinService;
	@Autowired
	@Qualifier("etapeServiceImpl")
	IGenericService<Etape> etapeService;
	@Autowired
	@Qualifier("actionerServiceImpl")
	IGenericService<Actioner> actionerService;
	BatchParams batchParams;

	public BatchRecordTemperatures() {
		/**
		 * Parameters to pass for this batch : 0- 1- 2- 3- 4- 5-
		 */

		logger.fine("Dont forget to set batch params !");

	}

	public BatchRecordTemperatures(BatchParams args) {
		/**
		 * Parameters to pass for this batch : 0- 1- 2- 3- 4- 5-
		 */
		if (!args.validateParams()) {

			logger.severe("Could not initialize batch, invalid params : " + args.getInvalidParams() + "");
			System.exit(1);
		}
		this.batchParams = args;

	}

	/**
	 * Params order : - 0 LaunchType : ONCE, SECOND, MINUTE, HOUR, INDEFINITE -
	 * 1 Launch length 2-n Specific argument
	 */
	public void run() {

		if (this.batchParams != null) {
			this.execute();
		} else {
			throw new RuntimeException("Could not start batch without parameters !");
		}
	}

	private Object[] getTaskParameters(String[] batchParams, int firstElement) {
		String[] specParams = new String[3];
		Object[] result = new Object[3];

		if (batchParams.length > 0 && batchParams.length > firstElement) {

			System.arraycopy(batchParams, firstElement, specParams, 0, result.length);

			logger.info(
					"Params : " + String.valueOf(String.join(";", batchParams) + " | " + String.join(";", specParams)));

			Brassin brassin = null;
			Etape etape = null;
			Actioner actioner = null;
			try {
				brassin = brassinService.getElementById(Long.parseLong(specParams[0]));

				etape = etapeService.getElementById(Long.parseLong(specParams[1]));
				actioner = actionerService.getElementById(Long.parseLong(specParams[2]));
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (ServiceException e) {

				e.printStackTrace();
			}
			result[0] = brassin;
			result[1] = etape;
			result[2] = actioner;
			logger.fine("Transmitting " + result[0] + result[1] + result[2] + " to task");

		}

		return result;
	}

	public void setBatchParams(BatchParams batchParams) {

		this.batchParams = batchParams;

	}

	@Override
	public synchronized void execute() {

		TaskParams taskParams = this.batchParams.getTaskParams();

		Integer threadSleep = Integer.parseInt(ConfigLoader.getConfigByKey(
				Constants.PROJECT_ROOT_PATH + "/" + Constants.BREW_CONF + "/batches.properties",
				"brewspberry.batches.threads.delay"));

		double timeLength;
		long startTime;

		logger.fine("Thread sleep param : " + threadSleep);
		if (batchParams.getLaunchType() != null) {

			switch (batchParams.getLaunchType()) {

			case ONCE:
				try {
					currentTask = new RecordTemperatureFromFileTask();

					currentTask.setTaskParameters(taskParams);
					currentTask.setWriteParameters("ALL");

					Thread t = new Thread((Runnable) currentTask);

					t.start();
				} catch (Throwable t) {
					t.printStackTrace();
				}
				break;

			case SECOND:

				timeLength = new Double(batchParams.getDuration()) * 1000;

				startTime = System.currentTimeMillis();

				while ((System.currentTimeMillis() - startTime) < timeLength) {

					logger.info("--BEGIN : Time length : " + timeLength + " | Real : "
							+ (System.currentTimeMillis() - startTime));

					try {
						currentTask = new RecordTemperatureFromFileTask();

						currentTask.setTaskParameters(taskParams);
						currentTask.setWriteParameters("ALL");

						Thread t = new Thread((Runnable) currentTask);

						t.start();

						Thread.sleep(threadSleep);
					} catch (Throwable e) {

						e.printStackTrace();
					}

				}

				break;

			case MINUTE:
				try {

					logger.info("Recording temperatures for " + batchParams.getDuration() + " minutes");
					timeLength = new Double(batchParams.getDuration()) * 1000.0 * 60.0;
					startTime = System.currentTimeMillis();

					currentTask.setTaskParameters(taskParams);
					currentTask.setWriteParameters("ALL");

					while ((System.currentTimeMillis() - startTime) < timeLength) {

						try {
							Thread t = new Thread((Runnable) currentTask);
							t.start();
							Thread.sleep(threadSleep);
						} catch (Throwable e) {

							e.printStackTrace();
						}
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}

				break;

			case HOUR:
				try {
					logger.info("Recording temperatures for " + batchParams.getDuration() + " hours");

					timeLength = new Double(batchParams.getDuration()) * 1000.0 * 3600.0;
					startTime = System.currentTimeMillis();

					currentTask = new RecordTemperatureFromFileTask();
					currentTask.setTaskParameters(taskParams);
					currentTask.setWriteParameters(String.join("ALL"));

					while ((System.currentTimeMillis() - startTime) < timeLength) {

						try {
							new Thread((Runnable) currentTask).start();
							Thread.sleep(threadSleep);
						} catch (Throwable e) {

							e.printStackTrace();
						}
					}

				} catch (Throwable t) {
					t.printStackTrace();
				}
				break;

			case INDEFINITE:

				break;

			}
		}

		logger.info("Exiting program");

	}

}