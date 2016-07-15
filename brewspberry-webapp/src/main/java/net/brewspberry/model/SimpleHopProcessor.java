package net.brewspberry.model;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.util.LogManager;

public class SimpleHopProcessor implements Processor<SimpleHoublon> {

	private Logger logger = LogManager.getInstance(SimpleHopProcessor.class.getName());
	@Autowired
	IGenericService<SimpleHoublon> simpleHopService; 

	
	
	private String ing_desc;
	private String ing_four;
	private String shbl_variete;
	private double shbl_acide_alpha;
	private String shbl_aromes;
	private Integer shbl_type;
	

	@Override
	public Boolean record(SimpleHoublon parentObject, HttpServletRequest request) {

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
			 
			 

			 if (request.getParameter("shbl_variete") != null){
				 
				 shbl_variete = (String) request.getParameter("shbl_variete");
				 
				 parentObject.setShbl_variete(shbl_variete);
			 } else {
				 logger.warning("Did not add shbl_variete");
			 }
			 

			 if (request.getParameter("shbl_acide_alpha") != null){
				 
				 shbl_acide_alpha = Double.parseDouble((String) request.getParameter("shbl_acide_alpha"));
				 
				 parentObject.setShbl_acide_alpha(shbl_acide_alpha);
			 } else {
				 logger.warning("Did not add shbl_acide_alpha");
			 }

			 if (request.getParameter("shbl_aromes") != null){
				 
				 shbl_aromes = (String) request.getParameter("shbl_aromes");
				 
				 parentObject.setShbl_aromes(shbl_aromes);
			 } else {
				 logger.warning("Did not add shbl_aromes");
			 }

			 if (request.getParameter("shbl_type") != null){
				 
				 shbl_type = Integer.parseInt((String) request.getParameter("shbl_type"));
				 
				 parentObject.setShbl_type(shbl_type);
			 } else {
				 logger.warning("Did not add shbl_type");
			 }
			 

			 
			 if (parentObject.getStb_id() == 0){
				 
				 try {
					simpleHopService.save(parentObject);
					return true;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;

				}
				 				 
			 }
			 else if (parentObject.getStb_id() > 0){
				 
				 simpleHopService.update(parentObject);
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


	public IGenericService<SimpleHoublon> getSimpleHopService() {
		return simpleHopService;
	}


	public void setSimpleHopService(IGenericService<SimpleHoublon> simpleHopService) {
		this.simpleHopService = simpleHopService;
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


	public String getShbl_variete() {
		return shbl_variete;
	}


	public void setShbl_variete(String shbl_variete) {
		this.shbl_variete = shbl_variete;
	}


	public double getShbl_acide_alpha() {
		return shbl_acide_alpha;
	}


	public void setShbl_acide_alpha(double shbl_acide_alpha) {
		this.shbl_acide_alpha = shbl_acide_alpha;
	}


	public String getShbl_aromes() {
		return shbl_aromes;
	}


	public void setShbl_aromes(String shbl_aromes) {
		this.shbl_aromes = shbl_aromes;
	}


	public Integer getShbl_type() {
		return shbl_type;
	}


	public void setShbl_type(Integer shbl_type) {
		this.shbl_type = shbl_type;
	}

}
