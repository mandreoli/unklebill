package boundary;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import store.Account;
import datatype.Currency;
import datatype.Date;
import executor.FieldParser;
import executor.Login;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;



public class InsertAccount extends BaseBoundary {

	private int wWidth = 320;
	private int wHeight = 260;
	private JDialog mainDialog = null;
	private JPanel mainPane = null;
	private JButton exitBtn = null;
	private JButton saveBtn = null;
	private JTextField nameText = null;
	private JTextField balanceText = null;
	private JTextArea descrText = null;
	private Color errorColor = new Color(255, 99, 99);
	private Color normalColor = new Color(255, 255, 255);
	private JCheckBox primaryBox = null;
	private Account account = null;
	private JComboBox currencyBox = null;

	
	
	public InsertAccount() {
		getMainDialog().setVisible(true);
	}
	
	/**
	 * @wbp.parser.constructor
	 **/
	public InsertAccount(Account account) {
		this.account = account;
		getMainDialog().setVisible(true);		
	}
	
	private JDialog getMainDialog() {
		if (mainDialog == null) {
			mainDialog = new JDialog();
			mainDialog.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/favico.png")));
			mainDialog.setTitle("Add new account");
			mainDialog.setSize(new Dimension(this.wWidth, this.wHeight));		
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			mainDialog.setLocation(new Point((d.width-wWidth)/2, (d.height-wHeight)/2));
			mainDialog.setResizable(false);
			mainDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);			
			mainDialog.setContentPane(getMainPane());
			if (this.account != null)
				catchTypedField(nameText, descrText, balanceText);
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
			
			JLabel nameLabel = new JLabel("Account name");
			nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			nameLabel.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			nameLabel.setToolTipText("Unique account name");
			nameLabel.setBounds(20, 18, 90, 16);
			mainPane.add(nameLabel);
			
			JLabel descrLabel = new JLabel("Description");
			descrLabel.setToolTipText("Optional description");
			descrLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			descrLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			descrLabel.setBounds(6, 44, 104, 16);
			mainPane.add(descrLabel);
			
			JLabel balanceLabel = new JLabel("Initial balance");
			balanceLabel.setToolTipText("Beginning balance");
			balanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			balanceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			balanceLabel.setBounds(6, 137, 104, 16);
			mainPane.add(balanceLabel);
			
			nameText = new JTextField();
			nameText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(nameText, descrText, balanceText);
					if (FieldParser.checkUser(nameText.getText()))
						nameText.setBackground(normalColor);
					else
						nameText.setBackground(errorColor);
				}
			});
			nameText.setToolTipText("Account name");
			nameText.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			nameText.setBounds(120, 12, 180, 27);
			mainPane.add(nameText);
			nameText.setColumns(10);
			if (this.account != null) {
				nameText.setText(this.account.getAccount());
			}
			
			JScrollPane scrollDescrPane = new JScrollPane();
			scrollDescrPane.setBounds(122, 44, 175, 82);
			mainPane.add(scrollDescrPane);
			
			descrText = new JTextArea();
			descrText.setLineWrap(true);
			descrText.setWrapStyleWord(true);
			descrText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(nameText, descrText, balanceText);
					if (descrText.getText().length() <= 160)
						descrText.setBackground(normalColor);
					else
						descrText.setBackground(errorColor);
				}
			});
			descrText.setToolTipText("Optional description");
			if (this.account != null) {
				descrText.setText(this.account.getDescription());
			}
			scrollDescrPane.setViewportView(descrText);
			
			balanceText = new JTextField("0.0");
			balanceText.setHorizontalAlignment(SwingConstants.RIGHT);
			balanceText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(nameText, descrText, balanceText);										
					if (FieldParser.checkFloat(balanceText.getText(), true))
						balanceText.setBackground(normalColor);
					else
						balanceText.setBackground(errorColor);
				}
			});
			balanceText.setToolTipText("Beginning balance");
			balanceText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			balanceText.setColumns(10);
			balanceText.setBounds(120, 131, 90, 27);
			mainPane.add(balanceText);
			if (this.account != null) {
				balanceText.setText(String.valueOf(this.account.getBalance()));
				balanceText.setEditable(false);
			}
			
			mainPane.add(getPrimaryBox());
			mainPane.add(getCurrencyBox());
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
			exitBtn.setToolTipText("Back to home");
			exitBtn.setIcon(new ImageIcon(getClass().getResource("/icons/error16.png")));
			exitBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			exitBtn.setBounds(20, 200, 90, 30);
		}
		return exitBtn;
	}

	private JButton getSaveBtn() {
		if (saveBtn == null) {
			saveBtn = new JButton();
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					if (account == null) {						
						if (Account.loadAccount(nameText.getText(), Login.getUser().getUser()) == null) {
							Account account = new Account(nameText.getText(), Login.getUser().getUser(), descrText.getText(), Double.valueOf(balanceText.getText()), Date.getCurrentDate(), currencyBox.getSelectedItem().toString(), primaryBox.isSelected());
							account.saveAccount();
							ok("Account added<br/>with success.");
							mainDialog.dispose();
						}
						else
							fail("The account name<br/>is already in use!");
					}
					else {
						account.setAccount(nameText.getText());
						account.setDescription(descrText.getText());
						account.setBalance(Double.valueOf(balanceText.getText()));
						account.setUsable(primaryBox.isSelected());
						account.setCreation(Date.getCurrentDate());
						account.setCurrency(currencyBox.getSelectedItem().toString());
						account.updateAccount();
						ok("Account modified<br/>with success.");
						mainDialog.dispose();
					}
				}
			});
			if (this.account == null) {
				saveBtn.setToolTipText("Add this account");
				saveBtn.setText("Add");
			}
			else {
				saveBtn.setToolTipText("Modify this account");
				saveBtn.setText("Modify");
			}
			saveBtn.setIcon(new ImageIcon(getClass().getResource("/icons/ok16.png")));
			saveBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			saveBtn.setLocation(210, 200);
			saveBtn.setEnabled(false);
			saveBtn.setSize(new Dimension(90, 30));
		}
		return saveBtn;
	}
	
	private void catchTypedField(JTextField account, JTextArea descr, JTextField balance) {
		
		if (FieldParser.checkUser(account.getText()) && descr.getText().length() <= 160 && FieldParser.checkFloat(balance.getText(), true)) {
			saveBtn.setEnabled(true);
		}
		else {
			saveBtn.setEnabled(false);
		}
	}
	
	private JCheckBox getPrimaryBox() {
		if (primaryBox == null) {
			primaryBox = new JCheckBox("Primary  ");
			primaryBox.setToolTipText("Set default account");
			primaryBox.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			primaryBox.setHorizontalTextPosition(SwingConstants.LEFT);
			primaryBox.setHorizontalAlignment(SwingConstants.RIGHT);
			primaryBox.setBounds(28, 162, 117, 23);
			if (this.account != null) {				
				primaryBox.setSelected(this.account.isUsable());
			}
		}
		return primaryBox;
	}
	
	private JComboBox getCurrencyBox() {
		if (currencyBox == null) {
			currencyBox = new JComboBox(Currency.values());
			currencyBox.setBounds(210, 131, 90, 27);
			if (this.account != null) {	
				currencyBox.setSelectedItem(this.account.getCurrency());
			}
		}
		return currencyBox;
	}
}	

