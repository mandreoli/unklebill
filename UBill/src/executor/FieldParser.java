package executor;

import java.util.regex.Pattern;

public class FieldParser {
	
	private static boolean checkUser(String user) {
		String text = user.trim();
		String regex = "^[A-Za-z0-9]{4,15}";
		
		if (!Pattern.matches(regex, text))
			return false;
		
		return true;
	}
	
	private static boolean checkPassword(char[] password) {
		String text = new String(password);
		String regex = "^[A-Za-z0-9]{4,15}";
		
		if (!Pattern.matches(regex, text))
			return false;
		
		return true;
	}
	
	private static boolean checkName(String name) {
		String text = name.trim();		
		
		if (text.equals("") || text.length() > 30)
			return false;
		
		return true;
	}
	
	public static boolean check(String user, char[] password) {
		if (checkUser(user) && checkPassword(password))
			return true;		
		
		return false;
	}
	
	public static boolean check(String user, char[] password, String name) {
		if (checkUser(user) && checkPassword(password) && checkName(name))
			return true;		
		
		return false;
	}
	
	public static boolean check(String user, char[] password, char[] password2, String name) {
		String pwd1 = new String(password);
		String pwd2 = new String(password2);
		
		if (checkUser(user) && checkPassword(password) && pwd1.equals(pwd2) && checkName(name))
			return true;		
		
		return false;
	}
}
