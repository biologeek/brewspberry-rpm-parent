package net.brewspberry.main.business;

import java.util.List;

import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.business.exceptions.ServiceException;

public interface IGenericService<T> {
	
	public T save(T arg0) throws Exception;
	public T update (T arg0) throws DAOException;
	public T getElementById(long id) throws ServiceException;
	public T getElementByName(String name) throws ServiceException;
	public List<T> getAllElements();
	public void deleteElement (long id);
	public void deleteElement (T arg0);
	
	public List<T> getAllDistinctElements ();

}
