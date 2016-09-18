package net.brewspberry.front.ws.beans;

import java.util.Date;

public class UserFullBean {
	
	
	private long userID;
	private String userLogin;
	private String userFirstName;
	private String userLastName;
	private String userSessionToken;
	private String userProfile;
	private boolean userActive;
	private boolean userBlocked;
	private Date userLastConnection;
	private Date userBirthDay;


	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
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
	public String getUserSessionToken() {
		return userSessionToken;
	}
	public void setUserSessionToken(String userSessionToken) {
		this.userSessionToken = userSessionToken;
	}
	public String getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	public boolean isUserActive() {
		return userActive;
	}
	public void setUserActive(boolean userActive) {
		this.userActive = userActive;
	}
	public boolean isUserBlocked() {
		return userBlocked;
	}
	public void setUserBlocked(boolean userBlocked) {
		this.userBlocked = userBlocked;
	}
	public Date getUserLastConnection() {
		return userLastConnection;
	}
	public void setUserLastConnection(Date userLastConnection) {
		this.userLastConnection = userLastConnection;
	}
	public Date getUserBirthDay() {
		return userBirthDay;
	}
	public void setUserBirthDay(Date userBirthDay) {
		this.userBirthDay = userBirthDay;
	}
	

}
