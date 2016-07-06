package net.brewspberry.session;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import net.brewspberry.business.beans.User;

public class SessionHelper {
	
	@Autowired
	public static HttpSession session;
	private static SessionHelper instance;
	
	public static SessionHelper getInstance(){
		
		if (instance == null)
			instance = new SessionHelper();
		
		return instance;
		
	}
	
	
	public User getCurrentUser(){
		
		
		User result = null;
		
		if (session != null){
			
			if (session.getAttribute("user") != null){
				if (session.getAttribute("user") instanceof User){
					
					return (User) session.getAttribute("user");
					
				}
			}
		}
		
		return null;		
	}

}
