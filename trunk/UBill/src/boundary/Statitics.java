package boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import datatype.Date;
import datatype.Month;
import datatype.Transactions;
import executor.FieldParser;
import executor.Login;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import store.Transaction;


public class Statitics {
	
	private JPanel statsPane = null;
	private JLabel accountLabel = null;
	private JTabbedPane tabPane = null;
	private JPanel yearPane = null;
	private JPanel monthPane = null;
	private JComboBox yearBox1 = null;
	private JComboBox yearBox2 = null;
	private JComboBox monthBox = null;
	private JLabel dateLabel1 = null;
	private JLabel dateLabel2 = null;
	private Date date = new Date(Date.getCurrentDate());
	private DefaultTableModel monthTableModel = null;
	private RenderTableBody monthRender = null;
	private JTable monthTable = null;
	private JScrollPane monthScrollPane = null;
	private DefaultTableModel yearTableModel = null;
	private RenderTableBody yearRender = null;
	private JTable yearTable = null;
	private JScrollPane yearScrollPane = null;
	private JButton prevMonthBtn = null;
	private JButton nextMonthBtn = null;
	private JButton prevYearBtn = null;
	private JButton nextYearBtn = null;
	private JButton reportMonthBtn = null;
	private JButton reportYearBtn = null;
	private JLabel accountBalanceLabel = null;
	private JLabel balanceLabel = null;
	private Color active = new Color(0, 128, 0);
	private Color neutro = new Color(0, 0, 0);
	private Color passive = new Color(128, 0, 0);
	
	
	
