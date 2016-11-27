package net.brewspberry.main.business.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificUserDao;
import net.brewspberry.main.business.ISpecificUserService;
import net.brewspberry.main.business.beans.User;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.util.EncryptionUtils;
import net.brewspberry.main.util.LogManager;
import net.brewspberry.main.util.validators.UserValidator;
import net.brewspberry.main.util.validators.UserValidatorErrors;

@Service ("userServiceImpl")
@Transactional
public class UserServiceImpl implements IGenericService<User>,
		ISpecificUserService {

	@Autowired
	@Qualifier("userDaoImpl")
	private IGenericDao<User> userDao;
	@Autowired
	private ISpecificUserDao userSpecDao;
	private List<UserValidatorErrors> errors;
	private Logger logger = LogManager.getInstance(UserServiceImpl.class.getName());

	public UserServiceImpl() {
		super();
		
	}

	@Override
	@Transactional
	public User save(User arg0) throws Exception {
		User res = null;
		this.setErrors(null);

		if (arg0.getUs_registration() == null) {
			arg0.setUs_registration(new Date());
		}

		String encryptedPasswd = EncryptionUtils.encryptPassword(
				arg0.getUs_password(), "MD5");

		arg0.setUs_password(encryptedPasswd);
		arg0.setUs_force_inactivated(false);
		arg0.setUs_active(false);

		// Everything is OK

		try {
			res = userDao.save(arg0);
		} catch (DAOException e) {
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public User update(User arg0) {
		
		return null;
	}

	@Override
	public User getElementById(long id) {
		User result = new User();
		if (id > 0) {
			result = userDao.getElementById(id);
		}
		return result;
	}

	@Override
	public List<User> getAllElements() {
		
		return null;
	}

	@Override
	public void deleteElement(long id) {
		

	}

	@Override
	public void deleteElement(User arg0) {
		

	}

	@Override
	public List<User> getAllDistinctElements() {
		
		return null;
	}

	@Override
	/**
	 * Checks if user credentials are correct, then checks if user is active and not blocked
	 * 
	 * @return a full user if he/she's OK, else null 
	 */
	public User returnUserByCredentials(String username,
			String notYetEncryptedPassword) {
		this.setErrors(null);
		User res = new User();
		List<UserValidatorErrors> errs = UserValidator.getInstance()
				.isUsernameAndPasswordUserValid(username,
						notYetEncryptedPassword);

		if (errs.isEmpty()) {

			String encryptedPasswd = EncryptionUtils.encryptPassword(
					notYetEncryptedPassword, "MD5");
			logger.info("Incoming password"+encryptedPasswd);
			res.setUs_login(username);
			res.setUs_password(encryptedPasswd);
			res = userSpecDao.returnUserByCredentials(res);
			logger.info("DB user " +res.toString());

			if (this.checkIfUserIsActiveAndNotBlocked(res)) {
				return res;
			}

		}

		this.setErrors(errs);
		return null;
	}

	@Override
	public boolean checkIfUserIsActiveAndNotBlocked(User user) {

		if (user != null) {
			if (user.isUs_active() && !user.isUs_force_inactivated()) {
				return true;
			}
		}
		return false;
	}

	public IGenericDao<User> getUserDao() {
		return userDao;
	}

	public void setUserDao(IGenericDao<User> userDao) {
		this.userDao = userDao;
	}

	public ISpecificUserDao getUserSpecDao() {
		return userSpecDao;
	}

	public void setUserSpecDao(ISpecificUserDao userSpecDao) {
		this.userSpecDao = userSpecDao;
	}

	public List<UserValidatorErrors> getErrors() {
		return errors;
	}

	public void setErrors(List<UserValidatorErrors> errors) {
		this.errors = errors;
	}

	@Override
	/**
	 * 
	 * @param cookies
	 * @return
	 */
	public User getUserByCookieData(Cookie[] cookies) {

		User user = new User();

		if (cookies.length > 0) {
			for (Cookie cookie : cookies) {

				if (cookie.getName().equals("user.login")) {

					user.setUs_login(cookie.getValue());

				} else if (cookie.getName().equals("user.token")) {

					user.setUs_session_token(cookie.getValue());

				}
			}

			return userSpecDao.getUserByCookieData(user);
		}

		return user;
	}

	@Override
	public User getElementByName(String name) throws ServiceException {
		
		return null;
	}

}
