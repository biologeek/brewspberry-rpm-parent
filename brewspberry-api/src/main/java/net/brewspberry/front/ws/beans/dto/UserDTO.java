package net.brewspberry.front.ws.beans.dto;

import net.brewspberry.business.beans.User;
import net.brewspberry.front.ws.beans.responses.UserFullBean;
import net.brewspberry.front.ws.beans.responses.UserRegisterBean;

public class UserDTO {

	public User fromFullUserToBusinessObject(UserRegisterBean bean) {
		
		return null;
	}
	public User fromRegistrationToBusinessObject(UserRegisterBean bean) {
		
		return null;
	}
	
	public UserRegisterBean toTransferObject(User user){
		return null;
		
	}
	public UserFullBean toFullBean(User user){
		return null;		
	}

}
