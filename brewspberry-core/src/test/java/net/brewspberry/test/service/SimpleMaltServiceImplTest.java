package net.brewspberry.test.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.business.beans.User;
import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.business.service.SimpleMaltServiceImpl;
import net.brewspberry.test.AbstractTest;


@Transactional
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
	public void shouldGetById(){
		
		
		
	}
	
}
