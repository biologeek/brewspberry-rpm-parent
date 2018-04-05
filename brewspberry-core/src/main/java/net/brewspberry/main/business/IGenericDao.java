package net.brewspberry.main.business;

import java.util.List;

import net.brewspberry.main.business.exceptions.DAOException;

public interface IGenericDao<T> {
	
	public T save(T arg0) throws DAOException;
	public T update (T arg0) throws DAOException;
	public T getElementById(long id);
	public T getElementByName(String name);
	public List<T> getAllElements();
	public void deleteElement (long id);
	public void deleteElement (T arg0);
	
	public List<T> getAllDistinctElements ();

}
