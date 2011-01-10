package executor;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import boundary.SplashScreen;

public class UBill {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {		
		try {
			//UIManager.put("nimbusBase", new Color(255, 248, 220));
			//UIManager.put("nimbusBlueGrey", new Color(255, 248, 220));
			//UIManager.put("control", new Color(255, 248, 220));
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");		
        } catch (Exception e) {
        	UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }
        new SplashScreen();
	}

}
