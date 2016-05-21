package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityNotFoundException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificTemperatureMeasurementService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.TemperatureMeasurement;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;
import net.brewspberry.util.HibernateUtil;
import net.brewspberry.util.LogManager;

public class TemperatureMeasurementDaoImpl implements
		ISpecificTemperatureMeasurementService,
		IGenericDao<TemperatureMeasurement> {

	Logger logger = LogManager.getInstance(TemperatureMeasurementDaoImpl.class
			.getName());
	Session session = HibernateUtil.getSession();
	StatelessSession statelessSession = HibernateUtil.getStatelessSession();

	@SuppressWarnings("unchecked")
	@Override
	public List<TemperatureMeasurement> getTemperatureMeasurementByBrassin(
			Brassin brassin) {

		List<TemperatureMeasurement> result = session
				.createCriteria(TemperatureMeasurement.class)
				.add(Restrictions.eq("tmes_brassin", brassin)).list();

		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public List<TemperatureMeasurement> getTemperatureMeasurementByEtape(
			Etape etape) {
		@SuppressWarnings("unchecked")
		List<TemperatureMeasurement> result = (List<TemperatureMeasurement>) session
				.createCriteria(TemperatureMeasurement.class)
				.add(Restrictions.eq("tmes_etape", etape)).list();
		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public TemperatureMeasurement getLastTemperatureMeasurementByUUID(
			String uuid) throws Exception {

		TemperatureMeasurement result = new TemperatureMeasurement();

		if (uuid != null) {

			if (uuid != "") {
				Criteria cr = session
						.createCriteria(TemperatureMeasurement.class);

				cr.add(Restrictions.eq("tmes_probeUI", uuid));
				cr.addOrder(Order.desc("tmes_date"));

				result = ((List<TemperatureMeasurement>) cr.list()).get(0);

			}
		} else {
			throw new Exception("Empty string is not permitted !!");
		}
		if (result == null || result.equals(new TemperatureMeasurement())) {
			throw new EntityNotFoundException(
					"Temperature measurement not found for uuid " + uuid);
		}

		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public TemperatureMeasurement getLastTemperatureMeasurementByName(
			String name) throws Exception {

		String sqlQuery = "FROM TemperatureMeasurement WHERE tmes_probe_name = '"
				+ name + "' ORDER BY tmes_date DESC";

		TemperatureMeasurement result = new TemperatureMeasurement();

		if (name != null) {

			if (name != "") {

				/*
				 * Beware, if multiple brews at the same time, there may be
				 * conflict :
				 * 
				 * should add as parameter and criterias Brassin and Etape
				 * objects to better filter
				 * 
				 * but it's ok if you do only 1 brew or step at a time
				 */

				Query request = session.createQuery(sqlQuery).setMaxResults(1);

				result = (TemperatureMeasurement) request.uniqueResult();

			} else {
				throw new Exception("Empty string is not permitted !!");
			}

		}

		if (result == null || result.equals(new TemperatureMeasurement())) {
			throw new EntityNotFoundException(
					"Temperature measurement not found for name " + name);
		}

		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public List<TemperatureMeasurement> getAllLastTemperatureMeasurements(
			List<String> uuidOrName, Boolean uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TemperatureMeasurement> getAllLastTemperatureMeasurementsFromCSV(
			List<String> uuidOrName, Boolean uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemperatureMeasurement getLastTemperatureMeasurementsByNameFromCSV(
			String uuidOrName, Boolean uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemperatureMeasurement save(TemperatureMeasurement arg0)
			throws DAOException {
		Transaction tx = (Transaction) session.beginTransaction();
		TemperatureMeasurement result = null;
		try {

			long id = (long) session.save(arg0);
			tx.commit();

			result = this.getElementById(id);

			logger.fine("Saved TemperatureMeasurement with id " + id);

		} catch (HibernateException e) {

			tx.rollback();
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();

		}
		return result;
	}

	@Override
	public TemperatureMeasurement update(TemperatureMeasurement arg0) {
		Transaction tx = session.beginTransaction();
		TemperatureMeasurement result = null;

		try {
			session.update(arg0);
			tx.commit();
		} catch (HibernateException e) {

			tx.rollback();
		} finally {

			HibernateUtil.closeSession();

		}

		return result;
	}

	@Override
	public TemperatureMeasurement getElementById(long id) {

		return (TemperatureMeasurement) session.get(
				TemperatureMeasurement.class, id);
	}

	@Override
	public TemperatureMeasurement getElementByName(String name) {

		return null;
	}

	@Override
	public List<TemperatureMeasurement> getAllElements() {

		List<TemperatureMeasurement> result = null;

		result = session.createQuery("from TemperatureMeasurement").list();
		return result;
	}

	@Override
	public void deleteElement(long id) {

		Transaction tx = session.beginTransaction();

		try {

			TemperatureMeasurement toDel = this.getElementById(id);
			if (toDel != null) {

				session.delete(toDel);
				tx.commit();
			} else {
				tx.rollback();
			}

		} catch (HibernateException e) {
			tx.rollback();

		} finally {
			HibernateUtil.closeSession();
		}

	}

	@Override
	public void deleteElement(TemperatureMeasurement arg0) {

		Transaction tx = session.beginTransaction();

		try {

			if (arg0 != null) {

				session.delete(arg0);
				tx.commit();
			} else {
				tx.rollback();
			}

		} catch (HibernateException e) {
			tx.rollback();

		} finally {
			HibernateUtil.closeSession();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TemperatureMeasurement> getAllDistinctElements() {

		List<TemperatureMeasurement> result = new ArrayList<TemperatureMeasurement>();
		result = session.createQuery(
				"from TemperatureMeasurement group by tmes_probe_name").list();

		HibernateUtil.closeSession();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TemperatureMeasurement> getTemperatureMeasurementsAfterID(
			Etape etape, String uuid, long tmesID, int modulo) {

		List<TemperatureMeasurement> result = new ArrayList<TemperatureMeasurement>();
		logger.fine("Restrictions : " + etape.getEtp_id() + " uuid : " + uuid
				+ " ID : " + tmesID);
		Criteria query;

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MINUTE,
				Integer.parseInt(ConfigLoader.getConfigByKey(
						Constants.CONFIG_PROPERTIES,
						"param.chart.timeLengthInMinutes")));
		if (etape != null) {
			if (tmesID > 0) {

				query = session
						.createCriteria(TemperatureMeasurement.class)
						.add(Restrictions.gt("tmes_id", tmesID))
						.add(Restrictions.eq("tmes_etape", etape))
						.add(Restrictions.gt("tmes_date", cal.getTime()))
						.add(Restrictions.sqlRestriction("tmes_id mod "
								+ modulo + " = 0"));

				if (uuid != null && !uuid.equals("all")) {

					query.add(Restrictions.eq("tmes_probeUI", uuid));

				}

				query.addOrder(Order.asc("tmes_date"));
				result = (List<TemperatureMeasurement>) query.list();
			}
		}

		logger.info("Fetched " + result.size() + " new results after ID "
				+ tmesID);

		return result;
	}

	@Override
	public List<TemperatureMeasurement> getLastTemperatureByStepAndUUID(
			Etape stepID, String uuid) {

		List<TemperatureMeasurement> result = new ArrayList<TemperatureMeasurement>();

		Session session = HibernateUtil.getSession();

		Criteria crit = session.createCriteria(TemperatureMeasurement.class);
		crit.add(Restrictions.eq("tmes_etape", stepID));
		crit.add(Restrictions.eq("tmes_probeUI", uuid));
		crit.addOrder(Order.desc("tmes_date"));
		
		if (!uuid.equals("") || !uuid.equalsIgnoreCase("all")){
			
			crit.setMaxResults(1);
		
		}
		
		return result;
	}
	
	

}