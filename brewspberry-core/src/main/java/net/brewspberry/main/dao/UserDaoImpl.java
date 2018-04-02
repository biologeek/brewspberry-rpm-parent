package net.brewspberry.main.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.User;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.data.ISpecificUserDao;

@Repository
@Transactional
public class UserDaoImpl implements IGenericDao<User>, ISpecificUserDao {

	@Autowired
	SessionFactory sessionFactory;
	
	
	public UserDaoImpl() {
		super();
		
	}

	@Override
	@Transactional
	public User save(User arg0) throws DAOException {

		Session session = sessionFactory.getCurrentSession();

		long id = (long) sessionFactory.getCurrentSession().save(arg0);

		User result = this.getElementById(id);

		return result;
	}

	@Override
	public User update(User arg0) {
		
		return null;
	}

	@Override
	public User getElementById(long id) {

		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public User getElementByName(String name) {
		
		return null;
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
	public User returnUserByCredentials(User user) {

		Criteria userCriteria = sessionFactory.getCurrentSession().createCriteria(
				User.class);

		userCriteria.add(Restrictions.eq("us_login", user.getUs_login()));
		userCriteria.add(Restrictions.eq("us_password", user.getUs_password()));

		User result = (User) userCriteria.uniqueResult();

		if (result == null)
			result = new User();
		return result;
	}

	@Override
	public boolean checkIfUserIsActiveAndNotBlocked(User user) {
		
		return true;
	}

	@Override
	/**
	 * Returns User with cookie data (login and session token required)
	 */
	public User getUserByCookieData(User user) {

		if (user.getUs_session_token() != null) {
			if (user.getUs_login() != null) {
				Criteria crt = sessionFactory.getCurrentSession().createCriteria(
						User.class);

				crt.add(Restrictions.eq("us_login", user.getUs_login()));
				crt.add(Restrictions.eq("us_session_token",
						user.getUs_session_token()));

				user = (User) crt.uniqueResult();
			}
		}

		return user;
	}
}
