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
import net.brewspberry.main.business.beans.brewing.SimpleMalt;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.dao.SimpleMaltDAOImpl;


@Service("simpleMaltServiceImpl")
@Transactional
public class SimpleMaltServiceImpl implements IGenericService<SimpleMalt>,
		ISpecificIngredientService {

	@Autowired
	@Qualifier("simpleMaltDAOImpl")
	IGenericDao<SimpleMalt> maltDAO;

	@Override
	public SimpleMalt save(SimpleMalt arg0) throws Exception {
		
		return maltDAO.save(arg0);
	}

	@Override
	public SimpleMalt update(SimpleMalt arg0) {
		
		return maltDAO.update(arg0);
	}

	@Override
	public SimpleMalt getElementById(long id) {
		
		return maltDAO.getElementById(id);
	}

	@Override
	public List<SimpleMalt> getAllElements() {
		
		return maltDAO.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		

	}

	@Override
	public void deleteElement(SimpleMalt arg0) {
		
		maltDAO.deleteElement(arg0);
	}

	@Override
	public List<SimpleMalt> getAllDistinctElements() {
		
		return maltDAO.getAllDistinctElements();
	}

	@Override
	public List<SimpleMalt> getIngredientFromArrayId(String[] array, String[] arrayQte, String[] arrayPrix) {
		List<SimpleMalt> result = new ArrayList<SimpleMalt>();

		if (array != null) {

			if (array.length != 0) {

				for (int i = 0; i < array.length; i++) {

					long currentID = 0;

					try {
						currentID = Long.parseLong(array[i]);
					} catch (Exception e) {
						e.printStackTrace();
					}

					SimpleMalt currentIngredient = maltDAO
							.getElementById(currentID);

					currentIngredient.setStb_id(0);

					if (!currentIngredient.equals(new SimpleMalt())) {
						result.add(currentIngredient);
					}
				}

			}

		}

		return result;
	}

	@Override
	public SimpleMalt getElementByName(String name) throws ServiceException {
		
		return null;
	}

}
