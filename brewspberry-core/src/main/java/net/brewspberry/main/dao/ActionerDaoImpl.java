package net.brewspberry.main.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.monitoring.Actioner;
import net.brewspberry.main.business.beans.monitoring.ConcreteTemperatureMeasurement;
import net.brewspberry.main.business.beans.monitoring.GenericActionner;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.data.ISpecificActionerDao;
import net.brewspberry.main.util.LogManager;

@Repository
@javax.transaction.Transactional
public class ActionerDaoImpl implements IGenericDao<Actioner>, ISpecificActionerDao {

	static final Logger logger = LogManager.getInstance(ActionerDaoImpl.class.toString());

	@Autowired
	EntityManager em;

	@Override
	public Actioner save(Actioner arg0) throws DAOException {
		Actioner actionner;

		try {
			logger.info("Saving Actioner whith uuid " + arg0.getAct_generic().getGact_uuid());
			actionner = em.merge(arg0);

			if (arg0.getAct_id() == 0 && actionner.getAct_id() != 0) {
				arg0.setAct_id(actionner.getAct_id());
			} else {

				logger.severe("Oh, I think we got a problem here : act_id = " + arg0.getAct_id());
			}
		} catch (HibernateException e) {
			throw new DAOException();
		}
		logger.info("save returned id : " + actionner.getAct_id() + " " + arg0.getAct_id());
		return arg0;
	}

	@Override
	public Actioner update(Actioner arg0) {
		arg0 = em.merge(arg0);
		return arg0;
	}

	@Override
	public Actioner getElementById(long id) {
		Actioner result = new Actioner();

		result = (Actioner) em.find(Actioner.class, id);

		return result;
	}

	@Override
	public Actioner getElementByName(String name) {

		Actioner result = new Actioner();

		result = (Actioner) em.createQuery("from Actioner where act_nom = " + name)
				.getSingleResult();
		return result;
	}

	@Override
	public List<Actioner> getAllElements() {

		return (List<Actioner>) em.createQuery("from Actioner").getResultList();
	}

	@Override
	public void deleteElement(long id) {
		Actioner toDel = new Actioner();

		toDel = this.getElementById(id);

		em.remove(toDel);
	}

	@Override
	public void deleteElement(Actioner arg0) {
		em.remove(arg0);

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
		List<Actioner> result = em.createQuery("select distinct a from Actioner a")
				.getResultList();
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

		Query crit = em.createQuery("from Actioner WHERE act_brassin = "+actioner.getAct_brassin()
				+" and act_etape = "+actioner.getAct_etape()
				+" and act_generic = "+actioner.getAct_generic());

		Actioner result = (Actioner) crit.getSingleResult();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Actioner> getActionerByBrassin(Brassin brassin) {
		return em.createQuery("from ConcreteTemperatureMeasurement where act_brassin = "+ brassin).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Actioner> getActionnerByEtape(Etape etape) {
		return em.createQuery("from ConcreteTemperatureMeasurement where act_etape = "+etape).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GenericActionner> getAllGenericActionners() {

		Query list = em.createQuery("from GenericActionner");

		return list.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Actioner> getActiveActionners() {
		return em//
				.createQuery("from Actioner where act_generic.gact_activated = true").getResultList();
	}
}