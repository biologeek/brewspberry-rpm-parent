package net.brewspberry.main.business.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificIngredientService;
import net.brewspberry.main.business.ISpecificMaltDAO;
import net.brewspberry.main.business.beans.Malt;
import net.brewspberry.main.business.beans.SimpleMalt;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.dao.MaltDAOImpl;
import net.brewspberry.main.dao.SimpleMaltDAOImpl;

@Service (value="maltServiceImpl")
@Transactional
public class MaltServiceImpl implements IGenericService<Malt>,
		ISpecificMaltDAO, ISpecificIngredientService {

	@Autowired
	IGenericDao<SimpleMalt> smaltDAO;
	@Autowired
	IGenericDao<Malt> maltDAO;

	
	
	@Override
	public Malt save(Malt arg0) throws Exception {
		
		return maltDAO.save(arg0);
	}

	@Override
	public Malt update(Malt arg0) {
		
		return maltDAO.update(arg0);
	}

	@Override
	public Malt getElementById(long id) {
		
		return maltDAO.getElementById(id);
	}

	@Override
	public List<Malt> getAllElements() {
		
		return maltDAO.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		

	}

	@Override
	public void deleteElement(Malt arg0) {
		
		maltDAO.deleteElement(arg0);
	}

	@Override
	public List<Malt> getAllDistinctElements() {
		
		return maltDAO.getAllDistinctElements();
	}

	@Override
	public List<Malt> getIngredientFromArrayId(String[] array, String[] arrayQte, String[] arrayPrix) {
		List<Malt> result = new ArrayList<Malt>();

		if (array != null) {

			if (array.length != 0) {

				for (int i = 0; i < array.length; i++) {

					long currentID = 0;

					try {
						currentID = Long.parseLong(array[i]);
					} catch (Exception e) {
						e.printStackTrace();
					}

					Malt currentIngredient = new Malt();
					
					SimpleMalt currentSimpleMalt = smaltDAO.getElementById(currentID);

					currentIngredient.setIng_desc(currentSimpleMalt.getIng_desc());
					currentIngredient.setIng_disc(currentSimpleMalt.getIng_disc());
					currentIngredient.setIng_fournisseur(currentSimpleMalt.getIng_fournisseur());
					currentIngredient.setSmal_cereale(currentSimpleMalt.getSmal_cereale());
					currentIngredient.setSmal_couleur(currentSimpleMalt.getSmal_couleur());
					currentIngredient.setSmal_type(currentSimpleMalt.getSmal_type());
					currentIngredient.setStb_id(0);

					currentIngredient.setIng_quantite((float) 0.0);
					currentIngredient.setMalt_brassin(null);

					if (!currentIngredient.equals(new Malt())) {
						result.add(currentIngredient);
					}
				}

			}

		}

		return result;
	}

	@Override
	public Malt getElementByName(String name) throws ServiceException {
		
		return null;
	}

}
