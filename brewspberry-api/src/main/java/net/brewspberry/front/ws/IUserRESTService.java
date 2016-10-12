package net.brewspberry.front.ws;

import java.util.List;

import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.front.ws.beans.responses.StandardResponse;
import net.brewspberry.front.ws.beans.responses.UserBean;
import net.brewspberry.front.ws.beans.responses.UserFullBean;
import net.brewspberry.front.ws.beans.responses.UserRegisterBean;

public interface IUserRESTService {	
	
	public UserFullBean doRegister(UserRegisterBean bean) throws Exception;
	public UserFullBean doUpdateUser(UserRegisterBean bean) throws Exception;
	public StandardResponse deleteUser(long userID);
	public <T extends UserBean> T getUser(long userID) throws ServiceException, BusinessException;
	public List<UserFullBean> getUsers();
}
