package net.brewspberry.front.ws;

import java.util.List;

public interface DTO<T, U> {
/**
 * 
 * Determines Data Transfer Objects behaviour
 * 
 */
	
	
	public T toBusinessObject(U cplObj);
	public List<T> toBusinessObjectList(List<U> cplLst);
	public U toFrontObject(T cplObj);
	public List<U> toFrontObjectList(List<T> cplLst);
	
	
	
	
}
