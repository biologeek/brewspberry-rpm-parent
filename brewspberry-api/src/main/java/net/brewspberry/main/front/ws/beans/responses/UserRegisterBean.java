package net.brewspberry.main.front.ws.beans.responses;

public class UserRegisterBean extends UserBean {
	
	private long userId;
	private String userLogin;
	private String userUnencrytedPassword;
	private String userFirstName;
	private String userLastName;
	private String userAge;
	
	
	public UserRegisterBean() {
		super();
		
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public String getUserUnencrytedPassword() {
		return userUnencrytedPassword;
	}
	public void setUserUnencrytedPassword(String userUnencrytedPassword) {
		this.userUnencrytedPassword = userUnencrytedPassword;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserAge() {
		return userAge;
	}
	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}
	
}
