package net.brewspberry.regulation.services;

import net.brewspberry.regulation.model.RegulationModel;

public interface RegulationBusinessService<T extends RegulationModel> {
	
	public  void launchRegulation(T model);

}
