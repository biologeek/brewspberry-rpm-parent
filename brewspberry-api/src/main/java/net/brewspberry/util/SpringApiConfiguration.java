package net.brewspberry.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@Import(SpringCoreConfiguration.class)
public class SpringApiConfiguration {

}
