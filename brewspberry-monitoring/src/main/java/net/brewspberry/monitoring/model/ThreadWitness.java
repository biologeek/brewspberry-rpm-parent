package net.brewspberry.monitoring.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ThreadWitness {

	@Id
	private String uuid;
	private Date date;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ThreadWitness uuid(String uuid2) {
		uuid = uuid2;
		return this;
	}

	public ThreadWitness date(Date date2) {
		date = date2;
		return this;
	}

}
