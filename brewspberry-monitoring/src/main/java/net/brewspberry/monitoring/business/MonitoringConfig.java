package net.brewspberry.monitoring.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.hibernate.dialect.MySQL55Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mysql.jdbc.Driver;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.w1.W1Master;
import com.pi4j.wiringpi.GpioUtil;
import com.zaxxer.hikari.HikariDataSource;

import net.brewspberry.monitoring.controller.impl.LocalDateTimeConverter;

@SpringBootApplication(scanBasePackages="net.brewspberry.monitoring")
@EnableJms
@EnableJpaRepositories (basePackages="net.brewspberry.monitoring.repositories")
@EnableWebMvc
@PropertySources(value = { @PropertySource("file:${app.parameters}/monitoring.properties") })
public class MonitoringConfig implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringConfig.class, args);
	}

	@Value("${datasource.jdbc.url}")
	private String jdbcUrl;

	@Value("${datasource.jdbc.user}")
	private String jdbcUser;

	@Value("${datasource.jdbc.password}")
	private String jdbcPassword;

	private Logger logger = Logger.getLogger(MonitoringConfig.class.getName());

	@Value("${activemq.broker.url}")
	private String brokerURL;

	@Bean
	/**
	 * 1-wire master controller bean
	 */
	public W1Master w1Master() {
		return new W1Master();
	}

	@Bean
	/**
	 * Creates the controller that will manage devices plugged to the Pi
	 * 
	 * @return
	 * @throws BeanCreationException
	 *             in case root privileges are required to manage pins
	 */
	public GpioController gpioController() throws IOException {

		String raspbianVmArg = System.getProperty("raspbian");
		boolean os = raspbianVmArg != null && raspbianVmArg.equalsIgnoreCase("true");

		if (os) {
			// FIXME ? Is it really the best solution ?
			Process p = Runtime.getRuntime().exec("whoami");
			String user = new BufferedReader(new InputStreamReader(p.getInputStream())).readLine();

			if (GpioUtil.isPrivilegedAccessRequired() && !"root".equals(user)) {
				logger.severe("Privileged Access Required to access pins");
				throw new BeanCreationException("Privileged Access Required to access pins");
			}

			return GpioFactory.getInstance();
		}
		return null;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setJpaDialect(new HibernateJpaDialect());
		bean.setPersistenceUnitName("monitoringPersistence");
		bean.setJpaProperties(jpaProperties());
		bean.setDataSource(datasource());
		bean.setPackagesToScan("net.brewspberry.monitoring.model");
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
		props.setProperty("hibernate.dialect", MySQL55Dialect.class.getName());
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		return props;
	}

	@Bean
	public MessageConverter jmsConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		return converter;
	}

	@Bean
	public JmsTemplate temperatureJmsTemplate() {
		JmsTemplate tpl = new JmsTemplate();
		tpl.setConnectionFactory(springConnectionFactory());
		tpl.setMessageConverter(jmsConverter());
		tpl.setDefaultDestinationName("V1/Temperatures");
		tpl.setPubSubDomain(false);
		return tpl;
	}

	@Bean(name="connectionFactory")
	public ConnectionFactory springConnectionFactory() {
		SingleConnectionFactory factory = new SingleConnectionFactory();
		factory.setTargetConnectionFactory(amqConnectionFactory());
		factory.setReconnectOnException(true);
		return factory;
	}

	@Bean
	public ConnectionFactory amqConnectionFactory() {
		ActiveMQConnectionFactory conFact = new ActiveMQConnectionFactory();
		conFact.setBrokerURL(brokerURL);
		return conFact;
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
