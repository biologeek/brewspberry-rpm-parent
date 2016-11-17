package net.brewspberry.tests.ws.dto;

import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Assert;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.EtapeType;
import net.brewspberry.business.beans.PalierType;
import net.brewspberry.front.ws.beans.dto.BrassinDTO;
import net.brewspberry.front.ws.beans.requests.BrewRequest;
import net.brewspberry.front.ws.beans.responses.ComplexBrewResponse;
import net.brewspberry.front.ws.beans.responses.SimpleBrewResponse;
import net.brewspberry.tests.config.ApiSpringTestConfiguration;
import net.brewspberry.tests.utils.TestUtils;



@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={ApiSpringTestConfiguration.class})
@WebAppConfiguration
public class BrewDTOTest {
	
	
	
	
	Brassin brew = TestUtils.SIMPLE_BREW;
	@Autowired
	private IGenericService<Brassin> brewService;
	
	@Test
	public void shouldBeValidBrewWithOneStep(){
		
		
		brew.setBra_etapes(new HashSet<Etape>());
		brew.getBra_etapes().add(TestUtils.SIMPLE_STEP1);
		
		Assert.assertEquals(brew.getBra_etapes().size(), 1);
		
	}
	
	public void shouldBeAutowired (){
		
		Assert.assertNotNull(brewService);
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
