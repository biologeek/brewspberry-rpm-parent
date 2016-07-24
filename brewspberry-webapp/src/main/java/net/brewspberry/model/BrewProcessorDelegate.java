package net.brewspberry.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.dialect.function.StandardAnsiSqlAggregationFunctions;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificIngredientService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.beans.SimpleLevure;
import net.brewspberry.business.service.BrassinServiceImpl;
import net.brewspberry.business.service.HopServiceImpl;
import net.brewspberry.business.service.MaltServiceImpl;
import net.brewspberry.business.service.YeastServiceImpl;
import net.brewspberry.util.LogManager;

public class BrewProcessorDelegate implements Processor<Brassin> {

	@Autowired
	@Qualifier("BrassinServiceImpl")
	IGenericService<Brassin> brassinService;
	@Autowired
	@Qualifier("BrassinServiceImpl")
	IGenericService<Malt> maltService;
	@Autowired
	@Qualifier("BrassinServiceImpl")
	IGenericService<Houblon> hopService;
	@Autowired
	@Qualifier("BrassinServiceImpl")
	IGenericService<Levure> yeastService;

	@Autowired
	ISpecificIngredientService maltIngSpecService;
	@Autowired
	ISpecificIngredientService hopIngSpecService;
	@Autowired
	ISpecificIngredientService levureIngSpecService;

	private Logger logger = LogManager.getInstance(BrewProcessorDelegate.class.getName());

