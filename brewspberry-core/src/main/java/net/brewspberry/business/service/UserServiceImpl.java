package net.brewspberry.business.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.User;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.EncryptionUtils;
import net.brewspberry.util.validators.UserValidator;
import net.brewspberry.util.validators.UserValidatorErrors;

public class UserServiceImpl implements IGenericService<User>, ISpecificUserService {

	private IGenericDao<User> userDao;
	private ISpecificUserService userSpecDao;
	private List<UserValidatorErrors> errors;

	@Override
	@Transactional
	public User save(User arg0) throws DAOException {
		this.setErrors(null);

		List<UserValidatorErrors> err = UserValidator.getInstance().isCompleteUserValid(arg0);

		if (err.isEmpty()) {

			// Everything is OK

			try {
				userDao.save(arg0);
			} catch (DAOException e) {
				e.printStackTrace();
			}

		}
		this.setErrors(err);
		return arg0;
	}

	@Override
	public User update(User arg0) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteElement(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteElement(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User returnUserByCredentials(String username, String notYetEncryptedPassword) {
		this.setErrors(null);
		User res = null;
		List <UserValidatorErrors> errs = UserValidator.getInstance().isUsernameAndPasswordUserValid(username, notYetEncryptedPassword);
		
		
		
		if (errs.isEmpty()){
			
			String encryptedPasswd = EncryptionUtils.encryptPassword(notYetEncryptedPassword, "MD5");
			
			res = userSpecDao.returnUserByCredentials(username, encryptedPasswd);
			
			if (this.checkIfUserIsActiveAndNotBlocked(res)){
				return res;
			}
			
		}
		
		this.setErrors(errs);
		return null;
	}

	@Override
	public boolean checkIfUserIsActiveAndNotBlocked(User user) {

		if (user.isUs_active() && user.isUs_force_inactivated()) {
			return true;
		} else {

			return false;
		}
	}

	public IGenericDao<User> getUserDao() {
		return userDao;
	}

	public void setUserDao(IGenericDao<User> userDao) {
		this.userDao = userDao;
	}

	public ISpecificUserService getUserSpecDao() {
		return userSpecDao;
	}

	public void setUserSpecDao(ISpecificUserService userSpecDao) {
		this.userSpecDao = userSpecDao;
	}

	public List<UserValidatorErrors> getErrors() {
		return errors;
	}

	public void setErrors(List<UserValidatorErrors> errors) {
		this.errors = errors;
	}

	@Override
	public User getUserByCookieData(Cookie[] cookies) {

		User user = new User();
		
		
		if (cookies.length > 0){
			for (Cookie cookie : cookies){
				
				if (cookie.getName().equals("user.name")){
					
					user.setUs_login(cookie.getValue());
				
				} else if (cookie.getName().equals("user.token")){
					
					user.setUs_session_token(cookie.getValue());
				
				}			
			}
		}

		
		return null;
	}

}
