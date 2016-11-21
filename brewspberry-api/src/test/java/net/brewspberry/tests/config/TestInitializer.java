package net.brewspberry.tests.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import net.brewspberry.test.util.config.SpringCoreTestConfiguration;
import net.brewspberry.test.util.*;

public class TestInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer {

		@Override
		protected Class<?>[] getRootConfigClasses() {
			// TODO Auto-generated method stub
			return new Class<?>[]{SpringCoreTestConfiguration.class};
		}

		@Override
		protected Class<?>[] getServletConfigClasses() {
			// TODO Auto-generated method stub
			return new Class<?>[]{ApiSpringTestConfiguration.class};
		}

		@Override
		protected String[] getServletMappings() {
			// TODO Auto-generated method stub
			return new String[]{"/*", "/"};
		}
	
}
