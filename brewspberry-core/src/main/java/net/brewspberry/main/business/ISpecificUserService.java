package net.brewspberry.main.business;

import java.util.List;

import javax.servlet.http.Cookie;

import net.brewspberry.main.business.beans.User;
import net.brewspberry.main.util.validators.UserValidatorErrors;

public interface ISpecificUserService {
	
	
	public User returnUserByCredentials(String username, String encryptedPassword);

	public boolean checkIfUserIsActiveAndNotBlocked(User user);

	public User getUserByCookieData(Cookie[] cookies);
	public List<UserValidatorErrors> getErrors();
	public void setErrors(List<UserValidatorErrors> errors);
}
