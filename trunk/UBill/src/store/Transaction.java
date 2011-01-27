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

package store;

import org.hibernate.Session;

import executor.FieldParser;
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
		this.payment = FieldParser.roundDouble(payment);
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
		this.payment = FieldParser.roundDouble(payment);
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
		this.payment = FieldParser.roundDouble(payment);
	}

	public double getPayment() {
		return FieldParser.roundDouble(this.payment);
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
	
	public static Transaction loadTransaction(int refid, String user, String account) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Transaction loaded = (Transaction)session.createQuery("FROM Transaction WHERE id="+refid+"").uniqueResult();		
		session.getTransaction().commit();		
		
		Transaction t = null;
		if (loaded != null)
			t = new Transaction(loaded.getId(), loaded.getUser(), loaded.getAccount(), loaded.getEntry(), loaded.getType(), loaded.getPayment(), loaded.getYear(), loaded.getMonth(), loaded.getDay(), loaded.getRefid(), loaded.getReference()); 
		
		return t;
	}	
}
