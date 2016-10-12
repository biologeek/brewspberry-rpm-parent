package net.brewspberry.front.ws.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.User;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.front.ws.IUserRESTService;
import net.brewspberry.front.ws.beans.dto.UserDTO;
import net.brewspberry.front.ws.beans.responses.StandardResponse;
import net.brewspberry.front.ws.beans.responses.UserFullBean;
import net.brewspberry.front.ws.beans.responses.UserRegisterBean;
import net.brewspberry.util.LogManager;

@RequestMapping("/userService")
@RestController
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
	@PostMapping("/create")
	/**
	 * Register new User to the application
	 */
	public UserFullBean doRegister(UserRegisterBean bean) throws Exception {

		User result = null;

		if (bean != null) {

			if (isValidRegistrationBean(bean)) {

				// Converting user to business tobject
				User user = new UserDTO().fromRegistrationToBusinessObject(bean);

				result = userGenService.save(user);

			}

		}

		return new UserDTO().toFullBean(result);
	}

	@Override
	@PutMapping("/update")
	public UserFullBean doUpdateUser(UserRegisterBean bean) throws Exception {
		User currentUserInDB = null;
		if (isValidUserBean(bean)) {

			if (bean.getUserId() > 0) {

				try {
					currentUserInDB = userGenService.getElementById(bean.getUserId());
				} catch (ServiceException e) {

					e.printStackTrace();
				}

				if (currentUserInDB != null) {
					/*
					 * If user already exists, update him
					 */
					User businessUser = new UserDTO().fromFullUserToBusinessObject(bean);

					User res = userGenService.update(businessUser);

					return new UserDTO().toFullBean(res);

				} else {
					throw new Exception("Could not find corresponding user !");
				}

			} else {
				throw new Exception("Invalid user ID, cannot be negative or 0 !");
			}
		} else {
			throw new Exception("Invalid user. At least 1 field is not correct !");
		}

	}

	private boolean isValidUserBean(UserRegisterBean bean) {

		return false;
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public StandardResponse deleteUser(@PathVariable("id") long userID) {

		return new StandardResponse().message("OK");
	}

	@Override
	@GetMapping("/{id}")
	public UserFullBean getUser(@PathVariable("id") long userID) throws ServiceException, BusinessException {

		UserFullBean result = null;

		if (userID > 0) {
			User res = userGenService.getElementById(userID);

			if (res != null) {
				/*
				 * if query returned a user
				 */
				result = new UserDTO().toFullBean(res);
				return result;
			} else {

				/*
				 * Else empty response
				 */
				return null;
			}

		} else {
			throw new BusinessException("Negative IDs not authorized !");
		}

	}

	@Override
	@GetMapping("/")
	public List<UserFullBean> getUsers() {

		List<User> users = userGenService.getAllElements();
		List<UserFullBean> resultList = new ArrayList<UserFullBean>();

		if (users.size() > 0) {

			UserDTO dto = new UserDTO();

			for (User user : users) {

				resultList.add(dto.toFullBean(user));

			}
		}

		return resultList;

	}

	/**
	 * Checks required fields in case of modification or creation of a new User
	 * 
	 * @param bean
	 *            JSON mapped object of user
	 * @return true if required fields are filled and correct, false else.
	 */
	private boolean isValidRegistrationBean(UserRegisterBean bean) {

		return false;
	}

}
