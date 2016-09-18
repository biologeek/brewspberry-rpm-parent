package net.brewspberry.util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;

public class LogManager {
	
	
	private static Logger logger;
	private static Handler handler;
	private static FileHandler fh;
	
	@Value("${param.logger.level}")
	public String loggerLevel;
	
	public static String logFile = "/home/pi/logger.log";

	public static Logger getInstance(String name){
		
		if (logger == null){
			handler = new ConsoleHandler();
			try {
				fh = new FileHandler();
			} catch (SecurityException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			logger = Logger.getLogger(name);
			logger.setLevel(Level.INFO);
			logger.addHandler(handler);
			
		}
		
		return logger;
	}
	
	public static void setLevel (){
		
		if (logger != null){
			logger.setLevel(Level.ALL);
		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LogManager.logger = logger;
	}

	public static Handler getHandler() {
		return handler;
	}

	public static void setHandler(Handler handler) {
		LogManager.handler = handler;
	}

	public static FileHandler getFh() {
		return fh;
	}

	public static void setFh(FileHandler fh) {
		LogManager.fh = fh;
	}

	public static String getLogFile() {
		return logFile;
	}

	public static void setLogFile(String logFile) {
		LogManager.logFile = logFile;
	}

	public String getLoggerLevel() {
		return loggerLevel;
	}

	public void setLoggerLevel(String loggerLevel) {
		this.loggerLevel = loggerLevel;
	}

}
