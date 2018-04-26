package net.brewspberry.monitoring.services.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import net.brewspberry.monitoring.exceptions.TechnicalException;
import net.brewspberry.monitoring.model.DaemonThreadState;
import net.brewspberry.monitoring.services.ThreadStateServices;

public class ThreadStateServicesImpl implements ThreadStateServices {

	private static final String THREAD_RETURN_CODE_FILE_SUFFIX = "pid";
	private String folder;
	private BufferedReader returnCodeInputStream;
	private BufferedOutputStream returnCodeOutputStream;

	/**
	 * Default constructor with mandatory folder path
	 * 
	 * @param folderName
	 * @throws FileNotFoundException
	 */

	public ThreadStateServicesImpl(String folderName) {
		this.folder = folderName;
	}

	@Override
	public DaemonThreadState readState(String sensorUuid) throws TechnicalException {
		try {
			File file = new File(folder + "/" + sensorUuid + "."+ THREAD_RETURN_CODE_FILE_SUFFIX);
			if (!file.exists())
				file.createNewFile();
			if (!file.isFile())
				throw new IOException();

			returnCodeInputStream = new BufferedReader(new FileReader(file));

			String returnCode = returnCodeInputStream.readLine();
			if (returnCode == null || "0".equals(returnCode) || returnCode.isEmpty())
				return DaemonThreadState.noError(sensorUuid);
			else
				return DaemonThreadState.xErrors(Integer.valueOf(returnCode), sensorUuid);
		} catch (IOException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage());
		} finally {
			try {
				returnCodeInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new TechnicalException("error.close");
			}
		}
	}

	@Override
	public List<DaemonThreadState> readStates() throws TechnicalException {
		Assert.notNull(folder, "Folder is null");
		try {
			return Files//
					.list(Paths.get(folder))//
					.filter(new Predicate<Path>() {
						@Override
						public boolean test(Path t) {
							return t.getFileName().toString().endsWith(THREAD_RETURN_CODE_FILE_SUFFIX);
						}
					})//
					.map(t -> t.getFileName().toString().split("\\.")[0])//
					.map(t -> {
						try {
							return readState(t);
						} catch (TechnicalException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}).collect(Collectors.toList());
		} catch (IOException e) {
			throw new TechnicalException(e.getMessage());
		}
	}

	@Override
	public void writeState(DaemonThreadState state) {

	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	@Override
	public void cleanState(String uuid) {
		Assert.notNull(folder, "Folder is null !");

		File file = new File(folder + "/" + uuid + "." + THREAD_RETURN_CODE_FILE_SUFFIX);
		if (file.exists() && file.isFile())
			file.delete();

	}

	@Override
	public void cleanState(List<String> collect) {
		collect.stream().forEach(t -> cleanState(t));
	}
}
