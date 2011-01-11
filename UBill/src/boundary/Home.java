package boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JPanel;
import datatype.Accounts;
import datatype.Transactions;
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
	
	
	public Home(JPanel mainPane) {
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
			
			JLabel userLabel = new JLabel(" "+Login.getFullname());
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
			manageAccountsPane.add(getAccountLabel());
			manageAccountsPane.add(getBtnRemove());
			manageAccountsPane.add(getBtnAdd());
			manageAccountsPane.add(getListLabel());
			manageAccountsPane.add(getScrollAccountPane());
			
			JButton showBtn = new JButton("");
			showBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			showBtn.setEnabled(false);
			showBtn.setToolTipText("Show account details");
			showBtn.setIcon(new ImageIcon(getClass().getResource("/icons/viewAccount24.png")));
			showBtn.setBounds(165, 50, 32, 32);
			manageAccountsPane.add(showBtn);
			
			JButton modBtn = new JButton("");
			modBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			modBtn.setEnabled(false);
			modBtn.setIcon(new ImageIcon(getClass().getResource("/icons/modAccount24.png")));
			modBtn.setToolTipText("Edit account");
			modBtn.setBounds(165, 85, 32, 32);
			manageAccountsPane.add(modBtn);	
		}
		return manageAccountsPane;
	}
	
	private JScrollPane getScrollAccountPane() {
		if (scrollAccountPane == null) {
			scrollAccountPane = new JScrollPane();
			scrollAccountPane.setBounds(10, 50, 150, 148);
			scrollAccountPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollAccountPane.setViewportView(getListAccounts());
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
						btnRemove.setEnabled(true);
				}
			});			
			populateListAccounts();
		}
		return listAccounts;
	}
	
	private void populateListAccounts() {
		listAccounts.setSelectedIndex(-1);
		btnRemove.setEnabled(false);
		
		this.accounts = Accounts.loadAccounts(Login.getUsername());
		
		Account account = Account.loadDefaultAccount(Login.getUsername());
		if (account != null) {
			Login.setAccount(account);
			System.out.println("default: "+Login.getAccount().getAccount());
		}
		
		if (this.accounts.getNumAccounts() > 0) {
			listAccounts.setListData(this.accounts.getAccountsNames().toArray());		
		}
		else
			listAccounts.setListData(new Object[0]);
		
		showAccountInfo(Login.getAccount());		
		listAccounts.repaint();
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
					if (confirm("You are deleting <i>"+listAccounts.getSelectedValue().toString()+"</i>.<br/>Are you sure?") == 0) {
						String accountName = accounts.getAccount(listAccounts.getSelectedValue().toString()).getAccount();
						if (Transactions.loadTransactions(Login.getUsername(), accountName).getNumTransactions() > 0) {
							if (abort("You are deleting all<br/>transtactions for this account.<br/>Continue anyway?") == 0) {
								accounts.getAccount(accountName).removeAccount();
								//TODO cancellare tutte le transazioni dell'account per quell'utente
							}
						}
						else {
							accounts.getAccount(accountName).removeAccount();
						}
						
						if (Login.getAccount() != null && accountName.equals(Login.getAccount().getAccount()))
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
	
	private JLabel getAccountLabel() {
		if (accountLabel == null) {
			accountLabel = new JLabel("");
			accountLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
			accountLabel.setBounds(221, 25, 225, 32);
		}
		return accountLabel;
	}
	
	private void showAccountInfo(Account account) {
		if (account != null) {
			this.accountLabel.setText(account.getAccount());
		}
		else {
			this.accountLabel.setText("");
		}
	}
}