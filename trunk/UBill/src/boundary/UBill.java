package boundary;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.luna.LunaLookAndFeel;


public class UBill {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {		
		try {
			UIManager.setLookAndFeel(new LunaLookAndFeel());			
        } catch (Exception e) {
        	UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
        new SplashScreen();
	}

}
