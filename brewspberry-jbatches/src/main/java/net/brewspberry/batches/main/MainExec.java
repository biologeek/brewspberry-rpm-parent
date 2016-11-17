package net.brewspberry.batches.main;

import java.util.logging.Logger;

import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;

import net.brewspberry.batches.beans.BatchParams;
import net.brewspberry.batches.beans.BatchParams.LaunchType;
import net.brewspberry.batches.beans.TaskParams;
import net.brewspberry.batches.launchers.Batch;
import net.brewspberry.batches.launchers.BatchRecordTemperatures;
import net.brewspberry.business.ISpecificActionerLauncherService;
import net.brewspberry.util.LogManager;

public class MainExec {

	private static final Logger logger = LogManager.getInstance(MainExec.class.getName());
	
	@Autowired
	ISpecificActionerLauncherService launcherService;

	public static void main(String[] args) {

		if (args.length > 0) {

			switch (args[0]) {

			case "batchRecordTemperatures":

				BatchParams batchParams = new BatchParams.BatchParamsBuilder().buildBatchParams(args);

				System.arraycopy(args, 1, batchParams, 0, args.length - 1);
				logger.info("Launching batchRecordTemperatures batch");

				try {
					
					laun
					Batch recordTemperature = new BatchRecordTemperatures(batchParams);
					System.exit(0);

				} catch (Exception e) {

					e.printStackTrace();
					System.exit(1);
				}

				break;
			}

		}

	}

}
