package net.brewspberry.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificStockDao;
import net.brewspberry.business.beans.stock.CompteurType;
import net.brewspberry.business.beans.stock.StockCounter;
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
		
		
		List<CompteurType> list = new ArrayList<CompteurType>();
		
		CompteurType typ1 = new CompteurType();
		typ1.setCty_id(4);
		
		list.add(typ1);
		List<StockCounter> result = specDao.getStockCountersByTypes(list);
		
		Assert.assertEquals(1, result.size());
		
		
		CompteurType typ2 = new CompteurType();
		
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
		
		
		List<StockCounter> res = specDao.getStockForPrimaryMaterials();
		
		Assert.assertEquals(1, res.size());		
		
	}
}
