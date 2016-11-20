package net.brewspberry.batches.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import net.brewspberry.batches.util.config.JBatchesApplicationConfig;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;
import net.brewspberry.util.config.SpringCoreConfiguration;


@Configuration
@ComponentScan(basePackages = { "net.brewspberry" }, excludeFilters={
		@ComponentScan.Filter(type=FilterType.REGEX, pattern={"net\\.brewspberry\\.util\\.config*"})
})
@Import({ SpringCoreTestConfiguration.class})
@PropertySources(value={@PropertySource("classpath:config.test.properties")
, @PropertySource("classpath:c3po.properties")
, @PropertySource("classpath:devices.test.properties")
})
public class BatchesSpringTestConfiguration {

	
	
}
