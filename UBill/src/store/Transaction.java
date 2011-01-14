package store;

import org.hibernate.Session;
import util.HibernateUtil;

public class Transaction {
	private int id = 0;	
	private String user = null;
	private String account = null;	
	private String entry = null;
	private char type = '+';
	private double payment = 0.0;
	private String date = null;

	public Transaction() {
	  
	}
	
	public Transaction(String user, String account, String entry, char type, double payment, String date) {
		this.user = user;
		this.account = account;
		this.entry = entry;
		this.type = type;
		this.payment = payment;
		this.date = date;
	}
	
	public Transaction(int id, String user, String account, String entry, char type, double payment, String date) {
		this.id = id;
		this.user = user;
		this.account = account;
		this.entry = entry;
		this.type = type;
		this.payment = payment;
		this.date = date;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return this.user;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount() {
		return this.account;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getEntry() {
		return this.entry;
	}

	public void setType(char type) {
		this.type = type;
	}

	public char getType() {
		return this.type;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public double getPayment() {
		return this.payment;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return this.date;
	}
  
	public void saveTransaction() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(this);
		session.getTransaction().commit();
	}
	
	public void updateTransaction() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	}
	
	public static Transaction loadTransaction(int id) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Transaction loaded = (Transaction)session.createQuery("FROM transaction WHERE id="+id).uniqueResult();		
		session.getTransaction().commit();		
		
		Transaction t = null;
		if (loaded != null)
			t = new Transaction(loaded.getId(), loaded.getUser(), loaded.getAccount(), loaded.getEntry(), loaded.getType(), loaded.getPayment(), loaded.getDate()); 
		
		return t;
	}
}
