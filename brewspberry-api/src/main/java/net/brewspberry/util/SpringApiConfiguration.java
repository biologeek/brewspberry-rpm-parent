package net.brewspberry.util;

import net.brewspberry.util.config.SpringCoreConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@Import(SpringCoreConfiguration.class)
public class SpringApiConfiguration {

}
