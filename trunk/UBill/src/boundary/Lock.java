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
import executor.FieldParser;
import executor.Login;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Lock extends BaseBoundary {
	
	private JPanel lockPane = null;
	private JPanel loginPane = null;
	private JPanel mainPane = null;
	private JLabel lockLabelImage = null;
	private JTextField userText = null;
	private JPasswordField pwdText = null;
	private Lock lock = null;
	private Main main = null;
	private JButton loginBtn = null;
	private JButton newBtn = null;
	private JLabel lockTitleLabel = null;
	private JLabel lockParLabel = null;
	private Color errorColor = new Color(255, 99, 99);
	private Color normalColor = new Color(255, 255, 255);

	
	
	public Lock(JPanel mainPane, Main main) {
		this.mainPane = mainPane;
		this.main = main;
		this.mainPane.add(getLockPane(), BorderLayout.CENTER);
		this.lock = this;
	}
	
	public JPanel getMainPane() {
		return this.mainPane;
	}
	
	public Main getMain() {
		return this.main;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private JPanel getLockPane() {
		if (lockPane == null) {
			lockPane = new JPanel();
			lockPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			lockPane.setLocation(new Point(120, 0));
			lockPane.setSize(new Dimension(480, 435));
			lockPane.setLayout(null);
			lockPane.add(getLoginPane());
			
			lockLabelImage = new JLabel();
			lockLabelImage.setBounds(40, 40, 128, 128);
			lockLabelImage.setIcon(new ImageIcon(getClass().getResource("/icons/used/uLock.png")));
			lockPane.add(lockLabelImage);
			
			lockParLabel = new JLabel("<html>Type your username and<br/>password to login!<br/><br/>If you don't have credentials<br/>you can create a new user.</html>");
			lockParLabel.setVerticalAlignment(SwingConstants.TOP);
			lockParLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			lockParLabel.setBounds(190, 65, 250, 117);
			lockPane.add(lockParLabel);
			
			lockTitleLabel = new JLabel("Unkle Bill says:");
			lockTitleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
			lockTitleLabel.setBounds(190, 30, 250, 41);
			lockPane.add(lockTitleLabel);			
		}
		return lockPane;
	}
	
	public void addLoginPane() {
		this.lockPane.add(getLoginPane());
		lockPane.add(lockLabelImage);
		lockPane.add(lockParLabel);
	}
	
	private JPanel getLoginPane() {
		if (loginPane == null) {
			loginPane = new JPanel();			
			loginPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Login", TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande", Font.PLAIN, 12), Color.DARK_GRAY));
			loginPane.setBounds(70, 215, 340, 185);			
			loginPane.setLayout(null);		
			
			JLabel nameLabel = new JLabel("Username");
			nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			nameLabel.setBounds(45, 44, 61, 16);
			loginPane.add(nameLabel);
			
			JLabel pwdLabel = new JLabel("Password");
			pwdLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			pwdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			pwdLabel.setBounds(45, 84, 61, 16);
			loginPane.add(pwdLabel);
			
			userText = new JTextField();
			userText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(userText, pwdText);
					if (FieldParser.checkUser(userText.getText()))
						userText.setBackground(normalColor);
					else
						userText.setBackground(errorColor);
				}
			});
			userText.setToolTipText("Your username");
			userText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			userText.setBounds(118, 39, 172, 27);
			loginPane.add(userText);
			userText.setColumns(10);
			
			pwdText = new JPasswordField();
			pwdText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(userText, pwdText);
					if (FieldParser.checkPassword(pwdText.getPassword()))
						pwdText.setBackground(normalColor);
					else
						pwdText.setBackground(errorColor);
				}
			});
			pwdText.setToolTipText("Your password");
			pwdText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			pwdText.setBounds(118, 79, 172, 27);
			loginPane.add(pwdText);
			
			loginBtn = new JButton("Login");
			loginBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {								
					if (Login.checkUser(userText.getText(), pwdText.getPassword())) {
						Login.login(main, mainPane, lockPane);
					}
					else
						fail("Login failed. Check fields!");
				}
			});
			loginBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			loginBtn.setIcon(new ImageIcon(Lock.class.getResource("/icons/used/login16.png")));
			loginBtn.setToolTipText("Login");
			loginBtn.setBounds(230, 140, 90, 30);
			loginBtn.setEnabled(false);
			loginPane.add(loginBtn);
			
			newBtn = new JButton("New");
			newBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lockPane.remove(loginPane);
					lockPane.remove(lockLabelImage);
					lockPane.remove(lockParLabel);
					new Registration(lockPane, lock);
					lockPane.repaint();
				}
			});
			newBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			newBtn.setIcon(new ImageIcon(Lock.class.getResource("/icons/used/sign16.png")));
			newBtn.setToolTipText("Add a new user");
			newBtn.setBounds(20, 140, 90, 30);
			loginPane.add(newBtn);
		}
		return loginPane;
	}		
	
	private void catchTypedField(JTextField user, JPasswordField pwd) {
		
		if (FieldParser.checkUser(user.getText()) && FieldParser.checkPassword(pwd.getPassword())) {
			loginBtn.setEnabled(true);
		}
		else {
			loginBtn.setEnabled(false);
		}
	}
}
