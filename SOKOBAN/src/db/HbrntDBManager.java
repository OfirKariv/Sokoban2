package db;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HbrntDBManager implements DBManager {

	private SessionFactory factory;

	@Override
	public List<String> getTable(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public HbrntDBManager() {

		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		Configuration config = new Configuration();
		config.configure();
		factory = config.buildSessionFactory();

	}

	public void addUser(User u) {
		Session session = null;
		Transaction tx = null;

		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.save(u);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void addLevel(LevelInfo l) {
		Session session = null;
		Transaction tx = null;

		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.save(l);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void addUserData(UserData ud) {
		Session session = null;
		Transaction tx = null;

		try {
			session = factory.openSession();
			tx = session.beginTransaction();

			// UserData ud2 = new UserData(ud);
			session.save(ud);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}
	}

}
