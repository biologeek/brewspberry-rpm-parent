package net.brewspberry.tests.ws.dto;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.PalierType;
import net.brewspberry.front.ws.beans.dto.BrassinDTO;
import net.brewspberry.front.ws.beans.responses.ComplexBrewResponse;
import net.brewspberry.front.ws.beans.responses.SimpleBrewResponse;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;
import net.brewspberry.tests.config.ApiSpringTestConfiguration;
import net.brewspberry.tests.utils.TestUtils;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApiSpringTestConfiguration.class, SpringCoreTestConfiguration.class})
@WebAppConfiguration
public class BrewDTOTest {
	
	
	
	
	Brassin brew = TestUtils.SIMPLE_BREW;
	@Autowired
	@Qualifier("brassinServiceImpl")
	private IGenericService<Brassin> brewService;
	@Autowired
	@Qualifier("etapeServiceImpl")
	private IGenericService<Etape> stepService;
	
	@Test
	public void shouldBeValidBrewWithOneStep(){
		
		
		brew.setBra_etapes(new HashSet<Etape>());
		brew.getBra_etapes().add(TestUtils.SIMPLE_STEP1);
		
		Assert.assertEquals(brew.getBra_etapes().size(), 1);
		
	}
	
	@Test
	public void shouldBeAutowired (){
		
		//Assert.assertNotNull(brewService);
		Assert.assertNotNull(stepService);
	}

	public void shouldConvertToComplexDTOObject(){
		
		Brassin brew = TestUtils.SIMPLE_BREW;

		brew.setBra_etapes(new HashSet<Etape>());
		brew.getBra_etapes().add(TestUtils.SIMPLE_STEP1);
		
		
		
		ComplexBrewResponse req  = new ComplexBrewResponse();
		
		
		req = BrassinDTO.getInstance().toComplexBrewResponse(brew);
		
		
		
		Assert.assertNotNull(req.getSteps());
		Assert.assertEquals(req.getSteps().get(0).getStageType(), PalierType.MASH_OUT);
		
	}
	public void shouldConvertToSimpleDTOObject(){
		
		Brassin brew = TestUtils.SIMPLE_BREW;

		brew.setBra_etapes(new HashSet<Etape>());
		brew.getBra_etapes().add(TestUtils.SIMPLE_STEP1);
		
		
		
		SimpleBrewResponse req  = new SimpleBrewResponse();
		
		
		req = BrassinDTO.getInstance().toSimpleBrewResponse(brew);
		
		
		
		Assert.assertNotNull(req.getDescription());
		Assert.assertEquals(req.getId(), 1);
		Assert.assertEquals(req.getStatus(), 10);
		Assert.assertEquals(req.getQuantity(), 50, 0.1);
		
	}
	
	
	
	
	
	

}
