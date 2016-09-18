package net.brewspberry.tests.ws;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import net.brewspberry.business.ISpecificStockService;

public class RESTStockServiceImplTest {

	@Autowired
	ISpecificStockService stockSpecService;
	
	
	@Before
	public void beforeTest(){
		
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	
	@Test
	public void testAutowiring (){
		
		Assert.assertNotNull(stockSpecService);
		
	}
	
	
	public void shouldModifyStockCounter(){
		
		
		
	}
}
