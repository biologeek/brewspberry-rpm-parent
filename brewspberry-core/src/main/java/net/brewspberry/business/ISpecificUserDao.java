package net.brewspberry.business;

import net.brewspberry.business.beans.User;

public interface ISpecificUserDao {
	
	
	public User returnUserByCredentials(String username, String encryptedPassword);

}
