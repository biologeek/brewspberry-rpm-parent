package net.brewspberry.batches.test;

import net.brewspberry.batches.tasks.RecordTemperatureFromFileTask;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class RecordTemperatureFromFileTaskTest {

	
	@Autowired
	private RecordTemperatureFromFileTask task;

	@Test
	public void shouldBeAutowired(){
		
		Assert.assertNotNull(task);
		
	}
}
