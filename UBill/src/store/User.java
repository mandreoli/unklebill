package store;

import org.hibernate.Session;

import datatype.Accounts;
import datatype.Transactions;
import util.HibernateUtil;

public class User {
	
	private int id = 0;
	private String user = null;
	private String password = null;
	private String name = null;
	private String address = null;
	private String mail = null;
	private String currency = null;
	private boolean auto = false;
	
	public User() {
		
	}
	
	public User(String user, String password, String name, String address, String mail, String currency, boolean auto) {
		this.user = user;
		this.password = password;
		this.name = name;
		this.address = address;
		this.mail = mail;
		this.currency = currency;
		this.auto = auto;
	}
	
	public User(int id, String user, String password, String name, String address, String mail, String currency, boolean auto) {
		this.id = id;
		this.user = user;
		this.password = password;
		this.name = name;
		this.address = address;
		this.mail = mail;
		this.currency = currency;
		this.auto = auto;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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
	
	public void updateUser() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	}
	
	public void deleteUser() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(this);
		session.getTransaction().commit();
	}

	public void updateUser(String oldUser) {
		Accounts accounts = Accounts.loadAccounts(oldUser);
		
		for (Account a : accounts.getAccounts()) {
			Transactions transactions = Transactions.loadTransactions(oldUser, a.getAccount());
			for (Transaction t : transactions.getTransactions()) {
				t.setUser(this.user);
				t.updateTransaction();
			}
			a.setUser(this.user);
			a.updateAccount();
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	}
	
	public static User loadUser(String user, String password) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User loaded = (User)session.createQuery("FROM User WHERE user='"+user+"' AND password='"+password+"'").uniqueResult();		
		session.getTransaction().commit();		
		
		User u = null;
		if (loaded != null)
			u = new User(loaded.getId(), loaded.getUser(), loaded.getPassword(), loaded.getName(), loaded.getAddress(), loaded.getMail(), loaded.getCurrency(), loaded.isAuto()); 
		
		return u;
	}
	
	public static User updatableUser(String user, String password, int id) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User loaded = (User)session.createQuery("FROM User WHERE (user='"+user+"' OR password='"+password+"') AND id<>"+id).uniqueResult();		
		session.getTransaction().commit();		
		
		User u = null;
		if (loaded != null)
			u = new User(loaded.getId(), loaded.getUser(), loaded.getPassword(), loaded.getName(), loaded.getAddress(), loaded.getMail(), loaded.getCurrency(), loaded.isAuto()); 
		
		return u;
	}
	
	public static boolean checkFreeUser(String user, char[] pwd) {
		User loadedUser = loadUser(user, new String(pwd));
		
		if (loadedUser == null)
			return true;
		
		return false;
	}
	
	public static boolean checkUpdatableUser(String user, char[] pwd, int id) {
		User loadedUser = updatableUser(user, new String(pwd), id);
		
		if (loadedUser == null)
			return true;
		
		return false;
	}
}
