package net.brewspberry.test.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificIngredientService;
import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.business.beans.User;
import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.business.service.SimpleMaltServiceImpl;
import net.brewspberry.test.AbstractTest;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;


@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfiguration.class)
public class SimpleMaltServiceImplTest extends AbstractTest {

	@Autowired
	@Qualifier("simpleMaltServiceImpl")
	IGenericService<SimpleMalt> sMalService;
	
	SimpleMalt sm = new SimpleMalt();
	
	
	@Before
	public void init(){

		sm.setIng_desc("test");
		sm.setIng_fournisseur("Weyermann");
		sm.setIng_unitary_price(30);
		sm.setIng_unitary_price_unit(StockUnit.SAC_25_KG);
		sm.setSmal_cereale("orge");
		sm.setIng_unitary_weight(25);
		sm.setIng_unitary_weight_unit(StockUnit.KILO);
		
	}
	
	@Test
	public void shouldSaveSimpleMalt(){
		
		
		SimpleMalt result = null;
		try {
			
			
			result = sMalService.save(sm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertNotNull(result);
		
	}
	
	
	@Test
	public void shouldGetById() throws ServiceException{
		
		
		SimpleMalt res = sMalService.getElementById(1);
		
		Assert.assertNotNull(res);
		
		
		//Testing EAGER fetch over Ingredient
		Assert.assertEquals("Weyermann", res.getIng_fournisseur());
		
	}
	
	
	
	
}
