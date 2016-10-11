package net.brewspberry.business.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificBrassinDAO;
import net.brewspberry.business.ISpecificBrassinService;
import net.brewspberry.business.beans.Biere;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.BrewStatus;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.dao.BrassinDaoImpl;
import net.brewspberry.util.HibernateUtil;

@Service 
@Transactional
public class BrassinServiceImpl implements IGenericService<Brassin>,
		ISpecificBrassinService {

	@Autowired
	IGenericDao<Brassin> brassinDAO;
	@Autowired
	ISpecificBrassinDAO specBrassinDAO;

	@Override
	public Brassin save(Brassin arg0) throws Exception {
		
		return brassinDAO.save(arg0);
	}

	@Override
	public Brassin update(Brassin arg0) {
		
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
