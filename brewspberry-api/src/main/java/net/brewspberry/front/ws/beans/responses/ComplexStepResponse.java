package net.brewspberry.front.ws.beans.responses;

import java.util.List;

public class ComplexStepResponse extends SimpleStepResponse {
	
	
	private List<ActionnerResponse> actioners;

	public ComplexStepResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<ActionnerResponse> getActioners() {
		return actioners;
	}

	public void setActioners(List<ActionnerResponse> actioners) {
		this.actioners = actioners;
	}

}
