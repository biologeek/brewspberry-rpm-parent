package net.brewspberry.monitoring.services;

public interface ThreadWitnessServices {
	/**
	 * Creates a witness to follow thread execution lifecyle
	 * @param uuid UUID of thread
	 */
	public void witnessThreadStart(String uuid) throws Exception;
	/**
	 * Indirectly interrupts thread execution by changing witness state so that {@link #checkWitness()} makes thread stop.
	 * @param uuid UUID of thread to stop
	 */
	public void witnessThreadinterrupt(String uuid);
}
