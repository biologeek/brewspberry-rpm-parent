package net.brewspberry.controller.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.Request;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.User;
import net.brewspberry.business.beans.UserProfile;
import net.brewspberry.business.service.UserServiceImpl;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.EncryptionUtils;
import net.brewspberry.util.LogManager;
import net.brewspberry.util.validators.UserValidator;
import net.brewspberry.util.validators.UserValidatorErrors;

@WebServlet({"/user", "/", "/register"})
public class UserServlet extends HttpServlet {

	
	// (?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])

	/**
	 * 
	 */
	private static final long serialVersionUID = -5053309074376760642L;
	private ISpecificUserService userSpecService;
	private IGenericService<User> userService;
	private HttpSession currentSession;
	Logger logger = LogManager.getInstance(UserServlet.class.getName());

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		currentSession = request.getSession();

		userService = new UserServiceImpl();
		userSpecService = new UserServiceImpl();
		List<UserValidatorErrors> errs;

		logger.info("Entering doPost");
		String hiddenParam = request.getParameter("formType");

		switch (hiddenParam) {

		case "registration":
			
			
			User userToValidate = new User();
			
			userToValidate = feedUserWithFormData(request);
			
			
			errs = UserValidator.getInstance().validateFormUser(userToValidate);
			
			
			if (errs.size() == 0){
				/*
				 * User is valid
				 */
				
				try {
					userService.save(userToValidate);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} else {
				/*
				 * Treating the errors
				 */
				
				request.setAttribute("errors", errs);
				
				request.setAttribute("user", userToValidate);
				
			}
			
		
			

			break;

		case "connection":

			errs = UserValidator.getInstance()
					.isUsernameAndPasswordUserValid(request.getParameter("username"), request.getParameter("password"));
			logger.info(request.getParameter("username") + "trnzrjgelfg");
			if (errs.isEmpty()) {

				// error list is empty so it's OK
				String encryptedPassword = EncryptionUtils.encryptPassword(request.getParameter("password"), "MD5");
				User user = userSpecService.returnUserByCredentials(request.getParameter("username"), encryptedPassword);

				if (user != null && !user.equals(new User())) {

					if (userSpecService.checkIfUserIsActiveAndNotBlocked(user)) {

						try {
							connectUserAndBuildHisSession(user, request, response);
							response.sendRedirect("/Accueil");

						} catch (Exception e) {

							logger.severe("Could not create session for user " + user.toString());
							errs = new ArrayList<UserValidatorErrors>();
							errs.add(UserValidatorErrors.SESSION_BUILDING);
							
							
							//If there's an error, putting errors in JSP
							request.setAttribute("errors", errs);
							request.getRequestDispatcher("index.jsp").forward(request, response);

						}
					} else {
						
						request.setAttribute("errors", userSpecService.getErrors());
						response.sendRedirect("/");

					}
					
				} else {
					
					request.setAttribute("errors", userSpecService.getErrors());
					response.sendRedirect("/");
					
				}
			}
			

			break;
		}

	}
	
	
	private User feedUserWithFormData(HttpServletRequest request) {
		
		User userToValidate = new User();
		
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String age = request.getParameter("age");
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		//String birthday = request.getParameter("birthday");

		if (userName != null && !userName.equals("")){
			
			userToValidate.setUs_login(userName);
			
		}
		if (password != null && !password.equals("")){
			userToValidate.setUs_password(password);
		}
		if (age != null && !age.equals("")){
			try {
				userToValidate.setUs_age(Integer.parseInt(age));
			} catch (Exception e){
				
				logger.severe(age+" is not a valid number !");
			}
		}
		if (firstName != null && !firstName.equals("")){
			userToValidate.setUs_prenom(firstName);
		}
		if (lastName != null && !lastName.equals("")){
			userToValidate.setUs_nom(lastName);
		}

		
		return userToValidate;
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		logger.info("Entering doGet");

		request.getRequestDispatcher("index.jsp").forward(request, response);	
		
	}



	/**
	 * Method creating cookies, checking their presence, updating user and
	 * building session attributes
	 * 
	 * @param user
	 * @param req
	 * @param rep
	 */
	private void connectUserAndBuildHisSession(User user, HttpServletRequest req, HttpServletResponse rep) {

		if (this.currentSession.isNew()) {

			rep.addCookie(new Cookie("user.login", user.getUs_login()));
			rep.addCookie(new Cookie("user.status", user.getUs_profile().getClass().getSimpleName()));
			rep.addCookie(new Cookie("user.token", this.currentSession.getId()));
			rep.addCookie(new Cookie("user.connectionTime", String.valueOf(this.currentSession.getCreationTime())));

			this.currentSession.setAttribute("user", user);

			user.setUs_last_connection(Calendar.getInstance().getTime());

			user.setUs_session_token(currentSession.getId());

			if (user.getUs_first_connection() == null) {
				user.setUs_first_connection(Calendar.getInstance().getTime());
			}

			// Updating user
			userService.update(user);
		} else {

			if (currentSession.getId().equals(user.getUs_session_token())) {

				currentSession.setAttribute("user", user);

			}

		}

	}

	private String computeAuthorizationsForSession(UserProfile us_profile) {

		StringBuilder str = new StringBuilder();

		if (us_profile.isRead()) {
			str.append(1);
		} else {
			str.append(0);
		}

		if (us_profile.isWrite()) {
			str.append(1);
		} else {
			str.append(0);
		}

		if (us_profile.isExecute()) {
			str.append(1);
		} else {
			str.append(0);
		}

		if (!us_profile.getSpecialAuths().isEmpty()) {
			for (String key : us_profile.getSpecialAuths().keySet()) {

				if (us_profile.getSpecialAuths().get(key)) {
					str.append(key = ":" + 1 + ";");
				} else {
					str.append(key = ":" + 1 + ";");
				}

			}
			str.substring(0, str.length() - 1);
		}

		return str.toString();
	}

}
