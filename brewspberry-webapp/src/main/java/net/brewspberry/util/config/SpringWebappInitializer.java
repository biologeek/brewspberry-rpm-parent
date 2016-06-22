package net.brewspberry.util.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class SpringWebappInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext)
			throws ServletException {
		AnnotationConfigWebApplicationContext mvcContext = getMVCContext();
/*
		WebApplicationContext context = getContext();
		servletContext.addListener(new ContextLoaderListener(context));
*/	
		
		
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"DispatcherServlet", new DispatcherServlet(mvcContext));
		
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("*.do");
	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		
		
		//context.setConfigLocation("net.brewspberry.util");
		
		context.register(AppConfig.class, SpringCoreConfiguration.class);
		context.refresh();
		return context;
	}

	
	private  AnnotationConfigWebApplicationContext getMVCContext(){
		 // now the config for the Dispatcher servlet
		   AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		   mvcContext.setConfigLocation("net.brewspberry.util.config");
		   mvcContext.register(SpringWebappConfiguration.class);
		return mvcContext;
			
		
		
	}
}
