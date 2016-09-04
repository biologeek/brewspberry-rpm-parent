package net.brewspberry.util.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "net.brewspberry")
@Import({ SpringCoreConfiguration.class})
public class AppConfig {
}