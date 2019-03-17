package net.brewspberry.monitoring.services.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;

//@RunWith(BlockJUnit4ClassRunner.class)
public class ThreadStateServicesImplTest {

	ThreadStateServicesImpl sut;

	@Before
	public void init() {
		Path path = Paths.get("src", "test", "resources", "thread_tests");
		path.toString();
		sut = new ThreadStateServicesImpl();
	}

	@After
	public void after() {
	}

	
	
}
