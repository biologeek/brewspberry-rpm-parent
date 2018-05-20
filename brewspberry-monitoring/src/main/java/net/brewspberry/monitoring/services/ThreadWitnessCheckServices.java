package net.brewspberry.monitoring.services;

import net.brewspberry.monitoring.model.ThreadWitness;

public interface ThreadWitnessCheckServices {

	/**
	 * Pre condition executed periodically by running thread to check if thread can continue. <br>
	 * <br>
	 * If witness is not present or invalid, thread should stop
	 * 
	 * @param uuid UUID of thread
	 */
	public ThreadWitness checkWitness(String uuid);

}
