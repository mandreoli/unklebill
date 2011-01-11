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
