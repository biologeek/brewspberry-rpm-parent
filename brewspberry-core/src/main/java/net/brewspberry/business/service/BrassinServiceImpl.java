package net.brewspberry.business.service;

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
		// TODO Auto-generated method stub
		return brassinDAO.update(arg0);
	}

	@Override
	public Brassin getElementById(long id) {
		return brassinDAO.getElementById(id);
	}

	@Override
	public List<Brassin> getAllElements() {
		// TODO Auto-generated method stub
		return brassinDAO.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		brassinDAO.deleteElement(id);

	}

	@Override
	public void deleteElement(Brassin arg0) {
		// TODO Auto-generated method stub
		brassinDAO.deleteElement(arg0);
	}

	@Override
	public List<Brassin> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return brassinDAO.getAllDistinctElements();
	}

	@Override
	public Brassin getBrassinByBeer(Biere beer) {
		// TODO Auto-generated method stub
		return specBrassinDAO.getBrassinByBeer(beer);
	}

	

}