	@Override
	public boolean record(Brassin currentBrassin, HttpServletRequest request) {

		String currentDateDebut;
		String currentDateFin;
		String currentBrassinNom;
		String currentBrassinStatut;
		String currentBrassinQte = "";
		String[] currentBrassinHoublonsPrix = null;
		String[] currentBrassinLevuresPrix = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

		// Date de début du brassin
		if (request.getParameter("dateDebut") != null) {

			if (currentBrassin == null) {
				currentBrassin = new Brassin();
			}
			try {
				currentDateDebut = request.getParameter("dateDebut");
				System.out.println(currentDateDebut);
				System.out.println(sdf.toString());
				System.out.println(currentBrassin.toString());

				currentBrassin.setBra_debut(sdf.parse(currentDateDebut));

			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
		// Date de fin du brassin

		if (request.getParameter("dateFin") != null) {

			try {

				currentDateFin = request.getParameter("dateFin");

				currentBrassin.setBra_fin(sdf.parse(currentDateFin));

			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		// "Nom" du brassin
		if (request.getParameter("brassinNom") != null) {

			try {

				currentBrassinNom = request.getParameter("brassinNom");

				currentBrassin.setBra_nom(currentBrassinNom);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		// Quantité finale du brassin
		if (request.getParameter("brassinQte") != null) {

			try {

				currentBrassinQte = request.getParameter("brassinQte");

				System.out.println("Qte : " + currentBrassinQte);
				currentBrassin.setBra_quantiteEnLitres(Double.parseDouble(currentBrassinQte));

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		// Statut du brassin
		if (request.getParameter("statutBrassin") != null) {

			try {

				currentBrassinStatut = request.getParameter("statutBrassin");

				if (currentBrassinStatut.equals(""))
					currentBrassin.setBra_statut(10);
				else
					currentBrassin.setBra_statut(Integer.parseInt(currentBrassinStatut));

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		// Malts et quantités
		if ((request.getParameterValues("malt") != null) && (request.getParameterValues("maltQte") != null)) {

			try {

				setBraMaltsFromServletRequestParameters(currentBrassin, request);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		// Récupération des houblons

		if ((request.getParameterValues("houblon") != null) && (request.getParameterValues("houblonQte") != null)
				&& (request.getParameterValues("houblonType") != null)) {

			try {

				setBrewHopsFromServletRequestParameters(currentBrassin, request, currentBrassinHoublonsPrix);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		// Récupération des levures

		if ((request.getParameterValues("levure") != null) && (request.getParameterValues("levureQte") != null)) {

			try {

				setBrewYeastsFromServletRequestParameters(currentBrassin, request, currentBrassinLevuresPrix);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		// If not null => updates brew
		if (request.getParameter("brassinID") != null && request.getParameter("brassinID") != "") {

			try {
				Long.parseLong(request.getParameter("brassinID"));

				currentBrassin.setBra_id(Long.parseLong(request.getParameter("brassinID")));
			} catch (Exception e) {

				logger.severe(request.getParameter("brassinID") + " is not a number !!");

				return false;

			}

		}

		currentBrassin.setBra_date_maj(new Date());

		// Enregistrement du brassin
		try {
			return recordingBrewAndAssociatedIngredients(currentBrassin);
		} catch (Exception e) {

			e.printStackTrace();
			logger.severe("Got an error while saving brew or ingredients...");

			return false;
		}

	}

	/**
	 * Delegate function for recording brew and ingredients
	 * 
	 * @param currentBrassin
	 * @return
	 * @throws Exception
	 */
	private boolean recordingBrewAndAssociatedIngredients(Brassin currentBrassin) throws Exception {
		if (currentBrassin.getBra_id() > 0) {
			currentBrassin = brassinService.update(currentBrassin);
			logger.info("Updating Brassin with ID " + currentBrassin.getBra_id());
		} else {
			currentBrassin = brassinService.save(currentBrassin);
			logger.info("Saving Brassin with ID " + currentBrassin.getBra_id());
		}
		// Enregistrement des malts
		for (Malt malt : currentBrassin.getBra_malts()) {

			if (malt != null)
				maltService.save(malt);
			else
				throw new NullPointerException();
		}

		// Enregistrement des houblons
		for (Houblon hop : currentBrassin.getBra_houblons()) {

			if (hop != null)
				hopService.save(hop);
			else {
				throw new NullPointerException();
			}
		}

		// Enregistrement des levures
		for (Levure lev : currentBrassin.getBra_levures()) {

			if (lev != null)
				yeastService.save(lev);
			else
				throw new NullPointerException();
		}
		return true;
	}

	/**
	 * Delegate method that builds brew yeasts from request parameters
	 * 
	 * @param currentBrassin
	 * @param request
	 * @param currentBrassinLevuresPrix
	 * @throws Exception
	 */
	private void setBrewYeastsFromServletRequestParameters(Brassin currentBrassin, HttpServletRequest request,
			String[] currentBrassinLevuresPrix) throws Exception {
		String[] currentBrassinLevures;
		String[] currentBrassinLevuresQte;
		currentBrassinLevures = request.getParameterValues("levure");
		currentBrassinLevuresQte = request.getParameterValues("levureQte");

		List<Levure> levs = levureIngSpecService.getIngredientFromArrayId(currentBrassinLevures,
				currentBrassinLevuresQte, currentBrassinLevuresPrix);
		currentBrassin.setBra_levures(levs);

		int i = 0;
		if (currentBrassinLevures.length == currentBrassinLevuresQte.length) {
			for (Levure lev : currentBrassin.getBra_levures()) {
				// Pour chaque levure enregistrée, on définit la
				// quantité
				logger.info(
						"Got " + currentBrassinLevures.length + " yeasts, brew yeast id=" + currentBrassinLevures[i]);

				lev.setIng_quantite(Float.parseFloat(currentBrassinLevuresQte[i]));

			}
		} else {
			throw new Exception(currentBrassinLevuresQte.length + " quantités et " + currentBrassinLevures.length
					+ " levures recus !");
		}
	}

	/**
	 * Delegate method to set brew hops from request parameters
	 * 
	 * @param currentBrassin
	 * @param request
	 * @param currentBrassinHoublonsPrix
	 * @throws Exception
	 */
	private void setBrewHopsFromServletRequestParameters(Brassin currentBrassin, HttpServletRequest request,
			String[] currentBrassinHoublonsPrix) throws Exception {
		String[] currentBrassinHoublons;
		String[] currentBrassinHoublonsQte;
		String[] currentBrassinHoublonsType;
		currentBrassinHoublons = request.getParameterValues("houblon");
		currentBrassinHoublonsQte = request.getParameterValues("houblonQte");
		currentBrassinHoublonsType = request.getParameterValues("houblonType");

		currentBrassin.setBra_houblons(hopIngSpecService.getIngredientFromArrayId(currentBrassinHoublons,
				currentBrassinHoublonsQte, currentBrassinHoublonsPrix));

		int i = 0;
		if ((currentBrassinHoublons.length == currentBrassinHoublonsQte.length)
				&& (currentBrassinHoublons.length == currentBrassinHoublonsType.length)) {
			for (Houblon houblon : currentBrassin.getBra_houblons()) {
				// Pour chaque houblon enregistré, on définit la
				// quantité

				logger.fine("Got " + currentBrassinHoublons.length + " hops, brew hop id=" + currentBrassinHoublons[i]);
				houblon.setIng_quantite(Float.parseFloat(currentBrassinHoublonsQte[i]));
				houblon.setShbl_type(Integer.parseInt(currentBrassinHoublonsType[i]));
			}

		} else {
			throw new Exception(currentBrassinHoublonsQte.length + " quantités et " + currentBrassinHoublons.length
					+ " houblons recus !");
		}
	}

	/**
	 * Delegate method to set brew malts from request parameters
	 * 
	 * @param currentBrassin
	 * @param request
	 * @throws Exception
	 */
	private void setBraMaltsFromServletRequestParameters(Brassin currentBrassin, HttpServletRequest request)
			throws Exception {
		String[] currentBrassinMalts;
		String[] currentBrassinMaltsQte;
		String[] currentBrassinMaltsPrix;
		currentBrassinMalts = request.getParameterValues("malt");
		currentBrassinMaltsQte = request.getParameterValues("maltQte");
		currentBrassinMaltsPrix = request.getParameterValues("maltsPrix");

		currentBrassin.setBra_malts(maltIngSpecService.getIngredientFromArrayId(currentBrassinMalts,
				currentBrassinMaltsQte, currentBrassinMaltsPrix));

		int i = 0;
		if (currentBrassinMalts.length == currentBrassinMaltsQte.length) {

			// Pour chaque malt enregistré, on définit la quantité
			for (Malt malt : currentBrassin.getBra_malts()) {
				logger.fine("Got " + currentBrassinMalts.length + " malts, brew mid=" + currentBrassinMalts[i]);

				malt.setIng_quantite(Float.parseFloat(currentBrassinMaltsQte[i]));

			}
		} else {
			throw new Exception(
					currentBrassinMaltsQte.length + " quantités et " + currentBrassinMalts.length + " malts recus !");
		}
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

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}