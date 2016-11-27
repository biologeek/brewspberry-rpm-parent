package net.brewspberry.main.batches.launchers;

import net.brewspberry.main.batches.beans.BatchParams;

public interface Batch {

	
	
	public void execute ();
	public void setBatchParams(BatchParams batchParams);
	
}
