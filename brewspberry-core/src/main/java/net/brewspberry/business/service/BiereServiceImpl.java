package net.brewspberry.business.service;

import java.util.List;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificBiereDAO;
import net.brewspberry.business.beans.Biere;
import net.brewspberry.dao.BiereDaoImpl;
import net.brewspberry.exceptions.DAOException;

public class BiereServiceImpl implements IGenericDao<Biere>, ISpecificBiereDAO{

	
	IGenericDao<Biere> biereDAO = new BiereDaoImpl ();
	ISpecificBiereDAO specBiereDAO = new BiereDaoImpl ();
	@Override
	public Biere save(Biere arg0) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Biere update(Biere arg0) {
		// TODO Auto-generated method stub
		return biereDAO.update(arg0);
	}

	@Override
	public Biere getElementById(long id) {
		return biereDAO.getElementById(id);
	}

	@Override
	public List<Biere> getAllElements() {
		// TODO Auto-generated method stub
		return biereDAO.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		biereDAO.deleteElement(id);
		
	}

	@Override
	public void deleteElement(Biere arg0) {
		// TODO Auto-generated method stub
		biereDAO.deleteElement(arg0);
	}

	@Override
	public List<Biere> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return biereDAO.getAllDistinctElements();
	}

	@Override
	public Biere getElementByName(String name) {
		return biereDAO.getElementByName(name);
	}

}
