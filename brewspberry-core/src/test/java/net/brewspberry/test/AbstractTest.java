package net.brewspberry.test;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.brewspberry.test.util.config.SpringCoreTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfiguration.class)

public abstract class AbstractTest {

	@Autowired
	EntityManagerFactory sessFact;
	// @Autowired
	// private EntityManagerFactory entmanFact;

	public AbstractTest() {

	}

	@Before
	public void createDB() throws IOException {
		/*
		
		Session sess = sessFact.getCurrentSession();
		//EntityManager ent = entmanFact.createEntityManager();
		System.out.println("*** Preparing database ***");
		
		File file = new File(
				"src/test/resources/net/brewspberry/test/db/create-db.sql"
				);
				
				if (file.exists()){
		BufferedReader br = new BufferedReader(
				new FileReader(
							file
						)
				); 
		StringBuilder wholeQueryBuilder = new StringBuilder();
		String cur;
		
		
		while ((cur = br.readLine()) != null){
			wholeQueryBuilder.append(cur+"\n");
		}

		sess.createSQLQuery(wholeQueryBuilder.toString()).executeUpdate();
		System.out.println("*** Prepared database ***");

				} else {
					
					System.out.println("######## ERROR FILE NOT FOUND #######");
				}
				*/
	}
}
