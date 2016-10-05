package net.brewspberry.front.ws.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.User;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.front.ws.IUserRESTService;
import net.brewspberry.front.ws.beans.dto.UserDTO;
import net.brewspberry.front.ws.beans.responses.UserFullBean;
import net.brewspberry.front.ws.beans.responses.UserRegisterBean;
import net.brewspberry.util.LogManager;

@Path("/userService")
public class UserRestServiceImpl implements IUserRESTService {

	private Logger logger;
	@Autowired
	@Qualifier("userServiceImpl")
	private IGenericService<User> userGenService;

	public UserRestServiceImpl() {
		super();

		logger = LogManager.getInstance(this.getClass().getName());
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/create")
	/**
	 * Register new User to the application
	 */
	public Response doRegister(UserRegisterBean bean) {

		User result = null;

		if (bean != null) {

			if (isValidRegistrationBean(bean)) {

				// Converting user to business tobject
				User user = new UserDTO().fromRegistrationToBusinessObject(bean);

				try {
					result = userGenService.save(user);
				} catch (Exception e) {
					return Response.status(500).entity(e.toString()).build();
				}

			}

		}

		return Response.status(200).entity(result).build();
	}

	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PUT
	@Path("/update")
	public Response doUpdateUser(UserRegisterBean bean) {
		User currentUserInDB = null;
		if (isValidUserBean(bean)){
			
			if (bean.getUserId() > 0){
				
				try {
					currentUserInDB = userGenService.getElementById(bean.getUserId());
				} catch (ServiceException e) {
					
					e.printStackTrace();
				}
				
				if (currentUserInDB != null){
					/*
					 * If user already exists, update him
					 */
					User businessUser = new UserDTO().fromFullUserToBusinessObject(bean);
					
					User res = userGenService.update(businessUser);
					
					return Response.status(200).entity(new UserDTO().toFullBean(res)).build();
					
				} else {
					return Response.status(500).entity("Could not find corresponding user !").build();
				}
				
			} else{
				return Response.status(500).entity("Invalid user ID, cannot be negative or 0 !").build();
			} 
		} else {
			return Response.status(500).entity("Invalid user. At least 1 field is not correct !").build();
		}
		
	}

	private boolean isValidUserBean(UserRegisterBean bean) {
		
		return false;
	}

	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@DELETE
	@Path("/delete/{id}")
	public Response deleteUser(@PathParam("id") long userID) {
		
		return null;
	}

	@Override
	@Path("/{id}")
	public Response getUser(@PathParam("id") long userID) {

		UserFullBean result = null;

		if (userID > 0) {

			try {
				User res = userGenService.getElementById(userID);
				
				if (res != null){
					/*
					 * if query returned a user
					 */
					result = new UserDTO().toFullBean(res);
					return Response.status(200).entity(result).build();
				} else {
					
					/*
					 * Else empty response
					 */
					return Response.status(204).build();
				}
			} catch (ServiceException e) {
				return Response.status(500).entity(e.toString()).build();
			}

		} else {
			return Response.status(500).entity(new BusinessException("Negative IDs not authorized !").toString()).build();
		}

	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response getUsers() {


		List<User> users = userGenService.getAllElements();
		List<UserFullBean> resultList = new ArrayList<UserFullBean>();
		
		if (users.size() > 0){
			
			UserDTO dto = new UserDTO();
			
			for (User user : users){
				
				resultList.add(dto.toFullBean(user));
				
			}
		}
		
		return Response.status(200).entity(resultList).build();		
		
	}

	
	/**
	 * Checks required fields in case of modification or creation of a new User
	 * @param bean JSON mapped object of user
	 * @return true if required fields are filled and correct, false else.
	 */
	private boolean isValidRegistrationBean(UserRegisterBean bean) {
		
		return false;
	}

}
