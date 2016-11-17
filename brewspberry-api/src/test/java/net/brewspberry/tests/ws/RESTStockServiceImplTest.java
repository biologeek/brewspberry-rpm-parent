package net.brewspberry.tests.ws;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;
import net.brewspberry.tests.config.ApiSpringTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={ApiSpringTestConfiguration.class})
@WebAppConfiguration
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
