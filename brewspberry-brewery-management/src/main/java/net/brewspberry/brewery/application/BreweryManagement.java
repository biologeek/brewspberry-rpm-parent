package net.brewspberry.brewery.application;

import java.util.Properties;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.dialect.PostgreSQL94Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zaxxer.hikari.HikariDataSource;

import net.brewspberry.brewery.converters.LocalDateTimeConverter;

@SpringBootApplication(scanBasePackages="net.brewspberry.brewery")
@EnableJpaRepositories (basePackages="net.brewspberry.brewery.repositories")
@EnableWebMvc
@PropertySources(value = { @PropertySource("file:${app.parameters}/brewery.properties") })
@EntityScan(basePackages = {"net.brewspberry.brewery.model"})
public class BreweryManagement implements WebMvcConfigurer {
	


	@Value("${datasource.jdbc.url}")
	private String jdbcUrl;

	@Value("${datasource.jdbc.user}")
	private String jdbcUser;

	@Value("${datasource.jdbc.password}")
	private String jdbcPassword;

	private Logger logger = Logger.getLogger(BreweryManagement.class.getName());
	
	public static void main(String[] args) {
		SpringApplication.run(BreweryManagement.class, args);
	}

	

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setJpaDialect(new HibernateJpaDialect());
		bean.setPersistenceUnitName("monitoringPersistence");
		bean.setJpaProperties(jpaProperties());
		bean.setDataSource(datasource());
		bean.setPackagesToScan("net.brewspberry.brewery.model");
		bean.setPersistenceProvider(new HibernatePersistenceProvider());
		return bean;
	}
	
	
	@Bean
	public EntityManager entityManager() {
		return entityManagerFactory().getNativeEntityManagerFactory().createEntityManager();
	}

	@Bean
	public DataSource datasource() {
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(jdbcUrl);
		ds.setUsername(jdbcUser);
		ds.setPassword(jdbcPassword);
		return ds;
	}

	private Properties jpaProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.connection.driver_class", Driver.class.getName());
		props.setProperty("hibernate.dialect", PostgreSQL94Dialect.class.getName());
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		return props;
	}

	@Bean
	public MessageConverter jmsConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		return converter;
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
	    registry.addConverter(new LocalDateTimeConverter());
	}
	
	

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")//
		.allowedMethods("*")//
		.allowedOrigins("*");
	}
}
