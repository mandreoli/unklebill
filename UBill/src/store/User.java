package store;

import org.hibernate.Session;
import util.HibernateUtil;

public class User {
	
	private String user = null;
	private String password = null;
	private String name = null;
	private String currency = null;
	private boolean auto = false;
	
	public User() {
		
	}
	
	public User(String user, String password, String name, String currency, boolean auto) {
		this.user = user;
		this.password = password;
		this.name = name;
		this.currency = currency;
		this.auto = auto;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return this.user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public boolean isAuto() {
		return this.auto;
	}
	
	public void saveUser() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(this);
		session.getTransaction().commit();
	}
	
	public static User loadUser(String user, String password) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User loaded = (User)session.createQuery("FROM User WHERE user='"+user+"' AND password='"+password+"'").uniqueResult();		
		session.getTransaction().commit();		
		
		User u = null;
		if (loaded != null)
			u = new User(loaded.getUser(), loaded.getPassword(), loaded.getName(), loaded.getCurrency(), loaded.isAuto()); 
		
		return u;
	}
	
	public static User checkFreeUser(String user, String password) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User loaded = (User)session.createQuery("FROM User WHERE user='"+user+"' OR password='"+password+"'").uniqueResult();		
		session.getTransaction().commit();		
		
		User u = null;
		if (loaded != null)
			u = new User(loaded.getUser(), loaded.getPassword(), loaded.getName(), loaded.getCurrency(), loaded.isAuto()); 
		
		return u;
	}
}
