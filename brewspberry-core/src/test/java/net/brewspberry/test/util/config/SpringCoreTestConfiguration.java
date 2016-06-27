package net.brewspberry.test.util.config;

import java.sql.DriverManager;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.h2.tools.Server;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.sql.Connection;

@Configuration
@ComponentScan(basePackages = { "net.brewspberry" })
@EnableTransactionManagement
@PropertySources(value={@PropertySource("classpath:config.properties")
	, @PropertySource("classpath:c3po.properties")
	, @PropertySource("classpath:devices.properties")
})
public class SpringCoreTestConfiguration {
	
	Server server;
	
	private Environment env;
	
	
	
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
		
	    dataSource.setDriverClassName("org.h2.Driver");
	    dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
	    dataSource.setUsername("sa");
	    dataSource.setPassword("");
	   
	    /*
	    dataSource.setDriverClassName("org");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/brewspberry");
	    dataSource.setUsername("root");
	    dataSource.setPassword("raspberry");
	 */
	    return dataSource;
	}
	
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
	 
		this.startDatabase();
		
		
	    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
	 
	    sessionBuilder.scanPackages("net.brewspberry");
	 
	    return sessionBuilder.buildSessionFactory();
	}
	
	
	private void startDatabase() {
		
        try {
            server = Server.createTcpServer("-tcpAllowOthers").start();
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.
                getConnection("jdbc:h2:tcp://localhost/~/stackoverflow", "sa", "");
            System.out.println("Connection Established: "
                    + conn.getMetaData().getDatabaseProductName() + "/" + conn.getCatalog());

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}


	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
	        SessionFactory sessionFactory) {
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager(
	            sessionFactory);
	 
	    return transactionManager;
	}
	
	Properties hibernateProperties() {
	      return new Properties() {
	         /**
			 * 
			 */
			private static final long serialVersionUID = 3546518510147677728L;

			{
	            setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
	            setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
	            setProperty("hibernate.globally_quoted_identifiers", "true");
	         }
	      };
	   }

}
