package net.brewspberry.util.validators;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.brewspberry.business.beans.User;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;

public class UserValidator {

	
	private static UserValidator instance;
	
	
	
	private UserValidator(){
		
	}
	
	
	public static UserValidator getInstance(){
		
		if (instance  == null)
			instance = new UserValidator();
		
		return instance;
	}
	
	
	public List<UserValidatorErrors> isCompleteUserValid (User user){
		
		
		
		return null;	
	}
	
	public List<UserValidatorErrors> isUsernameAndPasswordUserValid (String username, String notYetEncryptedPassword){
		
		List<UserValidatorErrors> errors = new ArrayList<UserValidatorErrors>();
		
		if (username.equals(""))
			errors.add(UserValidatorErrors.EMPTY_USER);
		if (notYetEncryptedPassword.equals(""))
			errors.add(UserValidatorErrors.EMPTY_PASSWORD);
		
		if (notYetEncryptedPassword.length() < Integer.parseInt(ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "param.session.passwd.length")))
			errors.add(UserValidatorErrors.SHORT_PASSWORD);
		
		
		return errors;	
	}
	
	public List<UserValidatorErrors> validateFormUser(User userToValidate){
		
		List<UserValidatorErrors> errors = new ArrayList<UserValidatorErrors>();
	
		if (this.isUserMandatoryFieldsNullity(userToValidate)){
			
		}
		
		return errors;
	}


	private boolean isUserMandatoryFieldsNullity(User userToValidate) {

		if (userToValidate.getUs_login() != null && !userToValidate.getUs_login().equals("") ){
			
		}
		
		
		return false;
	}
	
}
