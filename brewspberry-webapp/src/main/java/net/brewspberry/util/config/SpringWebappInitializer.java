package net.brewspberry.util.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import net.brewspberry.filters.AuthentificationFilter;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringWebappInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer implements
		WebApplicationInitializer {

	public void onStartup(ServletContext servletContext)
			throws ServletException {

		AnnotationConfigWebApplicationContext rootContext = getContext(servletContext);

		servletContext.addListener(new ContextLoaderListener(rootContext));

	/*	ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"DispatcherServlet", new DispatcherServlet(getMVCContext()));

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("*.do");
	*/
		}

	private AnnotationConfigWebApplicationContext getContext(
			ServletContext servletContext) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

		context.setServletContext(servletContext);

		// context.setConfigLocation("net.brewspberry.util");

		context.register(SpringCoreConfiguration.class);

		context.refresh();
		return context;
	}

	private AnnotationConfigWebApplicationContext getMVCContext() {
		// now the config for the Dispatcher servlet
		AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		// mvcContext.setConfigLocation("net.brewspberry.util.config");
		mvcContext.register(SpringCoreConfiguration.class);
		return mvcContext;

	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new AuthentificationFilter() };

	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
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
