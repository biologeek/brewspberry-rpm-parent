package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.AbstractFinishedProduct;
import net.brewspberry.main.business.beans.brewing.AbstractIngredient;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.beans.stock.FinishedProductCounter;
import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.StockCounter;
import net.brewspberry.main.business.beans.stock.Stockable;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.data.ISpecificStockDao;

@Repository
public class StockDAOImpl implements IGenericDao<StockCounter>, ISpecificStockDao {

	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<StockCounter> getWholeStockForProduct(Stockable arg0) {
		Query crit = em.createQuery("from StockCounter where cpt_product = "+arg0+" order by cpt_id");
		return (List<StockCounter>) crit.getResultList();
	}

	@Override
	public StockCounter save(StockCounter arg0) throws DAOException {
		em.persist(arg0);

		return arg0;
	}

	@Override
	public StockCounter update(StockCounter arg0) {

		try {
			em.persist(arg0);
			return arg0;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public StockCounter getElementById(long id) {

		return (StockCounter) em.find(StockCounter.class, id);
	}

	@Override
	public StockCounter getElementByName(String name) {

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockCounter> getAllElements() {

		return (List<StockCounter>) em.createQuery("from StockCounter").getResultList();
	}

	@Override
	public void deleteElement(long id) {

	}

	@Override
	public void deleteElement(StockCounter arg0) {

	}

	@Override
	public List<StockCounter> getAllDistinctElements() {

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RawMaterialCounter> getStockForPrimaryMaterials() {

		Query crit = em.createQuery("from RawMaterialCounter");

		// Getting all Abstract ingedients

		return (List<RawMaterialCounter>) crit.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinishedProductCounter> getStockForFinishedProducts() {

		Query crit = em.createQuery("from FinishedProductCounter");

		// Getting all Abstract ingedients

		return (List<FinishedProductCounter>) crit.getResultList();
	}

	@Override
	public StockCounter getStockCounterByProductAndType(Stockable arg0, CounterType arg1) throws DAOException {

		Query crit;
		String query = "";
		if (arg0 instanceof AbstractFinishedProduct) {
			query = "from FinishedProductCounter";
		} else if (arg0 instanceof AbstractIngredient) {

			query = "from RawMaterialCounter";

		} else {
			throw new DAOException("This type of stockable is not usable !");
		}

		query += "where cpt_product = " + arg0.getStb_id();
		query += "and cpt_counter_type = " + arg1;

		crit = em.createQuery(query);
		return (StockCounter) crit.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockCounter> getStockCountersByTypes(List<CounterType> ar0) {

		Query crit = em.createQuery("from StockCounter where cpt_counter_type in (" + ar0.toArray() + ")");

		return (List<StockCounter>) crit.getResultList();
	}

	@Override
	public List<StockCounter> batchSaveStockCounter(List<StockCounter> listOfStockCounters) {

		int i = 0;
		List<StockCounter> result = new ArrayList<StockCounter>();

		for (StockCounter object : listOfStockCounters) {

			try {
				em.persist(object);
				if (i % 50 == 0) { // Same as the JDBC batch size
					// flush a batch of inserts and release memory:
					em.flush();
					em.clear();
				}
				i++;

				result.add(object);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		return null;
	}

}
