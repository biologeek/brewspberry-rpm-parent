package net.brewspberry.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTest {

	@Autowired
	SessionFactory sessFact;

	public AbstractTest() {

		try {
			this.createDB();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void createDB() throws IOException {
		
		
		Session sess = sessFact.getCurrentSession();
		
		
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
			wholeQueryBuilder.append(cur);
		}

		sess.createSQLQuery(wholeQueryBuilder.toString()).executeUpdate();
		
	}
}
