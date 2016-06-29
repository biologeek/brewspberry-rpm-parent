package net.brewspberry.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.service.BrassinServiceImpl;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;
import net.brewspberry.util.DeviceParser;

/**
 * Servlet implementation class Accueil
 */
@WebServlet({"/Accueil.do", "/Accueil", "/accueil"})
@Controller
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IGenericService<Brassin> brassinService;
	
	static Logger logger = Logger.getLogger(Accueil.class.toString());

    /**
     * Default constructor. 
     */
    public Accueil() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bids = request.getParameter("bid");
		Integer bid = null;
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		
		
		if (bids != null) {

			try{
				bid = Integer.parseInt(bids);
			}
			catch (Exception e){
				bid = 0;
			}
		}
		
		
		// Displays welcome page
		if (bid == null || bid == 0) {
			List<Brassin> brewList = brassinService.getAllElements();
			logger.fine("Got : "+brewList);
			request.setAttribute("brewList", brewList);
			request.getRequestDispatcher("accueil_viewer.jsp").forward(request, response);
		}
		
		//Displays brew page
		else if (bid > 0){
			
			logger.fine("Retrieving brew from DB for id : "+bid);
			Brassin currentBrew = brassinService.getElementById(bid);
			
			if (currentBrew != null){
				logger.fine("Found 1 brew : "+currentBrew);
			}
			request.setAttribute("brassin", currentBrew);
			
			logger.fine("Steps for brew : "+currentBrew.getBra_etapes());
			
			List<Actioner> availableActioners = new ArrayList<Actioner>();
			
			availableActioners = DeviceParser.getInstance().getDevices(Constants.DEVICES_PROPERTIES);
			for (Actioner act : availableActioners){
				
				logger.fine("Device : "+act.getAct_nom()+", pin="+act.getAct_raspi_pin()+", uuid="+act.getAct_uuid());
				
			}
			
			for (Etape step : currentBrew.getBra_etapes()){
				
				logger.fine("||" + step.getEtp_nom()+ " "+ step.getEtp_id()+ " "+ step.getEtp_numero());
				
			}
			
			request.setAttribute("stepCounter", Math.ceil((double) currentBrew.getBra_etapes().size()) - 1);
			request.setAttribute("steps", currentBrew.getBra_etapes());
			request.setAttribute("availableActioners", availableActioners);
			request.setAttribute("tempServlet", ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "servlets.temperature.graph"));

			request.setAttribute("actionerServiceAddress", ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "servlets.actioners.activation"));
			
			
			request.getRequestDispatcher("brew.jsp").forward(request, response);	
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public IGenericService<Brassin> getBrassinService() {
		return brassinService;
	}

	public void setBrassinService(IGenericService<Brassin> brassinService) {
		this.brassinService = brassinService;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		Accueil.logger = logger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}