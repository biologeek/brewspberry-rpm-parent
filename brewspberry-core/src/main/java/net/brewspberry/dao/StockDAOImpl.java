package net.brewspberry.dao;

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
import net.brewspberry.business.beans.stock.CompteurType;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;
import net.brewspberry.exceptions.DAOException;


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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockCounter update(StockCounter arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockCounter getElementById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockCounter getElementByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockCounter> getAllElements() {
		
		return sessionFactory.getCurrentSession().createQuery("from StockCounter").list();
	}

	@Override
	public void deleteElement(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteElement(StockCounter arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<StockCounter> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return null;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<StockCounter> getStockForPrimaryMaterials() {
		
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(StockCounter.class);
		
		// Getting all Abstract ingedients
		crit.add(Restrictions.eq("cpt_product.class", AbstractIngredient.class));
				
		return (List<StockCounter>) crit.list();
	}

	@Override
	public List<StockCounter> getStockForFinishedProducts() {

		Criteria crit = sessionFactory.getCurrentSession().createCriteria(StockCounter.class);
		
		// Getting all Abstract ingedients
		crit.add(Restrictions.eq("cpt_produit.class", AbstractFinishedProduct.class));
				
		return (List<StockCounter>) crit.list();
	}

	@Override
	public StockCounter getStockCounterByProductAndType(Stockable arg0, CompteurType arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockCounter> getStockCountersByTypes(List<CompteurType> ar0) {
		
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(StockCounter.class);
		Disjunction or = Restrictions.disjunction();
		
		for(CompteurType cpt : ar0){
			
			or.add(Restrictions.eq("cpt_counter_type", cpt));
			
		}
		
		crit.add(or);
		
		
		
		return (List<StockCounter>) crit.list();
	}


}
