package net.brewspberry.business;

import java.util.List;

public interface IGenericService<T> {
	
	public T save(T arg0) throws Exception;
	public T update (T arg0);
	public T getElementById(long id);
	public List<T> getAllElements();
	public void deleteElement (long id);
	public void deleteElement (T arg0);
	
	public List<T> getAllDistinctElements ();

}
