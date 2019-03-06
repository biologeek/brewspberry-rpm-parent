package net.brewspberry.monitoring.services;

/**
 * Allows to perform operations to witness Thread start and stop
 *
 */
public interface ThreadWitnessServices {
	/**
	 * Creates a witness to follow thread execution lifecyle
	 * @param uuid UUID of thread
	 */
	public void witnessThreadStart(String uuid) throws Exception;
	/**
	 * Indirectly interrupts thread execution by changing witness state so that {@link ThreadWitnessCheckServices#checkWitness()} makes thread stop.
	 * @param uuid UUID of thread to stop
	 */
	public void witnessThreadInterrupt(String uuid) throws Exception;
}
