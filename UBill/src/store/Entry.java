package store;

import org.hibernate.Session;

import util.HibernateUtil;

public class Entry {
	
	private String name = null;
	private String description = null;
	
	public Entry() {
		
	}
	
	public Entry(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
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
}
