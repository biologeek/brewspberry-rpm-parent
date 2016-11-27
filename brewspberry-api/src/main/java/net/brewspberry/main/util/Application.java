package net.brewspberry.main.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@SpringBootApplication
@PropertySources(value = { @PropertySource("file:${app.parameters}/config.properties"),@PropertySource("file:${app.parameters}/batches.properties")
,@PropertySource("classpath:c3po.properties"), @PropertySource("file:${app.parameters}/devices.properties") 
, @PropertySource("file:${app.parameters}/stock.properties") })
public class Application {
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
