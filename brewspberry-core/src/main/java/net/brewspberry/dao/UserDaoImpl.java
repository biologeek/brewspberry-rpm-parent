package net.brewspberry.dao;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificUserDao;
import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.User;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

@Repository
public class UserDaoImpl implements IGenericDao<User>, ISpecificUserDao {

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
	public User returnUserByCredentials(User user) {

		Criteria userCriteria = HibernateUtil.getSession().createCriteria(User.class);

		userCriteria.add(Restrictions.eq("us_login", user.getUs_login()));
		userCriteria.add(Restrictions.eq("us_password", user.getUs_password()));

		User result = (User) userCriteria.uniqueResult();

		return result;
	}

	@Override
	public boolean checkIfUserIsActiveAndNotBlocked(User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	/**
	 * Returns User with cookie data (login and session token required)
	 */
	public User getUserByCookieData(User user) {
		
		if (user.getUs_session_token() != null){
			if (user.getUs_login() != null){
				Criteria crt = HibernateUtil.getSession().createCriteria(User.class);
				

				crt.add(Restrictions.eq("us_login", user.getUs_login()));
				crt.add(Restrictions.eq("us_session_token", user.getUs_session_token()));
				
				user = (User) crt.uniqueResult();
			}
		}
		
		return user;
	}
}
