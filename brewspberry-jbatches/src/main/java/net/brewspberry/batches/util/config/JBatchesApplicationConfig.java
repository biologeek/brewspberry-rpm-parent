package net.brewspberry.batches.util.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;

import net.brewspberry.util.config.SpringCoreConfiguration;

@Configuration
@Import({SpringCoreConfiguration.class})
@ComponentScan({"net.brewspberry"})
@PropertySources(value = { @PropertySource("file:${app.parameters}/config.properties"),@PropertySource("file:${app.parameters}/batches.properties"),
		@PropertySource("classpath:c3po.properties"), @PropertySource("file:${app.parameters}/devices.properties") })
public class JBatchesApplicationConfig {

	@Autowired
	Environment env;
	

	@Bean
	public static PropertySourcesPlaceholderConfigurer configurer() {
		PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();
		return config;
	}
	
	
}
