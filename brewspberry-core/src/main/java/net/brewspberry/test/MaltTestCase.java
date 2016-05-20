package net.brewspberry.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.service.MaltServiceImpl;
import net.brewspberry.dao.MaltDAOImpl;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MaltTestCase {
/*
	static IGenericService<Malt> service=new MaltServiceImpl();
	
	Malt malt = new Malt();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		malt.setIng_fournisseur("MaltEurop");
		/*malt.setIng_prix(9.89);
		malt.setIng_quantite(10.0);
		malt.setMalt_cereale("Orge");
		malt.setMalt_couleur(5);
		malt.setMalt_type("blond");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAllDistinct() {
		System.out.println(service.getAllDistinctElements().getClass());
		
	}
	@Test
	public void testAll(){
		System.out.println(service.getAllDistinctElements());

	}
	@Test
	public void testById(){
		System.out.println(service.getElementById(1));

	}
	
	public void testSave(){
		try {
			System.out.println(service.save(malt));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
*/
}
