package net.brewspberry.business.service;


import java.util.List;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.User;
import net.brewspberry.dao.UserDaoImpl;
import net.brewspberry.exceptions.DAOException;

public class UserServiceImpl implements IGenericService<User>, ISpecificUserService {

	private UserDaoImpl userDao;

	@Override
	public User save(User arg0) throws DAOException {

		
		
		return null;
	}

	@Override
	public User update(User arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getElementById(long id) {
		User result = new User();
		if (id > 0){
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
	public User returnUserByCredentials(String username, String encryptedPassword) {

		if (!username.equals("") && !encryptedPassword.equals("")){
			
		}
		return null;
	}


}
