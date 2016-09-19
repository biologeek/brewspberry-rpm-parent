package net.brewspberry.test.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Assert;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;
import net.brewspberry.test.util.config.WebappSpringTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={WebappSpringTestConfiguration.class, SpringCoreTestConfiguration.class})
@WebAppConfiguration
public class AccueilControllerTest {

	
	@Autowired
	@Qualifier("brassinServiceImpl")
	IGenericService<Brassin> brassinGenService;
	
	
	@Test
	public void testAutowiring(){
		
		Assert.assertNotNull(brassinGenService);
	}
}
