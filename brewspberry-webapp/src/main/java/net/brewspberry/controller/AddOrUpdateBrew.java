package net.brewspberry.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificIngredientService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.business.beans.SimpleLevure;
import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.model.BrewProcessorDelegate;
import net.brewspberry.model.Processor;
import net.brewspberry.model.StepProcessor;

/**
 * Servlet implementation class AddOrUpdateBrew
 */
@WebServlet("/AddOrUpdateBrew.do")
@Controller
public class AddOrUpdateBrew extends HttpServlet {
	private static final long serialVersionUID = 1L;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static final Logger logger = Logger.getLogger("AddOrUpdate");

	private Long brewid = new Long(0);

	private List<SimpleMalt> maltList = new ArrayList<SimpleMalt>();

	private List<SimpleHoublon> hopList = new ArrayList<SimpleHoublon>();
	private List<SimpleLevure> yeastList = new ArrayList<SimpleLevure>();

	private Brassin currentBrassin = new Brassin();
	@Autowired
	IGenericService<Brassin> brassinService;
	@Autowired
	@Qualifier("maltServiceImpl")
	ISpecificIngredientService maltIngSpecService;

	@Autowired
	@Qualifier("maltServiceImpl")
	IGenericService<Malt> maltService;
	@Autowired
	IGenericService<Houblon> hopService;
	@Autowired
	IGenericService<Levure> yeastService;

	@Autowired
	@Qualifier("simpleMaltServiceImpl")
	IGenericService<SimpleMalt> simpleMaltService;
	@Autowired
	IGenericService<SimpleHoublon> simpleHopService;
	@Autowired
	IGenericService<SimpleLevure> simpleYeastService;

	@Autowired
	@Qualifier("hopServiceImpl")
	ISpecificIngredientService hopIngSpecService;
	@Autowired
	@Qualifier("yeastServiceImpl")
	ISpecificIngredientService levureIngSpecService;
	@Autowired
	IGenericService<Etape> etapeService;

	@Autowired
	@Qualifier("stepProcessor")
	Processor<Object> stepProc;

	@Autowired
	@Qualifier("brewProcessorDelegate")
	Processor<Brassin> brewProc;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddOrUpdateBrew() {
		super();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 *      Lors de l'affichage
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
																					// 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.

		if (request.getParameter("bid") != null && request.getParameter("bid") != "") {

			/*
			 * Mise à jour du brassin, - on récupère les données du brassin - on
			 * remplit les champs de la page - on renvoie vers la page
			 */

			brewid = Long.parseLong(request.getParameter("bid"));
			if (brewid > 0) {

				try {
					currentBrassin = brassinService.getElementById(brewid);
				} catch (ServiceException e) {
					
					e.printStackTrace();
				}

				if (currentBrassin != null) {

					setBrewAttributes(request);
				} else
					throw new NullPointerException();

			} else
				throw new NumberFormatException();

		}

		// Création de brassin
		// On passe les paramètres pour peupler les listes
		logger.fine("Création d'un brassin");

		maltList = simpleMaltService.getAllDistinctElements();
		hopList = simpleHopService.getAllDistinctElements();
		yeastList = simpleYeastService.getAllDistinctElements();

		System.out.print("bloublou");
		System.out.print(maltList);

		request.setAttribute("maltList", maltList);
		request.setAttribute("hopList", hopList);
		request.setAttribute("yeastList", yeastList);
		System.out.print(request.getAttribute("maltList"));

		request.getRequestDispatcher("add_update.jsp").forward(request, response);

	}

	private void setBrewAttributes(HttpServletRequest request) {
		request.setAttribute("dateDebutValue", sdf.format(currentBrassin.getBra_debut()));
		if (currentBrassin.getBra_fin() != null) {
			request.setAttribute("dateFinValue", sdf.format(currentBrassin.getBra_fin()));
		}

		request.setAttribute("brassinNomValue", currentBrassin.getBra_nom());
		request.setAttribute("brassinQteValue", currentBrassin.getBra_quantiteEnLitres());
		request.setAttribute("statutValue", currentBrassin.getBra_statut());
		request.setAttribute("JSONIngredientsValue",
				generateJSON(maltService.getAllElements(), hopService.getAllElements(), yeastService.getAllElements()));
		// Les ingrédients sont gérés par la suite dans la page web
		// via du JS

		request.setAttribute("brassinIDValue", currentBrassin.getBra_id());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 *      Lors de l'envoi du formulaire
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		Enumeration<String> parameterNames = request.getParameterNames();

		List<String[]> paramList = new ArrayList<String[]>();

		Boolean isUpdate;

		while (parameterNames.hasMoreElements()) {

			String paramName = parameterNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);

			for (int i = 0; i < paramValues.length; i++) {

				String paramValue = paramValues[i];
				String[] strArr = new String[2];
				strArr[0] = paramName.toString();
				strArr[1] = paramValue;
				System.out.println("| " + paramName + " " + paramValue);

			}

		}

