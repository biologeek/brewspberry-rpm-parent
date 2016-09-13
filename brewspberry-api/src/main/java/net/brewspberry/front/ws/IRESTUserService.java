package net.brewspberry.front.ws;

import javax.ws.rs.core.Response;

import net.brewspberry.front.ws.beans.UserLoginBean;
import net.brewspberry.front.ws.beans.UserRegisterBean;

public interface IRESTUserService {
	
	
	public Response doRegister(UserRegisterBean bean);

}
