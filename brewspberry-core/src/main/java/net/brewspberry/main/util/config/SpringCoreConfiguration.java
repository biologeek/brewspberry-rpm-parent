package net.brewspberry.main.util.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "net.brewspberry.main" })
@EnableTransactionManagement
public class SpringCoreConfiguration implements EnvironmentAware {

	@Autowired
	private Environment env;
	
	

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		HikariDataSource dataSource = new HikariDataSource();


		dataSource.setDriverClassName(env.getProperty("datasource.jdbc.driver"));
		dataSource.setJdbcUrl(env.getProperty("datasource.jdbc.address"));
		dataSource.setUsername(env.getProperty("datasource.jdbc.user"));
		dataSource.setPassword(env.getProperty("datasource.jdbc.password"));

		return dataSource;
	}

	@Autowired
	@Bean(name = "entityManagerFactory")
	public LocalEntityManagerFactoryBean entityManagerFactory() {

		LocalEntityManagerFactoryBean sessionBuilder = new LocalEntityManagerFactoryBean();
		sessionBuilder.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		sessionBuilder.setJpaProperties(hibernateProperties());
		sessionBuilder.setJpaDialect(new HibernateJpaDialect());
		sessionBuilder.setPersistenceUnitName("persistenceUnit");

		return sessionBuilder;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer configurer() {
		PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();

		return config;
	}

	
	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager(EntityManagerFactory sessionFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(sessionFactory);
		return transactionManager;
	}

	@Bean
	Properties hibernateProperties() {
		return new Properties() {

			private static final long serialVersionUID = 3546518510147677728L;

			{
				Properties properties = new Properties();
				properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
				properties.put("hibernate.hbm2ddl.auto", "update");
				properties.put("hibernate.show_sql", "true");
			}
		};
	}

	@Override
	public void setEnvironment(Environment arg0) {
		this.env = arg0;

	}

}
