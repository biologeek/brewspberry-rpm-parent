package net.brewspberry.business;

import net.brewspberry.business.beans.User;

public interface ISpecificUserService {
	
	
	public User returnUserByCredentials(String username, String encryptedPassword);

	public boolean checkIfUserIsActiveAndNotBlocked(User user);

}
