package net.brewspberry.controller.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.User;
import net.brewspberry.business.service.UserServiceImpl;
import net.brewspberry.util.EncryptionUtils;
import net.brewspberry.util.validators.UserValidator;

@WebServlet("/connection")
public class ConnectionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5053309074376760642L;
	private ISpecificUserService userSpecService;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		userSpecService = new UserServiceImpl();
		
		if (UserValidator.getInstance()
				.isUsernameAndPasswordUserValid(request.getParameter("username"), request.getParameter("password"))
				.isEmpty()) {
			
			// error list is empty so it's OK
			String encryptedPassword = EncryptionUtils.encryptPassword(request.getParameter("password"), "MD5");
			User user = userSpecService.returnUserByCredentials(request.getParameter("username"), encryptedPassword);
			
			if (user != null && !user.equals(new User())){
				
				
				userSpecService.checkIfUserIsActiveAndNotBlocked(user);
				
			}
		}

	}

}
