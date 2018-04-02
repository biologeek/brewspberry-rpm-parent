package net.brewspberry.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.beans.stock.CounterTypeConstants;
import net.brewspberry.main.business.beans.stock.FinishedProductCounter;
import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.StockCounter;
import net.brewspberry.main.business.beans.stock.Stockable;
import net.brewspberry.main.data.ISpecificStockDao;
import net.brewspberry.test.AbstractTest;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class StockDaoImplTest extends AbstractTest{
	
	@Autowired
	@Qualifier("stockDAOImpl")
	IGenericDao<StockCounter> genericDao;
	@Autowired
	@Qualifier("stockDAOImpl")
	ISpecificStockDao specDao;
	
	
	@Autowired
	@Qualifier("compteurTypeDaoImpl")
	private IGenericDao<CounterType> genericCounterTypeDAO;
	
	private List<CounterType> counterTypeList;

	
	
	
	@Before
	public void initTest (){
		
		counterTypeList = genericCounterTypeDAO.getAllElements();

		
	}

	
	@Test
	public void shouldGetCountersByTypes(){
		
		
		List<CounterType> list = new ArrayList<CounterType>();
		/*
		CounterType typ1 = new CounterType();
		typ1.setCty_id(4);
		*/
		
		
		list.add(CounterTypeConstants.STOCK_DISPO_VENTE.toDBCouter(counterTypeList));
		List<StockCounter> result = specDao.getStockCountersByTypes(list);
		
		Assert.assertEquals(0, result.size());
		
		
		list.add(CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(counterTypeList));
		
		List<StockCounter> res = specDao.getStockCountersByTypes(list);
		
		Assert.assertEquals(2, res.size());
		
		for (StockCounter s : res){
			if (s.getCpt_id() == 1){

				Assert.assertEquals(200000, s.getCpt_value(), 0.1);
				
			}
		}
		
	}
	
	@Test
	public void shouldGetStockForPrimaryMaterials(){
		
		
		List<RawMaterialCounter> res = specDao.getStockForPrimaryMaterials();
		
		Assert.assertEquals(4, res.size());
		
		for (StockCounter s : res){
			if (s.getCpt_id() == 1){

				Assert.assertEquals(200000, s.getCpt_value(), 0.1);
			}
		}
	}
	
	
	@Test
	public void shouldGetStockForFinishedProducts (){
		List<FinishedProductCounter> res = specDao.getStockForFinishedProducts();

		Assert.assertEquals(1, res.size());
		
		
		//Assert.assertEquals(14, res.get(0).getCpt_value(), 0.1);
		
		
	}
	
	
	@Test
	public void shouldGetStockCounterByProductAndType(){
		
		Assert.assertTrue(true);
	}
	
	
	@Test
	public void shouldGetElementById(){
		
		StockCounter res = genericDao.getElementById(1);
		
		Assert.assertNotNull(res);
		
		Assert.assertTrue(res instanceof RawMaterialCounter);
		
		RawMaterialCounter counter = (RawMaterialCounter) res;
		
		Assert.assertNotNull(counter.getCpt_product());
		
		
		
	}
	
}
