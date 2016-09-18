package net.brewspberry.model;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.business.beans.SimpleLevure;
import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.util.LogManager;

@Component
public class SimpleMaltProcessor implements Processor<SimpleMalt> {

	private String ing_desc;
	private String ing_four;
	private String smal_cereale;
	private String smal_type;
	private int smal_couleur;
	

	@Autowired
	@Qualifier("simpleMaltServiceImpl")
	IGenericService<SimpleMalt> simpleMaltService; 
	@Autowired
	@Qualifier("simpleHopServiceImpl")
	IGenericService<SimpleHoublon> simpleHopService; 
	@Autowired
	@Qualifier("simpleYeastServiceImpl")
	IGenericService<SimpleLevure> simpleYeastService; 
	
	
	
	Logger logger = LogManager.getInstance(SimpleMaltProcessor.class.getName());
	
	@Override
	public boolean record(SimpleMalt parentObject, HttpServletRequest request) {
		
		 if (request != null){
			 
			 if (request.getParameter("ing_description") != null){
				 
				 ing_desc = (String) request.getParameter("ing_description");
				 
				 parentObject.setIng_desc(ing_desc);
			 } else {
				 logger.warning("Did not add ing_desc");
			 }
			 

			 if (request.getParameter("ing_fournisseur") != null){
				 
				 ing_four = (String) request.getParameter("ing_fournisseur");
				 
				 parentObject.setIng_fournisseur(ing_four);
			 } else {
				 logger.warning("Did not add ing_four");
			 }
			 
			 

			 if (request.getParameter("smal_cereale") != null){
				 
				 smal_cereale = (String) request.getParameter("smal_cereale");
				 
				 parentObject.setSmal_cereale(smal_cereale);
			 } else {
				 logger.warning("Did not add smal_cereale");
			 }
			 

			 if (request.getParameter("smal_type") != null){
				 
				 smal_type = (String) request.getParameter("smal_type");
				 
				 parentObject.setSmal_type(smal_type);
			 } else {
				 logger.warning("Did not add smal_type");
			 }
			 
			 if (request.getParameter("smal_couleur") != null){
				 
				 smal_couleur = Integer.parseInt((String) request.getParameter("smal_couleur"));
				 
				 parentObject.setSmal_couleur(smal_couleur);
			 }
			 

			 
			 if (parentObject.getStb_id() == 0){
				 
				 try {
					 
					 logger.info("Saving Malt");
					simpleMaltService.save(parentObject);
					return true;

				} catch (Exception e) {
					
					e.printStackTrace();
					return false;

				}
				 				 
			 }
			 else if (parentObject.getStb_id() > 0){
				 logger.info("Updating Malt");

				 simpleMaltService.update(parentObject);
				return true;

			 }
			 else {
				 try {
					throw new Exception("Ingredient ID is not correct : "+parentObject.getStb_id());
				} catch (Exception e) {

					logger.severe("Ingredient ID is not correct : "+parentObject.getStb_id());
					
				}
					return false;

			 }
			 
		 }
		
		return false;
	}

	public String getIng_desc() {
		return ing_desc;
	}

	public void setIng_desc(String ing_desc) {
		this.ing_desc = ing_desc;
	}

	public String getIng_four() {
		return ing_four;
	}

	public void setIng_four(String ing_four) {
		this.ing_four = ing_four;
	}

	public String getSmal_cereale() {
		return smal_cereale;
	}

	public void setSmal_cereale(String smal_cereale) {
		this.smal_cereale = smal_cereale;
	}

	public String getSmal_type() {
		return smal_type;
	}

	public void setSmal_type(String smal_type) {
		this.smal_type = smal_type;
	}

	public int getSmal_couleur() {
		return smal_couleur;
	}

	public void setSmal_couleur(int smal_couleur) {
		this.smal_couleur = smal_couleur;
	}

	public IGenericService<SimpleMalt> getSimpleMaltService() {
		return simpleMaltService;
	}

	public void setSimpleMaltService(IGenericService<SimpleMalt> simpleMaltService) {
		this.simpleMaltService = simpleMaltService;
	}

	public IGenericService<SimpleHoublon> getSimpleHopService() {
		return simpleHopService;
	}

	public void setSimpleHopService(IGenericService<SimpleHoublon> simpleHopService) {
		this.simpleHopService = simpleHopService;
	}

	public IGenericService<SimpleLevure> getSimpleYeastService() {
		return simpleYeastService;
	}

	public void setSimpleYeastService(
			IGenericService<SimpleLevure> simpleYeastService) {
		this.simpleYeastService = simpleYeastService;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}
