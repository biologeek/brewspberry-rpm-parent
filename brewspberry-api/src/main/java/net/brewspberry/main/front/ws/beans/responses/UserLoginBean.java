package net.brewspberry.main.front.ws.beans.responses;

public class UserLoginBean extends UserBean {
	
	
	private String username;
	private String passwrd;
	
	
	
	public UserLoginBean() {
		super();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswrd() {
		return passwrd;
	}
	public void setPasswrd(String passwrd) {
		this.passwrd = passwrd;
	}
	

}
