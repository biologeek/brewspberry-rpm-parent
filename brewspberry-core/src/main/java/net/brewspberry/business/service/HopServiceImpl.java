package net.brewspberry.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificIngredientService;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.dao.HopDaoImpl;
import net.brewspberry.dao.SimpleHopDaoImpl;
import net.brewspberry.util.LogManager;

public class HopServiceImpl implements IGenericService<Houblon>, ISpecificIngredientService {

	
	IGenericDao<Houblon> hopDao = new HopDaoImpl ();
	IGenericDao<SimpleHoublon> shopDao = new SimpleHopDaoImpl ();
	
	
	
	static Logger logger = LogManager.getInstance(HopServiceImpl.class.getName());
	
	
	
	@Override
	public Houblon save(Houblon arg0) throws Exception {

		
			return hopDao.save(arg0);

	}

	@Override
	public Houblon update(Houblon arg0) {
		
		
			return hopDao.update(arg0);	
	}

	@Override
	public Houblon getElementById(long id) {
		// TODO Auto-generated method stub
		return hopDao.getElementById(id);
	}

	@Override
	public List<Houblon> getAllElements() {

			return (List<Houblon>) hopDao.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		hopDao.deleteElement(id);
		
	}

	@Override
	public void deleteElement(Houblon arg0) {
		if (arg0 instanceof Houblon)
			hopDao.deleteElement((Houblon) arg0);
		else
			hopDao.deleteElement(arg0);

	}

	@Override
	public List<Houblon> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return hopDao.getAllDistinctElements();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Houblon> getIngredientFromArrayId(String[] array, String[] arrayQte, String[] arrayPrix) {
		List<Houblon> result = new ArrayList<Houblon>();
		
		
		if (array != null) {
			
			if (array.length != 0){
				
				for (int i = 0 ; i < array.length ; i++){
					
					String currentName = "";
					long currentNameNumeric = 0;
					System.out.println(array);
					try {
						currentName = array[i];
						currentNameNumeric = Long.parseLong(currentName);
					} catch (Exception e) {
						e.printStackTrace();
					}
						logger.fine("Getting hopName "+currentName);
					
					Houblon currentIngredient = new Houblon();
					
					SimpleHoublon currentSimpleHop = shopDao.getElementById(currentNameNumeric);
					

					currentIngredient.setIng_desc(currentSimpleHop.getIng_desc());
					currentIngredient.setIng_fournisseur(currentSimpleHop.getIng_fournisseur());
					currentIngredient.setIng_disc(currentSimpleHop.getIng_disc());
					currentIngredient.setShbl_acide_alpha(currentSimpleHop.getShbl_acide_alpha());
					currentIngredient.setShbl_aromes(currentSimpleHop.getShbl_aromes());
					currentIngredient.setShbl_type(currentSimpleHop.getShbl_type());
					currentIngredient.setShbl_variete(currentSimpleHop.getShbl_variete());
					
					//Resetting ID so that it is saved when creating new brew
					currentIngredient.setIng_id((long) 0);
					//currentIngredient.getIng_ingredientEtape().(0.0);
					currentIngredient.setHbl_brassin(null);
					
					if (currentIngredient != null){
						if (!currentIngredient.equals(new Houblon())){
							result.add(currentIngredient);	
						}			
					}
				}
				
			}
			
		}
		
		
		return result;
	}

	public class SimpleHopServiceImpl implements IGenericService<SimpleHoublon>, ISpecificIngredientService {

		
		IGenericDao<SimpleHoublon> sHopDao = new SimpleHopDaoImpl ();
		
		final Logger logger = LogManager.getInstance(SimpleHopServiceImpl.class.getName());
		@Override
		public SimpleHoublon save(SimpleHoublon arg0) throws Exception {

				return sHopDao.save(arg0);

		}

		@Override
		public SimpleHoublon update(SimpleHoublon arg0) {
			
				return sHopDao.update(arg0);	
		}

		@Override
		public SimpleHoublon getElementById(long id) {
			// TODO Auto-generated method stub
			return sHopDao.getElementById(id);
		}

		@Override
		public List<SimpleHoublon> getAllElements() {

				return (List<SimpleHoublon>) sHopDao.getAllElements();
		}

		@Override
		public void deleteElement(long id) {
			sHopDao.deleteElement(id);
			
		}

		@Override
		public void deleteElement(SimpleHoublon arg0) {
			
				sHopDao.deleteElement(arg0);

		}

		@Override
		public List<SimpleHoublon> getAllDistinctElements() {
			// TODO Auto-generated method stub
			return sHopDao.getAllDistinctElements();
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<SimpleHoublon> getIngredientFromArrayId(String[] array, String[] arrayQte, String[] arrayPrix) {
			List<SimpleHoublon> result = new ArrayList<SimpleHoublon>();
			
			
			if (array != null) {
				
				if (array.length != 0){
					
					for (int i = 0 ; i < array.length ; i++){
						
						String currentName = "";
						long currentNameNumeric = 0;
						System.out.println(array);
						try {
							currentName = array[i];
							currentNameNumeric = Long.parseLong(currentName);
						} catch (Exception e) {
							e.printStackTrace();
						}
							logger.fine("Getting hopName "+currentName);
						
						SimpleHoublon currentIngredient = hopDao.getElementById(currentNameNumeric);
						
						//Resetting ID so that it is saved when creating new brew
						currentIngredient.setIng_id((long) 0);
						//currentIngredient.getIng_ingredientEtape().(0.0);
						
						if (currentIngredient != null){
							if (!currentIngredient.equals(new Houblon())){
								result.add(currentIngredient);	
							}			
						}
					}
					
				}
				
			}
			
			
			return result;
		}

		
		
		


	}

	
	


}