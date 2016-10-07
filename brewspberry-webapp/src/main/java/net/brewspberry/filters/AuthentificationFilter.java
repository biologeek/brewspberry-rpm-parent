package net.brewspberry.filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import net.brewspberry.business.beans.User;
import net.brewspberry.controller.session.UserServlet;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;
import net.brewspberry.util.LogManager;

//@WebFilter("/*")
public class AuthentificationFilter  {

	@Autowired
	private HttpSession currentSession;
	Logger logger = LogManager.getInstance(UserServlet.class.getName());

	public AuthentificationFilter() {
		super();
		
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = null;
		HttpServletResponse rep = null;

		class FilteredRequest extends HttpServletRequestWrapper {

			public FilteredRequest(HttpServletRequest request) {
				super(request);
			}

			@Override
			public String getParameter(final String arg0) {

				return super.getParameter(arg0);

			}
		}
		;

		if (request instanceof HttpServletRequest
				&& response instanceof HttpServletResponse) {

			req = new FilteredRequest((HttpServletRequest) request);
			rep = (HttpServletResponse) response;
			String loginPage = req.getContextPath()
					+ "/"
					+ ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES,
							"params.permissions.login.address");
			String shortLoginPage = req.getContextPath() + "/";
			
			logger.info("FILTER PARAMS : " + req.getRequestURI() + " "
					+ loginPage + " " + shortLoginPage);

			if (currentSession.getAttribute("user") != null
					|| req.getRequestURI().equals(loginPage)
					|| req.getRequestURI().equals(shortLoginPage)) {

				/*
				 * User is already logged or requests login servlet so letting
				 * him login
				 */
				if (currentSession.getAttribute("user") != null)
					logger.info("Logged as : "
							+ ((User) currentSession.getAttribute("user"))
									.getUs_login());

				chain.doFilter(req, response);

			} else {

				/*
				 * Go login
				 */
				logger.info("User " + request.getParameter("username")
						+ " is trying to login. Type = "
						+ request.getParameter("formType"));

				rep.sendRedirect(shortLoginPage);
			}
		}
	}

	public HttpSession getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(HttpSession currentSession) {
		this.currentSession = currentSession;
	}

	
	public void destroy() {
		

	}

}
