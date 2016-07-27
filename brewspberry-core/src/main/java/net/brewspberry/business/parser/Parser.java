package net.brewspberry.business.parser;

import java.util.List;

public interface Parser<T, U, V> {
	
	
	
	List<T> parse(U objectToBeParsed);
	List<T> parseList(List<U> listOfObjectsToBeParsed);
	
	List<V> compareTwoObjectsAndExtractStockMotions(U oldObject, U newOnject);

}
