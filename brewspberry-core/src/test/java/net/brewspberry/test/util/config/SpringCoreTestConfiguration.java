package net.brewspberry.test.util.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.brewspberry.main.util.config.SpringCoreConfiguration;

@Configuration
@ComponentScan(basePackages = { "net.brewspberry" }, excludeFilters={
		@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Configuration.class)
})
@EnableTransactionManagement
@PropertySources(value={@PropertySource("classpath:config.properties")
, @PropertySource("classpath:c3po.properties")
, @PropertySource("classpath:stock.properties")
, @PropertySource("classpath:devices.properties")
})
public class SpringCoreTestConfiguration {
	@Autowired
	private Environment environment;

	@Bean
	public LocalContainerEntityManagerFactoryBean emf() {
		LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory
				.setPackagesToScan(new String[] {"net.brewspberry"});
		sessionFactory.setJpaProperties(hibernateProperties());
		sessionFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return sessionFactory;
	}
	
	@Bean
	public EntityManager em(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource
				.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer configurer (){
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.hbm2ddl.import_files", "/net/brewspberry/test/db/table_data.sql");
		
		return properties;
	}

	@Bean
	@Autowired
	public JpaTransactionManager transactionManager(EntityManagerFactory s) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(s);
		return txManager;
	}
}
