package executor;

import javax.swing.JPanel;

import boundary.Home;
import boundary.Lock;
import boundary.Main;
import store.Account;
import store.User;

public class Login {
	
	private static User user = null;		
	private static Account account = null;
	
	public static boolean checkUser(String username, char[] pwd) {
		User loadedUser = User.loadUser(username, new String(pwd));
		
		if (loadedUser == null)
			return false;
		
		user = loadedUser;
		
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
		Login.user = null;
		main.enableNavigationButtons(false);
		mainPane.remove(panel);
		new Lock(mainPane, main);
		mainPane.repaint();
	}

	public static void setUser(User user) {
		Login.user = user;
	}

	public static User getUser() {
		return user;
	}

	public static void setAccount(Account account) {
		Login.account = account;
	}

	public static Account getAccount() {
		return account;
	}
}
