package net.brewspberry.main.business;

import net.brewspberry.main.business.beans.monitoring.Actioner;

public interface ISpecificActionerLauncherService {

	public Actioner startAction(Actioner actioner) throws Exception;

	public Actioner stopAction(Actioner actioner) throws Exception;
	


	public Actioner startAction(Long actioner) throws Exception;

	public Actioner stopAction(Long actioner) throws Exception;

}
