package net.brewspberry.main.batches.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import net.brewspberry.main.util.Constants;
import net.brewspberry.main.util.LogManager;
import net.brewspberry.main.util.OSUtils;

@Component
public class DS18b20TemperatureMeasurementParser {

	public static int threadSleepMillis = 100;

	private Logger logger;

	private String baseFolder;

	public DS18b20TemperatureMeasurementParser(String baseFolder2) {
		this.setBaseFolder(baseFolder2 == null ? Constants.DS18B20_DEVICES_FOLDER : baseFolder2);
	}

	public DS18b20TemperatureMeasurementParser() {
		logger = LogManager.getInstance(DS18b20TemperatureMeasurementParser.class.getName());
	}

	public File loadFile(String path) {
		File result = null;

		if (path != null) {

			try {
				result = new File(path);
			} catch (Exception e) {

				logger.severe("Could not open file : " + path);

			}

		}

		return result;
	}

	/**
	 * Searching and returning all files in /sys/bus/devices/28* /w1_slave
	 * 
	 * @return array of files
	 * @throws IOException
	 */
	public List<Path> findFilesToOpen() throws IOException {

		List<Path> files = new ArrayList<Path>();

		String glob = "regex:"+Constants.DS18B20_FILE_NAME;

		final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob);

		Files.walkFileTree(Paths.get(baseFolder), EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {

				logger.info("FileVisitor " + path.getFileName());

				
				if (pathMatcher.matches(path.getFileName())) {

					files.add(Paths.get(path.toString()));
					logger.fine("Added path " + Paths.get(path.toString()) + " to list");
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				logger.fine("File "+file.getFileName().toString()+" not corresponding to pattern");
				return FileVisitResult.CONTINUE;
			}
		});
		

		return files;
	}

	public synchronized int parseTemperature(String file) {

		int result = 0;

		if (file != null) {

			File fileF = new File(file);
			if (fileF.exists() && !fileF.isDirectory()) {

				try {

					List<String> content = Files.readAllLines(Paths.get(file));

					if (content.size() == 2) {
						while (!content.get(0).substring(content.get(0).length() - 3).equals("YES")) {

							try {
								Thread.sleep(threadSleepMillis);
							} catch (InterruptedException e) {

								e.printStackTrace();
							}
						}

						String resultLine = content.get(1).substring(content.get(1).length() - 5);

						result = Integer.parseInt(resultLine);

					}
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

		}

		return result;

	}

	public String getProbeUUIDFromFileName(String file) {

		String result = null;
		String UUIDPattern = Pattern.compile("28-[a-zA-Z0-9]{12}").pattern();
		String[] fileSplit;
		if (OSUtils.isWindows())
			fileSplit = file.split("\\\\");
		else
			fileSplit = file.split("/");

		for (String str : fileSplit) {

			if (str.matches(UUIDPattern)) {

				if (result == null) {
					result = str;
				} else {

					logger.severe("Not only one result found... Path incorrect !!");

				}
			}
		}
		return result;

	}

	public String getBaseFolder() {
		return baseFolder;
	}

	public void setBaseFolder(String baseFolder) {
		this.baseFolder = baseFolder;
	}
}
