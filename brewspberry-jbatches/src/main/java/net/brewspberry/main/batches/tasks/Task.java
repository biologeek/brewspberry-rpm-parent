package net.brewspberry.main.batches.tasks;

import net.brewspberry.main.batches.beans.TaskParams;
import net.brewspberry.main.batches.exceptions.NotTheGoodNumberOfArgumentsException;

public interface Task extends Runnable {

	public boolean checkSpecificParameters (Object[] specs) throws NotTheGoodNumberOfArgumentsException;
	public void setTaskParameters (TaskParams specs);
	public void setWriteParameters(String entityToWrite); 
}
