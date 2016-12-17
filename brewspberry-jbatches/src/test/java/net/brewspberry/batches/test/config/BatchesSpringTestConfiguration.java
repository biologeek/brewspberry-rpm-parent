package net.brewspberry.batches.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import net.brewspberry.test.util.config.SpringCoreTestConfiguration;


@Configuration
@ComponentScan(basePackages = { "net.brewspberry.test" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class) })
@Import({ SpringCoreTestConfiguration.class })
@PropertySources(value = { @PropertySource("classpath:config.test.properties"),
		@PropertySource("classpath:c3po.properties"), @PropertySource("classpath:devices.test.properties")
, @PropertySource("classpath:batches.test.properties"), @PropertySource("classpath:stock.test.properties") })
public class BatchesSpringTestConfiguration {


	@Bean
	public static PropertySourcesPlaceholderConfigurer configurer() {
		PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();
		return config;
	}
}
