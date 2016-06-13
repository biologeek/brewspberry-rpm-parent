package net.brewspberry.filters;

import java.io.IOException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.persistence.Enumerated;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.User;
import net.brewspberry.business.beans.UserProfile;
import net.brewspberry.business.service.UserServiceImpl;
import net.brewspberry.controller.session.UserServlet;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;
import net.brewspberry.util.EncryptionUtils;
import net.brewspberry.util.LogManager;
import net.brewspberry.util.validators.UserValidator;


//@WebFilter("/*")
public class AuthentificationFilter implements Filter {

	private HttpSession currentSession;
	Logger logger = LogManager.getInstance(UserServlet.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = null;
		HttpServletResponse rep = null;
		
		class FilteredRequest extends HttpServletRequestWrapper {

			public FilteredRequest(HttpServletRequest request) {
				super(request);
			}
			
			
			@Override
			public String getParameter(final String arg0){
				
				
				return super.getParameter(arg0);
				
			}
		};

		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {

			req = new FilteredRequest((HttpServletRequest) request);
			rep = (HttpServletResponse) response;
			String loginPage = req.getContextPath() + "/"
					+ ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "params.permissions.login.address");
			String shortLoginPage = req.getContextPath() + "/";
			currentSession = req.getSession();

			if (currentSession.getAttribute("user") != null || req.getRequestURI().equals(loginPage) || req.getRequestURI().equals(shortLoginPage)) {
				
				/*
				 * User  is already logged or requests login servlet so letting him login
				 * 
				 */
				
				chain.doFilter(req, response);

			} else {
				
				/*
				 * Go login
				 */
				logger.info("User "+request.getParameter("username")+" is trying to login. Type = "+request.getParameter("formType"));
				
				
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

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
