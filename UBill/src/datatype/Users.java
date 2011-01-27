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

import store.User;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;

public class Users {
	
	private LinkedList<User> users = null;
	
	public Users() {
		this.users = new LinkedList<User>();
	}
	
	public Users(LinkedList<User> users) {
		this.users = users;
	}
	
	public LinkedList<User> getUsers() {
		return this.users;
	}
	
	public int getNumUsers() {
		return this.users.size();
	}
	
	public void addUsers(User user) {
		this.users.add(user);
	}
	
	@SuppressWarnings("unchecked")
	public static Users loadUsers() {
		List<User> users = new LinkedList<User>();
		Users loadedUsers = null;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();	
		users = session.createQuery("FROM User").list();
		session.getTransaction().commit();
		loadedUsers = new Users(new LinkedList<User>(users));
		
		return loadedUsers;
	}
	
	public User getUser(String user, String password) {
		for (User u : this.users) {
			if (u.getUser().equals(user) && u.getPassword().equals(password));
				return u;
		}
		
		return null;
	}
}
