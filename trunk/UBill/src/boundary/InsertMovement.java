package boundary;

import javax.swing.JDialog;
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
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import store.Transaction;
import datatype.Accounts;
import datatype.Date;
import executor.FieldParser;
import executor.Login;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import javax.swing.JComboBox;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class InsertMovement extends BaseBoundary {

	private int wWidth = 320;
	private int wHeight = 240;
	private Transaction transaction = null;
	private Transaction ft = null;
	private Transaction tt = null;
	private JDialog mainDialog = null;
	private JPanel mainPane = null;
	private JButton exitBtn = null;
	private JButton saveBtn = null;
	private Color errorColor = new Color(255, 99, 99);
	private Color normalColor = new Color(255, 255, 255);
	private JLabel dateLabel = null;
	private JLabel amountLabel = null;
	private JLabel typeLabel = null;
	private JPanel transPane = null;
	private JLabel entranceIconLabel = null;
	private JLabel exitIconLabel = null;
	private JTextField dateText = null;
	private JTextField amountText = null;
	private JLabel currencyLabel = null;
	private JComboBox categoryBox = null;
	private JButton causalBtn = null;
	private JLabel monthLabel = null;
	private JLabel yearLabel = null;
	private int year = 0;
	private int month = 0;
	private Date date = new Date(Date.getCurrentDate());
	private JLabel fromLabel;
	private JLabel toLabel;
	private JComboBox fromBox;
	private JComboBox toBox;
	
	
	/**
	 * @wbp.parser.constructor
	 **/
	public InsertMovement(int month, int year) {
		this.year = year;
		this.month = month;
		getMainDialog().setVisible(true);
	}
	
	public InsertMovement(Transaction transaction, int month, int year) {
		this.year = year;
		this.month = month;
		this.transaction = transaction;
		getMainDialog().setVisible(true);		
	}
	
	public Transaction getTransaction() {
		return this.transaction;
	}
	
	private JDialog getMainDialog() {
		if (mainDialog == null) {
			mainDialog = new JDialog();
			mainDialog.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/favico.png")));
			mainDialog.setTitle("Add new movement");
			mainDialog.setSize(new Dimension(this.wWidth, this.wHeight));		
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			mainDialog.setLocation(new Point((d.width-wWidth)/2, (d.height-wHeight)/2));
			mainDialog.setResizable(false);			
			mainDialog.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					transaction = null;
					mainDialog.dispose();
				}
			});	
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
			mainPane.add(getTransPane());
			mainPane.add(getEntranceIconLabel());
			mainPane.add(getExitIconLabel());		
			mainPane.add(getFromLabel());
			mainPane.add(getToLabel());
			mainPane.add(getFromBox());
			mainPane.add(getToBox());
			catchTypedField(dateText, amountText);			
		}
		return mainPane;
	}

	private JButton getExitBtn() {
		if (exitBtn == null) {
			exitBtn = new JButton("Cancel");
			exitBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					transaction = null;
					getMainDialog().dispose();
				}
			});
			exitBtn.setToolTipText("Back to Payments");
			exitBtn.setIcon(new ImageIcon(getClass().getResource("/icons/error16.png")));
			exitBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			exitBtn.setBounds(20, 180, 90, 30);
		}
		return exitBtn;
	}

	private JButton getSaveBtn() {
		if (saveBtn == null) {
			saveBtn = new JButton();
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (transaction == null) {
						ft = new Transaction(Login.getUser().getUser(), fromBox.getSelectedItem().toString(), categoryBox.getSelectedItem().toString(), '-', Double.valueOf(amountText.getText()), year, month, Integer.valueOf(dateText.getText()), 0, null);
						ft.saveTransaction();												
									
						tt = new Transaction(Login.getUser().getUser(), toBox.getSelectedItem().toString(), categoryBox.getSelectedItem().toString(), '+', Double.valueOf(amountText.getText()), year, month, Integer.valueOf(dateText.getText()), ft.getId(), fromBox.getSelectedItem().toString());
						tt.saveTransaction();
						
						ft.setRefid(tt.getId());
						ft.setReference(toBox.getSelectedItem().toString());
						ft.updateTransaction();
						
						ok("<html>Transfer from <b>"+ft.getAccount()+"</b> to <b>"+tt.getAccount()+"</b><br/>executed with success!</html>");
						mainDialog.dispose();
					}
					else {
						Transaction t1 = Transaction.loadTransaction(transaction.getRefid(), Login.getUser().getUser(), transaction.getReference());
						
						transaction.setDay(Integer.valueOf(dateText.getText()));
						t1.setDay(Integer.valueOf(dateText.getText()));
						transaction.setPayment(Double.valueOf(amountText.getText()));
						t1.setPayment(Double.valueOf(amountText.getText()));
						
						if (transaction.getAccount().equals(fromBox.getSelectedItem().toString())) { 
							transaction.setReference(toBox.getSelectedItem().toString());
							transaction.setType('-');
							t1.setReference(fromBox.getSelectedItem().toString());
							t1.setType('+');							
						}
						else {
							transaction.setReference(fromBox.getSelectedItem().toString());
							transaction.setType('+');
							t1.setReference(toBox.getSelectedItem().toString());
							t1.setType('-');
						}
						
						if (!t1.getReference().equals(transaction.getAccount()))
							transaction.setAccount(t1.getReference());
						if (!transaction.getReference().equals(t1.getAccount()))
							t1.setAccount(transaction.getReference());
						
						t1.updateTransaction();
						transaction.updateTransaction();
						
						ok("<html>Transfer modified with success!</html>");
						mainDialog.dispose();
					}
				}
			});
			saveBtn.setIcon(new ImageIcon(getClass().getResource("/icons/ok16.png")));
			saveBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			saveBtn.setLocation(210, 180);
			saveBtn.setEnabled(false);
			saveBtn.setSize(new Dimension(90, 30));
			if (this.transaction == null) {
				saveBtn.setToolTipText("Add this movement");
				saveBtn.setText("Add");
			}
			else {
				saveBtn.setToolTipText("Modify this movement");
				saveBtn.setText("Modify");
			}
		}
		return saveBtn;
	}
	
	private void catchTypedField(JTextField date, JTextField amount) {
		int num = 0;
		if (date.getText().equals(""))
			num = 0;
		else
			num = Integer.valueOf(date.getText());
		
		if (Date.checkDate(month, num, year) && fromBox.getSelectedIndex() != 0 && toBox.getSelectedIndex() != 0 && fromBox.getSelectedIndex() != toBox.getSelectedIndex() && FieldParser.checkInt(date.getText()) && Integer.valueOf(dateText.getText()) > 0 && FieldParser.checkFloat(amount.getText(), false) && Double.valueOf(amount.getText()) > 0) {
			saveBtn.setEnabled(true);
		}
		else {			 
			saveBtn.setEnabled(false);
		}
	}
	
	private JLabel getDateLabel() {
		if (dateLabel == null) {
			dateLabel = new JLabel("Date");
			dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			dateLabel.setBounds(16, 28, 61, 16);
			dateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		}
		return dateLabel;
	}
	
	private JLabel getAmountLabel() {
		if (amountLabel == null) {
			amountLabel = new JLabel("Amount");
			amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			amountLabel.setBounds(16, 54, 61, 16);
			amountLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		}
		return amountLabel;
	}
	
	private JLabel getTypeLabel() {
		if (typeLabel == null) {
			typeLabel = new JLabel("Category");
			typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			typeLabel.setBounds(16, 80, 61, 16);
		}
		return typeLabel;
	}
	
	private JPanel getTransPane() {
		if (transPane == null) {
			transPane = new JPanel();
			transPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Transaction", TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande", Font.PLAIN, 12), Color.DARK_GRAY));
			transPane.setBounds(20, 60, 280, 111);
			transPane.setLayout(null);
			transPane.add(getDateLabel());
			transPane.add(getAmountLabel());
			transPane.add(getTypeLabel());
			transPane.add(getCurrencyLabel());
			transPane.add(getDateText());
			transPane.add(getAmountText());
			transPane.add(getCategoryBox());
			transPane.add(getCausalBtn());
			transPane.add(getMonthLabel());
			transPane.add(getYearLabel());
		}
		return transPane;
	}
	
	private JLabel getEntranceIconLabel() {
		if (entranceIconLabel == null) {
			entranceIconLabel = new JLabel("");
			entranceIconLabel.setIcon(new ImageIcon(getClass().getResource("/icons/entrance16.png")));
			entranceIconLabel.setBounds(166, 29, 16, 16);
		}
		return entranceIconLabel;
	}
	
	private JLabel getExitIconLabel() {
		if (exitIconLabel == null) {
			exitIconLabel = new JLabel("");
			exitIconLabel.setIcon(new ImageIcon(getClass().getResource("/icons/output16.png")));
			exitIconLabel.setBounds(20, 29, 16, 16);
		}
		return exitIconLabel;
	}
	
	private JTextField getDateText() {
		if (dateText == null) {
			dateText = new JTextField();
			dateText.setHorizontalAlignment(SwingConstants.RIGHT);
			dateText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(dateText, amountText);
					int num = 0;
					if (dateText.getText().equals(""))
						num = 0;
					else
						num = Integer.valueOf(dateText.getText());
					
					if (FieldParser.checkInt(dateText.getText()) && Date.checkDate(month, num, year))
						dateText.setBackground(normalColor);
					else
						dateText.setBackground(errorColor);
				}
			});
			dateText.setToolTipText("Insert date of transaction");
			dateText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			dateText.setBounds(155, 23, 30, 27);
			dateText.setColumns(10);			
			dateText.setText(String.valueOf(date.getDay()));
			if (transaction != null)
				dateText.setText(String.valueOf(transaction.getDay()));
		}
		return dateText;
	}
	
	private JLabel getMonthLabel() {
		if (monthLabel == null) {
			monthLabel = new JLabel(Date.getMonth(month));
			monthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			monthLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			monthLabel.setBounds(90, 28, 63, 16);
			if (transaction != null)
				monthLabel.setText(Date.getMonth(month));
		}
		return monthLabel;
	}
	
	private JLabel getYearLabel() {
		if (yearLabel == null) {
			yearLabel = new JLabel(", "+String.valueOf(year));
			yearLabel.setHorizontalAlignment(SwingConstants.LEFT);
			yearLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			yearLabel.setBounds(185, 28, 69, 16);
			if (transaction != null)
				yearLabel.setText(String.valueOf(transaction.getYear()));
		}
		return yearLabel;
	}
	
	private JTextField getAmountText() {
		if (amountText == null) {
			amountText = new JTextField();
			amountText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(dateText, amountText);
					if (FieldParser.checkFloat(amountText.getText(), false) && Double.valueOf(amountText.getText()) > 0)
						amountText.setBackground(normalColor);
					else
						amountText.setBackground(errorColor);
				}
			});
			amountText.setToolTipText("Insert the amount");
			amountText.setHorizontalAlignment(SwingConstants.RIGHT);
			amountText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			amountText.setColumns(10);
			amountText.setBounds(90, 49, 90, 27);
			if (transaction != null)
				amountText.setText(String.valueOf(transaction.getPayment()));
		}
		return amountText;
	}
	
	private JLabel getCurrencyLabel() {
		if (currencyLabel == null) {
			currencyLabel = new JLabel(Login.getAccount().getCurrency());
			currencyLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			currencyLabel.setBounds(180, 54, 41, 16);
		}
		return currencyLabel;
	}
	
	private JComboBox getCategoryBox() {
		if (categoryBox == null) {						
			categoryBox = new JComboBox(new String[]{"Transfer"});
			categoryBox.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			categoryBox.setBounds(89, 76, 132, 24);
			categoryBox.setEnabled(false);
		}
		return categoryBox;
	}
	
	private JButton getCausalBtn() {
		if (causalBtn == null) {
			causalBtn = new JButton("");
			causalBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new InsertEntry();					
				}
			});
			causalBtn.setToolTipText("Add new category");
			causalBtn.setIcon(new ImageIcon(getClass().getResource("/icons/add16.png")));
			causalBtn.setBounds(224, 73, 30, 30);	
			causalBtn.setEnabled(false);
		}
		return causalBtn;
	}
	
	public Transaction getPrimaryTransaction() {
		if (tt != null) {
			if (Login.getAccount().getAccount().equals(tt.getAccount()))
				return this.tt;
			else
				return this.ft;
		}
		return null;
	}
	
	public Transaction getOtherTransaction() {
		if (tt != null) {
			if (!Login.getAccount().getAccount().equals(tt.getAccount()))
				return this.tt;
			else
				return this.ft;
		}
		return null;
	}
	
	private JLabel getFromLabel() {
		if (fromLabel == null) {
			fromLabel = new JLabel("From account");
			fromLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			fromLabel.setBounds(20, 6, 99, 16);
		}
		return fromLabel;
	}
	
	private JLabel getToLabel() {
		if (toLabel == null) {
			toLabel = new JLabel("To account");
			toLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			toLabel.setBounds(166, 6, 99, 16);
		}
		return toLabel;
	}
	
	private JComboBox getFromBox() {
		if (fromBox == null) {
			fromBox = new JComboBox(retrieveAccounts().toArray());
			if (transaction != null) {
				if (transaction.getType() == '+')
					fromBox.setSelectedItem(transaction.getReference());
				else
					fromBox.setSelectedItem(Login.getAccount().getAccount());
			}
			fromBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					catchTypedField(dateText, amountText);	
				}
			});
			fromBox.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			fromBox.setBounds(40, 25, 110, 24);			
		}
		return fromBox;
	}
	
	private JComboBox getToBox() {
		if (toBox == null) {			
			toBox = new JComboBox(retrieveAccounts().toArray());
			if (transaction != null) {
				if (transaction.getType() == '-')
					toBox.setSelectedItem(transaction.getReference());
				else
					toBox.setSelectedItem(Login.getAccount().getAccount());
			}
			else
				toBox.setSelectedItem(Login.getAccount().getAccount());
			toBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					catchTypedField(dateText, amountText);	
				}
			});
			toBox.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			toBox.setBounds(185, 25, 110, 24);			
		}
		return toBox;
	}
	
	private LinkedList<String> retrieveAccounts() {		
		LinkedList<String> list = new LinkedList<String>();
		
		list = Accounts.loadAccounts(Login.getUser().getUser()).getAccountsNames();
		list.addFirst("");
		
		return list;
	}
}	

