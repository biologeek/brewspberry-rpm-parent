package net.brewspberry.util.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import net.brewspberry.util.config.SpringWebappConfiguration;

public class SpringWebappInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer implements
		WebApplicationInitializer {
/*
	public void onStartup(ServletContext servletContext)
			throws ServletException {

		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

		rootContext.setServletContext(servletContext);

		// rootContext.setConfigLocation("net.brewspberry.util");

		rootContext.register(SpringCoreConfiguration.class);

		//servletContext.addListener(new ContextLoaderListener(rootContext));

		getWebAppContext(servletContext);
	
	}


*/
	
	private void getWebAppContext(ServletContext servletContext) {
		// now the config for the Dispatcher servlet
		AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		// mvcContext.setConfigLocation("net.brewspberry.util.config");
			mvcContext.register(SpringWebappConfiguration.class);
		

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"DispatcherServlet", new DispatcherServlet(mvcContext));

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("*.do");
	}

	


	@Override
	protected Filter[] getServletFilters() {
		return null; // new Filter[] { new AuthentificationFilter() };

	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		return new Class<?>[]{SpringCoreConfiguration.class, SpringWebappConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		
		
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		
		return null;
	}

}
