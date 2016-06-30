package net.brewspberry.test.util.config;

import java.sql.DriverManager;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.Connection;

@Configuration
@ComponentScan(basePackages = { "net.brewspberry" })
@EnableTransactionManagement
@PropertySources(value = { @PropertySource("classpath:config.properties"), @PropertySource("classpath:c3po.properties"),
		@PropertySource("classpath:devices.properties") })
public class SpringCoreTestConfiguration {

	private static final String H2_JDBC_URL_TEMPLATE = "jdbc:h2:%s/target/db/test;AUTO_SERVER=TRUE";
	@Value("classpath:create-db.sql")
	private Resource H2_SCHEMA_SCRIPT;

	@Value("classpath:test-data.sql")
	private Resource H2_DATA_SCRIPT;

	@Value("classpath:drop-data.sql")
	private Resource H2_CLEANER_SCRIPT;

	Server server;

	private Environment env;

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:target/test");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		

		/*
		 * dataSource.setDriverClassName("org");
		 * dataSource.setUrl("jdbc:mysql://localhost:3306/brewspberry");
		 * dataSource.setUsername("root"); dataSource.setPassword("raspberry");
		 */
		return dataSource;
	}

	@Bean(name = "server")
	public EmbeddedDatabase getServer() {

		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("test")
				.addScript("net/brewspberry/test/db/create-db.sql");

		return builder.build();
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
			Connection conn = DriverManager.getConnection("jdbc:h2:target/test", "sa", "");
			System.out.println(
					"Connection Established: " + conn.getMetaData().getDatabaseProductName() + "/" + conn.getCatalog());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

		return transactionManager;
	}

	Properties hibernateProperties() {
		return new Properties() {

			private static final long serialVersionUID = 3546518510147677728L;

			{
				setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
				setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
				setProperty("hibernate.globally_quoted_identifiers", "true");
			}
		};
	}

	/*
	 * 
	 * 
	 * @Bean public DataSource dataSource(Environment env) throws Exception {
	 * return createH2DataSource(); }
	 * 
	 * 
	 * @Autowired
	 * 
	 * @Bean public DataSourceInitializer dataSourceInitializer(final DataSource
	 * dataSource) {
	 * 
	 * final DataSourceInitializer initializer = new DataSourceInitializer();
	 * initializer.setDataSource(dataSource);
	 * initializer.setDatabasePopulator(databasePopulator());
	 * initializer.setDatabaseCleaner(databaseCleaner()); return initializer; }
	 * 
	 * 
	 * private DatabasePopulator databasePopulator() { final
	 * ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	 * populator.addScript(H2_SCHEMA_SCRIPT);
	 * populator.addScript(H2_DATA_SCRIPT); return populator; }
	 * 
	 * private DatabasePopulator databaseCleaner() { final
	 * ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	 * populator.addScript(H2_CLEANER_SCRIPT); return populator; }
	 * 
	 * private DataSource createH2DataSource() { String jdbcUrl =
	 * String.format(H2_JDBC_URL_TEMPLATE, System.getProperty("user.dir"));
	 * JdbcDataSource ds = new JdbcDataSource(); ds.setURL(jdbcUrl);
	 * ds.setUser("sa"); ds.setPassword("");
	 * 
	 * return ds; }
	 * 
	 * @Bean public PlatformTransactionManager
	 * transactionManager(EntityManagerFactory entityManagerFactory) {
	 * JpaTransactionManager transactionManager = new JpaTransactionManager();
	 * transactionManager.setEntityManagerFactory(entityManagerFactory); return
	 * transactionManager; }
	 * 
	 * @Bean public LocalContainerEntityManagerFactoryBean
	 * entityManagerFactory(Environment env) throws Exception {
	 * HibernateJpaVendorAdapter vendorAdapter = new
	 * HibernateJpaVendorAdapter(); vendorAdapter.setGenerateDdl(Boolean.TRUE);
	 * vendorAdapter.setShowSql(Boolean.TRUE);
	 * 
	 * LocalContainerEntityManagerFactoryBean factory = new
	 * LocalContainerEntityManagerFactoryBean();
	 * factory.setPersistenceUnitName("sample");
	 * factory.setJpaVendorAdapter(vendorAdapter);
	 * factory.setPackagesToScan("com.sample.model");
	 * factory.setDataSource(dataSource(env));
	 * 
	 * factory.setJpaProperties(jpaProperties()); factory.setLoadTimeWeaver(new
	 * InstrumentationLoadTimeWeaver());
	 * 
	 * return factory; }
	 * 
	 * Properties jpaProperties() { Properties props = new Properties();
	 * props.put("hibernate.query.substitutions", "true 'Y', false 'N'");
	 * props.put("hibernate.hbm2ddl.auto", "create-drop");
	 * props.put("hibernate.show_sql", "false");
	 * props.put("hibernate.format_sql", "true");
	 * 
	 * return props; }
	 */
}
