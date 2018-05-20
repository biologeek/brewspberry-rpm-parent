package net.brewspberry.monitoring.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Technical class that will help follow state of a thread
 */
public class DaemonThreadState implements Serializable {

	private int errorOccurence;
	private String errorMessage;
	private String uuid;
	private StackTraceElement[] exceptionTrace;
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

	public StackTraceElement[] getExceptionTrace() {
		return exceptionTrace;
	}

	public void setExceptionTrace(StackTraceElement[] stackTraceElements) {
		this.exceptionTrace = stackTraceElements;
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
				+ ", exceptionTrace=" + Arrays.toString(exceptionTrace) + ", stateDate=" + stateDate + "]";
	}

	/**
	 * A thread state when no errors
	 * 
	 * @return
	 */
	public static DaemonThreadState noError(String uuid) {
		return new DaemonThreadState()//
				.errorOccurence(0)//
				.uuid(uuid)//
				.stateDate(new Date());
	}

	private DaemonThreadState uuid(String uuid2) {
		this.uuid = uuid2;
		return this;
	}

	private DaemonThreadState errorOccurence(int i) {
		errorOccurence = i;
		return this;
	}

	private DaemonThreadState stateDate(Date object) {
		stateDate = object;
		return this;
	}

	public static DaemonThreadState xErrors(Integer i, String uuid) {
		return new DaemonThreadState()//
				.uuid(uuid)//
				.stateDate(new Date())//
				.errorOccurence(i);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + errorOccurence;
		result = prime * result + Arrays.hashCode(exceptionTrace);
		result = prime * result + ((stateDate == null) ? 0 : stateDate.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DaemonThreadState other = (DaemonThreadState) obj;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (errorOccurence != other.errorOccurence)
			return false;
		if (!Arrays.equals(exceptionTrace, other.exceptionTrace))
			return false;
		if (stateDate == null) {
			if (other.stateDate != null)
				return false;
		} else if (!stateDate.equals(other.stateDate))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
