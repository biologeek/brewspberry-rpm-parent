package net.brewspberry.main.dao;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.monitoring.Actioner;
import net.brewspberry.main.business.beans.monitoring.ConcreteTemperatureMeasurement;
import net.brewspberry.main.business.beans.monitoring.GenericActionner;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.data.ISpecificActionerDao;
import net.brewspberry.main.util.HibernateUtil;
import net.brewspberry.main.util.LogManager;

@Repository
@javax.transaction.Transactional
public class ActionerDaoImpl implements IGenericDao<Actioner>, ISpecificActionerDao {

	static final Logger logger = LogManager.getInstance(ActionerDaoImpl.class.toString());

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Actioner save(Actioner arg0) throws DAOException {
		Long actID;

		try {
			logger.info("Saving Actioner whith uuid " + arg0.getAct_generic().getGact_uuid());
			actID = (Long) sessionFactory.getCurrentSession().save(arg0);

			if (arg0.getAct_id() == 0 && actID != 0) {
				arg0.setAct_id(actID);
			} else {

				logger.severe("Oh, I think we got a problem here : act_id = " + actID);
			}
		} catch (HibernateException e) {
			throw new DAOException();
		}
		logger.info("save returned id : " + actID + " " + arg0.getAct_id());
		return arg0;
	}

	@Override
	public Actioner update(Actioner arg0) {
		sessionFactory.getCurrentSession().update(arg0);
		return arg0;
	}

	@Override
	public Actioner getElementById(long id) {
		Actioner result = new Actioner();

		result = (Actioner) sessionFactory.getCurrentSession().get(Actioner.class, id);

		return result;
	}

	@Override
	public Actioner getElementByName(String name) {

		Actioner result = new Actioner();

		result = (Actioner) sessionFactory.getCurrentSession().createQuery("from Actioner where act_nom = " + name)
				.uniqueResult();
		return result;
	}

	@Override
	public List<Actioner> getAllElements() {

		return (List<Actioner>) sessionFactory.getCurrentSession().createQuery("from Actioner").list();
	}

	@Override
	public void deleteElement(long id) {
		Actioner toDel = new Actioner();

		toDel = this.getElementById(id);

		sessionFactory.getCurrentSession().delete(toDel);
	}

	@Override
	public void deleteElement(Actioner arg0) {
		sessionFactory.getCurrentSession().delete(arg0);

	}

	@Override
	/**
	 * Returns distinct elements from datasource. Entities are evicted so they
	 * can be later modified
	 * 
	 * @return
	 */
	public List<Actioner> getAllDistinctElements() {
		@SuppressWarnings("unchecked")
		List<Actioner> result = sessionFactory.getCurrentSession().createQuery("select distinct a from Actioner a")
				.list();

		for (Actioner elt : result) {
			sessionFactory.getCurrentSession().evict(elt);
		}

		return result;
	}

	@Override

	/**
	 * Returns actioner corresponding using {brew, step, type of generic
	 * actionner, UUID of generic actionner}
	 * 
	 * @param actioner
	 * @return the actionner corresponding to criteria
	 */
	public Actioner getActionerByFullCharacteristics(Actioner actioner) {

		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Actioner.class)
				.add(Restrictions.eq("act_brassin", actioner.getAct_brassin()))
				.add(Restrictions.eq("act_etape", actioner.getAct_etape()))
				.add(Restrictions.eq("act_generic", actioner.getAct_generic()));

		Actioner result = (Actioner) crit.uniqueResult();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Actioner> getActionerByBrassin(Brassin brassin) {
		return sessionFactory.getCurrentSession().createCriteria(ConcreteTemperatureMeasurement.class)
				.add(Restrictions.eq("act_brassin", brassin)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Actioner> getActionnerByEtape(Etape etape) {
		return sessionFactory.getCurrentSession().createCriteria(ConcreteTemperatureMeasurement.class)
				.add(Restrictions.eq("act_etape", etape)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GenericActionner> getAllGenericActionners() {

		Criteria list = sessionFactory.getCurrentSession().createCriteria(GenericActionner.class);

		return list.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Actioner> getActiveActionners() {
		return sessionFactory.getCurrentSession()//
				.createQuery("from Actioner where act_generic.gact_activated = true").list();
	}
}