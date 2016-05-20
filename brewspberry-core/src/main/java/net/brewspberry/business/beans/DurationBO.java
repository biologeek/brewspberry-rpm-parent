package net.brewspberry.business.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class DurationBO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3610500240459984835L;
	@Id@GeneratedValue
	private long dur_id;
	private long week;
	private long day;
	private long hour;
	private long minute;
	private long second;
	private long milisecond;
	
	@OneToOne(fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Etape dur_step;
	
	
	public DurationBO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public long getWeek() {
		return week;
	}
	public void setWeek(long week) {
		this.week = week;
	}
	public long getDay() {
		return day;
	}
	public void setDay(long l) {
		this.day = l;
	}
	public long getHour() {
		return hour;
	}
	public void setHour(long hour) {
		this.hour = hour;
	}
	public long getMinute() {
		return minute;
	}
	public void setMinute(long minute) {
		this.minute = minute;
	}
	public long getSecond() {
		return second;
	}
	public void setSecond(long second) {
		this.second = second;
	}
	public long getMilisecond() {
		return milisecond;
	}
	public void setMilisecond(long milisecond) {
		this.milisecond = milisecond;
	}


	public long getDur_id() {
		return dur_id;
	}


	public void setDur_id(long dur_id) {
		this.dur_id = dur_id;
	}


	public Etape getDur_step() {
		return dur_step;
	}


	public void setDur_step(Etape dur_step) {
		this.dur_step = dur_step;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return  week + "w " + day + "d " + hour
				+ "h " + minute + "m " + second + "s "+ milisecond + "ms";
	}
	

}
