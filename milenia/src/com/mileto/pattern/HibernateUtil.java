package com.mileto.pattern;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {


			/** Old way **/
			if (1 > 2) {


				// loads configuration and mappings
				Configuration configuration = new Configuration().configure();
				ServiceRegistry serviceRegistry
				= new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

				// builds a session factory from the service registry
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);


			}
			
			/** New way **/
			try {
				setUp();
			} catch (Exception e) {
				e.printStackTrace();
			}



		}

		return sessionFactory;
	}

	public static final ThreadLocal session = new ThreadLocal();

	public static Session currentSession() throws HibernateException {
		Session s = (Session) session.get();
		// Open a new Session, if this Thread has none yet

		if (sessionFactory == null) {
			sessionFactory = getSessionFactory();
		}

		if (s == null) {
			try {
				s = sessionFactory.openSession();
				session.set(s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return s;
	}

	public static void closeSession() throws HibernateException {
		Session s = (Session) session.get();
		session.set(null);
		if (s != null)
			s.close();
	}


	protected static void setUp() throws Exception {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}



	//public static void evict(Class pclass) {
	//sessionFactory.evict(pclass) ;
	//}
}

