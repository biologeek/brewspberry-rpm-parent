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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.LoginBean;
import net.brewspberry.business.beans.User;
import net.brewspberry.business.beans.UserProfile;
import net.brewspberry.business.exceptions.DAOException;
import net.brewspberry.business.service.UserServiceImpl;
import net.brewspberry.util.EncryptionUtils;
import net.brewspberry.util.LogManager;
import net.brewspberry.util.validators.UserValidator;
import net.brewspberry.util.validators.UserValidatorErrors;

@Controller
public class UserController {

	@Autowired
	@Qualifier("userServiceImpl")
	private ISpecificUserService userSpecService = new UserServiceImpl();
	@Autowired
	@Qualifier("userServiceImpl")
	private IGenericService<User> userService;
	Logger logger = LogManager.getInstance(UserController.class.getName());
	private List<UserValidatorErrors> errs;

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("login");
		LoginBean lb = new LoginBean();
		mav.addObject("loginBean", lb);
		return mav;

	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("loginBean") LoginBean loginBean) {

		errs = UserValidator.getInstance().isUsernameAndPasswordUserValid(
				request.getParameter("username"),
				request.getParameter("password"));
		logger.info(request.getParameter("username") + "  trnzrjgelfg");
		
		
		if (errs.isEmpty()) {

			User user = userSpecService.returnUserByCredentials(loginBean
					.getUsername(), EncryptionUtils.encryptPassword(
					loginBean.getPassword(), "MD5"));

			if (!user.equals(new User()) || user != null) {

				return "/brewspberry-webapp/accueil.do";

			}
		}

		return "/brewspberry-webapp/login.do";
	}

}
