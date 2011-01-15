package store;

import org.hibernate.Session;

import util.HibernateUtil;

public class Entry {
	
	private String name = null;
	private String user = null;
	private String description = null;
	
	public Entry() {
		
	}
	
	public Entry(String name, String user, String description) {
		this.name = name;
		this.user = user;
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return this.user;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
	
	public void saveEntry() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(this);
		session.getTransaction().commit();
	}
	
	public static Entry loadEntry(String user, String name) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Entry loaded = (Entry)session.createQuery("FROM Entry WHERE user='"+user+"' AND name='"+name+"'").uniqueResult();		
		session.getTransaction().commit();		
		
		Entry e = null;
		if (loaded != null)
			e = new Entry(loaded.name, loaded.getUser(), loaded.getDescription()); 
		
		return e;
	}
}
