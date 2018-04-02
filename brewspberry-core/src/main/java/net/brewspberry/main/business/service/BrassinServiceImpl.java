package net.brewspberry.main.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificBrassinService;
import net.brewspberry.main.business.beans.brewing.Biere;
import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.BrewStatus;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.dao.BrassinDaoImpl;
import net.brewspberry.main.data.ISpecificBrassinDAO;
import net.brewspberry.main.util.HibernateUtil;

@Service
@Transactional
public class BrassinServiceImpl implements IGenericService<Brassin>, ISpecificBrassinService {

	@Autowired
	IGenericDao<Brassin> brassinDAO;
	@Autowired
	ISpecificBrassinDAO specBrassinDAO;

	@Override
	public Brassin save(Brassin arg0) throws Exception {

		arg0.setBra_date_maj(new Date());

		return brassinDAO.save(arg0);
	}

	@Override
	public Brassin update(Brassin arg0) {
		arg0.setBra_date_maj(new Date());

		return brassinDAO.update(arg0);
	}

	@Override
	public Brassin getElementById(long id) {
		return brassinDAO.getElementById(id);
	}

	@Override
	public List<Brassin> getAllElements() {

		return brassinDAO.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		brassinDAO.deleteElement(id);

	}

	@Override
	public void deleteElement(Brassin arg0) {

		brassinDAO.deleteElement(arg0);
	}

	@Override
	public List<Brassin> getAllDistinctElements() {

		return brassinDAO.getAllDistinctElements();
	}

	@Override
	public Brassin getBrassinByBeer(Biere beer) {

		return specBrassinDAO.getBrassinByBeer(beer);
	}

	@Override
	public Brassin getElementByName(String name) throws ServiceException {

		return null;
	}

	@Override
	public List<Brassin> getActiveBrews() {

		List<BrewStatus> brewstatuses = new ArrayList<BrewStatus>();

		brewstatuses.add(BrewStatus.BREWING);
		brewstatuses.add(BrewStatus.FERMENTING);
		brewstatuses.add(BrewStatus.MATURING);
		brewstatuses.add(BrewStatus.BOTTLED);

		return getBrewsByStates(brewstatuses);
	}

	/**
	 * Returns brews filtered by states
	 * 
	 * @param statuses
	 * @return
	 */
	private List<Brassin> getBrewsByStates(List<BrewStatus> statuses) {

		return specBrassinDAO.getBrewByStates(statuses);
	}

}
