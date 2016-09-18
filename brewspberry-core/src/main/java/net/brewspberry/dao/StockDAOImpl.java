package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificStockDao;
import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;
import net.brewspberry.business.exceptions.DAOException;

@Repository
public class StockDAOImpl implements IGenericDao<StockCounter>, ISpecificStockDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<StockCounter> getWholeStockForProduct(Stockable arg0) {

		Session session = sessionFactory.getCurrentSession();

		Criteria crit = session.createCriteria(StockCounter.class);
		crit.add(Restrictions.eqOrIsNull("cpt_product", arg0));
		crit.addOrder(Order.asc("cpt_id"));

		return (List<StockCounter>) crit.list();
	}

	@Override
	public StockCounter save(StockCounter arg0) throws DAOException {

		long id = (long) sessionFactory.getCurrentSession().save(arg0);
		
		return this.getElementById(id);
	}

	@Override
	public StockCounter update(StockCounter arg0) {

		try {
			sessionFactory.getCurrentSession().update(arg0);
			return arg0;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public StockCounter getElementById(long id) {

		return (StockCounter) sessionFactory.getCurrentSession().get(StockCounter.class, id);
	}

	@Override
	public StockCounter getElementByName(String name) {
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockCounter> getAllElements() {

		return (List<StockCounter>) sessionFactory.getCurrentSession().createQuery("from StockCounter").list();
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

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RawMaterialCounter> getStockForPrimaryMaterials() {

		Criteria crit = sessionFactory.getCurrentSession().createCriteria(RawMaterialCounter.class);

		// Getting all Abstract ingedients

		return (List<RawMaterialCounter>) crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinishedProductCounter> getStockForFinishedProducts() {

		Criteria crit = sessionFactory.getCurrentSession().createCriteria(FinishedProductCounter.class);

		// Getting all Abstract ingedients

		return (List<FinishedProductCounter>) crit.list();
	}

	@Override
	public StockCounter getStockCounterByProductAndType(Stockable arg0, CounterType arg1) throws DAOException {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria crit;
		if (arg0 instanceof AbstractFinishedProduct){
			crit = session.createCriteria(FinishedProductCounter.class);
		} else if (arg0 instanceof AbstractIngredient) {
			
			crit = session.createCriteria(RawMaterialCounter.class);
			
		} else {
			throw new DAOException("This type of stockable is not usable !");
		}
		
		
		crit.add(Restrictions.eq("cpt_product", arg0));
		crit.add(Restrictions.eq("cpt_counter_type", arg1));
		
		return (StockCounter) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockCounter> getStockCountersByTypes(List<CounterType> ar0) {

		Criteria crit = sessionFactory.getCurrentSession().createCriteria(StockCounter.class);
		Disjunction or = Restrictions.disjunction();

		for (CounterType cpt : ar0) {

			or.add(Restrictions.eq("cpt_counter_type", cpt));

		}

		crit.add(or);

		return (List<StockCounter>) crit.list();
	}

	@Override
	public List<StockCounter> batchSaveStockCounter(List<StockCounter> listOfStockCounters) {

		int i = 0;
		List<StockCounter> result = new ArrayList<StockCounter>();
		
		
		Session session = sessionFactory.getCurrentSession();

		for (StockCounter object : listOfStockCounters) {

			try {
				long stkID = (long) session.save(object);

				if (i % 50 == 0) { // Same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
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
