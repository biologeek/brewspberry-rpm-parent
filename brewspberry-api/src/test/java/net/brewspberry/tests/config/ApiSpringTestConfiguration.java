package net.brewspberry.tests.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import net.brewspberry.test.util.config.SpringCoreTestConfiguration;




@Configuration
@ComponentScan(basePackages = { "net.brewspberry" })
@Import({ SpringCoreTestConfiguration.class})
public class ApiSpringTestConfiguration {

	
	
}
