package net.brewspberry.business;

import java.util.List;

import net.brewspberry.business.beans.User;
import net.brewspberry.util.validators.UserValidatorErrors;

import javax.servlet.http.Cookie;

public interface ISpecificUserService {
	
	
	public User returnUserByCredentials(String username, String encryptedPassword);

	public boolean checkIfUserIsActiveAndNotBlocked(User user);

	public User getUserByCookieData(Cookie[] cookies);
	public List<UserValidatorErrors> getErrors();
	public void setErrors(List<UserValidatorErrors> errors);
}
