package net.brewspberry.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.User;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

public class UserDaoImpl implements IGenericDao<User>, ISpecificUserService {

	
	
	
	@Override
	@Transactional
	public User save(User arg0) throws DAOException {

		Session session = HibernateUtil.getSession();
		
		long id = (long) session.save(arg0);
		
		User result = this.getElementById(id);
		
		return result;
	}

	@Override
	public User update(User arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getElementById(long id) {
		
		return (User) HibernateUtil.getSession().get(User.class, id);
	}

	@Override
	public User getElementByName(String name) {
		// TODO Auto-generated method stub
		return null;
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

		Criteria userCriteria = HibernateUtil.getSession().createCriteria(User.class);
		
		userCriteria.add(Restrictions.eq("us_username", username));
		userCriteria.add(Restrictions.eq("us_password", encryptedPassword));
		
		User result = (User) userCriteria.uniqueResult();
		
		return result;
	}

	@Override
	public boolean checkIfUserIsActiveAndNotBlocked(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
