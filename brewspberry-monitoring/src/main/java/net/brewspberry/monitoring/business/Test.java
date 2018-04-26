package net.brewspberry.monitoring.business;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {

	public static void main(String[] args) throws IOException, URISyntaxException {
		Path p = Paths.get(new URI("file:///home/xavier/exploit.py"));
		
		System.out.println(p.getFileName().toString());
		
	}

}
