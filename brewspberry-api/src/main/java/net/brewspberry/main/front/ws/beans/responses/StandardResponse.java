package net.brewspberry.main.front.ws.beans.responses;

public class StandardResponse {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StandardResponse message(String message) {
		this.message = message;
		
		return this;
	}

}
