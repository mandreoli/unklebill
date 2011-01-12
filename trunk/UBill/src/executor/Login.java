package executor;

import javax.swing.JPanel;

import boundary.Home;
import boundary.Lock;
import boundary.Main;
import store.Account;
import store.User;

public class Login {
	
	private static String username = null;
	private static String password = null;
	private static String fullname = null;
	private static Account account = null;
	
	public static boolean checkUser(String user, char[] pwd) {
		User loadedUser = User.loadUser(user, new String(pwd));
		
		if (loadedUser == null)
			return false;
		
		username = loadedUser.getUser();
		password = loadedUser.getPassword();
		fullname = loadedUser.getName();
		
		return true;
	}
	
	public static boolean checkFreeUser(String user, char[] pwd) {
		User loadedUser = User.checkFreeUser(user, new String(pwd));
		
		if (loadedUser == null)
			return true;
		
		return false;
	}
	
	public static void login(Main main, JPanel mainPane, JPanel panel) {
		main.enableNavigationButtons(true);
		mainPane.remove(panel);
		new Home(mainPane, main);
		mainPane.repaint();
	}
	
	public static void logout(Main main, JPanel mainPane, JPanel panel) {
		Login.username = null;
		Login.password = null;
		Login.fullname = null;
		main.enableNavigationButtons(false);
		mainPane.remove(panel);
		new Lock(mainPane, main);
		mainPane.repaint();
	}

	public static void setUsername(String username) {
		Login.username = username;
	}

	public static String getUsername() {
		return username;
	}

	public static void setPassword(String password) {
		Login.password = password;
	}

	public static String getPassword() {
		return password;
	}
	
	public static void setFullname(String fullname) {
		Login.fullname = fullname;
	}

	public static String getFullname() {
		return fullname;
	}

	public static void setAccount(Account account) {
		Login.account = account;
	}

	public static Account getAccount() {
		return account;
	}
}
