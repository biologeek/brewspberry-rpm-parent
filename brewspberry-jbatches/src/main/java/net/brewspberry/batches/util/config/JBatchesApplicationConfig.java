package net.brewspberry.batches.util.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import net.brewspberry.util.config.SpringCoreConfiguration;

@Configuration
@Import({SpringCoreConfiguration.class})
@PropertySource("file:${app.parameters}/batches.properties")
public class JBatchesApplicationConfig {

	
	
	
}
