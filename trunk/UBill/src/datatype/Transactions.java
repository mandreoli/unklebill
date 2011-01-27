/**
 * Copyright 2011 Michele Andreoli
 * 
 * This file is part of UnkleBill.
 *
 * UnkleBill is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * UnkleBill is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with UnkleBill; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 **/

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
		transactions = session.createQuery("FROM Transaction WHERE user='"+user+"' AND account='"+account+"' AND year="+year+" AND month="+month+" ORDER BY day").list();
		session.getTransaction().commit();
		loaded = new Transactions(new LinkedList<Transaction>(transactions));
		
		return loaded;
	}
	
	@SuppressWarnings("unchecked")
	public static Transactions loadTransactions(String user, String account, int year, int month, int day) {		
		List<Transaction> transactions = new LinkedList<Transaction>();
		Transactions loaded = null;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();		
		transactions = session.createQuery("FROM Transaction WHERE user='"+user+"' AND account='"+account+"' AND year="+year+" AND month="+month+" AND day="+day+" ORDER BY day").list();
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
	
	@SuppressWarnings("unchecked")
	public static Transactions loadTransactionsByEntry(String user, String entry) {
		List<Transaction> transactions = new LinkedList<Transaction>();
		Transactions loaded = null;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		transactions = session.createQuery("FROM Transaction WHERE user='"+user+"' AND entry='"+entry+"'").list();		
		session.getTransaction().commit();				
		loaded = new Transactions(new LinkedList<Transaction>(transactions));
		
		return loaded;
	}
}
