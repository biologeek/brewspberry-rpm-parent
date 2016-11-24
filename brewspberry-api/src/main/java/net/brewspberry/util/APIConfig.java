package net.brewspberry.util;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.brewspberry.util.config.SpringCoreConfiguration;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "net.brewspberry")
@Import(SpringCoreConfiguration.class)
public class APIConfig extends WebMvcConfigurerAdapter {

	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

}
