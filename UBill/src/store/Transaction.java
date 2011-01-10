package store;

import org.hibernate.Session;
import util.HibernateUtil;

public class Transaction {
	private int id = 0;
	private String account = null;
	private String entry = null;
	private String type = null;
	private double payment = 0.0;
	private String date = null;

	public Transaction() {
	  
	}
	
	public Transaction(String account, String entry, String type, double payment, String date) {
		this.account = account;
		this.entry = entry;
		this.type = type;
		this.payment = payment;
		this.date = date;
	}
	
	public Transaction(int id, String account, String entry, String type, double payment, String date) {
		this.id = id;
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

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
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
	
	public static Transaction loadTransaction(int id) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Transaction loaded = (Transaction)session.createQuery("FROM transaction WHERE id="+id).uniqueResult();		
		session.getTransaction().commit();		
		
		Transaction t = new Transaction(loaded.getId(), loaded.getAccount(), loaded.getEntry(), loaded.getType(), loaded.getPayment(), loaded.getDate()); 
		
		return t;
	}
}
