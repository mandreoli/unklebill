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
	
	public static void login(Main main, JPanel mainPane, JPanel panel) {
		main.enableNavigationButtons(true);
		mainPane.remove(panel);
		main.toggleNaviButtons(false, true, true);
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
