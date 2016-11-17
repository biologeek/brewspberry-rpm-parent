package net.brewspberry.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan
@SpringBootApplication
public class Application {
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
