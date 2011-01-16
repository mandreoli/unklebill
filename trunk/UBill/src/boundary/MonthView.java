package boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.util.Vector;
import javax.swing.JComboBox;
import datatype.Date;
import datatype.Month;
import datatype.Transactions;
import executor.Login;
import store.Transaction;
import java.awt.Color;


public class MonthView {

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
	private JLabel balanceLabel = null;
	private Color active = new Color(0, 128, 0);
	private Color neutro = new Color(0, 0, 0);
	private Color passive = new Color(128, 0, 0);
	private Transactions transactions = null;
	private Date date = new Date(Date.getCurrentDate());
	private Transactions entranceTrans = null;
	private Transactions exitTrans = null;
	private double entranceTot = 0.0;
	private double exitTot = 0.0;
	
	
	public MonthView(JLabel balanceLabel) {
		this.balanceLabel = balanceLabel;
		getMonthTab();
	}
	
	public JPanel getTab() {
		return this.monthTab;
	}
	
	private JPanel getMonthTab() {
		if (monthTab == null) {
			monthTab = new JPanel();
			monthTab.setLocation(2, 87);
			monthTab.setSize(new Dimension(477, 344));
			monthTab.setLayout(null);			
			monthTab.add(getSplitPane());
			monthTab.add(getMonthBox());
			monthTab.add(getYearBox());
			monthTab.add(getDelTransBtn());
			monthTab.add(getModTransBtn());
			populateTables();			
		}
		return monthTab;
	}
	
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerSize(4);
			splitPane.setBounds(10, 46, 456, 262);
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
					if (entranceTable.getSelectedRowCount() > 0) {
						delTransBtn.setEnabled(true);
						modTransBtn.setEnabled(true);
						exitTable.clearSelection();
					}
					else {
						modTransBtn.setEnabled(false);
						delTransBtn.setEnabled(false);
					}
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
					if (exitTable.getSelectedRowCount() > 0) {
						delTransBtn.setEnabled(true);
						modTransBtn.setEnabled(true);
						entranceTable.clearSelection();
					}
					else {
						delTransBtn.setEnabled(false);
						modTransBtn.setEnabled(false);
					}
				}
			});
		}
		return exitTable;
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
	
	private JComboBox getMonthBox() {
		if (monthBox == null) {
			monthBox = new JComboBox(Month.values());					
			monthBox.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			monthBox.setBounds(10, 14, 99, 24);			
			monthBox.setSelectedIndex(date.getMonth()-1);
			monthBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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
			yearBox.setBounds(108, 14, 71, 24);			
			yearBox.setSelectedItem(date.getYear());
			yearBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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
	
	private JButton getDelTransBtn() {
		if (delTransBtn == null) {
			delTransBtn = new JButton("Delete");
			delTransBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (entranceTable.getSelectedRowCount() > 0) {
						System.out.println(entranceTrans.getTransactions().get(entranceTable.getSelectedRow()).getPayment());
					}
					else {
						System.out.println(exitTrans.getTransactions().get(exitTable.getSelectedRow()).getPayment());
					}
				}
			});
			delTransBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			delTransBtn.setEnabled(false);
			delTransBtn.setToolTipText("Delete selected transaction");
			delTransBtn.setIcon(new ImageIcon(getClass().getResource("/icons/del16.png")));
			delTransBtn.setBounds(376, 11, 90, 30);
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
					
					Transaction oldT = new Transaction(t.getId(), t.getUser(), t.getAccount(), t.getEntry(), t.getType(), t.getPayment(), t.getYear(), t.getMonth(), t.getDay());					
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
			modTransBtn.setBounds(285, 11, 90, 30);
		}
		return modTransBtn;
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
		entrance.setText("+"+String.valueOf(this.entranceTot)+" "+Login.getAccount().getCurrency());
		exit.setText("-"+String.valueOf(this.exitTot)+" "+Login.getAccount().getCurrency());
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
}
