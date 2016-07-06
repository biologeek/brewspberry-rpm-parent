package net.brewspberry.batches.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.brewspberry.test.util.config.SpringCoreTestConfiguration;

@Configuration
@EnableTransactionManagement
@Import(SpringCoreTestConfiguration.class)
public class BatchesSpringTestConfiguration {

	
	
}
