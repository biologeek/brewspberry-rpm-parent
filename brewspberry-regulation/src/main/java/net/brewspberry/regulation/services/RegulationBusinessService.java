package net.brewspberry.regulation.services;

import net.brewspberry.monitoring.model.RegulationModel;

public interface RegulationBusinessService<T extends RegulationModel> {
	
	public  void launchRegulation(T model);

}
