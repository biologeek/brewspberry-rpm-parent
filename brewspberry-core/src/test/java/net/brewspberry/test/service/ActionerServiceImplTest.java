package net.brewspberry.test.service;

import static org.junit.Assert.*;

import java.util.Date;

import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.service.ActionerServiceImpl;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;
import net.brewspberry.util.Constants;
import net.brewspberry.util.HibernateUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pi4j.io.gpio.RaspiPin;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfiguration.class)
public class ActionerServiceImplTest {

	private ActionerServiceImpl actionerService;	
	private static Actioner actioner;

/*
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateUtil.configureSessionFactory();
		actioner = new Actioner();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private Actioner result;


	@Before
	public void setUp() throws Exception {
		actionerService = new ActionerServiceImpl();

		actioner.setAct_nom("ds18b20");
		actioner.setAct_uuid("abcdefghijklmnopqrstuv");
		actioner.setAct_raspi_pin(Constants.BREW_GPIO_TO_STR.get(RaspiPin.GPIO_04));
		actioner.setAct_date_debut(new Date());
		actioner.setAct_status(0);
		actioner.setAct_type("ds18b20");
		actioner.setAct_used(false);
		actioner.setAct_activated(false);
		
		result = new Actioner();
	}

	@After
	public void tearDown() throws Exception {
	}

*/
	@Test
	public void notRealTest(){
		
	}
	/*
	//@Test
	public void testSave() {
		
		try {
			result = actionerService.save(actioner);
		} catch (DAOException e) {
			
			fail ("Exception at Saving");
		}
		
	}

	//@Test
	public void testUpdate() {
		actioner.setAct_uuid("123456789");
		
		result = actionerService.update(actioner);
		
		Assert.assertEquals(result.getAct_uuid(), actioner.getAct_uuid());
	}

	//@Test
	public void testGetAllDistinctElements() {
		fail("Not yet implemented");
	}

	//@Test
	public void testGetActionerByBrassin() {
		fail("Not yet implemented");
	}

	//@Test
	public void testGetActionnerByEtape() {
		fail("Not yet implemented");
	}

	//@Test
	public void testGetRealTimeActionersByName() {
		fail("Not yet implemented");
	}

	//@Test
	public void testGetAllRealTimeActioners() {
		fail("Not yet implemented");
	}

	//@Test
	public void testStopActionInDatabase() {
		fail("Not yet implemented");
	}

	//@Test
	public void testStartAction() {
		fail("Not yet implemented");
	}

	//@Test
	public void testStopAction() {
		fail("Not yet implemented");
	}

	//@Test
	public void testGetPIDFromPs() {
		fail("Not yet implemented");
	}
*/
}
