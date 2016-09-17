package net.brewspberry.front.ws;

import javax.ws.rs.core.Response;

import net.brewspberry.front.ws.beans.UserLoginBean;
import net.brewspberry.front.ws.beans.UserRegisterBean;

public interface IRESTUserService {
	
	
	public Response doRegister(UserRegisterBean bean);
	public Response doUpdateUser(UserRegisterBean bean);
	public Response deleteUser(long userID);
	public Response getUser(long userID);
	public Response getUsers();
}
