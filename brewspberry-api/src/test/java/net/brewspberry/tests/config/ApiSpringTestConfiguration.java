package net.brewspberry.tests.config;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;



@Configuration
@ComponentScan(basePackages = "net.brewspberry", excludeFilters={
		@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Configuration.class)
})
@Import(SpringCoreTestConfiguration.class)
public class ApiSpringTestConfiguration {

//	
//
//	@Override
//	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//		configurer.enable();
//	}
//
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/**").addResourceLocations("/");
//	}
//
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("forward:/login.jsp");
//	}
//
//	@Bean(name = "viewResolver")
//	public InternalResourceViewResolver getViewResolver() {
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setPrefix("/");
//		viewResolver.setSuffix(".jsp");
//		return viewResolver;
//	}
//
//	
	
}
