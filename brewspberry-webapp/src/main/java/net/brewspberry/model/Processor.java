package net.brewspberry.model;

import javax.servlet.http.HttpServletRequest;

public interface Processor <T extends Object> {

	
	public boolean record (T parentObject, HttpServletRequest request);
}
