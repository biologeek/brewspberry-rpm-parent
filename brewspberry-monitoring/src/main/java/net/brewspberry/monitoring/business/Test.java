package net.brewspberry.monitoring.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {

	public static void main(String[] args) throws IOException {
		Process p = Runtime.getRuntime().exec("whoami");
		String user = new BufferedReader(new InputStreamReader(p.getInputStream())).readLine();
		
		System.out.println(user);
	}

}
