package net.brewspberry.main.business.beans;

import org.springframework.stereotype.Component;

public enum Duration {
	
	
	MILISECOND(1L),
	SECOND(1000L),
	MINUTE(60000L),
	HOUR(3600000L),
	DAY(86400000L),
	WEEK(604800000L),
	YEAR(220903200000L);
	
	long multiplicator = 1;

	
	Duration(long multi){
		this.multiplicator = multi;
	}
}
