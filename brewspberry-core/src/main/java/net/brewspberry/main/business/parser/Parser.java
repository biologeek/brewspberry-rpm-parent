package net.brewspberry.main.business.parser;

import java.util.List;

import net.brewspberry.main.business.beans.stock.CounterType;

public interface Parser<T, U, V> {
	
	
	/**
	 * Parses a step to extract stock counters from ingredients
	 * 
	 * objectToBeParsed : the object triggering stock motions (usually a step in a brew but may be a brew or whatever can create stock motions)
	 * counterType : type of stock counter used for generating parsed counters
	 */
	List<T> parse(U objectToBeParsed, CounterType counterType);
	List<T> parseList(List<U> listOfObjectsToBeParsed, CounterType counterType);
	
	List<V> compareTwoObjectsAndExtractStockMotions(U oldObject, U newObject, CounterType counterTypeFrom);

}
