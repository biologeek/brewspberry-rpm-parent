package net.brewspberry.main.batches.main;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import net.brewspberry.main.batches.beans.BatchParams;
import net.brewspberry.main.batches.launchers.Batch;
import net.brewspberry.main.batches.util.config.JBatchesApplicationConfig;
import net.brewspberry.main.util.LogManager;
import net.brewspberry.main.util.config.SpringCoreConfiguration;


public class MainExec {

	private static final Logger logger = LogManager.getInstance(MainExec.class.getName());
	
	
	
	@Autowired
	@Qualifier("batchRecordTemperatures")
	Batch recordTemperatureBatch;

	
	
	public MainExec() {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		
		ctx.register(JBatchesApplicationConfig.class);
		ctx.register(SpringCoreConfiguration.class);
		ctx.refresh();
		AutowireCapableBeanFactory acbFactory = ctx.getAutowireCapableBeanFactory();
		
		acbFactory.autowireBean(this);
	
	}

	public static void main(String[] args) {

		
		MainExec main = new MainExec();
						
		processArgsAndLaunchBatch(args, main);

	}

	private static void processArgsAndLaunchBatch(String[] args, MainExec main) {
		if (args.length > 0) {

			switch (args[0]) {

			case "batchRecordTemperatures":

				BatchParams batchParams = new BatchParams.BatchParamsBuilder().buildBatchParams(args);

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
