package net.brewspberry.main.dao;

import java.util.ArrayList;
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
import net.brewspberry.main.business.beans.brewing.Biere;
import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.BrewStatus;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.data.ISpecificBrassinDAO;
import net.brewspberry.main.util.LogManager;

@Repository
public class BrassinDaoImpl implements IGenericDao<Brassin>, ISpecificBrassinDAO {

	static final Logger logger = LogManager.getInstance(BrassinDaoImpl.class.getName());

	@Autowired
	EntityManager em;

	@Override
	public Brassin save(Brassin arg0) throws DAOException {
		em.persist(arg0);
		return arg0;
	}

	@Override
	public Brassin update(Brassin arg0) {
		em.merge(arg0);
		return arg0;
	}

	@Override
	public Brassin getElementById(long id) {
		return (Brassin) em.find(Brassin.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Brassin> getAllElements() {

		List<Brassin> result = null;

		result = (List<Brassin>) em.createQuery("from Brassin").getResultList();

		return result;
	}

	@Override
	public void deleteElement(long id) {

		Brassin brassin = this.getElementById(id);

		try {
			em.remove(brassin);

		} catch (HibernateException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void deleteElement(Brassin arg0) {

		try {
			em.remove(arg0);
		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Brassin> getAllDistinctElements() {

		List<Brassin> result = new ArrayList<Brassin>();
		result = em.createQuery("from Brassin group by ing_desc").getResultList();

		return result;
	}

	@Override
	public Brassin getBrassinByBeer(Biere beer) {
		Brassin result = (Brassin) em.createQuery("from Brassin where bra_beer.beer_id = "+beer).getSingleResult();
		return result;
	}

	@Override
	public Brassin getElementByName(String name) {
		Brassin result = (Brassin) em.createQuery("from Brassin where bra_nom = "+name).getSingleResult();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Returns brews depending on statuses given
	 * 
	 * @param statuses
	 * @return
	 */
	public List<Brassin> getBrewByStates(List<BrewStatus> statuses) {
		Query crit = em.createQuery("from Brassin where bra_statut in ("+ statuses.toArray()+")");

		return crit.getResultList();
	}

}