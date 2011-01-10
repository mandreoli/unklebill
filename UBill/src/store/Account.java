package store;

import org.hibernate.Session;
import util.HibernateUtil;

public class Account {
	private String account = null;
	private String user = null;
	private String description = null;
	private double balance = 0.0;
	private String creation = null;
	private boolean usable = false;

	public Account() {
	  
	}
	
	public Account(String account, String user, String description, double balance, String creation, boolean usable) {
		this.account = account;		
		this.user = user;
		this.description = description;
		this.balance = balance;
		this.creation = creation;
		this.usable = usable;
	}
  
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public double getBalance() {
		return this.balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getCreation() {
		return this.creation;
	}

	public void setCreation(String creation) {
		this.creation = creation;
	}
	
	public void setUsable(boolean usable) {
		this.usable = usable;
	}
	
	public boolean getUsable() {
		return this.usable;
	}	
  
	public void saveAccount() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(this);
		session.getTransaction().commit();
	}
	
	public static Account loadClient(String account) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Account loaded = (Account)session.createQuery("FROM Account WHERE account='"+account+"'").uniqueResult();		
		session.getTransaction().commit();		
		
		Account a = new Account(loaded.getAccount(), loaded.getUser(), loaded.getDescription(), loaded.getBalance(), loaded.getCreation(), loaded.getUsable()); 
		
		return a;
	}
}
