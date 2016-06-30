package net.brewspberry.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.test.util.config.SpringCoreTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfiguration.class)
@Transactional
public abstract class AbstractTest {

	@Autowired
	SessionFactory sessFact;
//	@Autowired
//	private EntityManagerFactory entmanFact;

	public AbstractTest() {


	}

	@Before
	public void createDB() throws IOException {
		
		
		Session sess = sessFact.getCurrentSession();
		//EntityManager ent = entmanFact.createEntityManager();
		
		BufferedReader br = new BufferedReader(
				new FileReader(
						new File(
								"src/test/resources/net/brewspberry/test/db/create-db.sql"
								)
						)
				); 
		
		StringBuilder wholeQueryBuilder = new StringBuilder();
		String cur;
		
		
		while ((cur = br.readLine()) != null){
			wholeQueryBuilder.append(cur+"\n");
		}

		sess.createSQLQuery(wholeQueryBuilder.toString()).executeUpdate();

	}
}
