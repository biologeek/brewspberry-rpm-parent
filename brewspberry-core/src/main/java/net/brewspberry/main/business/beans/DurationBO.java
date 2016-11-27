package net.brewspberry.main.business.beans;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.springframework.stereotype.Component;

@Embeddable
public class DurationBO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7946357890481699134L;
	/**
	 * 
	 */
	private int duration;
	private int unit;

	public DurationBO(long duration, int unit) {
		this.duration = (int) duration;
		this.unit = unit;
	}

	
	private DurationBO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public static long toLong(DurationBO duration) {

		if (duration == null){
			return 0L;
		}
		if (duration.unit == Calendar.SECOND)
			return duration.duration;
		else {

			switch (duration.unit) {
			case Calendar.MINUTE:
				return duration.duration * 60;
			case Calendar.HOUR:
				return duration.duration * 60 * 60;
			case Calendar.DATE:
				return duration.duration * 60 * 60 * 24;
			case Calendar.MONTH:
				return duration.duration * 60 * 60 * 24 * 30;

			case Calendar.YEAR:
				return duration.duration * 60 * 60 * 24 * 365;
			}
		}
		
		return 0;

	}

}
