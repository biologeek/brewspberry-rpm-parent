package net.brewspberry.monitoring.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import net.brewspberry.monitoring.exceptions.TechnicalException;
import net.brewspberry.monitoring.model.DaemonThreadState;

public interface ThreadStateServices {

	public DaemonThreadState readState(String sensorUuid) throws TechnicalException;
	/**
	 * Checks existing threads and reads state of all sensors
	 * @return
	 * @throws TechnicalException 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public List<DaemonThreadState> readStates() throws TechnicalException;

	public void writeState(DaemonThreadState state) throws TechnicalException;
	
	public void cleanState(String uuid) throws TechnicalException;
	public void cleanState(List<String> collect) throws TechnicalException;
}
