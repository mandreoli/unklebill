package boundary;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import store.User;

public class Registration extends BaseBoundary {
	
	private JPanel signinPane = null;
	private Lock lock = null;
	private JPanel lockPane = null;
	private JTextField userText;
	private JPasswordField pwdText;
	private JPasswordField pwd2Text;
	private JTextField nameText;
	
	
	public Registration(JPanel lockPane, Lock lock) {		
		this.lockPane = lockPane;
		this.lock = lock;
		lockPane.add(getSigninPane());
	}
	
	private JPanel getSigninPane() {
		if (signinPane == null) {
			signinPane = new JPanel();			
			signinPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "New user", TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande", Font.PLAIN, 12), Color.DARK_GRAY));
			signinPane.setBounds(70, 180, 340, 220);			
			signinPane.setLayout(null);
			
			JButton prevBtn = new JButton("Back");
			prevBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lockPane.remove(signinPane);
					lock.addLoginPane();
					lockPane.repaint();
				}
			});
			prevBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			prevBtn.setIcon(new ImageIcon(Registration.class.getResource("/icons/used/prev16.png")));			
			prevBtn.setBounds(20, 175, 90, 30);
			signinPane.add(prevBtn);
			
			JButton registerBtn = new JButton("Add");
			registerBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//TODO controllare i campi inseriti
					
					User user = new User(userText.getText(), new String(pwdText.getPassword()), nameText.getText(), null, false);
					user.saveUser();
					ok(nameText.getText()+" added!");
					
					if (Login.checkUser(userText.getText(), pwdText.getPassword())) {
						Login.login(lock.getMain(), lock.getMainPane(), lockPane);
					}
					else
						fail("Login failed");
				}
			});
			registerBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			registerBtn.setIcon(new ImageIcon(Registration.class.getResource("/icons/used/ok16.png")));
			registerBtn.setToolTipText("Create new user");
			registerBtn.setBounds(238, 175, 90, 30);
			signinPane.add(registerBtn);
			
			JLabel nameLabel = new JLabel("Name");
			nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			nameLabel.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			nameLabel.setBounds(45, 39, 69, 16);
			signinPane.add(nameLabel);
			
			JLabel userLabel = new JLabel("Username");
			userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			userLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			userLabel.setBounds(45, 75, 69, 16);
			signinPane.add(userLabel);
			
			JLabel pwdLabel = new JLabel("Password");
			pwdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			pwdLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			pwdLabel.setBounds(45, 109, 69, 16);
			signinPane.add(pwdLabel);
			
			JLabel pwd2Label = new JLabel("Confirm password");
			pwd2Label.setForeground(Color.DARK_GRAY);
			pwd2Label.setHorizontalAlignment(SwingConstants.RIGHT);
			pwd2Label.setFont(new Font("Lucida Grande", Font.PLAIN, 10));			
			pwd2Label.setBounds(20, 134, 94, 16);
			signinPane.add(pwd2Label);
			
			nameText = new JTextField();
			nameText.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			nameText.setToolTipText("Your complete name");
			nameText.setColumns(10);
			nameText.setBounds(118, 36, 172, 22);
			signinPane.add(nameText);
			
			userText = new JTextField();
			userText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			userText.setToolTipText("Your username");
			userText.setBounds(118, 72, 172, 22);
			signinPane.add(userText);
			userText.setColumns(10);
			
			pwdText = new JPasswordField();
			pwdText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			pwdText.setToolTipText("Your password");
			pwdText.setBounds(118, 106, 172, 22);
			signinPane.add(pwdText);
			
			pwd2Text = new JPasswordField();
			pwd2Text.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			pwd2Text.setToolTipText("Confirm your password");
			pwd2Text.setBounds(118, 130, 172, 22);
			signinPane.add(pwd2Text);
		}
		return signinPane;
	}
}
