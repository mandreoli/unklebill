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
import store.Entry;
import util.HibernateUtil;

public class Entries {
	
	private LinkedList<Entry> entries = null;
	
	public Entries() {
		this.entries = new LinkedList<Entry>();
	}
	
	public Entries(LinkedList<Entry> entries) {
		this.entries = entries;
	}
	
	public LinkedList<Entry> getEntries() {
		return this.entries;
	}
	
	public LinkedList<String> getEntriesNames() {
		LinkedList<String> entries = new LinkedList<String>();
		
		for (Entry e : this.entries)
			entries.add(e.getName());
		
		return entries;
	}
	
	public int getNumEntries() {
		return this.entries.size();
	}
	
	public void addEntry(Entry entry) {
		this.entries.add(entry);
	}
	
	@SuppressWarnings("unchecked")
	public static Entries loadEntries(String user) {
		List<Entry> entries = new LinkedList<Entry>();
		Entries loaded = null;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();	
		entries = session.createQuery("FROM Entry WHERE user='"+user+"'").list();
		session.getTransaction().commit();
		loaded = new Entries(new LinkedList<Entry>(entries));
		
		return loaded;
	}
}
