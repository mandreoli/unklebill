package store;

import org.hibernate.Session;
import util.HibernateUtil;

public class User {
	
	private String user = null;
	private String name = null;
	private String surname = null;
	
	public User() {
		
	}
	
	public User(String user, String name, String surname) {
		this.user = user;
		this.name = name;
		this.surname = surname;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return this.user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSurname() {
		return this.surname;
	}
	
	public void saveUser() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(this);
		session.getTransaction().commit();
	}
	
	public static User loadUser(String user) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User loaded = (User)session.createQuery("FROM User WHERE user='"+user+"'").uniqueResult();		
		session.getTransaction().commit();		
		
		User u = new User(loaded.getUser(), loaded.getName(), loaded.getSurname()); 
		
		return u;
	}
}
