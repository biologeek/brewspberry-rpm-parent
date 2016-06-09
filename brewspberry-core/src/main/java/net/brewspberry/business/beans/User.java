package net.brewspberry.business.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4984040139936454985L;

	// ID -> primary key
	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private int us_id, 
	
	// age 
	us_age;
	
	//Last name
	private String us_nom,
	// First name
	us_prenom, 
	
	// encrypted password
	us_password,
	
	us_login,
	
	us_session_token;

	public String getUs_session_token() {
		return us_session_token;
	}
	public void setUs_session_token(String us_session_token) {
		this.us_session_token = us_session_token;
	}
	public String getUs_login() {
		return us_login;
	}
	public void setUs_login(String us_login) {
		this.us_login = us_login;
	}
	// User profile to manage authorizations
	private UserProfile us_profile;
	
	// If user activated his/her account > true
	private boolean us_active;
	private Date us_date_activation;
	
	//If user was inactivated by admin > true
	private boolean us_force_inactivated;
	private Date us_date_inactivation;
	
	private Date us_last_connection;
	private Date us_first_connection;
	private Date us_birthday;
	
	
	public int getUs_id() {
		return us_id;
	}
	public void setUs_id(int us_id) {
		this.us_id = us_id;
	}
	public int getUs_age() {
		return us_age;
	}
	public void setUs_age(int us_age) {
		this.us_age = us_age;
	}
	public String getUs_nom() {
		return us_nom;
	}
	public void setUs_nom(String us_nom) {
		this.us_nom = us_nom;
	}
	public String getUs_prenom() {
		return us_prenom;
	}
	public void setUs_prenom(String us_prenom) {
		this.us_prenom = us_prenom;
	}
	public String getUs_password() {
		return us_password;
	}
	public void setUs_password(String us_password) {
		this.us_password = us_password;
	}
	public UserProfile getUs_profile() {
		return us_profile;
	}
	public void setUs_profile(UserProfile us_profile) {
		this.us_profile = us_profile;
	}
	public boolean isUs_active() {
		return us_active;
	}
	public void setUs_active(boolean us_active) {
		this.us_active = us_active;
	}
	public boolean isUs_force_inactivated() {
		return us_force_inactivated;
	}
	public void setUs_force_inactivated(boolean us_force_inactivated) {
		this.us_force_inactivated = us_force_inactivated;
	}
	public Date getUs_last_connection() {
		return us_last_connection;
	}
	public void setUs_last_connection(Date us_last_connection) {
		this.us_last_connection = us_last_connection;
	}
	public Date getUs_first_connection() {
		return us_first_connection;
	}
	public void setUs_first_connection(Date us_first_connection) {
		this.us_first_connection = us_first_connection;
	}
	public Date getUs_birthday() {
		return us_birthday;
	}
	public void setUs_birthday(Date us_birthday) {
		this.us_birthday = us_birthday;
	}
	public Date getUs_date_activation() {
		return us_date_activation;
	}
	public void setUs_date_activation(Date us_date_activation) {
		this.us_date_activation = us_date_activation;
	}
	public Date getUs_date_inactivation() {
		return us_date_inactivation;
	}
	public void setUs_date_inactivation(Date us_date_inactivation) {
		this.us_date_inactivation = us_date_inactivation;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
