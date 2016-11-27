package net.brewspberry.main.util;

import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import net.brewspberry.main.business.beans.AbstractIngredient;
import net.brewspberry.main.business.beans.Actioner;
import net.brewspberry.main.business.beans.Biere;
import net.brewspberry.main.business.beans.Brassin;
import net.brewspberry.main.business.beans.ConcreteTemperatureMeasurement;
import net.brewspberry.main.business.beans.DurationBO;
import net.brewspberry.main.business.beans.Etape;
import net.brewspberry.main.business.beans.Houblon;
import net.brewspberry.main.business.beans.Levure;
import net.brewspberry.main.business.beans.Malt;
import net.brewspberry.main.business.beans.PalierType;
import net.brewspberry.main.business.beans.SimpleHoublon;
import net.brewspberry.main.business.beans.SimpleLevure;
import net.brewspberry.main.business.beans.SimpleMalt;
import net.brewspberry.main.business.beans.User;

@SuppressWarnings("deprecation")
public class HibernateUtil {

	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static final ThreadLocal<StatelessSession> threadLocalState = new ThreadLocal<StatelessSession>();
	
	private static SessionFactory sessionFactory;
	private static Logger logger = LogManager.getInstance(HibernateUtil.class
			.getName());
	private static ServiceRegistry serviceRegistry;

	static {
		try {
			System.out.println("Trying to configure SessionFactory");
			sessionFactory = configureSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	public static SessionFactory configureSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure().addAnnotatedClass(Malt.class)
					.addAnnotatedClass(Houblon.class)
					.addAnnotatedClass(Levure.class)
					.addAnnotatedClass(SimpleMalt.class)
					.addAnnotatedClass(SimpleHoublon.class)
					.addAnnotatedClass(SimpleLevure.class)
					.addAnnotatedClass(AbstractIngredient.class)
					.addAnnotatedClass(Etape.class)
					.addAnnotatedClass(Biere.class)
					.addAnnotatedClass(Brassin.class)
					.addAnnotatedClass(ConcreteTemperatureMeasurement.class)
					.addAnnotatedClass(Actioner.class)
					.addAnnotatedClass(PalierType.class)
					.addAnnotatedClass(User.class)
					.addAnnotatedClass(DurationBO.class);

			serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		} catch (Exception e) {
			logger.severe("** Exception in SessionFactory **");
			e.printStackTrace();
		}
		return sessionFactory;
	}

	private HibernateUtil() {
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = configureSessionFactory();
		return sessionFactory;
	}

	public static Session getSession() throws HibernateException {
		Session session = getSessionFactory().openSession();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}
		session.flush();
		session.clear();

		return session;
	}

	public static StatelessSession getStatelessSession()
			throws HibernateException {
		StatelessSession statelessSession = getSessionFactory()
				.openStatelessSession();

		if (statelessSession == null) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			statelessSession = (sessionFactory != null) ? sessionFactory
					.openStatelessSession() : null;
			threadLocalState.set(statelessSession);
		}

		return statelessSession;
	}

	public static void rebuildSessionFactory() {
		try {
			sessionFactory = configureSessionFactory();
		} catch (Exception e) {
			logger.severe("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	public static void closeSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);
		if (session != null) {
			if (session.isOpen()) {
				session.close();
			}
		}
	}
}