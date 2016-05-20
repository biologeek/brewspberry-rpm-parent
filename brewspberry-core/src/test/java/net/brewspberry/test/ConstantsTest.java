package net.brewspberry.test;
import static org.junit.Assert.*;
import junit.framework.Assert;
import net.brewspberry.util.Constants;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ConstantsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		if (Constants.CEREALS != null){
			
			Assert.assertTrue((Constants.CEREALS.size()>0));
			
			Assert.assertTrue((Constants.CEREALS.get("wheat") == "Wheat"));
			
		}
	}

}
