package net.brewspberry.batches.util;

import net.brewspberry.util.config.SpringCoreConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Import(SpringCoreConfiguration.class)
public class BatchesSpringConfiguration {

}
