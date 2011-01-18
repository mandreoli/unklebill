package boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JPanel;
import datatype.Accounts;
import datatype.Date;
import datatype.Transactions;
import executor.FieldParser;
import executor.Login;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import store.Account;


public class Home extends BaseBoundary {
	
	private JPanel homePane = null;
	private JPanel manageAccountsPane = null;
	private JScrollPane scrollAccountPane = null;
	private Accounts accounts = null;
	private JList listAccounts = null;
	private JButton btnAdd = null;
	private JButton btnRemove = null;
	private JLabel listLabel = null;
	private JLabel accountLabel = null;
	private JLabel createLabel = null;
	private JLabel descrLabel = null;
	private JLabel balanceLabel = null;
	private JButton modBtn = null;
	private JButton showBtn = null;
	private JLabel primaryLabel = null;
	private Main main = null;
	private Color active = new Color(0, 128, 0);
	private Color neutro = new Color(0, 0, 0);
	private Color passive = new Color(128, 0, 0);
	
	
	
	public Home(JPanel mainPane, Main main) {
		this.main = main;
		mainPane.add(getHomePane(), BorderLayout.CENTER);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel getHomePane() {
		if (homePane == null) {
			homePane = new JPanel();
			homePane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			homePane.setLocation(new Point(120, 0));
			homePane.setSize(new Dimension(480, 435));
			homePane.setLayout(null);
			homePane.add(getManageAccountsPane());
			
			JLabel userLabel = new JLabel(" "+Login.getUser().getName());
			userLabel.setIcon(new ImageIcon(getClass().getResource("/icons/user24.png")));
			userLabel.setFont(new Font("Lucida Grande", Font.BOLD, 24));
			userLabel.setToolTipText("Logged user");
			userLabel.setBounds(20, 20, 442, 30);
			homePane.add(userLabel);
		}
		return homePane;
	}
	
	private JPanel getManageAccountsPane() {
		if (manageAccountsPane == null) {
			manageAccountsPane = new JPanel();
			manageAccountsPane.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			manageAccountsPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Manage accounts", TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande", Font.PLAIN, 12), Color.DARK_GRAY));
			manageAccountsPane.setBounds(6, 195, 468, 235);
			manageAccountsPane.setLayout(null);			
			manageAccountsPane.add(getBtnRemove());
			manageAccountsPane.add(getBtnAdd());
			manageAccountsPane.add(getShowBtn());
			manageAccountsPane.add(getModBtn());
			addingInfoLabels();
			manageAccountsPane.add(getListLabel());
			manageAccountsPane.add(getScrollAccountPane());				
		}
		return manageAccountsPane;
	}
	
	private void addingInfoLabels() {
		accountLabel = new JLabel("");
		accountLabel.setHorizontalAlignment(SwingConstants.LEFT);
		accountLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		accountLabel.setBounds(209, 25, 253, 32);
		
		createLabel = new JLabel("");
		createLabel.setHorizontalAlignment(SwingConstants.LEFT);
		createLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		createLabel.setBounds(209, 59, 253, 16);
		
		primaryLabel = new JLabel("");
		primaryLabel.setHorizontalAlignment(SwingConstants.LEFT);
		primaryLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		primaryLabel.setBounds(209, 78, 253, 16);
		
		descrLabel = new JLabel("");
		descrLabel.setVerticalAlignment(SwingConstants.TOP);
		descrLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		descrLabel.setBounds(209, 142, 253, 83);
		
		balanceLabel = new JLabel("");
		balanceLabel.setVerticalAlignment(SwingConstants.TOP);
		balanceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		balanceLabel.setBounds(209, 105, 253, 30);
		
		manageAccountsPane.add(accountLabel);
		manageAccountsPane.add(createLabel);
		manageAccountsPane.add(primaryLabel);
		manageAccountsPane.add(balanceLabel);		
		manageAccountsPane.add(descrLabel);	
	}
	
	private JScrollPane getScrollAccountPane() {
		if (scrollAccountPane == null) {
			scrollAccountPane = new JScrollPane(getListAccounts());
			scrollAccountPane.setBounds(10, 50, 150, 148);
			scrollAccountPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return scrollAccountPane;
	}
	
	private JList getListAccounts() {
		if (listAccounts == null) {
			listAccounts = new JList();			
			listAccounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listAccounts.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (listAccounts.getSelectedIndex() > -1)
						enableAccountsBtn(true);
				}
			});			
			populateListAccounts();
		}
		return listAccounts;
	}
	
	private void populateListAccounts() {
		this.listAccounts.setSelectedIndex(-1);
		enableAccountsBtn(false);
		this.main.enableLogButtons(false);
		Login.setAccount(null);
		
		this.accounts = Accounts.loadAccounts(Login.getUser().getUser());
		
		if (this.accounts.getNumAccounts() > 0)
			this.listAccounts.setListData(this.accounts.getAccountsNames().toArray());		
		else
			this.listAccounts.setListData(new Object[0]);
		
		Account account = Account.loadDefaultAccount(Login.getUser().getUser());
		if (account != null) {
			Login.setAccount(account);
			this.main.enableLogButtons(true);
			this.listAccounts.setSelectedValue(Login.getAccount().getAccount(), true);
		}
		
		showAccountInfo(Login.getAccount());		
		this.listAccounts.repaint();
	}
	
	private JButton getModBtn() {
		if (modBtn == null) {
			modBtn = new JButton("");
			modBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new InsertAccount(accounts.getAccount(listAccounts.getSelectedValue().toString()));
					populateListAccounts();
				}
			});
			modBtn.setEnabled(false);
			modBtn.setIcon(new ImageIcon(getClass().getResource("/icons/modAccount24.png")));
			modBtn.setToolTipText("Edit account");
			modBtn.setBounds(165, 85, 32, 32);
		}
		return modBtn;
	}
	
	private JButton getShowBtn() {
		if (showBtn == null) {
			showBtn = new JButton("");
			showBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					Account a = accounts.getAccount(listAccounts.getSelectedValue().toString());					
					a.setUsable(true);
					a.updateAccount();
					Login.setAccount(a);
					main.enableNavigationButtons(true);
					showAccountInfo(accounts.getAccount(listAccounts.getSelectedValue().toString()));
				}
			});
			showBtn.setEnabled(false);
			showBtn.setToolTipText("Show account details");
			showBtn.setIcon(new ImageIcon(getClass().getResource("/icons/viewAccount24.png")));
			showBtn.setBounds(165, 50, 32, 32);
		}
		return showBtn;
	}
	
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton("Add");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new InsertAccount();
					populateListAccounts();
				}
			});
			btnAdd.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			btnAdd.setHorizontalTextPosition(SwingConstants.RIGHT);
			btnAdd.setIcon(new ImageIcon(getClass().getResource("/icons/add16.png")));
			btnAdd.setToolTipText("Add an account");
			btnAdd.setBounds(10, 200, 75, 29);
		}
		return btnAdd;
	}
	
	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("Del");			
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean isPrimary = false;
					String accountName = accounts.getAccount(listAccounts.getSelectedValue().toString()).getAccount();
					String primaryString = "";
					
					if (Login.getAccount() != null && accountName.equals(Login.getAccount().getAccount())) {
							isPrimary = true;
							primaryString = "This is your primary account.<br/>";
					}
					
					if (confirm("You are deleting <i>"+listAccounts.getSelectedValue().toString()+"</i>.<br/>"+primaryString+"Are you sure?") == 0) {						
						if (Transactions.loadTransactions(Login.getUser().getUser(), Login.getAccount().getAccount()).getNumTransactions() > 0) {
							if (abort("You are deleting all<br/>transtactions for this account.<br/>Continue anyway?") == 0) {
								Transactions.deleteTransactions(Login.getUser().getUser(), accountName);
								accounts.getAccount(accountName).deleteAccount();								
							}
						}
						else {
							accounts.getAccount(accountName).deleteAccount();
						}
						
						if (isPrimary)
							Login.setAccount(null);
						
						populateListAccounts();
					}					
				}
			});
			btnRemove.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			btnRemove.setHorizontalTextPosition(SwingConstants.LEFT);
			btnRemove.setIcon(new ImageIcon(getClass().getResource("/icons/del16.png")));
			btnRemove.setToolTipText("Remove selected account");
			btnRemove.setBounds(85, 200, 75, 29);
			btnRemove.setEnabled(false);
		}
		return btnRemove;
	}
	
	private JLabel getListLabel() {
		if (listLabel == null) {
			listLabel = new JLabel("Created accounts");
			listLabel.setIcon(new ImageIcon(Home.class.getResource("/icons/accounts16.png")));
			listLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			listLabel.setBounds(10, 25, 150, 24);
		}
		return listLabel;
	}
	
	private void showAccountInfo(Account account) {
		if (account != null) {
			Date date = new Date(account.getCreation());
			String text = Date.getMonth(date.getMonth())+" "+Date.getDay(date.getDay())+", "+date.getYear();
			String prim = "NO";
			
			if (account.isUsable())
				prim = "YES";
			
			this.accountLabel.setText(account.getAccount());
			this.createLabel.setText("<html><b>Last modified:</b>  "+text+"</html>");
			this.primaryLabel.setText("<html><b>Primary:</b> "+prim+"</html>");
			
			double value = FieldParser.roundDouble(Double.valueOf(account.getBalance()));
			
			if (value < 0)
				this.balanceLabel.setForeground(passive);
			else if (value > 0)
				this.balanceLabel.setForeground(active);
			else
				this.balanceLabel.setForeground(neutro);
			
			this.balanceLabel.setText("<html><b>Current balance</b><br/>"+account.getBalance()+" "+account.getCurrency()+"</html>");
			this.descrLabel.setText("<html><b>Description</b><br/>"+account.getDescription()+"</html>");
		}
		else {
			this.accountLabel.setText("No primary account");			
			this.createLabel.setText("<html>Select an account and set it to primary</html>");
			this.primaryLabel.setText("<html></html>");
			this.balanceLabel.setText("<html></html>");
			this.descrLabel.setText("<html></html>");
		}
	}
	
	private void enableAccountsBtn(boolean flag) {
		btnRemove.setEnabled(flag);
		showBtn.setEnabled(flag);
		modBtn.setEnabled(flag);
	}
}