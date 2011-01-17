package boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import executor.Login;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.util.Vector;
import javax.swing.JComboBox;
import datatype.Date;
import datatype.Month;
import datatype.Transactions;
import store.Transaction;


public class Management extends BaseBoundary {
	
	private JPanel managePane = null;
	private JLabel accountLabel = null;
	private JLabel balanceLabel = null;
	private JLabel accountBalanceLabel = null;
	private JPanel monthTab = null;
	private JSplitPane splitPane = null;
	private JTable entranceTable = null;
	private JTable exitTable = null;
	private DefaultTableModel entranceTableModel = null;
	private DefaultTableModel exitTableModel = null;
	private JPanel entrancePane = null;
	private JPanel exitPane = null;
	private JButton addEntranceBtn = null;
	private JButton addExitBtn = null;
	private JScrollPane entranceScrollPane = null;
	private JScrollPane exitScrollPane = null;
	private JLabel entranceAmount = null;
	private JLabel exitAmount = null;
	private JComboBox monthBox = null;
	private JComboBox yearBox = null;
	private JButton delTransBtn = null;
	private JButton modTransBtn = null;
	private Color active = new Color(0, 128, 0);
	private Color neutro = new Color(0, 0, 0);
	private Color passive = new Color(128, 0, 0);
	private Transactions transactions = null;
	private Date date = new Date(Date.getCurrentDate());
	private Transactions entranceTrans = null;
	private Transactions exitTrans = null;
	private double entranceTot = 0.0;
	private double exitTot = 0.0;
	private JButton moveBtn = null;
	private JButton reportBtn = null;
	private JLabel dateLabel = null;
	private JLabel balanceMonthLabel = null;
	private JLabel monthLabel = null;
	private JButton prevMonthBtn = null;
	private JButton nextMonthBtn = null;
	
	
	public Management(JPanel mainPane) {		
		mainPane.add(getManagePane(), BorderLayout.CENTER);	
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel getManagePane() {
		if (managePane == null) {
			managePane = new JPanel();
			managePane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			managePane.setLocation(new Point(120, 0));
			managePane.setSize(new Dimension(480, 435));
			managePane.setLayout(null);
			managePane.add(getAccountLabel());
			managePane.add(getBalanceLabel());
			managePane.add(getAccountBalanceLabel());
			managePane.add(getMonthTab());
		}
		return managePane;
	}
	
	private JLabel getAccountLabel() {
		if (accountLabel == null) {
			accountLabel = new JLabel(Login.getAccount().getAccount());
			accountLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
			accountLabel.setBounds(15, 10, 245, 41);
		}
		return accountLabel;
	}
	
	private JLabel getAccountBalanceLabel() {
		if (accountBalanceLabel == null) {
			accountBalanceLabel = new JLabel("Account balance");
			accountBalanceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			accountBalanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			accountBalanceLabel.setBounds(312, 8, 149, 16);
		}
		return accountBalanceLabel;
	}
	
	private JLabel getBalanceLabel() {
		if (balanceLabel == null) {
			double value = Login.getAccount().getBalance();			
			balanceLabel = new JLabel(String.valueOf(value)+" "+Login.getAccount().getCurrency());
			if (value < 0)
				balanceLabel.setForeground(passive);
			else if (value > 0)
				balanceLabel.setForeground(active);
			else
				balanceLabel.setForeground(neutro);
			balanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			balanceLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
			balanceLabel.setBounds(193, 20, 268, 30);
		}
		return balanceLabel;
	}
	
	private JPanel getMonthTab() {
		if (monthTab == null) {
			monthTab = new JPanel();
			monthTab.setLocation(2, 55);
			monthTab.setSize(new Dimension(475, 376));
			monthTab.setLayout(null);			
			monthTab.add(getSplitPane());
			monthTab.add(getMonthBox());
			monthTab.add(getYearBox());
			monthTab.add(getDelTransBtn());
			monthTab.add(getModTransBtn());
			monthTab.add(getMoveBtn());
			monthTab.add(getReportBtn());
			monthTab.add(getDateLabel());
			monthTab.add(getBalanceMonthLabel());
			monthTab.add(getMonthLabel());
			monthTab.add(getPrevMonthBtn());
			monthTab.add(getNextMonthBtn());
			populateTables();			
		}
		return monthTab;
	}
	
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerSize(4);
			splitPane.setBounds(10, 105, 456, 262);
			splitPane.setLeftComponent(getEntrancePane());
			splitPane.setRightComponent(getExitPane());
			splitPane.setDividerLocation(228);
		}
		return splitPane;
	}
	
	private TableModel getEntranceTableModel() {
		if (entranceTableModel == null) {
			entranceTableModel = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}	
			};
			Object[] headers = new Object[3];
			headers[0] = "Amount";
			headers[1] = "Day";
			headers[2] = "Causal";
			entranceTableModel.setColumnIdentifiers(headers);
		}
		return entranceTableModel;
	}
	
	private TableModel getExitTableModel() {
		if (exitTableModel == null) {
			exitTableModel = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}	
			};
			Object[] headers = new Object[3];
			headers[0] = "Amount";
			headers[1] = "Day";
			headers[2] = "Causal";
			exitTableModel.setColumnIdentifiers(headers);
		}
		return exitTableModel;
	}
	
	private JTable getEntranceTable() {
		if (entranceTable == null) {			
			entranceTable = new JTable(getEntranceTableModel());
			entranceTable.setShowGrid(false);
			entranceTable.setBounds(new Rectangle(20, 70, 216, 235));			
			entranceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			entranceTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			entranceTable.getColumnModel().getColumn(1).setPreferredWidth(10);
			//entranceTable.getColumnModel().getColumn(2).setPreferredWidth(entranceTable.getWidth() - 150);			
			entranceTable.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					setEnabledButtons(entranceTable, exitTable);
				}
			});
		}
		return entranceTable;
	}
	
	private JTable getExitTable() {
		if (exitTable == null) {			
			exitTable = new JTable(getExitTableModel());
			exitTable.setBounds(new Rectangle(20, 70, 216, 235));			
			exitTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			exitTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			exitTable.getColumnModel().getColumn(1).setPreferredWidth(10);
			//exitTable.getColumnModel().getColumn(2).setPreferredWidth(exitTable.getWidth() - 150);
			exitTable.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {					
					setEnabledButtons(entranceTable, exitTable);
				}
			});
		}
		return exitTable;
	}
	
	private void setEnabledButtons(JTable entranceTable, JTable exitTable) {
		if (entranceTable.getSelectedRowCount() > 0) {
			delTransBtn.setEnabled(true);
			modTransBtn.setEnabled(true);
			exitTable.clearSelection();
		}
		else if (exitTable.getSelectedRowCount() > 0) {
			delTransBtn.setEnabled(true);
			modTransBtn.setEnabled(true);
			entranceTable.clearSelection();
		}
		else {
			modTransBtn.setEnabled(false);
			delTransBtn.setEnabled(false);
		}
	}
	
	private JPanel getEntrancePane() {
		if (entrancePane == null) {
			entrancePane = new JPanel();
			entrancePane.setBorder(null);
			entrancePane.setLayout(new BorderLayout(0, 0));
			entrancePane.add(getAddEntranceBtn(), BorderLayout.NORTH);
			entrancePane.add(getEntranceScrollPane(), BorderLayout.CENTER);
			entrancePane.add(getEntranceAmount(), BorderLayout.SOUTH);
		}
		return entrancePane;
	}
	
	private JPanel getExitPane() {
		if (exitPane == null) {
			exitPane = new JPanel();
			exitPane.setBorder(null);
			exitPane.setLayout(new BorderLayout(0, 0));
			exitPane.add(getAddExitBtn(), BorderLayout.NORTH);
			exitPane.add(getExitScrollPane(), BorderLayout.CENTER);
			exitPane.add(getExitAmount(), BorderLayout.SOUTH);
		}
		return exitPane;
	}
	
	private JButton getAddEntranceBtn() {
		if (addEntranceBtn == null) {
			addEntranceBtn = new JButton("Entrance");
			addEntranceBtn.setToolTipText("Add entrance transaction");
			addEntranceBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			addEntranceBtn.setIcon(new ImageIcon(getClass().getResource("/icons/entrance16.png")));
			addEntranceBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					InsertTransaction ins = new InsertTransaction(0, Date.getMonth(monthBox.getSelectedItem().toString()), Integer.valueOf(yearBox.getSelectedItem().toString()));
					calculateBalance(ins);
				}
			});
		}
		return addEntranceBtn;
	}
	
	private JButton getAddExitBtn() {
		if (addExitBtn == null) {
			addExitBtn = new JButton("Exit");
			addExitBtn.setToolTipText("Add exit transaction");
			addExitBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			addExitBtn.setIcon(new ImageIcon(getClass().getResource("/icons/output16.png")));
			addExitBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					InsertTransaction ins = new InsertTransaction(1, Date.getMonth(monthBox.getSelectedItem().toString()), Integer.valueOf(yearBox.getSelectedItem().toString()));
					calculateBalance(ins);
				}
			});
		}
		return addExitBtn;
	}
	
	private void calculateBalance(InsertTransaction ins) {
		if (ins.getTransaction() != null) {
			addRowsInTables(ins.getTransaction());
			
			double tot = Login.getAccount().getBalance();
			
			if (ins.getTransaction().getType() == '+')
				tot += ins.getTransaction().getPayment();
			else
				tot -= ins.getTransaction().getPayment();
			
			Login.getAccount().setBalance(tot);
			Login.getAccount().updateAccount();
			
			updateBalanceLabel(balanceLabel);
		}
	}
	
	private JScrollPane getEntranceScrollPane() {
		if (entranceScrollPane == null) {
			entranceScrollPane = new JScrollPane(getEntranceTable());
			entranceScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		}
		return entranceScrollPane;
	}
	
	private JScrollPane getExitScrollPane() {
		if (exitScrollPane == null) {
			exitScrollPane = new JScrollPane(getExitTable());
			exitScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		}
		return exitScrollPane;
	}
	
	private JLabel getEntranceAmount() {
		if (entranceAmount == null) {
			entranceAmount = new JLabel("0.0");
			entranceAmount.setForeground(active);
			entranceAmount.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		}
		return entranceAmount;
	}
	
	private JLabel getExitAmount() {
		if (exitAmount == null) {
			exitAmount = new JLabel("0.0");
			exitAmount.setForeground(passive);
			exitAmount.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		}
		return exitAmount;
	}
	
	private JLabel getDateLabel() {
		if (dateLabel == null) {
			dateLabel = new JLabel("Selected date");
			dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
			dateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			dateLabel.setBounds(15, 53, 149, 16);
		}
		return dateLabel;
	}
	
	private JComboBox getMonthBox() {
		if (monthBox == null) {
			monthBox = new JComboBox(Month.values());					
			monthBox.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			monthBox.setBounds(10, 70, 99, 24);			
			monthBox.setSelectedIndex(date.getMonth()-1);
			monthBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkPrevNextMonthBtn();
					entranceTableModel.setRowCount(0);
					exitTableModel.setRowCount(0);
					delTransBtn.setEnabled(false);
					modTransBtn.setEnabled(false);
					populateTables();
				}
			});
		}
		return monthBox;
	}
	
	private JComboBox getYearBox() {
		if (yearBox == null) {
			Vector<Integer> years = new Vector<Integer>();			
			for (int i = 2010; i < 2061; i++) {
				years.add(i);				
			}
			yearBox = new JComboBox(years);
			yearBox.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			yearBox.setBounds(108, 70, 71, 24);			
			yearBox.setSelectedItem(date.getYear());
			yearBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkPrevNextMonthBtn();
					entranceTableModel.setRowCount(0);
					exitTableModel.setRowCount(0);
					delTransBtn.setEnabled(false);
					modTransBtn.setEnabled(false);
					populateTables();					
				}
			});			
		}
		return yearBox;
	}
	
	private void checkPrevNextMonthBtn() {
		if (yearBox.getSelectedItem().toString().equals("2010") && monthBox.getSelectedIndex() == 0) {
			prevMonthBtn.setEnabled(false);
		}
		else if (yearBox.getSelectedItem().toString().equals("2060") && monthBox.getSelectedIndex() == 11) {
			nextMonthBtn.setEnabled(false);
		}
		else {
			nextMonthBtn.setEnabled(true);
			prevMonthBtn.setEnabled(true);
		}
	}
	
	private JButton getDelTransBtn() {
		if (delTransBtn == null) {
			delTransBtn = new JButton("Delete");
			delTransBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (abort("<html>Do you want to delete<br/>this transaction?</html>") == 0) {
						if (entranceTable.getSelectedRowCount() > 0) {		
							double tot = Login.getAccount().getBalance() - entranceTrans.getTransactions().get(entranceTable.getSelectedRow()).getPayment();
							Login.getAccount().setBalance(tot);
							entranceTot -= entranceTrans.getTransactions().get(entranceTable.getSelectedRow()).getPayment();
							entranceTrans.getTransactions().get(entranceTable.getSelectedRow()).deleteTransaction();
							entranceTableModel.removeRow(entranceTable.getSelectedRow());					
						}
						else {
							double tot = Login.getAccount().getBalance() + exitTrans.getTransactions().get(exitTable.getSelectedRow()).getPayment();
							Login.getAccount().setBalance(tot);
							exitTot -= exitTrans.getTransactions().get(exitTable.getSelectedRow()).getPayment();
							exitTrans.getTransactions().get(exitTable.getSelectedRow()).deleteTransaction();
							exitTableModel.removeRow(exitTable.getSelectedRow());
						}
						Login.getAccount().updateAccount();
						setEnabledButtons(entranceTable, exitTable);
						updateBalanceLabel(balanceLabel);
						updatePartialsAmounts(entranceAmount, exitAmount);
					}
				}
			});
			delTransBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			delTransBtn.setEnabled(false);
			delTransBtn.setToolTipText("Delete selected transaction");
			delTransBtn.setIcon(new ImageIcon(getClass().getResource("/icons/del16.png")));
			delTransBtn.setBounds(105, 11, 90, 30);
		}
		return delTransBtn;
	}
	
	private JButton getModTransBtn() {
		if (modTransBtn == null) {
			modTransBtn = new JButton("Modify");
			modTransBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Transaction t = null;
					
					if (entranceTable.getSelectedRowCount() > 0) {
						t = Transaction.loadTransaction(entranceTrans.getTransactions().get(entranceTable.getSelectedRow()).getId());						
					}
					else {
						t = Transaction.loadTransaction(exitTrans.getTransactions().get(exitTable.getSelectedRow()).getId());						
					}
					
					Transaction oldT = new Transaction(t.getId(), t.getUser(), t.getAccount(), t.getEntry(), t.getType(), t.getPayment(), t.getYear(), t.getMonth(), t.getDay(), t.getRefid(), t.getReference());					
					InsertTransaction ins = new InsertTransaction(t);									
					
					if (ins.getTransaction() != null) {
						double tot = 0.0;
						
						if (entranceTable.getSelectedRowCount() > 0) {
							updateRowsInTables(ins.getTransaction(), oldT, entranceTable.getSelectedRow());
							tot = Login.getAccount().getBalance() - oldT.getPayment();
						}
						else {
							updateRowsInTables(ins.getTransaction(), oldT, exitTable.getSelectedRow());
							tot = Login.getAccount().getBalance() + oldT.getPayment();
						}
						
						if (ins.getTransaction().getType() == '+')
							tot += ins.getTransaction().getPayment();
						else
							tot -= ins.getTransaction().getPayment();																	
						
						Login.getAccount().setBalance(tot);
						Login.getAccount().updateAccount();
						
						updateBalanceLabel(balanceLabel);
					}					
				}
			});
			modTransBtn.setIcon(new ImageIcon(getClass().getResource("/icons/edit16.png")));
			modTransBtn.setEnabled(false);
			modTransBtn.setToolTipText("Modify selected transaction");
			modTransBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			modTransBtn.setEnabled(false);
			modTransBtn.setBounds(10, 11, 90, 30);
		}
		return modTransBtn;
	}
	
	private JButton getMoveBtn() {
		if (moveBtn == null) {
			moveBtn = new JButton("Move");
			moveBtn.setIcon(new ImageIcon(getClass().getResource("/icons/transfer16.png")));
			moveBtn.setToolTipText("Add a movement between accounts");
			moveBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));			
			moveBtn.setBounds(200, 11, 90, 30);
		}
		return moveBtn;
	}

	private JButton getReportBtn() {
		if (reportBtn == null) {
			reportBtn = new JButton("Report");
			reportBtn.setIcon(new ImageIcon(getClass().getResource("/icons/report16.png")));
			reportBtn.setToolTipText("Create report for this month");
			reportBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));			
			reportBtn.setBounds(295, 11, 90, 30);
		}
		return reportBtn;
	}

	private void populateTables() {
		this.transactions = Transactions.loadTransactions(Login.getUser().getUser(), Login.getAccount().getAccount(), Integer.valueOf(yearBox.getSelectedItem().toString()), Date.getMonth(monthBox.getSelectedItem().toString()));
		this.entranceTrans = new Transactions();
		this.exitTrans = new Transactions();
		this.entranceTot = 0.0;
		this.exitTot = 0.0;
		
		if (this.transactions.getNumTransactions() > 0) {			
			for (Transaction t : this.transactions.getTransactions()) {
				addRowsInTables(t);
			}
		}
		
		updatePartialsAmounts(entranceAmount, exitAmount);
	}
	
	private void addRowsInTables(Transaction t) {
		Vector<String> vect = new Vector<String>();
		vect.add(String.valueOf(t.getPayment()+" "+Login.getAccount().getCurrency()));
		vect.add(Date.getDay(t.getDay()));
		vect.add(t.getEntry());		
		
		if (t.getType() == '+') {					
			this.entranceTableModel.addRow(vect);
			this.entranceTrans.addTransaction(t);
			this.entranceTot += t.getPayment();			
		}
		else {
			this.exitTableModel.addRow(vect);
			this.exitTrans.addTransaction(t);
			this.exitTot += t.getPayment();			
		}
		
		updatePartialsAmounts(entranceAmount, exitAmount);
	}
	
	private void updateRowsInTables(Transaction t, Transaction old, int row) {
		
		if (t.getType() == '+') {
			this.entranceTableModel.setValueAt(String.valueOf(t.getPayment())+" "+Login.getAccount().getCurrency(), row, 0);
			this.entranceTableModel.setValueAt(Date.getDay(t.getDay()), row, 1);
			this.entranceTableModel.setValueAt(t.getEntry(), row, 2);			
			this.entranceTrans.getTransactions().get(row).setPayment(t.getPayment());
			this.entranceTrans.getTransactions().get(row).setDay(t.getDay());
			this.entranceTrans.getTransactions().get(row).setEntry(t.getEntry());
			this.entranceTot -= old.getPayment();
			this.entranceTot += t.getPayment();			
		}
		else {
			this.exitTableModel.setValueAt(String.valueOf(t.getPayment())+" "+Login.getAccount().getCurrency(), row, 0);
			this.exitTableModel.setValueAt(Date.getDay(t.getDay()), row, 1);
			this.exitTableModel.setValueAt(t.getEntry(), row, 2);
			this.exitTrans.getTransactions().get(row).setPayment(t.getPayment());
			this.exitTrans.getTransactions().get(row).setDay(t.getDay());
			this.exitTrans.getTransactions().get(row).setEntry(t.getEntry());			
			this.exitTot -= old.getPayment();
			this.exitTot += t.getPayment();			
		}
		
		updatePartialsAmounts(entranceAmount, exitAmount);
	}
	
	private void updatePartialsAmounts(JLabel entrance, JLabel exit) {
		double monthBalance = this.entranceTot - this.exitTot;
		
		entrance.setText("+"+String.valueOf(this.entranceTot)+" "+Login.getAccount().getCurrency());
		exit.setText("-"+String.valueOf(this.exitTot)+" "+Login.getAccount().getCurrency());
		
		if (monthBalance > 0)
			balanceMonthLabel.setForeground(active);
		else if (monthBalance < 0)
			balanceMonthLabel.setForeground(passive);
		else
			balanceMonthLabel.setForeground(neutro);
		
		balanceMonthLabel.setText(String.valueOf(monthBalance)+" "+Login.getAccount().getCurrency());
	}
	
	private void updateBalanceLabel(JLabel label) {
		
		if (Login.getAccount().getBalance() > 0)
			label.setForeground(active);
		else if (Login.getAccount().getBalance() < 0)
			label.setForeground(passive);
		else
			label.setForeground(neutro);
			
		label.setText(String.valueOf(Login.getAccount().getBalance())+" "+Login.getAccount().getCurrency());
	}

	private JLabel getBalanceMonthLabel() {
		if (balanceMonthLabel == null) {
			balanceMonthLabel = new JLabel();
			balanceMonthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			balanceMonthLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
			balanceMonthLabel.setBounds(248, 70, 211, 23);
		}
		return balanceMonthLabel;
	}
	
	private JLabel getMonthLabel() {
		if (monthLabel == null) {
			monthLabel = new JLabel("Balance of the month");
			monthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			monthLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			monthLabel.setBounds(312, 55, 149, 16);
		}
		return monthLabel;
	}
	
	private JButton getPrevMonthBtn() {
		if (prevMonthBtn == null) {
			prevMonthBtn = new JButton("");
			prevMonthBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					if (monthBox.getSelectedIndex() == 0) {
						monthBox.setSelectedIndex(11);
						yearBox.setSelectedIndex(yearBox.getSelectedIndex()-1);
					}
					else {
						monthBox.setSelectedIndex(monthBox.getSelectedIndex()-1);
					}
				}
			});
			prevMonthBtn.setToolTipText("Previous month");
			prevMonthBtn.setIcon(new ImageIcon(Management.class.getResource("/icons/rewind16.png")));
			prevMonthBtn.setBounds(185, 66, 30, 30);
		}
		return prevMonthBtn;
	}
	
	private JButton getNextMonthBtn() {
		if (nextMonthBtn == null) {
			nextMonthBtn = new JButton("");			
			nextMonthBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (monthBox.getSelectedIndex() == 11) {
						monthBox.setSelectedIndex(0);
						yearBox.setSelectedIndex(yearBox.getSelectedIndex()+1);
					}
					else {
						monthBox.setSelectedIndex(monthBox.getSelectedIndex()+1);
					}
				}
			});
			nextMonthBtn.setToolTipText("Next month");
			nextMonthBtn.setIcon(new ImageIcon(Management.class.getResource("/icons/forward16.png")));
			nextMonthBtn.setBounds(215, 66, 30, 30);
		}
		return nextMonthBtn;
	}
}
