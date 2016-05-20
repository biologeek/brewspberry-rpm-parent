package net.brewspberry.util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager {
	
	
	private static Logger logger;
	private static Handler handler;
	private static FileHandler fh;
	
	public static String logFile = "/home/pi/logger.log";

	public static Logger getInstance(String name){
		
		Level newLevel = Level.parse(ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "param.logger.level"));
		
		if (logger == null){
			handler = new ConsoleHandler();
			try {
				fh = new FileHandler();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger = Logger.getLogger(name);
			logger.setLevel(newLevel);
			logger.addHandler(handler);
			
		}
		
		return logger;
	}
	
	public static void setLevel (){
		
		if (logger != null){
			logger.setLevel(Level.ALL);
		}
	}

}
