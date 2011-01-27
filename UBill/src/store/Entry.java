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

import datatype.Transactions;

import util.HibernateUtil;

public class Entry {
	
	private int id = 0;
	private String name = null;
	private String user = null;
	private String description = null;
	
	public Entry() {
		
	}
	
	public Entry(String name, String user, String description) {
		this.name = name;
		this.user = user;
		this.description = description;
	}
	
	public Entry(int id, String name, String user, String description) {
		this.id = id;
		this.name = name;
		this.user = user;
		this.description = description;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
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
	
	public void saveEntry() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(this);
		session.getTransaction().commit();
	}
	
	public void updateEntry(String oldEntry) {
		Transactions transactions = Transactions.loadTransactionsByEntry(this.user, oldEntry);
		for (Transaction t : transactions.getTransactions()) {
			t.setEntry(this.name);
			t.updateTransaction();
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	}
	
	public void deleteEntry() {
		Transactions transactions = Transactions.loadTransactionsByEntry(this.user, this.name);
		for (Transaction t : transactions.getTransactions()) {
			t.setEntry("");
			t.updateTransaction();
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(this);
		session.getTransaction().commit();
	}
	
	public static Entry loadEntry(String user, String name) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Entry loaded = (Entry)session.createQuery("FROM Entry WHERE user='"+user+"' AND name='"+name+"'").uniqueResult();		
		session.getTransaction().commit();		
		
		Entry e = null;
		if (loaded != null)
			e = new Entry(loaded.getId(), loaded.name, loaded.getUser(), loaded.getDescription()); 
		
		return e;
	}
	
	public static Entry updatableEntry(String user, String name, int id) {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Entry loaded = (Entry)session.createQuery("FROM Entry WHERE user='"+user+"' AND name='"+name+"' AND id<>"+id).uniqueResult();		
		session.getTransaction().commit();		
		
		Entry e = null;
		if (loaded != null)
			e = new Entry(loaded.getId(), loaded.name, loaded.getUser(), loaded.getDescription()); 
		
		return e;
	}
	
	public static boolean checkFreeEntry(String user, String name) {
		Entry loadedEntry = loadEntry(user, name);
		
		if (loadedEntry == null)
			return true;
		
		return false;
	}
	
	public static boolean checkUpdatableEntry(String user, String name, int id) {
		Entry loadedEntry = updatableEntry(user, name, id);
		
		if (loadedEntry == null)
			return true;
		
		return false;
	}
}
