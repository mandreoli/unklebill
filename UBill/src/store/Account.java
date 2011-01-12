package store;

import org.hibernate.Session;

import datatype.Accounts;
import util.HibernateUtil;

public class Account {
	private int id = 0;
	private String account = null;
	private String user = null;
	private String description = null;
	private double balance = 0.0;
	private String creation = null;
	private String currency = null;
	private boolean usable = false;

	public Account() {
	  
	}
	
	public Account(String account, String user, String description, double balance, String creation, String currency, boolean usable) {
		this.account = account;		
		this.user = user;
		this.description = description;
		this.balance = balance;
		this.creation = creation;
		this.currency = currency;
		this.usable = usable;
	}
	
	public Account(int id, String account, String user, String description, double balance, String creation, String currency, boolean usable) {
		this.id = id;
		this.account = account;		
		this.user = user;
		this.description = description;
		this.balance = balance;
		this.creation = creation;
		this.currency = currency;
		this.usable = usable;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public void setUsable(boolean usable) {
		this.usable = usable;
	}
	
	public boolean isUsable() {
		return this.usable;
	}	
  
	public void saveAccount() {
		Accounts accounts = Accounts.loadAccounts(this.getUser());
		
		if (this.isUsable() == true) {
			for (Account a : accounts.getAccounts()) {
				if (a.isUsable()) {
					a.setUsable(false);
					Session session = HibernateUtil.getSessionFactory().getCurrentSession();
					session.beginTransaction();
					session.update(a);
					session.getTransaction().commit();
				}
			}
		}
		else {
			if (accounts.getNumAccounts() == 0)
				this.setUsable(true);
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(this);
		session.getTransaction().commit();
	}
	
	public void updateAccount() {
		Accounts accounts = Accounts.loadAccounts(this.getUser());
		
		if (this.isUsable() == true) {
			for (Account a : accounts.getAccounts()) {
				if (a.isUsable()) {
					a.setUsable(false);
					Session session = HibernateUtil.getSessionFactory().getCurrentSession();
					session.beginTransaction();
					session.update(a);
					session.getTransaction().commit();
				}
			}
		}
		else {
			if (accounts.getNumAccounts() == 0)
				this.setUsable(true);
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	}
	
	public void removeAccount() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(this);
		session.getTransaction().commit();
	}
	
	public static Account loadAccount(String account, String user) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Account loaded = (Account)session.createQuery("FROM Account WHERE account='"+account+"' AND user='"+user+"'").uniqueResult();		
		session.getTransaction().commit();		
		
		Account a = null;
		if (loaded != null)
			a = new Account(loaded.getId(), loaded.getAccount(), loaded.getUser(), loaded.getDescription(), loaded.getBalance(), loaded.getCreation(), loaded.getCurrency(), loaded.isUsable()); 
		
		return a;
	}
	
	public static Account loadDefaultAccount(String user) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Account loaded = (Account)session.createQuery("FROM Account WHERE user='"+user+"' AND usable=true").uniqueResult();		
		session.getTransaction().commit();		
		
		Account a = null;
		if (loaded != null)
			a = new Account(loaded.getId(), loaded.getAccount(), loaded.getUser(), loaded.getDescription(), loaded.getBalance(), loaded.getCreation(), loaded.getCurrency(), loaded.isUsable()); 
		
		return a;
	}
}