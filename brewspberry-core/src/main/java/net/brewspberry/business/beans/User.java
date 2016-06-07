package net.brewspberry.business.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	// ID -> primary key
	private int us_id, 
	
	// age 
	us_age;
	
	//Last name
	private String us_nom,
	// First name
	us_prenom, 
	
	// encrypted password
	us_password;

	// User profile to manage authorizations
	private UserProfile us_profile;
	
	// If user activated his/her account > true
	private boolean us_active;
	
	//If user was inactivated by admin > true
	private boolean us_force_inactivated;
	
	private Date us_last_connection;
	private Date us_first_connection;
	
	
	
}
