package executor;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import boundary.SplashScreen;

public class UBill {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {		
		try {
			//UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        	UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }
        new SplashScreen();
	}

}
