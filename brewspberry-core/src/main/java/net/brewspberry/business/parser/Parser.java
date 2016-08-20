package net.brewspberry.business.parser;

import java.util.List;

import net.brewspberry.business.beans.stock.CounterType;

public interface Parser<T, U, V> {
	
	
	/**
	 * Parses a step to extract stock counters from ingredients
	 */
	List<T> parse(U objectToBeParsed, CounterType counterType);
	List<T> parseList(List<U> listOfObjectsToBeParsed, CounterType counterType);
	
	List<V> compareTwoObjectsAndExtractStockMotions(U oldObject, U newObject, CounterType counterTypeFrom);

}
