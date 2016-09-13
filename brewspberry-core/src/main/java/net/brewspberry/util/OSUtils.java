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

	public static boolean isWindows(){
		
		if (getSystemOS().contains("indows")){
			return true;
		}
		return false;
	}
	public static boolean isLinux(){
		
		if (getSystemOS().contains("inux")){
			return true;
		}
		return false;
	}
}
