package net.brewspberry.controller.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;

@WebServlet("/connection")
public class ConnectionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5053309074376760642L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (this.validateRequestParameters(request)) {

		}

	}

	private boolean validateRequestParameters(HttpServletRequest request) {

		int passwdLength = Integer.parseInt(ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "param.session.passwd.length"));
		
		if (request.getParameter("username") != null 
				&& !request.getParameter("username").equals("")){
			
			
		}
		
		if (request.getParameter("password") != null ){
			if(!request.getParameter("password").equals("")){
				if(request.getParameter("password").length() > passwdLength){
					
					
					
				} else {
					throw new SecurityException("Password not long enough ("+passwdLength+" min)");
				}
			} else {
				throw new SecurityException("Empty password ("+passwdLength+" min)");
			}
			
		}
		
		if (request.getParameter("username") != null 
				&& !request.getParameter("username").equals("")){
			
			
		}
	
			return false;
	}
}
