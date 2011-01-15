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
