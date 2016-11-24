package net.brewspberry.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan
@SpringBootApplication
@PropertySources(value = { @PropertySource("file:${app.parameters}/config.properties"),@PropertySource("file:${app.parameters}/batches.properties"),
		@PropertySource("classpath:c3po.properties"), @PropertySource("file:${app.parameters}/devices.properties") })
public class Application {
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