		if (request.getParameter("typeOfAdding") != null) {
			String typeOfAdding = (String) request.getParameter("typeOfAdding");

			switch (typeOfAdding) {

			/*
			 * Launching request Procesor (equvalent of Model in MVC)
			 */
			case "brew":

				if (request.getParameter("brassinID") != null && !request.getParameter("brassinID").equals("")) {

					/*
					 * Updating
					 */
					logger.info("Updating brew with ID : " + request.getParameter("brassinID"));
					try {
						Brassin currentBrew = brassinService
								.getElementById(Long.parseLong((String) request.getParameter("brassinID")));

						brewProc.record(currentBrew, request);

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {

					/*
					 * Creating new brew
					 */

					brewProc.record(new Brassin(), request);

				}
				break;

			case "step":

				Etape currentStep = null;

				String stepID = (String) request.getAttribute("step_id");

				if (stepID != null) {
					// Updating step

					try {
						currentStep = etapeService.getElementById(Long.parseLong(stepID));

						logger.info("Updating step with ID " + stepID);

						if (currentStep != null) {
							stepProc.record(currentStep, request);
						}
					} catch (Exception e) {

						e.printStackTrace();
					}
				} else {

					if (request.getParameter("bid") != null) {

						try {

							currentBrassin = brassinService.getElementById(Long.parseLong(request.getParameter("bid")));
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					stepProc.record(currentBrassin, request);

				}
				break;

			}

		}

		if (currentBrassin.getBra_id() > 0)
			response.sendRedirect("Accueil?bid=" + currentBrassin.getBra_id());
		else
			response.sendRedirect("Accueil");

	}

	public String generateJSON(List<Malt> malts, List<Houblon> hops, List<Levure> yeasts) {

		String JSONmalts = "";
		String JSONhops = "";
		String JSONyeasts = "";
		JSONmalts = JSONmalts + "\"ingredients\" : [";
		if (malts != null && malts.size() > 0) {
			for (Malt malt : malts) {
				JSONmalts = JSONmalts + "{\"typeIng\" :\"malt\",";

				JSONmalts = JSONmalts + "\"id\" : \"" + malt.getStb_id() + "\", " + "\"desc\" : \"" + malt.getIng_desc()
						+ "\", " + "\"cereale\" : \"" + malt.getSmal_cereale() + "\", " + "\"type\" : \""
						+ malt.getSmal_type() + "\", " + "\"qte\" : \"" + malt.getIng_quantite() + "\"},";
			}
		}
		if (hops != null && hops.size() > 0) {

			for (Houblon hop : hops) {
				JSONhops = JSONhops + "{\"typeIng\" :\"hop\",";

				JSONhops = JSONhops + "\"id\" : \"" + hop.getStb_id() + "\", " + "\"variete\" : \""
						+ hop.getShbl_variete() + "\", " + "\"acide_alpha\" : \"" + hop.getShbl_acide_alpha() + "\", "
						+ "\"qte\" : \"" + hop.getIng_quantite() + "\"},";
			}
		}
		if (yeasts != null && yeasts.size() > 0) {
			for (Levure yeast : yeasts) {
				JSONyeasts = JSONyeasts + "{\"typeIng\" :\"yeast\",";

				JSONyeasts = JSONyeasts + "\"id\" : \"" + yeast.getStb_id() + "\", " + "\"desc\" : \""
						+ yeast.getIng_desc() + "\", " + "\"qte\" : \"" + yeast.getIng_quantite() + "\"},";

			}
			JSONyeasts = JSONyeasts.substring(0, JSONyeasts.length() - 1);
		}
		JSONyeasts = JSONyeasts + "]";

		String result = "{" + JSONmalts + JSONhops + JSONyeasts + "}";
		result = result.replace(",]", "]");
		return result;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public Long getBrewid() {
		return brewid;
	}

	public void setBrewid(Long brewid) {
		this.brewid = brewid;
	}

	public List<SimpleMalt> getMaltList() {
		return maltList;
	}

	public void setMaltList(List<SimpleMalt> maltList) {
		this.maltList = maltList;
	}

	public List<SimpleHoublon> getHopList() {
		return hopList;
	}

	public void setHopList(List<SimpleHoublon> hopList) {
		this.hopList = hopList;
	}

	public List<SimpleLevure> getYeastList() {
		return yeastList;
	}

	public void setYeastList(List<SimpleLevure> yeastList) {
		this.yeastList = yeastList;
	}

	public Brassin getCurrentBrassin() {
		return currentBrassin;
	}

	public void setCurrentBrassin(Brassin currentBrassin) {
		this.currentBrassin = currentBrassin;
	}

	public IGenericService<Brassin> getBrassinService() {
		return brassinService;
	}

	public void setBrassinService(IGenericService<Brassin> brassinService) {
		this.brassinService = brassinService;
	}

	public ISpecificIngredientService getMaltIngSpecService() {
		return maltIngSpecService;
	}

	public void setMaltIngSpecService(ISpecificIngredientService maltIngSpecService) {
		this.maltIngSpecService = maltIngSpecService;
	}

	public IGenericService<Malt> getMaltService() {
		return maltService;
	}

	public void setMaltService(IGenericService<Malt> maltService) {
		this.maltService = maltService;
	}

	public IGenericService<Houblon> getHopService() {
		return hopService;
	}

	public void setHopService(IGenericService<Houblon> hopService) {
		this.hopService = hopService;
	}

	public IGenericService<Levure> getYeastService() {
		return yeastService;
	}

	public void setYeastService(IGenericService<Levure> yeastService) {
		this.yeastService = yeastService;
	}

	public ISpecificIngredientService getHopIngSpecService() {
		return hopIngSpecService;
	}

	public void setHopIngSpecService(ISpecificIngredientService hopIngSpecService) {
		this.hopIngSpecService = hopIngSpecService;
	}

	public ISpecificIngredientService getLevureIngSpecService() {
		return levureIngSpecService;
	}

	public void setLevureIngSpecService(ISpecificIngredientService levureIngSpecService) {
		this.levureIngSpecService = levureIngSpecService;
	}

	public IGenericService<Etape> getEtapeService() {
		return etapeService;
	}

	public void setEtapeService(IGenericService<Etape> etapeService) {
		this.etapeService = etapeService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

}