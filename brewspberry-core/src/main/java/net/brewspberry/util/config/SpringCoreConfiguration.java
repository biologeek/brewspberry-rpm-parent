package net.brewspberry.util.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
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

@Configuration
@ComponentScan({ "net.brewspberry" })
@EnableTransactionManagement
@PropertySources(value={@PropertySource("classpath:config.properties")
	, @PropertySource("classpath:c3po.properties")
	, @PropertySource("classpath:devices.properties")
})
public class SpringCoreConfiguration {
	
	
	@Autowired
	private Environment env;
	
	
	
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
		/*
	    dataSource.setDriverClassName("org.h2.Driver");
	    dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
	    dataSource.setUsername("sa");
	    dataSource.setPassword("");
	   
	    */
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/brewspberry");
	    dataSource.setUsername("root");
	    dataSource.setPassword("raspberry");
	 
	    return dataSource;
	}
	
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
	 
	    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
	 
	    sessionBuilder.scanPackages("net.brewspberry");
	 
	    return sessionBuilder.buildSessionFactory();
	}
	
	
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
	        SessionFactory sessionFactory) {
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager(
	            sessionFactory);
	 
	    return transactionManager;
	}
	
	
	@Bean
	Properties hibernateProperties() {
	      return new Properties() {
	         
			private static final long serialVersionUID = 3546518510147677728L;

			{
				Properties properties = new Properties();
				properties.put("hibernate.dialect", "MySQLDialect");
				properties.put("hibernate.hbm2ddl.auto", "update");
				properties.put("hibernate.show_sql", "true");
		   }
	      };
	   }
	   

}
