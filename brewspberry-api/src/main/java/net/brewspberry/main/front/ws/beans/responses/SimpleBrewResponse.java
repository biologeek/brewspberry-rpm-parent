package net.brewspberry.main.front.ws.beans.responses;

import java.util.Date;

import net.brewspberry.main.business.beans.brewing.BrewStatus;
import net.brewspberry.main.business.beans.brewing.EtapeType;

public class SimpleBrewResponse {
	
	public static enum BrewResponseType{
		
		full,light;
	}

	
	private long id;
	private String description;
	private Date beginning;
	private Date end;
	private Date maj;
	private float quantity;
	private BrewStatus status;
	private String type; // ?????
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getMaj() {
		return maj;
	}
	public void setMaj(Date maj) {
		this.maj = maj;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantite) {
		this.quantity = quantite;
	}
	public BrewStatus getStatus() {
		return status;
	}
	public void setStatus(BrewStatus status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Date getBeginning() {
		return beginning;
	}
	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}

}
