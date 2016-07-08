package net.brewspberry.business.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificIngredientService;
import net.brewspberry.business.ISpecificMaltDAO;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.dao.MaltDAOImpl;
import net.brewspberry.dao.SimpleMaltDAOImpl;

@Service(value="maltServiceImpl")
public class MaltServiceImpl implements IGenericService<Malt>,
		ISpecificMaltDAO, ISpecificIngredientService {

	@Autowired
	IGenericDao<SimpleMalt> smaltDAO;
	@Autowired
	IGenericDao<Malt> maltDAO;

	@Override
	public Malt save(Malt arg0) throws Exception {
		// TODO Auto-generated method stub
		return maltDAO.save(arg0);
	}

	@Override
	public Malt update(Malt arg0) {
		// TODO Auto-generated method stub
		return maltDAO.update(arg0);
	}

	@Override
	public Malt getElementById(long id) {
		// TODO Auto-generated method stub
		return maltDAO.getElementById(id);
	}

	@Override
	public List<Malt> getAllElements() {
		// TODO Auto-generated method stub
		return maltDAO.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteElement(Malt arg0) {
		// TODO Auto-generated method stub
		maltDAO.deleteElement(arg0);
	}

	@Override
	public List<Malt> getAllDistinctElements() {
		// TODO Auto-generated method stub
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


	@Service("simpleMaltServiceImpl")
	public class SimpleMaltServiceImpl implements IGenericService<SimpleMalt>,
			ISpecificIngredientService {

		IGenericDao<SimpleMalt> maltDAO = new SimpleMaltDAOImpl();

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

	}
}
