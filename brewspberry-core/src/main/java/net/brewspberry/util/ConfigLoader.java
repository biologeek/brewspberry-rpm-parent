package net.brewspberry.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javassist.bytecode.Opcode;

public class ConfigLoader {

	public static Map<String, String> getPropertiesFromFile(String filePath)
			throws IOException {

		Map<String, String> hashMap = new HashMap<String, String>();

		Properties props = openFile(filePath);

		for (final String prop : props.stringPropertyNames())
			hashMap.put(prop, props.getProperty(prop));

		return hashMap;
	}

	public static String getConfigByKey(String path, String key) {

		String result = new String();

		System.out.println("Loading config file : "+path);
		Properties props = null;
		try {
			props = openFile(path);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		if (props.containsKey(key)) {

			result = props.getProperty(key);

		} else {
			new Exception("No property found for key " + key);
		}

		return result;

	}

	public static Properties openFile(String file) throws IOException {

		Properties props = new Properties();

		InputStream in = null;

		in = new FileInputStream(file);

		props.load(in);

		return props;

	}
}
