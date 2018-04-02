package net.brewspberry.main.batches.beans;

import java.util.Arrays;
import java.util.logging.Logger;

import net.brewspberry.main.batches.beans.BatchParams.LaunchType;
import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.monitoring.Actioner;
import net.brewspberry.main.business.beans.monitoring.GenericActionner;
import net.brewspberry.main.util.LogManager;

public class BatchParams {
	
	public enum LaunchType {
		ONCE, SECOND, MINUTE, HOUR, INDEFINITE
	}
	
	private LaunchType launchType;
	
	private String duration;
	
	private Brassin brew;
	private Etape step;
	private Actioner actionner;
	private String uuid;
		
	private TaskParams taskParams;

	private Logger logger;
	
	public BatchParams() {
		logger = LogManager.getInstance(this.getClass().getName());
	}

	public LaunchType getLaunchType() {
		return launchType;
	}

	public void setLaunchType(LaunchType launchType) {
		this.launchType = launchType;
	}


	public TaskParams getTaskParams() {
		return taskParams;
	}

	public void setTaskParams(TaskParams taskParams) {
		this.taskParams = taskParams;
	}

	public boolean validateParams() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getInvalidParams() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}


	public BatchParams duration(String duration) {
		this.duration = duration;
		return this;
		
	}

	public BatchParams brew(Brassin brassin) {
		this.brew = brassin;
		return this;
		
	}
	public BatchParams step(Etape brassin) {
		this.step = brassin;
		return this;
		
	}
	public BatchParams actionner(Actioner brassin) {
		this.actionner = brassin;
		return this;
		
	}
	
	public BatchParams launchType(LaunchType launchType) {
		this.launchType = launchType;
		return this;
		
	}
	public BatchParams uuid(String string) {
		this.setUuid(string);
		return this;
	}
	
	public Brassin getBrew() {
		return brew;
	}

	public void setBrew(Brassin brew) {
		this.brew = brew;
	}

	public Etape getStep() {
		return step;
	}

	public void setStep(Etape step) {
		this.step = step;
	}

	public Actioner getActioner() {
		return actionner;
	}

	public void setActioner(Actioner actioner) {
		this.actionner = actioner;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public BatchParams taskParams(TaskParams taskParams) {
		this.taskParams = taskParams;
		return this;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public static class BatchParamsBuilder{
		
		private Logger logger;
		
		public BatchParamsBuilder() {
			logger = LogManager.getInstance(this.getClass().getName());
		}
		/**
		 * Converts an array of objects to a BatchParams object
		 * To correctly map parameters
		 * @param args 
		 * @return
		 */
		public BatchParams buildBatchParams(String[] args) {
			BatchParams params = new BatchParams()//
					.launchType(LaunchType.valueOf((String) args[0]))//
					.duration((String) args[1])//
					.brew(new Brassin().id(Long.parseLong(args[2])))//
					.step(new Etape().id(Long.parseLong(args[3])))//
					.actionner(new Actioner().id(Long.parseLong(args[4])))//
					.taskParams(buildTaskParams(new String[]{args[2], args[3], args[4]}));
					;
					
			if (args.length == 6){
				params.uuid(args[5]);
				params.taskParams(buildTaskParams(new String[]{args[2], args[3], args[4], args[5]}));
			}
			
			logger.info("Got following arguments : \n"
			+params.getLaunchType()+",\n"
			+params.getDuration()+",\n"
			+params.getBrew()+",\n"
			+params.getStep()+",\n"
			+params.getActioner());
			logger.info("+ uuid "+params.getUuid() );
			
			
			return params;
		}

		public TaskParams buildTaskParams(String[] args) {

			TaskParams params = new TaskParams()
					.brew(new Brassin().id(Long.parseLong(args[0])))//
					.step(new Etape().id(Long.parseLong(args[1])))//
					.actioner(new Actioner().id(Long.parseLong(args[2])));	
			
			if (args.length == 4){
				params.setUuid(args[3]);
			}
			return params;
		}
	}
}
