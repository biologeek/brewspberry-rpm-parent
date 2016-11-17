package net.brewspberry.batches.tasks;

import net.brewspberry.batches.beans.TaskParams;
import net.brewspberry.batches.exceptions.NotTheGoodNumberOfArgumentsException;

public interface Task extends Runnable {

	public boolean checkSpecificParameters (Object[] specs) throws NotTheGoodNumberOfArgumentsException;
	public void setTaskParameters (TaskParams specs);
	public void setWriteParameters(String entityToWrite); 
}
