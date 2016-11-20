package net.brewspberry.batches.launchers;

import net.brewspberry.batches.beans.BatchParams;

public interface Batch {

	
	
	public void execute ();
	public void setBatchParams(BatchParams batchParams);
	
}
