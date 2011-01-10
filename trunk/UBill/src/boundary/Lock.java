package boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Lock {
	
	private JPanel lockPane = null;
	private JPanel loginPane = null;
	private JPanel signinPane = null; 
	private JLabel lockLabelImage = null;
	

	public Lock(JPanel mainPane) {
		mainPane.add(getLockPane(), BorderLayout.CENTER);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel getLockPane() {
		if (lockPane == null) {
			lockPane = new JPanel();
			lockPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			lockPane.setLocation(new Point(120, 0));
			lockPane.setSize(new Dimension(480, 435));
			lockPane.setLayout(null);
			lockLabelImage = new JLabel();
			lockLabelImage.setBounds(176, 40, 128, 128);
			lockLabelImage.setIcon(new ImageIcon(getClass().getResource("/icons/used/lock128.png")));
			lockPane.add(lockLabelImage);
			lockPane.add(getLoginPane());			
		}
		return lockPane;
	}
	
	public JPanel getLoginPane() {
		if (loginPane == null) {
			loginPane = new JPanel();			
			loginPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Login", TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande", Font.PLAIN, 12), Color.DARK_GRAY));
			loginPane.setBounds(70, 215, 340, 185);			
			loginPane.setLayout(null);
			
			JLabel nameLabel = new JLabel("Name");
			nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			nameLabel.setBounds(45, 44, 61, 16);
			loginPane.add(nameLabel);
			
			JLabel pwdLabel = new JLabel("Password");
			pwdLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			pwdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			pwdLabel.setBounds(45, 84, 61, 16);
			loginPane.add(pwdLabel);
			
			JTextField textField = new JTextField();
			textField.setToolTipText("Your name");
			textField.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			textField.setBounds(118, 41, 172, 22);
			loginPane.add(textField);
			textField.setColumns(10);
			
			JPasswordField passwordField = new JPasswordField();
			passwordField.setToolTipText("Your password");
			passwordField.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			passwordField.setBounds(118, 81, 172, 22);
			loginPane.add(passwordField);
			
			JButton loginBtn = new JButton("Login");
			loginBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			loginBtn.setIcon(new ImageIcon(Lock.class.getResource("/icons/used/login16.png")));
			loginBtn.setToolTipText("Login");
			loginBtn.setBounds(230, 138, 90, 30);
			loginBtn.setEnabled(false);
			loginPane.add(loginBtn);
			
			JButton newBtn = new JButton("New user");
			newBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lockPane.remove(loginPane);
					lockPane.add(getSigninPane());
					lockPane.repaint();
				}
			});
			newBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			newBtn.setIcon(new ImageIcon(Lock.class.getResource("/icons/used/sign16.png")));
			newBtn.setToolTipText("Add a new user");
			newBtn.setBounds(20, 138, 90, 30);
			loginPane.add(newBtn);
		}
		return loginPane;
	}
	
	public JPanel getSigninPane() {
		if (signinPane == null) {
			signinPane = new JPanel();			
			signinPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "New user", TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande", Font.PLAIN, 12), Color.DARK_GRAY));
			signinPane.setBounds(70, 215, 340, 185);			
			signinPane.setLayout(null);
		}
		return signinPane;
	}
}
