package net.brewspberry.util.config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AbstractServletInitiator extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2146973171478741701L;

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		final AutowireCapableBeanFactory autowireCapableBeanFactory = WebApplicationContextUtils
				.getWebApplicationContext(arg0.getServletContext())
				.getAutowireCapableBeanFactory();
		autowireCapableBeanFactory.autowireBean(this);
	}

}
