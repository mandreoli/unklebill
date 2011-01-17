package datatype;

import java.util.LinkedList;
import java.util.List;
import org.hibernate.Session;
import store.Transaction;
import util.HibernateUtil;

public class Transactions {
	
	private LinkedList<Transaction> transactions = null;
	
	public Transactions() {
		this.transactions = new LinkedList<Transaction>();
	}
	
	public Transactions(LinkedList<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public LinkedList<Transaction> getTransactions() {
		return this.transactions;
	}
	
	public int getNumTransactions() {
		return this.transactions.size();
	}
	
	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}
	
	public Transaction getTransaction(int id) {
		for (Transaction t : this.transactions) {
			if (t.getId() == id)
				return t;
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Transactions loadTransactions(String user, String account) {
		List<Transaction> transactions = new LinkedList<Transaction>();
		Transactions loaded = null;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();	
		transactions = session.createQuery("FROM Transaction WHERE user='"+user+"' AND account='"+account+"'").list();
		session.getTransaction().commit();
		loaded = new Transactions(new LinkedList<Transaction>(transactions));
		
		return loaded;
	}
	
	@SuppressWarnings("unchecked")
	public static Transactions loadTransactions(String user, String account, int year, int month) {
		List<Transaction> transactions = new LinkedList<Transaction>();
		Transactions loaded = null;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();		
		transactions = session.createQuery("FROM Transaction WHERE user='"+user+"' AND account='"+account+"' AND year="+year+" AND month="+month).list();
		session.getTransaction().commit();
		loaded = new Transactions(new LinkedList<Transaction>(transactions));
		
		return loaded;
	}
	
	public static void deleteTransactions(String user, String account) {
		Transactions transactions = loadTransactions(user, account);
		
		for (Transaction t : transactions.getTransactions()) {
			t.deleteTransaction();
		}
	}
	
	public static void updateTransactions(String user, String account, String newAccount) {
		Transactions transactions = loadTransactions(user, account);
		Transactions references = loadReferencedTransactions(user, account);		
		
		for (Transaction t : transactions.getTransactions()) {
			t.setAccount(newAccount);
			t.updateTransaction();
		}
		
		for (Transaction t : references.getTransactions()) {
			t.setReference(newAccount);
			t.updateTransaction();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static Transactions loadReferencedTransactions(String user, String reference) {
		List<Transaction> transactions = new LinkedList<Transaction>();
		Transactions loaded = null;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		transactions = session.createQuery("FROM Transaction WHERE user='"+user+"' AND reference='"+reference+"'").list();		
		session.getTransaction().commit();				
		loaded = new Transactions(new LinkedList<Transaction>(transactions));
		
		return loaded;
	}
}
