package db;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.sun.corba.se.impl.naming.pcosnaming.NameServer;

public class HbrntDBManager implements DBManager {

	private SessionFactory factory;

	public HbrntDBManager() {

		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		Configuration config = new Configuration();
		config.configure();
		factory = config.buildSessionFactory();

	}

	@Override
	public List<DbObject> getTable(String s) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<DbObject> DBdata = new LinkedList<>();

		tx = session.beginTransaction();
		List fromDB = session.createQuery(s).list();
		for (Iterator iterator = fromDB.iterator(); iterator.hasNext();) {

			DbObject db = (DbObject) iterator.next();
			DBdata.add(db);

		}

		System.out.println(DBdata.toString());
		return DBdata;
	}

	public void addUser(String name) {

		User user = new User(name);

		Transaction tx = null;
		Session session = factory.openSession();

		try {
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

	}

	public int addLevel(String name) {
		int lvlID = checkName(name);
		if (lvlID != -1)
			return lvlID;

		LevelInfo lvl = new LevelInfo(name);

		Transaction tx = null;
		Session session = factory.openSession();

		try {
			tx = session.beginTransaction();
			lvlID = (Integer) session.save(lvl);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}
		lvl.setLevelID(lvlID);
		return lvlID;
	}

	@Override
	public int checkName(String name)

	{

		Session session = factory.openSession();
		try {
			Query<LevelInfo> query = session.createQuery("from level");
			List<LevelInfo> list = query.list();
			System.out.println(name);
			for (LevelInfo li : list) {
				{
					if (li.getLevelName().equals(name)) {
						return li.getLevelID();
					}

				}

			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return -1;
	}

	public void addUserData(String userName, int lvlID, int stepCount, int timer) {
		UserData ud = new UserData(userName, lvlID, stepCount, timer);
		Session session = null;
		Transaction tx = null;
		session = factory.openSession();

		try {
			tx = session.beginTransaction();
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

	@Override
	public void stop() {
		factory.close();

	}

}
