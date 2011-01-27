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
import store.Account;
import util.HibernateUtil;

public class Accounts {
	
	private LinkedList<Account> accounts = null;
	
	public Accounts() {
		this.accounts = new LinkedList<Account>();
	}
	
	public Accounts(LinkedList<Account> accounts) {
		this.accounts = accounts;
	}
	
	public LinkedList<Account> getAccounts() {
		return this.accounts;
	}
	
	public LinkedList<String> getAccountsNames() {
		LinkedList<String> names = new LinkedList<String>();
		
		for (Account a : this.accounts)
			names.add(a.getAccount());
		
		return names;
	}
	
	public int getNumAccounts() {
		return this.accounts.size();
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	@SuppressWarnings("unchecked")
	public static Accounts loadAccounts(String name) {
		List<Account> accounts = new LinkedList<Account>();
		Accounts loadedClients = null;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();	
		accounts = session.createQuery("FROM Account WHERE user='"+name+"'").list();
		session.getTransaction().commit();
		loadedClients = new Accounts(new LinkedList<Account>(accounts));
		
		return loadedClients;
	}
	
	public Account getAccount(String name) {
		for (Account a : this.accounts) {
			if (a.getAccount().equals(name))
				return a;
		}
		
		return null;
	}
}
