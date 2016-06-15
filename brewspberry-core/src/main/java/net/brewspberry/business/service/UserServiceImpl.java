package net.brewspberry.business.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificUserDao;
import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.User;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.EncryptionUtils;
import net.brewspberry.util.validators.UserValidator;
import net.brewspberry.util.validators.UserValidatorErrors;

public class UserServiceImpl implements IGenericService<User>, ISpecificUserService {

	private IGenericDao<User> userDao;
	private ISpecificUserDao userSpecDao;
	private List<UserValidatorErrors> errors;

	@Override
	@Transactional
	public User save(User arg0) throws DAOException {
		User res = null;
		this.setErrors(null);


		if (arg0.getUs_registration() == null){
			arg0.setUs_registration(new Date());
		} 
		
		String encryptedPasswd = EncryptionUtils.encryptPassword(arg0.getUs_password(), "MD5");
		
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
		User res = new User();
		List <UserValidatorErrors> errs = UserValidator.getInstance().isUsernameAndPasswordUserValid(username, notYetEncryptedPassword);
		
		
		
		if (errs.isEmpty()){
			
			String encryptedPasswd = EncryptionUtils.encryptPassword(notYetEncryptedPassword, "MD5");
			res.setUs_login(username);
			res.setUs_password(encryptedPasswd);
			res = userSpecDao.returnUserByCredentials(res);
			
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
		
		
		if (cookies.length > 0){
			for (Cookie cookie : cookies){
				
				if (cookie.getName().equals("user.login")){
					
					user.setUs_login(cookie.getValue());
				
				} else if (cookie.getName().equals("user.token")){
					
					user.setUs_session_token(cookie.getValue());
				
				}			
			}
			
			return userSpecDao.getUserByCookieData(user);
		}

		
		return user;
	}

}
