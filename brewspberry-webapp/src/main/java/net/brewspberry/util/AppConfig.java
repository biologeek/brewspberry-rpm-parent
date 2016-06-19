package net.brewspberry.util;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "net.brewspberry.*")
@Import({SpringCoreConfiguration.class, SpringWebappConfiguration.class})
public class AppConfig {
}