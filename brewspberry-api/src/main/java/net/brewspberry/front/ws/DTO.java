package net.brewspberry.front.ws;

import java.util.List;


/**
 * 
 * Determines Data Transfer Objects behaviour
 * T : API type
 * U : Model type
 * 
 */
public interface DTO<T, U> {
	
	
	public T toBusinessObject(U cplObj);
	public List<T> toBusinessObjectList(List<U> cplLst);
	public U toFrontObject(T cplObj);
	public List<U> toFrontObjectList(List<T> cplLst);
	
	
	
	
}
