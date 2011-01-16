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
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private int refid = 0;
	private String reference = null;

	public Transaction() {
	  
	}
	
	public Transaction(String user, String account, String entry, char type, double payment, int year, int month, int day, int refid, String reference) {
		this.user = user;
		this.account = account;
		this.entry = entry;
		this.type = type;
		this.payment = payment;
		this.year = year;
		this.month = month;
		this.day = day;
		this.refid = refid;
		this.reference = reference;
	}
	
	public Transaction(int id, String user, String account, String entry, char type, double payment, int year, int month, int day, int refid, String reference) {
		this.id = id;
		this.user = user;
		this.account = account;
		this.entry = entry;
		this.type = type;
		this.payment = payment;
		this.year = year;
		this.month = month;
		this.day = day;
		this.refid = refid;
		this.reference = reference;
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

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return this.day;
	}

	public void setDay(int day) {
		this.day = day;
	}
  
	public int getRefid() {
		return this.refid;
	}

	public void setRefid(int refid) {
		this.refid = refid;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
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
	
	public void deleteTransaction() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(this);
		session.getTransaction().commit();
	}
	
	public static Transaction loadTransaction(int id) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Transaction loaded = (Transaction)session.createQuery("FROM Transaction WHERE id="+id).uniqueResult();		
		session.getTransaction().commit();		
		
		Transaction t = null;
		if (loaded != null)
			t = new Transaction(loaded.getId(), loaded.getUser(), loaded.getAccount(), loaded.getEntry(), loaded.getType(), loaded.getPayment(), loaded.getYear(), loaded.getMonth(), loaded.getDay(), loaded.getRefid(), loaded.getReference()); 
		
		return t;
	}
}
