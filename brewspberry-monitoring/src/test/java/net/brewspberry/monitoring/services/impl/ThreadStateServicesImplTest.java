package net.brewspberry.monitoring.services.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mockito;

@RunWith(BlockJUnit4ClassRunner.class)
public class ThreadStateServicesImplTest {

	ThreadStateServicesImpl sut;
	private EntityManager em;
	@Before
	public void init() {
		Path path = Paths.get("src", "test", "resources", "thread_tests");
		path.toString();
		em = Mockito.mock(EntityManager.class);
		sut = new ThreadStateServicesImpl(em);
	}

	@After
	public void after() {
	}

	
	
}
