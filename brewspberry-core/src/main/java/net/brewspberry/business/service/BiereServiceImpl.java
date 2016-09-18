package net.brewspberry.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificBiereDAO;
import net.brewspberry.business.beans.Biere;
import net.brewspberry.business.exceptions.DAOException;

@Service 
@Transactional
public class BiereServiceImpl implements IGenericDao<Biere>, ISpecificBiereDAO{

	@Autowired
	IGenericDao<Biere> biereDAO;
	@Autowired
	ISpecificBiereDAO specBiereDAO;
	
	@Override
	public Biere save(Biere arg0) throws DAOException {
		
		return null;
	}

	@Override
	public Biere update(Biere arg0) {
		
		return biereDAO.update(arg0);
	}

	@Override
	public Biere getElementById(long id) {
		return biereDAO.getElementById(id);
	}

	@Override
	public List<Biere> getAllElements() {
		
		return biereDAO.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		biereDAO.deleteElement(id);
		
	}

	@Override
	public void deleteElement(Biere arg0) {
		
		biereDAO.deleteElement(arg0);
	}

	@Override
	public List<Biere> getAllDistinctElements() {
		
		return biereDAO.getAllDistinctElements();
	}

	@Override
	public Biere getElementByName(String name) {
		return biereDAO.getElementByName(name);
	}

}
