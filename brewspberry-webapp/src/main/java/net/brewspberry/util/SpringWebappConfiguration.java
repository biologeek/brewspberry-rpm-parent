package net.brewspberry.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "net.brewspberry" })
@Import({SpringCoreConfiguration.class, SpringHibernateCoreConfiguration.class})
public class SpringWebappConfiguration extends  WebMvcConfigurerAdapter {

	
	

	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setPrefix("/");
	    viewResolver.setSuffix(".jsp");
	    return viewResolver;
	}	
	
	
}
