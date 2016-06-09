package net.brewspberry.controller.session;

import java.io.IOException;
import java.util.Calendar;
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
import net.brewspberry.util.EncryptionUtils;
import net.brewspberry.util.LogManager;
import net.brewspberry.util.validators.UserValidator;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5053309074376760642L;
	private ISpecificUserService userSpecService;
	Logger logger = LogManager.getInstance(UserServlet.class.getName());
	private HttpSession currentSession;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		currentSession = request.getSession();

		userSpecService = new UserServiceImpl();

		if (UserValidator.getInstance()
				.isUsernameAndPasswordUserValid(request.getParameter("username"), request.getParameter("password"))
				.isEmpty()) {

			// error list is empty so it's OK
			String encryptedPassword = EncryptionUtils.encryptPassword(request.getParameter("password"), "MD5");
			User user = userSpecService.returnUserByCredentials(request.getParameter("username"), encryptedPassword);

			if (user != null && !user.equals(new User())) {

				if (userSpecService.checkIfUserIsActiveAndNotBlocked(user)) {

					try {
						connectUserAndBuildHiSession(user, request, response);
					} catch (Exception e) {

						logger.severe("Could not create session for user " + user.toString());

					}
				}
			}
		}

	}

	/**
	 * Method creating cookies, checking their presence, updating user and
	 * building session attributes
	 * 
	 * @param user
	 * @param request
	 * @param response
	 */
	private void connectUserAndBuildHiSession(User user, HttpServletRequest request, HttpServletResponse response) {

		if (this.currentSession.isNew()) {

			response.addCookie(new Cookie("user.login", user.getUs_login()));
			response.addCookie(new Cookie("user.status", user.getUs_profile().getClass().getSimpleName()));
			response.addCookie(new Cookie("user.token", this.currentSession.getId()));
			response.addCookie(
					new Cookie("user.connectionTime", String.valueOf(this.currentSession.getCreationTime())));

			this.currentSession.setAttribute("user.login", user.getUs_login());
			this.currentSession.setAttribute("user.status", computeAuthorizationsForSession(user.getUs_profile()));
			this.currentSession.setAttribute("user.token", this.currentSession.getId());
			this.currentSession.setAttribute("user.connectionTime", this.currentSession.getCreationTime());

			user.setUs_last_connection(Calendar.getInstance().getTime());
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

	public HttpSession getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(HttpSession currentSession) {
		this.currentSession = currentSession;
	}

}
