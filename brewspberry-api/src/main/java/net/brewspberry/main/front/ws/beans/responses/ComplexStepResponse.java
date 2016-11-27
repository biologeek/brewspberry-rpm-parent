package net.brewspberry.main.front.ws.beans.responses;

import java.util.List;

import net.brewspberry.main.front.ws.beans.requests.HopRequest;
import net.brewspberry.main.front.ws.beans.requests.MaltRequest;
import net.brewspberry.main.front.ws.beans.requests.YeastRequest;

public class ComplexStepResponse extends SimpleStepResponse {
	
	
	private List<ActionnerResponse> actioners;
	private List<MaltRequest> malts;
	private List<HopRequest> hops;
	private List<YeastRequest> yeasts;

	public ComplexStepResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<MaltRequest> getMalts() {
		return malts;
	}

	public void setMalts(List<MaltRequest> malts) {
		this.malts = malts;
	}

	public List<HopRequest> getHops() {
		return hops;
	}

	public void setHops(List<HopRequest> hops) {
		this.hops = hops;
	}

	public List<YeastRequest> getYeasts() {
		return yeasts;
	}

	public void setYeasts(List<YeastRequest> yeasts) {
		this.yeasts = yeasts;
	}

	public List<ActionnerResponse> getActioners() {
		return actioners;
	}

	public void setActioners(List<ActionnerResponse> actioners) {
		this.actioners = actioners;
	}

}
