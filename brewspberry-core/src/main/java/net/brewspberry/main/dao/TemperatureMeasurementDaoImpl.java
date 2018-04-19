package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.monitoring.Actioner;
import net.brewspberry.main.business.beans.monitoring.ConcreteTemperatureMeasurement;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.data.ISpecificTemperatureMeasurementDao;
import net.brewspberry.main.util.ConfigLoader;
import net.brewspberry.main.util.Constants;
import net.brewspberry.main.util.DateManipulator;
import net.brewspberry.main.util.LogManager;

@Repository
public class TemperatureMeasurementDaoImpl
		implements ISpecificTemperatureMeasurementDao, IGenericDao<ConcreteTemperatureMeasurement> {

	Logger logger = LogManager.getInstance(TemperatureMeasurementDaoImpl.class.getName());

	@Autowired
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementByBrassin(Brassin brassin) {

		List<ConcreteTemperatureMeasurement> result = em
				.createQuery("from ConcreteTemperatureMeasurement where tmes_brassin = " + brassin).getResultList();

		return result;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementByEtape(Etape etape) {
		@SuppressWarnings("unchecked")
		List<ConcreteTemperatureMeasurement> result = (List<ConcreteTemperatureMeasurement>) em
				.createQuery("from ConcreteTemperatureMeasurement where tmes_etape = " + etape.getEtp_id())
				.getResultList();

		return result;
	}

	@Override
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementByUUID(String uuid) throws Exception {

		ConcreteTemperatureMeasurement result = new ConcreteTemperatureMeasurement();

		if (uuid != null) {

			if (uuid != "") {
				Query cr = em.createQuery(
						"from ConcreteTemperatureMeasurement where tmes_probeUI = " + uuid + " order by tmes_date");

				result = ((List<ConcreteTemperatureMeasurement>) cr.getResultList()).get(0);

			}
		} else {
			throw new Exception("Empty string is not permitted !!");
		}
		if (result == null || result.equals(new ConcreteTemperatureMeasurement())) {
			throw new EntityNotFoundException("Temperature measurement not found for uuid " + uuid);
		}

		return result;
	}

	@Override
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementByName(String name) throws Exception {

		String sqlQuery = "FROM TemperatureMeasurement WHERE tmes_probe_name = '" + name + "' ORDER BY tmes_date DESC";

		ConcreteTemperatureMeasurement result = new ConcreteTemperatureMeasurement();

		if (name != null) {

			if (name != "") {

				/*
				 * Beware, if multiple brews at the same time, there may be conflict :
				 * 
				 * should add as parameter and criterias Brassin and Etape objects to better
				 * filter
				 * 
				 * but it's ok if you do only 1 brew or step at a time
				 */

				Query request = em.createQuery(sqlQuery).setMaxResults(1);

				result = (ConcreteTemperatureMeasurement) request.getSingleResult();

			} else {
				throw new Exception("Empty string is not permitted !!");
			}

		}

		if (result == null || result.equals(new ConcreteTemperatureMeasurement())) {
			throw new EntityNotFoundException("Temperature measurement not found for name " + name);
		}

		return result;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getAllLastTemperatureMeasurements(List<String> uuidOrName, Boolean uuid)
			throws Exception {

		return null;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getAllLastTemperatureMeasurementsFromCSV(List<String> uuidOrName,
			Boolean uuid) throws Exception {

		return null;
	}

	@Override
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementsByNameFromCSV(String uuidOrName, Boolean uuid)
			throws Exception {

		return null;
	}

	@Override
	public ConcreteTemperatureMeasurement save(ConcreteTemperatureMeasurement arg0) throws DAOException {
		ConcreteTemperatureMeasurement result = null;
		try {

			em.persist(arg0);
			logger.fine("Saved TemperatureMeasurement with id " + arg0.getTmes_id());

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {

		}
		return result;
	}

	@Override
	public ConcreteTemperatureMeasurement update(ConcreteTemperatureMeasurement arg0) {

		ConcreteTemperatureMeasurement result = null;

		try {
			em.persist(arg0);

		} catch (HibernateException e) {

		} finally {

		}

		return result;
	}

	@Override
	public ConcreteTemperatureMeasurement getElementById(long id) {

		return (ConcreteTemperatureMeasurement) em.find(ConcreteTemperatureMeasurement.class, id);
	}

	@Override
	public ConcreteTemperatureMeasurement getElementByName(String name) {

		return null;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getAllElements() {

		List<ConcreteTemperatureMeasurement> result = null;

		result = em.createQuery("from TemperatureMeasurement").getResultList();
		return result;
	}

	@Override
	public void deleteElement(long id) {

		try {

			ConcreteTemperatureMeasurement toDel = this.getElementById(id);
			if (toDel != null) {

				em.remove(toDel);

			} else {

			}

		} catch (HibernateException e) {

		} finally {

		}

	}

	@Override
	public void deleteElement(ConcreteTemperatureMeasurement arg0) {

		try {

			if (arg0 != null) {

				em.remove(arg0);

			} else {

			}

		} catch (HibernateException e) {

		} finally {

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConcreteTemperatureMeasurement> getAllDistinctElements() {

		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();
		result = em.createQuery("from TemperatureMeasurement group by tmes_probe_name").getResultList();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementsAfterIDForStepUUIDAndDelay(Etape etape,
			String uuid, int numberOfPoints, long tmesID, float modulo) {

		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();
		logger.fine("Restrictions : " + etape.getEtp_id() + " uuid : " + uuid + " ID : " + tmesID);
		Query query;

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MINUTE, Integer
				.parseInt(ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "param.chart.timeLengthInMinutes")));
		if (etape != null) {
			if (tmesID > 0) {
				String str = "from ConcreteTemperatureMeasurement where tmes_id > " + tmesID
				+ " tmes_etape = " + etape + " and tmes_date>" + cal.getTime();
				// .add(Restrictions.sqlRestriction("tmes_id mod "
				// + modulo + " = 0"));

				if (uuid != null && !uuid.equals("all")) {

					str += " and tmes_probeUI"+ uuid;

				}

				str += "order by tmes_date";
				result = (List<ConcreteTemperatureMeasurement>) em.createQuery(str).getResultList();
			}
		}

		logger.info("Fetched " + result.size() + " new results after ID " + tmesID);

		return result;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getLastTemperatureByStepAndUUID(Etape stepID, String uuid) {

		String str = "from ConcreteTemperatureMeasurement where tmes_etape = " + stepID;

		if (uuid != null && "all".equalsIgnoreCase(uuid))
			str += "and tmes_probeUI = " + uuid;

		str += " order by tmes_date";
		
		return em.createQuery(str).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConcreteTemperatureMeasurement> getLastTemperatureMeasurementByStepUUIDNumberOfPointsAndDelay(
			Etape etapeID, String uuid, int numberOfPoints, float delay) throws Exception {
		String query = "from ConcreteTemperatureMeasurement where tmes_etape.etp_id = " + etapeID;

		if (!uuid.equals("all")) {

			query += "tmes_probeUI = " + uuid;

		}

		query += "order by tmes_id desc";

		query += " limit " + numberOfPoints;

		query += "and tmes_date = "
				+ DateManipulator.getInstance().getDateFromDateAndDelay(new Date(), (float) -delay, "SECONDS");
		Query tmesQuery = em.createQuery(query);
		return (List<ConcreteTemperatureMeasurement>) tmesQuery.getResultList();
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getTemperaturesByStepAndUUID(Etape stepID, String uuid, Long lastID) {
		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();
		String query = "from ConcreteTemperatureMeasurement where tmes_etape = " + stepID + " and tmes_probeUI= " + uuid;
		
		if (lastID != null && lastID.longValue() > 0) {

			query += " and tmes_id > "+ lastID;

		}

		query += " order by tmes_date desc";
		Query crit = em.createQuery(query);
		result = crit.getResultList();

		return result;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getTemperaturesForActionners(List<Actioner> actionners) {
		Query crit = em.createQuery("from ConcreteTemperatureMeasurement where tmes_actioner in ("+actionners.toArray()+")");
		return crit.getResultList();
	}

}