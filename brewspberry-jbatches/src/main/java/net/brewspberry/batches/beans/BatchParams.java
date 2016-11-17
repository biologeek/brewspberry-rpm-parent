package net.brewspberry.batches.beans;

import net.brewspberry.batches.beans.BatchParams.LaunchType;
import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;

public class BatchParams {
	
	public enum LaunchType {
		ONCE, SECOND, MINUTE, HOUR, INDEFINITE
	}
	
	private LaunchType launchType;
	
	private String duration;
	
	private String brewID;
	private String stepID;
	private String actionerID;
	
	private TaskParams taskParams;

	public LaunchType getLaunchType() {
		return launchType;
	}

	public void setLaunchType(LaunchType launchType) {
		this.launchType = launchType;
	}

	public String getBrewID() {
		return brewID;
	}

	public void setBrewID(String brewID) {
		this.brewID = brewID;
	}

	public String getStepID() {
		return stepID;
	}

	public void setStepID(String stepID) {
		this.stepID = stepID;
	}

	public String getActionerID() {
		return actionerID;
	}

	public void setActionerID(String actionerID) {
		this.actionerID = actionerID;
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
	
	public BatchParams launchType(LaunchType launchType) {
		this.launchType = launchType;
		return this;
		
	}
	
	public BatchParams brew(String brew) {
		this.brewID = brew;
		return this;
		
	}
	
	public BatchParams step(String step) {
		this.stepID = step;
		return this;
		
	}

	public BatchParams actionner(String actionner) {
		this.actionerID = actionner;
		return this;
		
	}
	
	public BatchParams taskParams(TaskParams taskParams) {
		this.taskParams = taskParams;
		return this;
		
	}
	
	
	public static class BatchParamsBuilder{
		

		/**
		 * Converts an array of objects to a BatchParams object
		 * To correctly map parameters
		 * @param args
		 * @return
		 */
		public BatchParams buildBatchParams(Object[] args) {
			BatchParams params = new BatchParams()//
					.launchType(LaunchType.valueOf((String) args[0]))//
					.duration((String) args[1])//
					.brew((String) args[2])//
					.step((String) args[3])//
					.actionner((String) args[4])//
					.taskParams(buildTaskParams(args));
					;
			
			
			return params;
		}

		public TaskParams buildTaskParams(Object[] args) {

			TaskParams params = new TaskParams()
					.brew((Brassin) args[0])//
					.step((Etape) args[1])//
					.actioner((Actioner) args[2]);		
			return params;
		}

	}
	

}
