package net.brewspberry.test.service;

import java.util.Calendar;
import java.util.Date;

import net.brewspberry.business.ISpecificEtapeService;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfiguration.class)
public class EtapeServiceImplTest {

	
	@Autowired
	ISpecificEtapeService specEtapeService;
	
	Etape etapeStart;
	Etape etapeStop;
	
	
	@Before
	public void init(){
		
		Calendar calCrea = Calendar.getInstance();
		calCrea.add(Calendar.HOUR, -1);

		
		Calendar calBegin = Calendar.getInstance();
		calBegin.add(Calendar.MINUTE, -10);

		
		Calendar calEnd= Calendar.getInstance();
		calEnd.add(Calendar.MINUTE, -10);

		etapeStart = new Etape();
		
		
		etapeStart.setEtp_creation_date(calCrea.getTime());
		etapeStart.setEtp_debut(calBegin.getTime());
		etapeStart.setEtp_fin(calEnd.getTime());
		
		

		etapeStop= new Etape();
		
		
		etapeStop.setEtp_creation_date(calCrea.getTime());
		etapeStop.setEtp_debut(calBegin.getTime());
		etapeStop.setEtp_debut_reel(calBegin.getTime());
		etapeStop.setEtp_fin(calEnd.getTime());
		
		
	}

	@Test
	public void shouldStopStepForReal(){
		
		
		Date date = new Date();
		Etape result = specEtapeService.stopStepForReal(etapeStop);
		
		
		Assert.assertEquals(date, result.getEtp_fin_reel());
		
	}
	

	@Test
	public void shouldStartStepForReal(){
		
	}
	
	
}
