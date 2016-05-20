package net.brewspberry.test;

import java.util.Calendar;
import org.junit.Test;

import org.junit.Assert;
import net.brewspberry.util.DateManipulator;


public class DateManipulatorTest {
	
	
	
	@Test
	public void shouldFormatDateFromVariousPatterns(){
		
		String date1 = "2016-04-28 17:00:00";
		String date2 = "2015-03-20 16:25:31.2000";
		String date3 = "10/02/2014 15:40:50";
		String date4 = "12/01/2014 13:32:57.1234";
		
		Calendar resDate1 = DateManipulator.formatDateFromVariousPatterns(date1);
		
		
		Calendar resDate2 = DateManipulator.formatDateFromVariousPatterns(date2);
		Calendar resDate3 = DateManipulator.formatDateFromVariousPatterns(date3);
		Calendar resDate4 = DateManipulator.formatDateFromVariousPatterns(date4);
		
		Assert.assertEquals(resDate1.get(Calendar.DAY_OF_MONTH), 28);

		Assert.assertEquals(resDate2.get(Calendar.SECOND), 33);
		
		Assert.assertEquals(resDate3.get(Calendar.SECOND), 50);
		
		Assert.assertEquals(resDate4.get(Calendar.SECOND), 58);

	}
	

}
