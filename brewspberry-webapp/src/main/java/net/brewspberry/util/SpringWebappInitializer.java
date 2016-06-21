package net.brewspberry.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringWebappInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext)
			throws ServletException {
		WebApplicationContext context = getContext();
		servletContext.addListener(new ContextLoaderListener(context));
		
		AnnotationConfigWebApplicationContext mvcContext = getMVCContext();
		
		
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"DispatcherServlet", new DispatcherServlet(mvcContext));
		
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("*.do");
	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("net.brewspberry.util");
		
		context.register(AppConfig.class);
		return context;
	}

	
	private  AnnotationConfigWebApplicationContext getMVCContext(){
		 // now the config for the Dispatcher servlet
		   AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		   mvcContext.register(SpringWebappConfiguration.class);
		return mvcContext;
			
		
		
	}
}
