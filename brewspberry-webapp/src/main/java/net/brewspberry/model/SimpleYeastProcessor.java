package net.brewspberry.model;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.SimpleLevure;
import net.brewspberry.model.Processor;
import net.brewspberry.util.LogManager;

@Component
public class SimpleYeastProcessor implements Processor<SimpleLevure> {

	private Logger logger = LogManager.getInstance(SimpleHopProcessor.class.getName());
	@Autowired
	@Qualifier("simpleYeastServiceImpl")
	IGenericService<SimpleLevure> simpleYeastService; 

	

	private String ing_desc;
	private String ing_four;
	private String slev_espece;
    private String slev_floculation;
    private String slev_aromes;
    
    
	@Override
	public boolean record(SimpleLevure parentObject, HttpServletRequest request) {

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
			 
			 

			 if (request.getParameter("slev_espece") != null){
				 
				 slev_espece = (String) request.getParameter("slev_espece");
				 
				 parentObject.setSlev_espece(slev_espece);
			 } else {
				 logger.warning("Did not add slev_espece");
			 }
			 

			 if (request.getParameter("slev_floculation") != null){
				 
				 slev_floculation = (String) request.getParameter("slev_floculation");
				 
				 parentObject.setSlev_floculation(slev_floculation);
			 } else {
				 logger.warning("Did not add slev_floculation");
			 }

			 if (request.getParameter("slev_aromes") != null){
				 
				 slev_aromes = (String) request.getParameter("slev_aromes");
				 
				 parentObject.setSlev_aromes(slev_aromes);
			 } else {
				 logger.warning("Did not add slev_aromes");
			 }

			 
			 if (parentObject.getStb_id() == 0){
				 
				 // Saving yeast
				 try {
					simpleYeastService.save(parentObject);
					return true;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;

				}
				 				 
			 }
			 else if (parentObject.getStb_id() > 0){
				 
				 // Updating yeast
				 
				 simpleYeastService.update(parentObject);
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


	public Logger getLogger() {
		return logger;
	}


	public void setLogger(Logger logger) {
		this.logger = logger;
	}


	public IGenericService<SimpleLevure> getSimpleYeastService() {
		return simpleYeastService;
	}


	public void setSimpleYeastService(
			IGenericService<SimpleLevure> simpleYeastService) {
		this.simpleYeastService = simpleYeastService;
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


	public String getSlev_espece() {
		return slev_espece;
	}


	public void setSlev_espece(String slev_espece) {
		this.slev_espece = slev_espece;
	}


	public String getSlev_floculation() {
		return slev_floculation;
	}


	public void setSlev_floculation(String slev_floculation) {
		this.slev_floculation = slev_floculation;
	}


	public String getSlev_aromes() {
		return slev_aromes;
	}


	public void setSlev_aromes(String slev_aromes) {
		this.slev_aromes = slev_aromes;
	}

}
