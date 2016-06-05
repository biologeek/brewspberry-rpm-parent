package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import net.brewspberry.business.beans.ConcreteTemperatureMeasurement;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;
import net.brewspberry.util.DateManipulator;
import net.brewspberry.util.HibernateUtil;
import net.brewspberry.util.LogManager;

public class TemperatureMeasurementDaoImpl implements
		ISpecificTemperatureMeasurementService,
		IGenericDao<ConcreteTemperatureMeasurement> {

	Logger logger = LogManager.getInstance(TemperatureMeasurementDaoImpl.class
			.getName());
	Session session = HibernateUtil.getSession();
	StatelessSession statelessSession = HibernateUtil.getStatelessSession();

	@SuppressWarnings("unchecked")
	@Override
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementByBrassin(
			Brassin brassin) {

		List<ConcreteTemperatureMeasurement> result = session
				.createCriteria(ConcreteTemperatureMeasurement.class)
				.add(Restrictions.eq("tmes_brassin", brassin)).list();

		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementByEtape(
			Etape etape) {
		@SuppressWarnings("unchecked")
		List<ConcreteTemperatureMeasurement> result = (List<ConcreteTemperatureMeasurement>) session
				.createCriteria(ConcreteTemperatureMeasurement.class)
				.add(Restrictions.eq("tmes_etape", etape)).list();
		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementByUUID(
			String uuid) throws Exception {

		ConcreteTemperatureMeasurement result = new ConcreteTemperatureMeasurement();

		if (uuid != null) {

			if (uuid != "") {
				Criteria cr = session
						.createCriteria(ConcreteTemperatureMeasurement.class);

				cr.add(Restrictions.eq("tmes_probeUI", uuid));
				cr.addOrder(Order.desc("tmes_date"));

				result = ((List<ConcreteTemperatureMeasurement>) cr.list()).get(0);

			}
		} else {
			throw new Exception("Empty string is not permitted !!");
		}
		if (result == null || result.equals(new ConcreteTemperatureMeasurement())) {
			throw new EntityNotFoundException(
					"Temperature measurement not found for uuid " + uuid);
		}

		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementByName(
			String name) throws Exception {

		String sqlQuery = "FROM TemperatureMeasurement WHERE tmes_probe_name = '"
				+ name + "' ORDER BY tmes_date DESC";

		ConcreteTemperatureMeasurement result = new ConcreteTemperatureMeasurement();

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

				result = (ConcreteTemperatureMeasurement) request.uniqueResult();

			} else {
				throw new Exception("Empty string is not permitted !!");
			}

		}

		if (result == null || result.equals(new ConcreteTemperatureMeasurement())) {
			throw new EntityNotFoundException(
					"Temperature measurement not found for name " + name);
		}

		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getAllLastTemperatureMeasurements(
			List<String> uuidOrName, Boolean uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getAllLastTemperatureMeasurementsFromCSV(
			List<String> uuidOrName, Boolean uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementsByNameFromCSV(
			String uuidOrName, Boolean uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConcreteTemperatureMeasurement save(ConcreteTemperatureMeasurement arg0)
			throws DAOException {
		Transaction tx = (Transaction) session.beginTransaction();
		ConcreteTemperatureMeasurement result = null;
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
	public ConcreteTemperatureMeasurement update(ConcreteTemperatureMeasurement arg0) {
		Transaction tx = session.beginTransaction();
		ConcreteTemperatureMeasurement result = null;

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
	public ConcreteTemperatureMeasurement getElementById(long id) {

		return (ConcreteTemperatureMeasurement) session.get(
				ConcreteTemperatureMeasurement.class, id);
	}

	@Override
	public ConcreteTemperatureMeasurement getElementByName(String name) {

		return null;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getAllElements() {

		List<ConcreteTemperatureMeasurement> result = null;

		result = session.createQuery("from TemperatureMeasurement").list();
		return result;
	}

	@Override
	public void deleteElement(long id) {

		Transaction tx = session.beginTransaction();

		try {

			ConcreteTemperatureMeasurement toDel = this.getElementById(id);
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
	public void deleteElement(ConcreteTemperatureMeasurement arg0) {

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
	public List<ConcreteTemperatureMeasurement> getAllDistinctElements() {

		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();
		result = session.createQuery(
				"from TemperatureMeasurement group by tmes_probe_name").list();

		HibernateUtil.closeSession();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementsAfterIDForStepUUIDAndDelay(
			Etape etape, String uuid, int numberOfPoints, long tmesID,
			float modulo) {

		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();
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
						.createCriteria(ConcreteTemperatureMeasurement.class)
						.add(Restrictions.gt("tmes_id", tmesID))
						.add(Restrictions.eq("tmes_etape", etape))
						.add(Restrictions.gt("tmes_date", cal.getTime()));
					//	.add(Restrictions.sqlRestriction("tmes_id mod "
						//		+ modulo + " = 0"));

				if (uuid != null && !uuid.equals("all")) {

					query.add(Restrictions.eq("tmes_probeUI", uuid));

				}

				query.addOrder(Order.asc("tmes_date"));
				result = (List<ConcreteTemperatureMeasurement>) query.list();
			}
		}

		logger.info("Fetched " + result.size() + " new results after ID "
				+ tmesID);

		return result;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getLastTemperatureByStepAndUUID(
			Etape stepID, String uuid) {

		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();

		Session session = HibernateUtil.getSession();

		Criteria crit = session.createCriteria(ConcreteTemperatureMeasurement.class);
		crit.add(Restrictions.eq("tmes_etape", stepID));
		crit.add(Restrictions.eq("tmes_probeUI", uuid));
		crit.addOrder(Order.desc("tmes_date"));

		if (!uuid.equals("") || !uuid.equalsIgnoreCase("all")) {

			crit.setMaxResults(1);

		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConcreteTemperatureMeasurement> getLastTemperatureMeasurementByStepUUIDNumberOfPointsAndDelay(
			Etape etapeID, String uuid, int numberOfPoints, float delay)
			throws Exception {

		Session session = HibernateUtil.getSession();

		Criteria tmesCriteria = session
				.createCriteria(ConcreteTemperatureMeasurement.class);

		tmesCriteria.add(Restrictions.eq("tmes_etape", etapeID));

		if (!uuid.equals("all")) {

			tmesCriteria.add(Restrictions.eq("tmes_probeUI", uuid));

		}

		tmesCriteria.addOrder(Order.desc("tmes_id"));

		tmesCriteria.setMaxResults(numberOfPoints);

		tmesCriteria.add(Restrictions.le(
				"tmes_date",
				DateManipulator.getInstance().getDateFromDateAndDelay(
						new Date(), (float) -delay, "SECONDS")));

		return (List<ConcreteTemperatureMeasurement>) tmesCriteria.list();
	}

}