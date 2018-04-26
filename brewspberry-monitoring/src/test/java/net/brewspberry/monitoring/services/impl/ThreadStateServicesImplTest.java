package net.brewspberry.monitoring.services.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import net.brewspberry.monitoring.exceptions.TechnicalException;
import net.brewspberry.monitoring.model.DaemonThreadState;

@RunWith(BlockJUnit4ClassRunner.class)
public class ThreadStateServicesImplTest {

	ThreadStateServicesImpl sut;
	private static String MAIN_FOLDER;

	@Before
	public void init() {
		Path path = Paths.get("src", "test", "resources", "thread_tests");
		MAIN_FOLDER = path.toString();
		sut = new ThreadStateServicesImpl(MAIN_FOLDER);
	}

	@After
	public void after() {
		removeData();
	}

	@Test
	public void shouldReadState_givenFileDoesNotExist() throws TechnicalException {

		DaemonThreadState state = sut.readState("28-1234567890");

		Assert.assertEquals("28-1234567890", state.getUuid());
		Assert.assertEquals(0, state.getErrorOccurence());
		Assert.assertEquals(null, state.getErrorMessage());
	}

	private void removeData() {
		File file = new File(MAIN_FOLDER);
		if (file.exists()) {
			Arrays.asList(file.listFiles()).forEach(File::delete);
		}
		if (!file.exists())
			file.mkdir();
	}

	@Test
	public void shouldReadState_givenEmptyFile() throws IOException, TechnicalException {
		File file = new File(MAIN_FOLDER + "/28-098765432.pid");
		file.createNewFile();

		DaemonThreadState state = sut.readState("28-098765432");

		Assert.assertEquals("28-098765432", state.getUuid());
		Assert.assertEquals(0, state.getErrorOccurence());
		Assert.assertEquals(null, state.getErrorMessage());
	}
	
	@Test
	public void shouldReadState_givenFileWothErrors() throws IOException, TechnicalException {
		writeAFile("28-098765432.pid", "5");
		
		DaemonThreadState state = sut.readState("28-098765432");

		Assert.assertEquals("28-098765432", state.getUuid());
		Assert.assertEquals(5, state.getErrorOccurence());
		Assert.assertEquals(null, state.getErrorMessage());
		
	}

	private void writeAFile(String file, String errors) throws IOException {
		File fil = new File(MAIN_FOLDER + "/"+file);
		FileWriter writer = new FileWriter(fil);
		writer.write(errors);
		writer.close();
	}
	
	@Test
	public void shouldReadStates_givenEmptyFolder() throws TechnicalException {
		List<DaemonThreadState> st = sut.readStates();
		Assert.assertNotNull(st);
		Assert.assertEquals(0, st.size());
	}
	
	@Test
	public void shouldReadStates_given2threadsRunning() throws TechnicalException, IOException {
		writeAFile("28-098765432.pid", "5");
		writeAFile("28-111111111.pid", "0");

		List<DaemonThreadState> st = sut.readStates();
		Assert.assertNotNull(st);
		Assert.assertEquals(2, st.size());
	}
	
	@Test
	public void shouldReadStates_given0threadsRunning() throws TechnicalException, IOException {
		List<DaemonThreadState> st = sut.readStates();
		Assert.assertNotNull(st);
		Assert.assertTrue(st.isEmpty());
	}
	
	
}
