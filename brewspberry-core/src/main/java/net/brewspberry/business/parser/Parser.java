package net.brewspberry.business.parser;

import java.util.List;

public interface Parser<T, U> {
	
	
	
	List<T> parse(U objectToBeParsed);
	List<T> parseList(List<U> listOfObjectsToBeParsed);
	
	List<T> compareTwoObjects(U oldObject, U newOnject);

}
