package net.brewspberry.business.beans.builders;

import net.brewspberry.business.beans.User;

public abstract class UserBuilder {

	
	protected User user;
	
	public abstract User profile();

	public UserBuilder() {
		super();
		this.user = new User();
		
	}

	public UserBuilder login (String login){
		
		user.setUs_login(login);
		return this;		
	}

	public UserBuilder password (String passwd){
		
		user.setUs_password(passwd);
		return this;		
	}

	public UserBuilder firstName (String firstName){
		
		user.setUs_prenom(firstName);
		return this;		
	}
	

	public UserBuilder lastName (String lastName){
		
		user.setUs_nom(lastName);
		return this;		
	}
	
	public UserBuilder age (int age){
		
		user.setUs_age(age);
		return this;		
	}
	
	
	public User build(){
		return user;
	}
}
