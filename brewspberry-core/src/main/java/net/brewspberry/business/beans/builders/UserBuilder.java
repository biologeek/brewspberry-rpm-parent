package net.brewspberry.business.beans.builders;

import net.brewspberry.business.beans.User;

public abstract class UserBuilder {

	
	protected User user;
	
	public abstract User profile();
	

	public User login (String login){
		
		user.setUs_login(login);
		return user;		
	}

	public User password (String passwd){
		
		user.setUs_password(passwd);
		return user;		
	}

	public User firstName (String firstName){
		
		user.setUs_prenom(firstName);
		return user;		
	}
	

	public User lastName (String lastName){
		
		user.setUs_nom(lastName);
		return user;		
	}
	
	public User age (int age){
		
		user.setUs_age(age);
		return user;		
	}
	
	
	public User build(){
		return user;
	}
}
