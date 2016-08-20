package net.brewspberry.business.service;

import java.util.ArrayList;
import java.util.List;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificIngredientService;
import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.dao.SimpleMaltDAOImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("simpleMaltServiceImpl")
@Transactional
public class SimpleMaltServiceImpl implements IGenericService<SimpleMalt>,
		ISpecificIngredientService {

	@Autowired
	@Qualifier("simpleMaltDAOImpl")
	IGenericDao<SimpleMalt> maltDAO;

	@Override
	public SimpleMalt save(SimpleMalt arg0) throws Exception {
		// TODO Auto-generated method stub
		return maltDAO.save(arg0);
	}

	@Override
	public SimpleMalt update(SimpleMalt arg0) {
		// TODO Auto-generated method stub
		return maltDAO.update(arg0);
	}

	@Override
	public SimpleMalt getElementById(long id) {
		// TODO Auto-generated method stub
		return maltDAO.getElementById(id);
	}

	@Override
	public List<SimpleMalt> getAllElements() {
		// TODO Auto-generated method stub
		return maltDAO.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteElement(SimpleMalt arg0) {
		// TODO Auto-generated method stub
		maltDAO.deleteElement(arg0);
	}

	@Override
	public List<SimpleMalt> getAllDistinctElements() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

}
