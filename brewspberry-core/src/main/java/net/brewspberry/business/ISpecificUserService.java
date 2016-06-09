package net.brewspberry.business;

import net.brewspberry.business.beans.User;
import javax.servlet.http.Cookie;

public interface ISpecificUserService {
	
	
	public User returnUserByCredentials(String username, String encryptedPassword);

	public boolean checkIfUserIsActiveAndNotBlocked(User user);

	public User getUserByCookieData(Cookie[] cookies);
}
