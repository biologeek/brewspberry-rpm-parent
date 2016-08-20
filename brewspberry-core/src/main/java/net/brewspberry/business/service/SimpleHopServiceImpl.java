package net.brewspberry.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificIngredientService;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.dao.SimpleHopDaoImpl;
import net.brewspberry.util.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("simpleHopServiceImpl")
@Transactional
public class SimpleHopServiceImpl implements IGenericService<SimpleHoublon>, ISpecificIngredientService {

	
	IGenericDao<SimpleHoublon> sHopDao = new SimpleHopDaoImpl ();
	
	final Logger logger = LogManager.getInstance(SimpleHopServiceImpl.class.getName());

	private IGenericDao<Houblon> hopDao;
	
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
					currentIngredient.setStb_id((long) 0);
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

	@Override
	public SimpleHoublon getElementByName(String name) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}


}
