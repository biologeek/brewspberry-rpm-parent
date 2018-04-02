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

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificIngredientService;
import net.brewspberry.main.business.ISpecificStockService;
import net.brewspberry.main.business.beans.User;
import net.brewspberry.main.business.beans.brewing.SimpleMalt;
import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.business.service.SimpleMaltServiceImpl;
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
