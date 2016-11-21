package net.brewspberry.batches.main;

import java.util.logging.Logger;

import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.brewspberry.batches.beans.BatchParams;
import net.brewspberry.batches.beans.BatchParams.LaunchType;
import net.brewspberry.batches.beans.TaskParams;
import net.brewspberry.batches.launchers.Batch;
import net.brewspberry.batches.launchers.BatchRecordTemperatures;
import net.brewspberry.batches.util.config.JBatchesApplicationConfig;
import net.brewspberry.business.ISpecificActionerLauncherService;
import net.brewspberry.util.LogManager;

public class MainExec {

	private static final Logger logger = LogManager.getInstance(MainExec.class.getName());
	
	ApplicationContext context;
	
	@Autowired
	ISpecificActionerLauncherService launcherService;
	
	@Autowired
	Batch recordTemperatureBatch;
	
	public MainExec() {
		context = new AnnotationConfigApplicationContext(JBatchesApplicationConfig.class);
	}

	public static void main(String[] args) {

		
		MainExec main = new MainExec(); 
		
		if (args.length > 0) {

			switch (args[0]) {

			case "batchRecordTemperatures":

				BatchParams batchParams = new BatchParams.BatchParamsBuilder().buildBatchParams(args);

				System.arraycopy(args, 1, batchParams, 0, args.length - 1);
				logger.info("Launching batchRecordTemperatures batch");

				try {
					
					main.setParams(batchParams);
					main.start();
					
					System.exit(0);

				} catch (Exception e) {

					e.printStackTrace();
					System.exit(1);
				}

				break;
			}

		} else {
			logger.warning("No arguments provided, exiting with status code 0");
			System.exit(0);
		}

	}
	

	public void setParams(BatchParams params){
		
		this.recordTemperatureBatch.setBatchParams(params);
		
	}

	public void start(){
		
		this.recordTemperatureBatch.execute();
		
	}

}
