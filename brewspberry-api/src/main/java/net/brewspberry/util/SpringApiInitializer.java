package net.brewspberry.util;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import net.brewspberry.util.config.SpringCoreConfiguration;

public class SpringApiInitializer extends AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{SpringCoreConfiguration.class, SpringApiConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
