/**
 * Copyright 2011 Michele Andreoli
 * 
 * This file is part of UnkleBill.
 *
 * UnkleBill is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * UnkleBill is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with UnkleBill; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 **/

package boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JPanel;
import datatype.Accounts;
import datatype.Date;
import datatype.Transactions;
import executor.FieldParser;
import executor.Languages;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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
	private JButton modAccBtn = null;
	private JButton btnCategory = null;
	private JLabel totalLabel = null;
	private JPanel totalBalancePane = null;
	private JLabel totalBalanceLabel = null;
	private JLabel userLabel = null;
	private JButton delUserBtn = null;
	private JLabel uncleLabel = null;
	private JLabel msgBackgroundLabel = null;
	private JLabel msgLabel = null;
	private Languages lang = Login.getLang();
	
	
	public Home(JPanel mainPane, Main main) {
		this.main = main;
		mainPane.add(getHomePane(), BorderLayout.CENTER);
		this.setLanguage(lang);
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
			userLabel = new JLabel(" "+Login.getUser().getName());
			userLabel.setIcon(new ImageIcon(getClass().getResource("/icons/user24.png")));
			userLabel.setFont(new Font("Lucida Grande", Font.BOLD, 24));
			userLabel.setToolTipText("Logged user");
			userLabel.setBounds(20, 20, 442, 30);
			homePane.add(userLabel);
			homePane.add(getModAccBtn());
			homePane.add(getBtnCategory());
			homePane.add(getTotalBalancePane());
			homePane.add(getDelUserBtn());
			refreshMsg();
		}
		return homePane;
	}
	
	private JPanel getManageAccountsPane() {
		if (manageAccountsPane == null) {
			manageAccountsPane = new JPanel();
			manageAccountsPane.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			manageAccountsPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), lang.home_titleManageAcc, TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande", Font.PLAIN, 12), Color.DARK_GRAY));
			manageAccountsPane.setBounds(6, 162, 464, 260);
			manageAccountsPane.setLayout(null);			
			manageAccountsPane.add(getMsgLabel());
			manageAccountsPane.add(getMsgBackgroundLabel());
			manageAccountsPane.add(getUncleLabel());
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
		accountLabel.setBounds(209, 78, 253, 32);
		
		createLabel = new JLabel("");
		createLabel.setHorizontalAlignment(SwingConstants.LEFT);
		createLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		createLabel.setBounds(209, 107, 253, 16);
		
		primaryLabel = new JLabel("");
		primaryLabel.setHorizontalAlignment(SwingConstants.LEFT);
		primaryLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		primaryLabel.setBounds(209, 125, 253, 16);
		
		descrLabel = new JLabel("");
		descrLabel.setVerticalAlignment(SwingConstants.TOP);
		descrLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		descrLabel.setBounds(209, 187, 253, 62);
		
		balanceLabel = new JLabel("");
		balanceLabel.setVerticalAlignment(SwingConstants.TOP);
		balanceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		balanceLabel.setBounds(209, 150, 253, 30);
		
		manageAccountsPane.add(accountLabel);
		manageAccountsPane.add(createLabel);
		manageAccountsPane.add(primaryLabel);
		manageAccountsPane.add(balanceLabel);		
		manageAccountsPane.add(descrLabel);	
	}
	
	private JScrollPane getScrollAccountPane() {
		if (scrollAccountPane == null) {
			scrollAccountPane = new JScrollPane(getListAccounts());
			scrollAccountPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			scrollAccountPane.setBounds(10, 110, 150, 113);
			scrollAccountPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return scrollAccountPane;
	}
	
	private JList getListAccounts() {
		if (listAccounts == null) {
			listAccounts = new JList();			
			listAccounts.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						showAccountInfo(accounts.getAccount(listAccounts.getSelectedValue().toString()));
						refreshMsg();
					}
				}
			});
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
		refreshMsg();
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
			modBtn.setToolTipText(lang.home_modBtnTip);
			modBtn.setBounds(165, 145, 32, 32);
		}
		return modBtn;
	}
	
	private JButton getShowBtn() {
		if (showBtn == null) {
			showBtn = new JButton("");
			showBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {										
					showAccountInfo(accounts.getAccount(listAccounts.getSelectedValue().toString()));
					refreshMsg();
				}
			});
			showBtn.setEnabled(false);
			showBtn.setToolTipText(lang.home_showBtnTip);
			showBtn.setIcon(new ImageIcon(getClass().getResource("/icons/viewAccount24.png")));
			showBtn.setBounds(165, 110, 32, 32);
		}
		return showBtn;
	}
	
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton(lang.home_addBtn);
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new InsertAccount();
					populateListAccounts();
					updateBalanceLabel();
				}
			});
			btnAdd.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			btnAdd.setHorizontalTextPosition(SwingConstants.RIGHT);
			btnAdd.setIcon(new ImageIcon(getClass().getResource("/icons/add16.png")));
			btnAdd.setToolTipText(lang.home_addBtnTip);
			btnAdd.setBounds(10, 225, 75, 29);
		}
		return btnAdd;
	}
	
	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton(lang.home_delBtn);			
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean isPrimary = false;
					Account account = accounts.getAccount(listAccounts.getSelectedValue().toString());
					String primaryString = "";
					
					if (Login.getAccount() != null && account.getAccount().equals(Login.getAccount().getAccount())) {
							isPrimary = true;
							primaryString = lang.home_delBtnMsg[0]+".<br/>";
					}
					
					if (confirm(lang.home_delBtnMsg[1]+" <b>"+listAccounts.getSelectedValue().toString()+"</b>.<br/>"+primaryString+lang.home_delBtnMsg[2]) == 0) {						
						if (Transactions.loadTransactions(Login.getUser().getUser(), account.getAccount()).getNumTransactions() > 0) {
							if (abort(lang.home_delBtnMsg[3]) == 0) {
								Transactions.deleteTransactions(Login.getUser().getUser(), account.getAccount());
								accounts.getAccount(account.getAccount()).deleteAccount();								
							}
						}
						else {
							account.deleteAccount();
						}
						
						if (isPrimary)
							Login.setAccount(null);
						
						populateListAccounts();
					}
					updateBalanceLabel();
				}
			});
			btnRemove.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			btnRemove.setHorizontalTextPosition(SwingConstants.LEFT);
			btnRemove.setIcon(new ImageIcon(getClass().getResource("/icons/del16.png")));
			btnRemove.setToolTipText(lang.home_delBtnTip);
			btnRemove.setBounds(85, 225, 75, 29);
			btnRemove.setEnabled(false);
		}
		return btnRemove;
	}
	
	private JLabel getListLabel() {
		if (listLabel == null) {
			listLabel = new JLabel(lang.home_labelList);
			listLabel.setIcon(new ImageIcon(Home.class.getResource("/icons/accounts16.png")));
			listLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			listLabel.setBounds(10, 85, 150, 24);
		}
		return listLabel;
	}
	
	private void showAccountInfo(Account account) {				
		if (account != null) {
			account.setUsable(true);
			account.updateAccount();
			Login.setAccount(account);
			main.toggleNaviButtons(false, true, true);
			
			Date date = new Date(account.getCreation());
			String text = Date.getMonth(date.getMonth())+" "+Date.getDay(date.getDay())+", "+date.getYear();
			String prim = lang.no;
			
			if (account.isUsable())
				prim = lang.yes;
			
			this.accountLabel.setText(account.getAccount());
			this.createLabel.setText("<html><b>"+lang.home_createLabel+":</b>  "+text+"</html>");
			this.primaryLabel.setText("<html><b>"+lang.home_primaryLabel+":</b> "+prim+"</html>");
			
			double value = account.getBalance();
			String sign = "";
			
			if (value < 0)
				this.balanceLabel.setForeground(passive);
			else if (value > 0) {
				this.balanceLabel.setForeground(active);
				sign = "+";
			}
			else
				this.balanceLabel.setForeground(neutro);
			
			this.balanceLabel.setText("<html><b>"+lang.home_balanceLabel+"</b><br/>"+sign+account.getBalance()+" "+account.getCurrency()+"</html>");
			this.descrLabel.setText("<html><b>"+lang.home_descrLabel+"</b><br/>"+account.getDescription()+"</html>");
		}
		else {
			this.accountLabel.setText(lang.home_primaryWarning);			
			this.createLabel.setText("<html>"+lang.home_primaryWarningTip+"</html>");
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
	
	private JButton getModAccBtn() {
		if (modAccBtn == null) {
			modAccBtn = new JButton(lang.home_modAccBtn);
			modAccBtn.setIcon(new ImageIcon(getClass().getResource("/icons/setting16.png")));
			modAccBtn.setToolTipText(lang.home_modAccBtnTip);
			modAccBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			modAccBtn.setBounds(15, 55, 90, 30);
			modAccBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ModifyProfile(Login.getUser());
					userLabel.setText(" "+Login.getUser().getName());
					updateBalanceLabel();
				}
			});
		}
		return modAccBtn;
	}
	
	private JButton getBtnCategory() {
		if (btnCategory == null) {
			btnCategory = new JButton(lang.home_categBtn);
			btnCategory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ModifyLabels(Login.getUser());
				}
			});
			btnCategory.setIcon(new ImageIcon(getClass().getResource("/icons/category16.png")));
			btnCategory.setToolTipText(lang.home_categBtnTip);
			btnCategory.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			btnCategory.setBounds(105, 55, 90, 30);
		}
		return btnCategory;
	}
	
	private JLabel getTotalLabel() {
		if (totalLabel == null) {
			totalLabel = new JLabel("");
			totalLabel.setBounds(6, 18, 48, 48);
			totalLabel.setIcon(new ImageIcon(getClass().getResource("/icons/safety48.png")));
			totalLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		}
		return totalLabel;
	}
	
	private JPanel getTotalBalancePane() {
		if (totalBalancePane == null) {
			totalBalancePane = new JPanel();
			totalBalancePane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), lang.home_titleTotalBalance, TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande", Font.PLAIN, 12), Color.DARK_GRAY));
			totalBalancePane.setBounds(6, 90, 464, 72);
			totalBalancePane.setLayout(null);
			totalBalancePane.add(getTotalLabel());
			totalBalancePane.add(getTotalBalanceLabel());
		}
		return totalBalancePane;
	}
	
	private JLabel getTotalBalanceLabel() {
		if (totalBalanceLabel == null) {
			totalBalanceLabel = new JLabel("");
			totalBalanceLabel.setToolTipText(lang.home_totalBalanceTip);
			totalBalanceLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
			totalBalanceLabel.setBounds(57, 18, 210, 48);
			updateBalanceLabel();
		}
		return totalBalanceLabel;
	}
	
	private double calculateTotalBalance() {
		double total = 0.0;
		
		if (this.accounts.getNumAccounts() > 0) {
			for (Account a : this.accounts.getAccounts()) {
				total += a.getBalance();
			}
		}
		
		return total;
	}
	
	private void updateBalanceLabel() {
		String sign = "";
		double total = calculateTotalBalance();
		
		if (total > 0) {
			this.totalBalanceLabel.setForeground(active);
			sign = "+";
		}
		else if (total < 0)
			this.totalBalanceLabel.setForeground(passive);
		else
			this.totalBalanceLabel.setForeground(neutro);
		
		this.totalBalanceLabel.setText(sign+String.valueOf(FieldParser.roundDouble(total))+" "+Login.getUser().getCurrency());
	}
	private JButton getDelUserBtn() {
		if (delUserBtn == null) {
			delUserBtn = new JButton(lang.home_delUserBtn);
			delUserBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (confirm(lang.home_delUserBtnMsg[0]+"<br/><b>"+Login.getUser().getName()+"</b>?") == 0) {
						Accounts accounts = Accounts.loadAccounts(Login.getUser().getUser());
						if (accounts.getNumAccounts() > 0) {
							if (abort(lang.home_delUserBtnMsg[1]) == 0) {
								for (Account account : accounts.getAccounts()) {
									Transactions.deleteTransactions(Login.getUser().getUser(), account.getAccount());
									accounts.getAccount(account.getAccount()).deleteAccount();
								}
								Login.getUser().deleteUser();
								Login.logout(main, main.getMainPane(), (JPanel)main.getMainPane().getComponent(1));
							}							
						}
						else {
							Login.getUser().deleteUser();
							Login.logout(main, main.getMainPane(), (JPanel)main.getMainPane().getComponent(1));
						}
					}
				}
			});
			delUserBtn.setIcon(new ImageIcon(getClass().getResource("/icons/delUser16.png")));
			delUserBtn.setToolTipText(lang.home_delUserBtnTip);
			delUserBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			delUserBtn.setBounds(375, 55, 90, 30);
		}
		return delUserBtn;
	}
	
	private JLabel getUncleLabel() {
		if (uncleLabel == null) {
			uncleLabel = new JLabel("");
			uncleLabel.setBounds(390, 15, 64, 64);
		}
		return uncleLabel;
	}
	
	private JLabel getMsgBackgroundLabel() {
		if (msgBackgroundLabel == null) {
			msgBackgroundLabel = new JLabel("");
			msgBackgroundLabel.setBounds(5, 15, 384, 64);
			msgBackgroundLabel.setIcon(new ImageIcon(getClass().getResource("/icons/smoke.png")));
		}
		return msgBackgroundLabel;
	}
	
	private JLabel getMsgLabel() {
		if (msgLabel == null) {
			msgLabel = new JLabel("");
			msgLabel.setBounds(18, 25, 357, 44);
			msgLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
		}
		return msgLabel;
	}
	
	private void refreshMsg() {
		
		if (this.accounts.getNumAccounts() > 0) {
			if (Login.getAccount() == null) {
				this.uncleLabel.setIcon(new ImageIcon(getClass().getResource("/icons/UBill64.png")));
				this.msgLabel.setText("<html>Select an account and set it as primary with a double click.<br/>After that you can start to add transactions in the <i>Payments</i> tab on the left.</html>");
			}
			else {
				String msgTot = "";
				String msgBalance = "";
				ImageIcon ics = null;
				int zeroB = 0;
				int zeroA = 0;
				double tot = calculateTotalBalance();
				double balance = Login.getAccount().getBalance();
				
				if (tot >= 0.0 && balance >= 0.0) {					
					if (tot > 10000)
						msgTot = "The total amount of your accounts is very strong, you'll want to become a bank!";
					else if (tot > 0)
						msgTot = "The total amount of your accounts is ok.";
					else {
						msgTot = "Be careful, the total amount of your accounts is unsafe.";
						zeroA++;
					}
					
					if (balance > 2000)
						msgBalance = "The balance of <b>"+Login.getAccount().getAccount()+"</b> account is very safe!";
					else if (balance > 0)
						msgBalance = "The balance of <b>"+Login.getAccount().getAccount()+"</b> account is good.";
					else {
						msgBalance = "The balance of <b>"+Login.getAccount().getAccount()+"</b> account is unsafe.";
						zeroB++;
					}
					
					if (zeroA == 0 && zeroB == 0)
						ics = new ImageIcon(getClass().getResource("/icons/UBill_up_gg64.png"));
					else if (zeroA == 0 && zeroB == 1)
						ics = new ImageIcon(getClass().getResource("/icons/UBill_up_gy64.png"));
					else if (zeroA == 0 && zeroB == 1)
						ics = new ImageIcon(getClass().getResource("/icons/UBill_up_yg64.png"));
					else
						ics = new ImageIcon(getClass().getResource("/icons/UBill_up_yy64.png"));
				}
				else if (tot >= 0.0 && balance < 0.0) {		
					if (tot > 10000)
						msgTot = "The total amount of your accounts is very strong, you'll want to become a bank!";
					else if (tot > 0)
						msgTot = "The total amount of your accounts is ok.";
					else {
						msgTot = "Be careful, the total amount of your accounts is unsafe.";
						zeroA++;
					}
					
					if (balance < -1000)
						msgBalance = "Attention, watch the balance of <b>"+Login.getAccount().getAccount()+"</b> account, is terrible!";
					else
						msgBalance = "Attention, the balance of <b>"+Login.getAccount().getAccount()+"</b> account is not good.";
					
					if (zeroA == 1)
						ics = new ImageIcon(getClass().getResource("/icons/UBill_up_yr64.png"));
					else
						ics = new ImageIcon(getClass().getResource("/icons/UBill_up_gr64.png"));
				}
				else if (tot < 0.0 && balance >= 0.0) {
					if (tot < -5000)
						msgTot = "Be careful, watch the total amount of your accounts, is terrible!";
					else
						msgTot = "Be careful, the total amount of your accounts is not good.";
					
					if (balance > 2000)
						msgBalance = "The balance of <b>"+Login.getAccount().getAccount()+"</b> account is very safe!";
					else if (balance > 0)
						msgBalance = "The balance of <b>"+Login.getAccount().getAccount()+"</b> account is good.";
					else {
						zeroB++;
						msgBalance = "The balance of <b>"+Login.getAccount().getAccount()+"</b> account is unsafe.";
					}
					
					if (zeroB == 1)
						ics = new ImageIcon(getClass().getResource("/icons/UBill_up_ry64.png"));
					else
						ics = new ImageIcon(getClass().getResource("/icons/UBill_up_rg64.png"));
				}
				else if (tot < 0.0 && balance < 0.0) {			
					if (tot < -5000)
						msgTot = "Be careful, watch the total amount of your accounts, is terrible!";
					else
						msgTot = "Be careful, the total amount of your accounts is not good.";
					
					if (balance < -1000)
						msgBalance = "Attention, watch the balance of <b>"+Login.getAccount().getAccount()+"</b> account, is terrible!";
					else
						msgBalance = "Attention, the balance of <b>"+Login.getAccount().getAccount()+"</b> account is not good.";
				
					ics = new ImageIcon(getClass().getResource("/icons/UBill_up_rr64.png"));
				}
				
				this.uncleLabel.setIcon(ics);
				this.msgLabel.setText("<html>"+msgTot+"<br/>"+msgBalance+"</html>");
			}
		}
		else {
			this.uncleLabel.setIcon(new ImageIcon(getClass().getResource("/icons/UBill64.png")));
			this.msgLabel.setText("<html>Nice to meet you <b>"+Login.getUser().getName()+"</b>!<br/>My name is UnkleBill, I'll help you in the difficult choices.<br/>Now you can create and manage one or more accounts.</html>");
		}
	}
}