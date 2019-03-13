package net.brewspberry.monitoring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Technical class that will help follow state of a thread
 */
@Entity
public class ThreadState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -273031448620946979L;
	private int errorOccurence;
	private String errorMessage;
	@Id
	private String uuid;
	private Date stateDate;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorOccurence() {
		return errorOccurence;
	}

	public void setErrorOccurence(int errorOccurence) {
		this.errorOccurence = errorOccurence;
	}
	
	public Date getStateDate() {
		return stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	@Override
	public String toString() {
		return "DaemonThreadState [errorOccurence=" + errorOccurence + ", errorMessage=" + errorMessage
				+ ", stateDate=" + stateDate + "]";
	}

	/**
	 * A thread state when no errors
	 * 
	 * @return
	 */
	public static ThreadState noError(String uuid) {
		return new ThreadState()//
				.errorOccurence(0)//
				.uuid(uuid)//
				.stateDate(new Date());
	}

	private ThreadState uuid(String uuid2) {
		this.uuid = uuid2;
		return this;
	}

	private ThreadState errorOccurence(int i) {
		errorOccurence = i;
		return this;
	}

	private ThreadState stateDate(Date object) {
		stateDate = object;
		return this;
	}
	/**
	 * Builds a {@link ThreadState} with errors for device
	 * @param occurence number of errors
	 * @param uuid UUID if device
	 * @param errorMessage TODO
	 * @return a ThreadState object
	 */
	public static ThreadState xErrors(Integer occurence, String uuid, String errorMessage) {
		return new ThreadState()//
				.uuid(uuid)//
				.stateDate(new Date())//
				.errorOccurence(occurence);
	}

}
