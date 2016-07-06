package net.brewspberry.batches.test;

import net.brewspberry.batches.exceptions.NotTheGoodNumberOfArgumentsException;
import net.brewspberry.batches.tasks.RecordTemperatureFromFileTask;
import net.brewspberry.batches.test.config.BatchesSpringTestConfiguration;
import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BatchesSpringTestConfiguration.class)
public class RecordTemperatureFromFileTaskTest {

	
	@Autowired
	public RecordTemperatureFromFileTask task;

	@Test
	public void shouldBeAutowired(){
		
		Assert.assertNotNull(task);
		
	}
	
	
	
	public void shouldCheckSpecificParameters (){
		
		Brassin br = new Brassin ();
		Etape et = new Etape();
		Actioner ac = new Actioner();
		
		
		Object[] specs = {br, et, ac};
		
		try {
			Assert.assertTrue(task.checkSpecificParameters(specs));
		} catch (NotTheGoodNumberOfArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
