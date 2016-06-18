package net.brewspberry.util;

public class OSUtils {

	
	
	public static String getSystemOS(){
		
		return System.getProperty("os.name");
		
	}
	
	
	public static boolean isRaspbian(){
		
		
		if (getSystemOS().contains("aspbian")){
			return true;
		}
		return false;
	}
}
