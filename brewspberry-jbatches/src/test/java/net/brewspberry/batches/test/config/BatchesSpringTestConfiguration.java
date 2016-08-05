package net.brewspberry.batches.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.brewspberry.test.util.config.SpringCoreTestConfiguration;


@Configuration
@ComponentScan(basePackages = { "net.brewspberry.*" })
@Import({ SpringCoreTestConfiguration.class})
public class BatchesSpringTestConfiguration {

	
	
}
