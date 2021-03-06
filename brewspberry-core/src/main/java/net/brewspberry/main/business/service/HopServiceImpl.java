package net.brewspberry.main.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificIngredientService;
import net.brewspberry.main.business.beans.brewing.Houblon;
import net.brewspberry.main.business.beans.brewing.SimpleHoublon;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.dao.SimpleHopDaoImpl;
import net.brewspberry.main.util.LogManager;

@Service (value="hopServiceImpl")
@Transactional
public class HopServiceImpl implements IGenericService<Houblon>, ISpecificIngredientService {

	
	@Autowired
	IGenericDao<Houblon> hopDao;
	@Autowired
	IGenericDao<SimpleHoublon> shopDao;
	
	
	
	static Logger logger = LogManager.getInstance(HopServiceImpl.class.getName());
	
	
	
	@Override
	public Houblon save(Houblon arg0) throws Exception {

		
			return hopDao.save(arg0);

	}

	@Override
	public Houblon update(Houblon arg0) throws DAOException {
		
		
			return hopDao.update(arg0);	
	}

	@Override
	public Houblon getElementById(long id) {
		
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
					currentIngredient.setStb_id((long) 0);
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

	@Override
	public Houblon getElementByName(String name) throws ServiceException {
		
		return null;
	}

}