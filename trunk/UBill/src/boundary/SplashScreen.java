package boundary;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;

import org.hibernate.Session;

import util.HibernateUtil;

public class SplashScreen extends JWindow {

	private static final long serialVersionUID = 1L;
	private JPanel mainPane = null;
	private int wWidth = 480;
	private int wHeight = 200;
    
    public SplashScreen() {
    	setContentPane(getMainPane());
    	setSize(new Dimension(this.wWidth, this.wHeight));		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(new Point((d.width-wWidth)/2, (d.height-wHeight)/2));
		start();		
    }
    
    private JPanel getMainPane() {
    	if (mainPane == null) {
    		mainPane = new JPanel();    		
    		//mainPane.setBackground(new Color(255, 248, 220));
    		mainPane.setBorder(new LineBorder(new Color(51, 102, 204), 5));
    		mainPane.setSize(new Dimension(this.wWidth, this.wHeight));
			mainPane.setLayout(null);
			
			JLabel devLabel = new JLabel("UnkleBill v1.0 developed by Michele Andreoli");
			devLabel.setForeground(Color.DARK_GRAY);
			devLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
			devLabel.setHorizontalAlignment(SwingConstants.CENTER);
			devLabel.setBounds(6, 6, 468, 16);
			mainPane.add(devLabel);
			
			JLabel iconLabel = new JLabel("");
			iconLabel.setIcon(new ImageIcon(SplashScreen.class.getResource("/icons/UBill_185x185.png")));
			iconLabel.setBounds(6, 6, 185, 188);
			mainPane.add(iconLabel);
			
			JLabel titleLabel = new JLabel("I Want Your...");
			titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
			titleLabel.setFont(new Font("Lucida Fax", Font.BOLD, 34));
			titleLabel.setBounds(180, 41, 287, 81);
			mainPane.add(titleLabel);
			
			JLabel title2Label = new JLabel("Bills!");
			title2Label.setForeground(Color.RED);
			title2Label.setHorizontalAlignment(SwingConstants.CENTER);
			title2Label.setFont(new Font("SansSerif", Font.BOLD, 44));
			title2Label.setBounds(180, 87, 287, 81);
			mainPane.add(title2Label);
			
			JLabel imgLabel = new JLabel("");
			imgLabel.setIcon(new ImageIcon(SplashScreen.class.getResource("/icons/chek48.png")));
			imgLabel.setBounds(401, 134, 48, 48);
			mainPane.add(imgLabel);
    	}
    	return mainPane;
    }
    
    private void start() {
    	setVisible(true);
        try {
        	@SuppressWarnings("unused")
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        	Main main = new Main(this);
    		main.START();    		
        } catch (Exception e) {
        	System.err.println("Error: splahscreen\n"+e);
        	this.dispose();
        }
        setVisible(false); 
    }
}
