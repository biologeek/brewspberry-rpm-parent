package net.brewspberry.business.service;

import java.util.List;

import javax.transaction.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.User;
import net.brewspberry.dao.UserDaoImpl;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.validators.UserValidator;
import net.brewspberry.util.validators.UserValidatorErrors;

public class UserServiceImpl implements IGenericService<User>, ISpecificUserService {

	private IGenericDao<User> userDao;

	@Override
	@Transactional
	public User save(User arg0) throws DAOException {

		List<UserValidatorErrors> err = UserValidator.getInstance().isCompleteUserValid(arg0);

		if (err.isEmpty()) {

			// Everything is OK

			try {
				userDao.save(arg0);
			} catch (DAOException e) {
				e.printStackTrace();
			}

		}

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

		List <UserValidatorErrors> errs = UserValidator.getInstance().isUsernameAndPasswordUserValid(username, notYetEncryptedPassword);
		if (errs.isEmpty()){
			
		}
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

}
