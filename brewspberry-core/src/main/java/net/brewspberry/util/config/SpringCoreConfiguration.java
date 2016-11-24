package net.brewspberry.util.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({ "net.brewspberry" })
@EnableTransactionManagement
public class SpringCoreConfiguration implements EnvironmentAware {

	@Autowired
	private Environment env;

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();


		dataSource.setDriverClassName(env.getProperty("datasource.jdbc.driver"));
		dataSource.setUrl(env.getProperty("datasource.jdbc.address"));
		dataSource.setUsername(env.getProperty("datasource.jdbc.user"));
		dataSource.setPassword(env.getProperty("datasource.jdbc.password"));

		return dataSource;
	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {

		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

		sessionBuilder.scanPackages("net.brewspberry");

		return sessionBuilder.buildSessionFactory();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer configurer() {
		PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();

		return config;
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

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
