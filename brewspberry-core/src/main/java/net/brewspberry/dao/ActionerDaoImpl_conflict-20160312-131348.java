package net.brewspberry.dao;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificActionerDao;
import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.TemperatureMeasurement;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;
import net.brewspberry.util.LogManager;

public class ActionerDaoImpl implements IGenericDao<Actioner>,
		ISpecificActionerDao {

	static final Logger logger = LogManager.getInstance(ActionerDaoImpl.class
			.toString());

	Session session = HibernateUtil.getSession();
	StatelessSession statelessSession = HibernateUtil.getStatelessSession();

	@Override
	public Actioner save(Actioner arg0) throws DAOException {
		Transaction tx = session.beginTransaction();
		Long actID;

		try {
			logger.fine("Saving Actioner with uuid " + arg0.getAct_uuid());
			actID = (Long) session.save(arg0);

			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			throw new DAOException();
		} finally {
			HibernateUtil.closeSession();
		}
		logger.info("save returned id : " + actID + " " + arg0.getAct_id()+" with uuid " + arg0.getAct_uuid());
		return arg0;
	}

	@Override
	public Actioner update(Actioner arg0) {
		Transaction tx = session.beginTransaction();
		
		try {
			
			session.update (arg0);
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

		result = (Actioner) session.get(Actioner.class, id);
		HibernateUtil.closeSession();

		return result;
	}

	@Override
	public Actioner getElementByName(String name) {
		
		Actioner result = new Actioner();
		session = HibernateUtil.getSession();
		result = (Actioner) session.createQuery("from Actioner where act_nom = "+name).uniqueResult();
		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public List<Actioner> getAllElements() {
		

		return (List<Actioner>) session.createQuery("from Actioner");
	}

	@Override
	public void deleteElement(long id) {
		
		Transaction tx = session.beginTransaction();
		Actioner toDel = new Actioner();
		
		try {
			
			toDel = this.getElementById(id);
			
			session.delete(toDel);
			tx.commit();
			
		} catch (HibernateException e){
			
			tx.rollback();
			e.printStackTrace();
		}

	}

	@Override
	public void deleteElement(Actioner arg0) {
		Transaction tx = session.beginTransaction();
		
		
		try {
			
			session.delete(arg0);
			tx.commit();
			
		} catch (HibernateException e){
			
			tx.rollback();
			e.printStackTrace();
		}

		

	}

	@Override
	public List<Actioner> getAllDistinctElements() {

		return (List<Actioner>) session.createQuery("from Actioner");
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Retrieves actioner by : 
	 *  - Brew ID
	 *  - Step ID
	 *  - Type
	 *  -  UUID
	 */
	public Actioner getActionerByFullCharacteristics(Actioner actioner) {
		Actioner result;
		List<Actioner> listResult;

		
		
		String hqlReq = "from Actioner WHERE act_brassin = "
				 			+ actioner.getAct_brassin().getBra_id() + " AND act_etape = "
				 			+ actioner.getAct_etape().getEtp_id() + " AND act_type = '"
				 			+ actioner.getAct_type() + "' AND act_uuid = '"
				 			+ actioner.getAct_uuid()+"' order by act_id DESC";		

		listResult = ((List<Actioner>) session.createQuery(hqlReq).list());

		if (listResult.size() > 0){
			
			result = listResult.get(0);
		} else {
			result = null;
		}
		
		HibernateUtil.closeSession();

		return result;
	}

	@Override
	public List<Actioner> getActionerByBrassin(Brassin brassin) {
		return session.createCriteria(TemperatureMeasurement.class).add(Restrictions.eq("act_brassin", brassin)).list();
	}

	@Override
	public List<Actioner> getActionnerByEtape(Etape etape) {
		return session.createCriteria(TemperatureMeasurement.class).add(Restrictions.eq("act_etape", etape)).list();
	}

}