	public Statitics(JPanel mainPane) {		
		mainPane.add(getManagePane(), BorderLayout.CENTER);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel getManagePane() {
		if (statsPane == null) {
			statsPane = new JPanel();
			statsPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			statsPane.setLocation(new Point(120, 0));
			statsPane.setSize(new Dimension(480, 435));
			statsPane.setLayout(null);
			statsPane.add(getAccountLabel());
			statsPane.add(getAccountBalanceLabel());
			statsPane.add(getBalanceLabel());
			statsPane.add(getTabPane());
		}
		return statsPane;
	}
	
	private JLabel getAccountLabel() {
		if (accountLabel == null) {
			accountLabel = new JLabel(Login.getAccount().getAccount());			
			accountLabel.setHorizontalAlignment(SwingConstants.CENTER);
			accountLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
			accountLabel.setBounds(123, 5, 200, 48);
		}
		return accountLabel;
	}
	
	private JLabel getAccountBalanceLabel() {
		if (accountBalanceLabel == null) {
			accountBalanceLabel = new JLabel("Account balance");
			accountBalanceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			accountBalanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			accountBalanceLabel.setBounds(320, 8, 149, 16);
		}
		return accountBalanceLabel;
	}
	
	private JLabel getBalanceLabel() {
		if (balanceLabel == null) {
			balanceLabel = new JLabel();
			double value = Login.getAccount().getBalance();			
			String sign = "";
			if (value < 0)
				balanceLabel.setForeground(passive);
			else if (value > 0) {
				sign = "+";
				balanceLabel.setForeground(active);
			}
			else
				balanceLabel.setForeground(neutro);
			
			balanceLabel.setText(sign+String.valueOf(value)+" "+Login.getAccount().getCurrency());
			balanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			balanceLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
			balanceLabel.setBounds(279, 20, 191, 30);
		}
		return balanceLabel;
	}
	
	private JTabbedPane getTabPane() {
		if (tabPane == null) {
			tabPane = new JTabbedPane();
			tabPane.setLocation(2, 31);
			tabPane.setSize(new Dimension(475, 400));
			tabPane.add(getYearPane());
			tabPane.add(getMonthPane());
			tabPane.setTitleAt(0, "Year");
			tabPane.setTitleAt(1, "Month");
		}
		return tabPane;
	}
	
	private JPanel getYearPane() {
		if (yearPane == null) {
			yearPane = new JPanel();
			yearPane.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			yearPane.setLayout(null);
			yearPane.add(getYearScrollPane());
			yearPane.add(getDateLabel1());
			yearPane.add(getYearBox1());
			yearPane.add(getPrevYearBtn());
			yearPane.add(getNextYearBtn());
			yearPane.add(getReportYearBtn());
			populateYearTable();
		}
		return yearPane;
	}
	
	private JPanel getMonthPane() {
		if (monthPane == null) {
			monthPane = new JPanel();
			monthPane.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			monthPane.setLayout(null);
			monthPane.add(getMonthScrollPane());
			monthPane.add(getDateLabel2());
			monthPane.add(getYearBox2());
			monthPane.add(getMonthBox());			
			monthPane.add(getPrevMonthBtn());
			monthPane.add(getNextMonthBtn());			
			monthPane.add(getReportMonthBtn());
			populateMonthTable();
		}
		return monthPane;
	}
	
	private JLabel getDateLabel1() {
		if (dateLabel1 == null) {
			dateLabel1 = new JLabel("Selected year");
			dateLabel1.setHorizontalAlignment(SwingConstants.LEFT);
			dateLabel1.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			dateLabel1.setBounds(10, 15, 85, 15);
		}
		return dateLabel1;
	}
	
	private JLabel getDateLabel2() {
		if (dateLabel2 == null) {
			dateLabel2 = new JLabel("Selected date");
			dateLabel2.setHorizontalAlignment(SwingConstants.LEFT);
			dateLabel2.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			dateLabel2.setBounds(10, 15, 85, 15);
		}
		return dateLabel2;
	}
	
	private JComboBox getYearBox1() {
		if (yearBox1 == null) {
			yearBox1 = new JComboBox(insertYears());
			yearBox1.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			yearBox1.setBounds(90, 10, 71, 24);			
			yearBox1.setSelectedItem(date.getYear());
			yearBox1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkPrevNextMonthBtn(yearBox1, 1);			
				}
			});			
		}
		return yearBox1;
	}
	
	private JComboBox getYearBox2() {
		if (yearBox2 == null) {
			yearBox2 = new JComboBox(insertYears());
			yearBox2.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			yearBox2.setBounds(188, 10, 71, 24);			
			yearBox2.setSelectedItem(date.getYear());
			yearBox2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkPrevNextMonthBtn(yearBox2, 2);					
				}
			});			
		}
		return yearBox2;
	}
	
	private Vector<Integer> insertYears() {
		Vector<Integer> years = new Vector<Integer>();			
		for (int i = 2010; i < 2101; i++) {
			years.add(i);				
		}
		return years;
	}
	
	private JComboBox getMonthBox() {
		if (monthBox == null) {
			monthBox = new JComboBox(Month.values());					
			monthBox.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			monthBox.setBounds(90, 10, 99, 24);
			monthBox.setSelectedIndex(date.getMonth()-1);
			monthBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkPrevNextMonthBtn(yearBox2, 2);
				}
			});			
		}
		return monthBox;
	}
	
	private JScrollPane getMonthScrollPane() {
		if (monthScrollPane == null) {
			monthScrollPane = new JScrollPane(getMonthTable());
			monthScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			monthScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			monthScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			monthScrollPane.setBounds(new Rectangle(20, 50, 435, 305));	
		}
		return monthScrollPane;
	}
	
	private TableModel getMonthTableModel() {
		if (monthTableModel == null) {
			monthTableModel = new DefaultTableModel() {
                private static final long serialVersionUID = 1L;
                @Override
                public boolean isCellEditable(int row, int column) {
                        return false;
                }       
			};
			Object[] headers = new Object[6];
			headers[0] = "Day";
			headers[1] = "Entrance";			
			headers[2] = "Exit";
			headers[3] = "T.Entrance";
			headers[4] = "T.Exit";
			headers[5] = "Total";
			monthTableModel.setColumnIdentifiers(headers);			
		}
		return monthTableModel;
	}
	
	private JTable getMonthTable() {
		if (monthTable == null) {			
			monthTable = new JTable(getMonthTableModel());
			monthTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			monthTable.setShowGrid(false);
			monthTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			monthTable.getColumnModel().getColumn(0).setPreferredWidth(40);
			monthTable.getColumnModel().getColumn(1).setPreferredWidth(80);			
			monthTable.getColumnModel().getColumn(2).setPreferredWidth(80);
			monthTable.getColumnModel().getColumn(3).setPreferredWidth(80);
			monthTable.getColumnModel().getColumn(4).setPreferredWidth(80);
			monthTable.getColumnModel().getColumn(5).setPreferredWidth(80);
			monthTable.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {

				}
			});
			monthRender = new RenderTableBody();
			for (int i = 0; i < monthTableModel.getColumnCount(); i++)
				monthTable.getColumn(monthTableModel.getColumnName(i)).setCellRenderer(monthRender);			
		}
		return monthTable;
	}
	
	private JScrollPane getYearScrollPane() {
		if (yearScrollPane == null) {
			yearScrollPane = new JScrollPane(getYearTable());
			yearScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			yearScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			yearScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			yearScrollPane.setBounds(new Rectangle(20, 50, 435, 305));	
		}
		return yearScrollPane;
	}
	
	private TableModel getYearTableModel() {
		if (yearTableModel == null) {
			yearTableModel = new DefaultTableModel() {
                private static final long serialVersionUID = 1L;
                @Override
                public boolean isCellEditable(int row, int column) {
                        return false;
                }       
			};
			Object[] headers = new Object[6];
			headers[0] = "Month";
			headers[1] = "Entrance";			
			headers[2] = "Exit";
			headers[3] = "T.Entrance";
			headers[4] = "T.Exit";
			headers[5] = "Total";
			yearTableModel.setColumnIdentifiers(headers);			
		}
		return yearTableModel;
	}
	
	private JTable getYearTable() {
		if (yearTable == null) {			
			yearTable = new JTable(getYearTableModel());
			yearTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			yearTable.setShowGrid(false);
			yearTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			yearTable.getColumnModel().getColumn(0).setPreferredWidth(55);
			yearTable.getColumnModel().getColumn(1).setPreferredWidth(80);			
			yearTable.getColumnModel().getColumn(2).setPreferredWidth(80);
			yearTable.getColumnModel().getColumn(3).setPreferredWidth(80);
			yearTable.getColumnModel().getColumn(4).setPreferredWidth(80);
			yearTable.getColumnModel().getColumn(5).setPreferredWidth(80);
			yearTable.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {

				}
			});
			yearRender = new RenderTableBody();
			for (int i = 0; i < yearTableModel.getColumnCount(); i++)
				yearTable.getColumn(yearTableModel.getColumnName(i)).setCellRenderer(yearRender);
		}
		return yearTable;
	}
	
	private JButton getPrevMonthBtn() {
		if (prevMonthBtn == null) {
			prevMonthBtn = new JButton("");
			prevMonthBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					if (monthBox.getSelectedIndex() == 0) {
						monthBox.setSelectedIndex(11);
						yearBox2.setSelectedIndex(yearBox2.getSelectedIndex()-1);
					}
					else {
						monthBox.setSelectedIndex(monthBox.getSelectedIndex()-1);
					}
					populateMonthTable();
				}
			});
			prevMonthBtn.setToolTipText("Previous month");
			prevMonthBtn.setIcon(new ImageIcon(getClass().getResource("/icons/rewind16.png")));
			prevMonthBtn.setBounds(265, 7, 30, 30);
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
						yearBox2.setSelectedIndex(yearBox2.getSelectedIndex()+1);
					}
					else {
						monthBox.setSelectedIndex(monthBox.getSelectedIndex()+1);
					}
					populateMonthTable();
				}
			});
			nextMonthBtn.setToolTipText("Next month");
			nextMonthBtn.setIcon(new ImageIcon(getClass().getResource("/icons/forward16.png")));
			nextMonthBtn.setBounds(295, 7, 30, 30);
		}
		return nextMonthBtn;
	}
	
	private JButton getPrevYearBtn() {
		if (prevYearBtn == null) {
			prevYearBtn = new JButton("");
			prevYearBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					yearBox1.setSelectedIndex(yearBox1.getSelectedIndex() - 1);
					populateYearTable();
				}
			});
			prevYearBtn.setToolTipText("Previous year");
			prevYearBtn.setIcon(new ImageIcon(getClass().getResource("/icons/rewind16.png")));
			prevYearBtn.setBounds(170, 7, 30, 30);
		}
		return prevYearBtn;
	}
	
	private JButton getNextYearBtn() {
		if (nextYearBtn == null) {
			nextYearBtn = new JButton("");			
			nextYearBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					yearBox1.setSelectedIndex(yearBox1.getSelectedIndex() + 1);
					populateYearTable();
				}
			});
			nextYearBtn.setToolTipText("Next year");
			nextYearBtn.setIcon(new ImageIcon(getClass().getResource("/icons/forward16.png")));
			nextYearBtn.setBounds(200, 7, 30, 30);
		}
		return nextYearBtn;
	}
	
	private void checkPrevNextMonthBtn(JComboBox yearBox, int num) {
		if (num == 2) {
			if (yearBox.getSelectedItem().toString().equals("2010") && monthBox.getSelectedIndex() == 0) {
				prevMonthBtn.setEnabled(false);
			}
			else if (yearBox.getSelectedItem().toString().equals("2100") && monthBox.getSelectedIndex() == 11) {
				nextMonthBtn.setEnabled(false);
			}
			else {
				nextMonthBtn.setEnabled(true);
				prevMonthBtn.setEnabled(true);
			}
		}
		else {
			if (yearBox.getSelectedItem().toString().equals("2010")) {
				prevYearBtn.setEnabled(false);
			}
			else if (yearBox.getSelectedItem().toString().equals("2100")) {
				nextYearBtn.setEnabled(false);
			}
			else {
				nextYearBtn.setEnabled(true);
				prevYearBtn.setEnabled(true);
			}
		}
	}
	
	private JButton getReportMonthBtn() {
		if (reportMonthBtn == null) {
			reportMonthBtn = new JButton("Report");
			reportMonthBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File file = new File(Login.getAccount().getAccount()+"_summary_"+monthBox.getSelectedItem().toString()+yearBox2.getSelectedItem().toString()+".pdf");
					new SaveReport(file, monthBox.getSelectedItem().toString(), yearBox2.getSelectedItem().toString());
				}
			});
			reportMonthBtn.setIcon(new ImageIcon(getClass().getResource("/icons/report16.png")));
			reportMonthBtn.setToolTipText("Create report for this month");
			reportMonthBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));			
			reportMonthBtn.setBounds(375, 7, 90, 30);
		}
		return reportMonthBtn;
	}
	
	private JButton getReportYearBtn() {
		if (reportYearBtn == null) {
			reportYearBtn = new JButton("Report");
			reportYearBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File file = new File(Login.getAccount().getAccount()+"_summary_"+yearBox1.getSelectedItem().toString()+".pdf");
					new SaveReport(file, yearBox1.getSelectedItem().toString());
				}
			});
			reportYearBtn.setIcon(new ImageIcon(getClass().getResource("/icons/report16.png")));
			reportYearBtn.setToolTipText("Create report for this month");
			reportYearBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));			
			reportYearBtn.setBounds(375, 7, 90, 30);
		}
		return reportYearBtn;
	}
	
	private void populateYearTable() {
		yearTableModel.setRowCount(0);
		int year = Integer.valueOf(yearBox1.getSelectedItem().toString());
		Object obj[] = new Object[6];
		double entrance = 0.0;
		double exit = 0.0;
		double entranceTransf = 0.0;
		double exitTransf = 0.0;
		double entranceTot = 0.0;
		double exitTot = 0.0;
		double entranceTransfTot = 0.0;
		double exitTransfTot = 0.0;
		double total = 0.0;
		Transactions ts = null;
		yearRender.resetRows();
		
		for (int month = 1; month <= Month.values().length; month++) {
			entrance = 0.0;
			exit = 0.0;			
			entranceTransf = 0.0;
			exitTransf = 0.0;
			total = 0.0;
			ts = Transactions.loadTransactions(Login.getUser().getUser(), Login.getAccount().getAccount(), year, month);
			
			for (Transaction t : ts.getTransactions()) {				
				if (t.getRefid() != 0) {
					if (t.getType() == '+') {
						entranceTransf += t.getPayment();	
					}
					else {
						exitTransf += t.getPayment();
					}
				}
				else {
					if (t.getType() == '+') {
						entrance += t.getPayment();
					}
					else {
						exit += t.getPayment();
					}
				}
			}
			
			entrance = FieldParser.roundDouble(entrance);
			exit = FieldParser.roundDouble(exit);
			entranceTransf = FieldParser.roundDouble(entranceTransf);
			exitTransf = FieldParser.roundDouble(exitTransf);
			total = FieldParser.roundDouble(entrance - exit + entranceTransf - exitTransf);	
			
			entranceTot += entrance;
			exitTot += exit;
			entranceTransfTot += entranceTransf;
			exitTransfTot += exitTransf;
			
			obj[0] = Date.getMonth(month);
			setRow(1, 0, entrance, obj);
			setRow(2, 0, exit, obj);
			setRow(3, 0, entranceTransf, obj);
			setRow(4, 0, exitTransf, obj);
			setRow(5, 1, total, obj);
			
			yearTableModel.addRow(obj);
		}
		
		entranceTot = FieldParser.roundDouble(entranceTot);
		exitTot = FieldParser.roundDouble(exitTot);
		entranceTransfTot = FieldParser.roundDouble(entranceTransfTot);
		exitTransfTot = FieldParser.roundDouble(exitTransfTot);
		total = FieldParser.roundDouble(entranceTot - exitTot + entranceTransfTot - exitTransfTot);
		
		yearRender.setRow(Month.values().length);
		
		obj[0] = "Total";
		setRow(1, 1, entranceTot, obj);
		setRow(2, 1, exitTot, obj);
		setRow(3, 1, entranceTransfTot, obj);
		setRow(4, 1, exitTransfTot, obj);
		setRow(5, 1, total, obj);
		
		yearTableModel.addRow(obj);
		
		showLastCell(yearTable);
	}
	
	private void populateMonthTable() {
		monthTableModel.setRowCount(0);
		int days = Date.getNumDays(Date.getMonth(monthBox.getSelectedItem().toString()));
		int month = Date.getMonth(monthBox.getSelectedItem().toString());
		int year = Integer.valueOf(yearBox2.getSelectedItem().toString());
		Object obj[] = new Object[6];	
		double entrance = 0.0;
		double exit = 0.0;
		double entranceTransf = 0.0;
		double exitTransf = 0.0;
		double entranceTot = 0.0;
		double exitTot = 0.0;
		double entranceTransfTot = 0.0;
		double exitTransfTot = 0.0;
		double total = 0.0;
		Transactions ts = null;
		monthRender.resetRows();
				
		for (int day = 1; day <= days; day++) {
			entrance = 0.0;
			exit = 0.0;
			entranceTransf = 0.0;
			exitTransf = 0.0;		
			ts = Transactions.loadTransactions(Login.getUser().getUser(), Login.getAccount().getAccount(), year, month, day);
			
			for (Transaction t : ts.getTransactions()) {				
				if (t.getRefid() != 0) {
					if (t.getType() == '+') {
						entranceTransf += t.getPayment();
						entranceTransfTot += entranceTransf;
					}
					else {
						exitTransf += t.getPayment();
						exitTransfTot += exitTransf;
					}
				}
				else {
					if (t.getType() == '+') {
						entrance += t.getPayment();
						entranceTot += entrance;
					}
					else {
						exit += t.getPayment();
						exitTot += exit;
					}
				}
			}
			
			entrance = FieldParser.roundDouble(entrance);
			exit = FieldParser.roundDouble(exit);
			entranceTransf = FieldParser.roundDouble(entranceTransf);
			exitTransf = FieldParser.roundDouble(exitTransf);
			total = FieldParser.roundDouble(entrance - exit + entranceTransf - exitTransf);
			
			obj[0] = Date.getDay(day);
			setRow(1, 0, entrance, obj);
			setRow(2, 0, exit, obj);
			setRow(3, 0, entranceTransf, obj);
			setRow(4, 0, exitTransf, obj);
			setRow(5, 1, total, obj);
			
			monthTableModel.addRow(obj);
		}
		
		entranceTot = FieldParser.roundDouble(entranceTot);
		exitTot = FieldParser.roundDouble(exitTot);
		entranceTransfTot = FieldParser.roundDouble(entranceTransfTot);
		exitTransfTot = FieldParser.roundDouble(exitTransfTot);
		total = FieldParser.roundDouble(entranceTot - exitTot + entranceTransfTot - exitTransfTot);
		
		monthRender.setRow(days);
		
		obj[0] = "Total";
		setRow(1, days, entranceTot, obj);
		setRow(2, days, exitTot, obj);
		setRow(3, days, entranceTransfTot, obj);
		setRow(4, days, exitTransfTot, obj);
		setRow(5, days, total, obj);
		
		monthTableModel.addRow(obj);
		
		showLastCell(monthTable);
	}
	
	private void setRow(int col, int row, double amount, Object obj[]) {
		String sign = null;
		if (col == 1 || col == 3)
			sign = "+";
		else if (col == 2 || col == 4)
			sign = "-";
		else if (col == 5 && amount >= 0.0)
			sign = "+";
		else
			sign = "";
				
		obj[col] = sign+amount+" "+Login.getAccount().getCurrency();
		
		if (row == 0 && amount == 0.0)
			obj[col] = "";
	}
	
	public void showLastCell(JTable table) {
		int row =  table.getRowCount() - 1;
		
		if (table.getRowCount() > 0) {
			Rectangle rect = table.getCellRect(row, 0, true);
		    table.scrollRectToVisible(rect);
		    table.clearSelection();
		    table.setRowSelectionInterval(row, row);
		    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
		}
	}
}
