package net.brewspberry.tests.ws.dto;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.tests.utils.TestUtils;

public class BrewDTOTest {
	
	
	
	
	Brassin brew = TestUtils.SIMPLE_BREW;
	@Autowired
	private IGenericService<Brassin> brewService;
	
	@Test
	public void shouldBeValidBrewWithOneStep(){
		
		
		Brassin brew = TestUtils.SIMPLE_BREW;
		
		brew.getBra_etapes().add(TestUtils.SIMPLE_STEP1);
		
		Assert.assertEquals(brew.getBra_etapes().size(), 1);
		
	}
	
	public void shouldBeAutowired (){
		
		Assert.assertNotNull(brewService);
	}
	

}
