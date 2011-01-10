package executor;

import javax.swing.JPanel;

import boundary.Home;
import boundary.Lock;
import boundary.Main;
import store.User;

public class Login {
	
	private static String username = null;
	private static String password = null;
	
	public static boolean checkUser(String user, char[] pwd) {
		User loadedUser = User.loadUser(user, new String(pwd));
		
		if (loadedUser == null)
			return false;
		
		username = loadedUser.getUser();
		password = loadedUser.getPassword();
		
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
		new Home(mainPane);
		mainPane.repaint();
	}
	
	public static void logout(Main main, JPanel mainPane, JPanel panel) {
		Login.username = null;
		Login.password = null;
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
}
