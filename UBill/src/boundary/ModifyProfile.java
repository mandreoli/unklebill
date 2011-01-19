package boundary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import datatype.Currency;
import executor.FieldParser;
import executor.Login;
import store.User;



public class ModifyProfile extends BaseBoundary {
	
	private int wWidth = 320;
	private int wHeight = 260;
	private JDialog mainDialog = null;
	private JPanel mainPane = null;
	private JButton exitBtn = null;
	private JButton saveBtn = null;
	private JTextField nameText = null;
	private JTextField userText = null;
	private JPasswordField pwdText = null;
	private JPasswordField pwd2Text = null;
	private Color errorColor = new Color(255, 99, 99);
	private Color normalColor = new Color(255, 255, 255);
	private JComboBox currencyBox = null;
	private JCheckBox autoCheck = null;
	private User user = null;
	
	
	public ModifyProfile(User user) {
		this.user = user;
		getMainDialog().setVisible(true);
	}
	
	private JDialog getMainDialog() {
		if (mainDialog == null) {
			mainDialog = new JDialog();
			mainDialog.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/favico.png")));
			mainDialog.setTitle("Modify your profile");
			mainDialog.setSize(new Dimension(this.wWidth, this.wHeight));		
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			mainDialog.setLocation(new Point((d.width-wWidth)/2, (d.height-wHeight)/2));
			mainDialog.setResizable(false);
			mainDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);			
			mainDialog.setContentPane(getMainPane());
			mainDialog.setModal(true);
		}
		return mainDialog;
	}
	
	private JPanel getMainPane() {
		if (mainPane == null) {
			mainPane = new JPanel();
			mainPane.setLayout(null);
			mainPane.add(getExitBtn());
			mainPane.add(getSaveBtn());
			
			JLabel iconLabel = new JLabel("");
			iconLabel.setIcon(new ImageIcon(getClass().getResource("/icons/user24.png")));
			iconLabel.setBounds(20, 15, 24, 24);
			mainPane.add(iconLabel);
			
			JLabel nameLabel = new JLabel("Name");
			nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			nameLabel.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			nameLabel.setBounds(45, 20, 69, 16);
			mainPane.add(nameLabel);
			
			JLabel userLabel = new JLabel("Username");
			userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			userLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			userLabel.setBounds(45, 56, 69, 16);
			mainPane.add(userLabel);
			
			JLabel pwdLabel = new JLabel("Password");
			pwdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			pwdLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			pwdLabel.setBounds(45, 90, 69, 16);
			mainPane.add(pwdLabel);
			
			JLabel pwd2Label = new JLabel("Confirm password");
			pwd2Label.setForeground(Color.DARK_GRAY);
			pwd2Label.setHorizontalAlignment(SwingConstants.RIGHT);
			pwd2Label.setFont(new Font("Lucida Grande", Font.PLAIN, 10));			
			pwd2Label.setBounds(20, 118, 94, 16);
			mainPane.add(pwd2Label);
			
			JLabel currencyLabel = new JLabel("Currency");
			currencyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			currencyLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			currencyLabel.setBounds(45, 160, 69, 16);
			mainPane.add(currencyLabel);
			
			nameText = new JTextField(user.getName());
			nameText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(userText, pwdText, pwd2Text, nameText);
					if (FieldParser.checkName(nameText.getText()))
						nameText.setBackground(normalColor);
					else
						nameText.setBackground(errorColor);
				}
			});
			nameText.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			nameText.setToolTipText("Your complete name");
			nameText.setColumns(10);
			nameText.setBounds(118, 15, 172, 27);
			mainPane.add(nameText);
			
			userText = new JTextField(user.getUser());
			userText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(userText, pwdText, pwd2Text, nameText);
					if (FieldParser.checkUser(userText.getText())) {						
						userText.setBackground(normalColor);
					}
					else
						userText.setBackground(errorColor);
				}
			});
			userText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			userText.setToolTipText("Your username");
			userText.setBounds(118, 51, 172, 27);
			mainPane.add(userText);
			userText.setColumns(10);
			
			pwdText = new JPasswordField(user.getPassword());
			pwdText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(userText, pwdText, pwd2Text, nameText);
					if (FieldParser.checkPassword(pwdText.getPassword()))
						pwdText.setBackground(normalColor);
					else
						pwdText.setBackground(errorColor);
				}
			});
			pwdText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			pwdText.setToolTipText("Your password");
			pwdText.setBounds(118, 85, 172, 27);
			mainPane.add(pwdText);
			
			pwd2Text = new JPasswordField(user.getPassword());
			pwd2Text.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(userText, pwdText, pwd2Text, nameText);
					if (FieldParser.checkPassword2(pwdText.getPassword(), pwd2Text.getPassword()))
						pwd2Text.setBackground(normalColor);
					else
						pwd2Text.setBackground(errorColor);
				}
			});
			pwd2Text.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			pwd2Text.setToolTipText("Confirm your password");
			pwd2Text.setBounds(118, 113, 172, 27);
			mainPane.add(pwd2Text);
			
			currencyBox = new JComboBox(Currency.values());
			currencyBox.setBounds(118, 157, 80, 24);
			currencyBox.setToolTipText("Default currency");
			currencyBox.setSelectedItem(user.getCurrency());
			mainPane.add(currencyBox);
			
			autoCheck = new JCheckBox();
			autoCheck.setHorizontalTextPosition(SwingConstants.LEFT);
			autoCheck.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			autoCheck.setHorizontalAlignment(SwingConstants.RIGHT);
			autoCheck.setText("Auto login");
			autoCheck.setSelected(user.isAuto());
			autoCheck.setBounds(194, 156, 98, 24);
			mainPane.add(autoCheck);
			
			catchTypedField(userText, pwdText, pwd2Text, nameText);
			
		}
		return mainPane;
	}
	
	private JButton getExitBtn() {
		if (exitBtn == null) {
			exitBtn = new JButton("Cancel");
			exitBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getMainDialog().dispose();
				}
			});
			exitBtn.setToolTipText("Cancel modifies");
			exitBtn.setIcon(new ImageIcon(getClass().getResource("/icons/error16.png")));
			exitBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			exitBtn.setBounds(20, 200, 90, 30);
		}
		return exitBtn;
	}
	
	private JButton getSaveBtn() {
		if (saveBtn == null) {
			saveBtn = new JButton("Modify");
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (User.checkUpdatableUser(userText.getText(), pwdText.getPassword(), user.getId())) {
						String oldUser = user.getUser();
						user.setName(nameText.getText());
						user.setUser(userText.getText());
						user.setPassword(new String(pwdText.getPassword()));
						user.setCurrency(currencyBox.getSelectedItem().toString());
						user.setAuto(autoCheck.isSelected());
						user.updateUser(oldUser);			
						ok("Profile updated with success!");
						Login.setUser(user);
						mainDialog.dispose();						
					}
					else
						fail("Username or password<br/>used by another user!");
				}
			});
			saveBtn.setToolTipText("Save modifies");
			saveBtn.setIcon(new ImageIcon(getClass().getResource("/icons/ok16.png")));
			saveBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			saveBtn.setLocation(210, 200);
			saveBtn.setEnabled(false);
			saveBtn.setSize(new Dimension(90, 30));
		}
		return saveBtn;
	}
	
	private void catchTypedField(JTextField user, JPasswordField pwd, JPasswordField pwd2, JTextField name) {				
		
		if (FieldParser.checkUser(user.getText()) && FieldParser.checkPassword(pwd.getPassword()) && FieldParser.checkPassword2(pwd.getPassword(), pwd2.getPassword()) && FieldParser.checkName(nameText.getText()))
			saveBtn.setEnabled(true);
		else
			saveBtn.setEnabled(false);		
	}
}
