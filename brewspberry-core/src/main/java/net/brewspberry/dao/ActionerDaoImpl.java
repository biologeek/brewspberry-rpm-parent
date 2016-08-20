package net.brewspberry.dao;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificActionerDao;
import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.ConcreteTemperatureMeasurement;
import net.brewspberry.business.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;
import net.brewspberry.util.LogManager;

@Repository
public class ActionerDaoImpl implements IGenericDao<Actioner>,
		ISpecificActionerDao {

	static final Logger logger = LogManager.getInstance(ActionerDaoImpl.class
			.toString());

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Actioner save(Actioner arg0) throws DAOException {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		Long actID;

		try {
			logger.info("Saving Actioner whith uuid " + arg0.getAct_uuid());
			actID = (Long) sessionFactory.getCurrentSession().save(arg0);

			tx.commit();
			if (arg0.getAct_id() == 0 && actID != 0) {
				arg0.setAct_id(actID);
			} else {

				logger.severe("Oh, I think we got a problem here : act_id = "
						+ actID);
			}
		} catch (HibernateException e) {
			tx.rollback();
			throw new DAOException();
		} finally {
			HibernateUtil.closeSession();
		}
		logger.info("save returned id : " + actID + " " + arg0.getAct_id());
		return arg0;
	}

	@Override
	public Actioner update(Actioner arg0) {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		
		try {
			
			sessionFactory.getCurrentSession().update (arg0);
			tx.commit();
			
		} catch (HibernateException e){
			
			e.printStackTrace();
			tx.rollback();
			
		} finally {
			HibernateUtil.closeSession();
		}
		return null;
	}

	@Override
	public Actioner getElementById(long id) {
		Actioner result = new Actioner();

		result = (Actioner) sessionFactory.getCurrentSession().get(Actioner.class, id);
		HibernateUtil.closeSession();

		return result;
	}

	@Override
	public Actioner getElementByName(String name) {
		
		Actioner result = new Actioner();

		result = (Actioner) sessionFactory.getCurrentSession().createQuery("from Actioner where act_nom = "+name).uniqueResult();
		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public List<Actioner> getAllElements() {
		

		return (List<Actioner>) sessionFactory.getCurrentSession().createQuery("from Actioner");
	}

	@Override
	public void deleteElement(long id) {
		
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		Actioner toDel = new Actioner();
		
		try {
			
			toDel = this.getElementById(id);
			
			sessionFactory.getCurrentSession().delete(toDel);
			tx.commit();
			
		} catch (HibernateException e){
			
			tx.rollback();
			e.printStackTrace();
		}

	}

	@Override
	public void deleteElement(Actioner arg0) {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		
		
		try {
			
			sessionFactory.getCurrentSession().delete(arg0);
			tx.commit();
			
		} catch (HibernateException e){
			
			tx.rollback();
			e.printStackTrace();
		}

		

	}

	@Override
	public List<Actioner> getAllDistinctElements() {

		return (List<Actioner>) sessionFactory.getCurrentSession().createQuery("from Actioner");
	}

	@Override
	public Actioner getActionerByFullCharacteristics(Actioner actioner) {

		String hqlReq = "from " + Actioner.class + " WHERE act_bra_id = "
				+ actioner.getAct_brassin().getBra_id() + " AND act_etp_id = "
				+ actioner.getAct_etape().getEtp_id() + " AND act_type = "
				+ actioner.getAct_type() + " AND act_uuid = "
				+ actioner.getAct_uuid();

		Actioner result = (Actioner) sessionFactory.getCurrentSession().createQuery(hqlReq).uniqueResult();

		HibernateUtil.closeSession();

		return result;
	}

	@Override
	public List<Actioner> getActionerByBrassin(Brassin brassin) {
		return sessionFactory.getCurrentSession().createCriteria(ConcreteTemperatureMeasurement.class).add(Restrictions.eq("act_brassin", brassin)).list();
	}

	@Override
	public List<Actioner> getActionnerByEtape(Etape etape) {
		return sessionFactory.getCurrentSession().createCriteria(ConcreteTemperatureMeasurement.class).add(Restrictions.eq("act_etape", etape)).list();
	}

}