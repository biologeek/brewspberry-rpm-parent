package net.brewspberry.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificStockDao;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;
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

	
	
	
	@Before
	public void initTest (){
		
		
		
	}

	
	@Test
	public void shouldGetCountersByTypes(){
		
		
		List<CounterType> list = new ArrayList<CounterType>();
		
		CounterType typ1 = new CounterType();
		typ1.setCty_id(4);
		
		list.add(typ1);
		List<StockCounter> result = specDao.getStockCountersByTypes(list);
		
		Assert.assertEquals(0, result.size());
		
		
		CounterType typ2 = new CounterType();
		
		typ2.setCty_id(1);
		list.add(typ2);
		
		List<StockCounter> res = specDao.getStockCountersByTypes(list);
		
		Assert.assertEquals(2, res.size());
		
		for (StockCounter s : res){
			if (s.getCpt_id() == 1){

				Assert.assertEquals(20, s.getCpt_value(), 0.1);
				
			}
		}
		
	}
	
	@Test
	public void shouldGetStockForPrimaryMaterials(){
		
		
		List<RawMaterialCounter> res = specDao.getStockForPrimaryMaterials();
		
		Assert.assertEquals(4, res.size());
		
		for (StockCounter s : res){
			if (s.getCpt_id() == 1){

				Assert.assertEquals(20, s.getCpt_value(), 0.1);
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
