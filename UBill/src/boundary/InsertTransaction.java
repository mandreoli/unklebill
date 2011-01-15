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
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import store.Transaction;
import datatype.Date;
import executor.FieldParser;
import executor.Login;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;



public class InsertTransaction extends BaseBoundary {

	private int wWidth = 320;
	private int wHeight = 240;
	private Transaction transaction = null;
	private JDialog mainDialog = null;
	private JPanel mainPane = null;
	private JButton exitBtn = null;
	private JButton saveBtn = null;
	private Color errorColor = new Color(255, 99, 99);
	private Color normalColor = new Color(255, 255, 255);
	private Color active = new Color(0, 128, 0);	
	private Color passive = new Color(128, 0, 0);
	private int flag = 0;
	private JLabel dateLabel = null;
	private JLabel amountLabel = null;
	private JLabel typeLabel = null;
	private JPanel transPane = null;
	private JRadioButton entranceRadio = null;
	private JRadioButton exitRadio = null;
	private JLabel entranceIconLabel = null;
	private JLabel exitIconLabel = null;
	private ButtonGroup radioGroup = null;
	private JTextField dateText = null;
	private JTextField amountText = null;
	private JLabel currencyLabel = null;
	private JComboBox comboBox = null;
	private JButton causalBtn = null;
	private JLabel monthLabel = null;
	private JLabel yearLabel = null;
	private int year = 0;
	private int month = 0;
	private Date date = new Date(Date.getCurrentDate());
	
	
	/**
	 * @wbp.parser.constructor
	 **/
	public InsertTransaction(int flag, int month, int year) {
		this.year = year;
		this.month = month;
		this.flag = flag;
		getMainDialog().setVisible(true);
	}
	
	public InsertTransaction(Transaction transaction) {
		this.transaction = transaction;
		
		if (transaction.getType() == '+')
			this.flag = 0;
		else
			this.flag = 1;
		
		getMainDialog().setVisible(true);		
	}
	
	private JDialog getMainDialog() {
		if (mainDialog == null) {
			mainDialog = new JDialog();
			mainDialog.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/favico.png")));
			mainDialog.setTitle("Add new transaction");
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
			mainPane.add(getEntranceRadio());
			mainPane.add(getExitRadio());
			getRadioGroup();
			mainPane.add(getEntranceIconLabel());
			mainPane.add(getExitIconLabel());			
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
					char type;
					if (entranceRadio.isSelected())
						type = '+';
					else
						type = '-';
					if (transaction == null) {											
						Transaction trans = new Transaction(Login.getUser().getName(), Login.getAccount().getAccount(), null, type, Double.valueOf(amountText.getText()), year, month, Integer.valueOf(dateText.getText()));
						transaction = trans;
						trans.saveTransaction();
						ok("Transaction added<br/>with success.");
						mainDialog.dispose();
					}
					else {											
						transaction.setDay(Integer.valueOf(dateText.getText()));
						transaction.setPayment(Double.valueOf(amountText.getText()));						
						transaction.updateTransaction();
						ok("Transaction modified<br/>with success.");
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
				saveBtn.setToolTipText("Add this transaction");
				saveBtn.setText("Add");
			}
			else {
				saveBtn.setToolTipText("Modify this transaction");
				saveBtn.setText("Modify");
			}
		}
		return saveBtn;
	}
	
	private void catchTypedField(JTextField date, JTextField amount) {
		if (FieldParser.checkInt(date.getText()) && Integer.valueOf(dateText.getText()) > 0 && FieldParser.checkFloat(amount.getText(), false) && Double.valueOf(amount.getText()) > 0) {
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
			typeLabel = new JLabel("Causal");
			typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			typeLabel.setBounds(16, 80, 61, 16);
		}
		return typeLabel;
	}
	
	private JPanel getTransPane() {
		if (transPane == null) {
			transPane = new JPanel();
			transPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Transaction", TitledBorder.LEFT, TitledBorder.TOP, new Font("Lucida Grande", Font.PLAIN, 12), Color.DARK_GRAY));
			transPane.setBounds(20, 57, 280, 111);
			transPane.setLayout(null);
			transPane.add(getDateLabel());
			transPane.add(getAmountLabel());
			transPane.add(getTypeLabel());
			transPane.add(getCurrencyLabel());
			transPane.add(getDateText());
			transPane.add(getAmountText());
			transPane.add(getComboBox());
			transPane.add(getCausalBtn());
			transPane.add(getMonthLabel());
			transPane.add(getYearLabel());
		}
		return transPane;
	}
	
	private ButtonGroup getRadioGroup() {
		if (radioGroup == null) {
			radioGroup = new ButtonGroup();
			radioGroup.add(entranceRadio);
			radioGroup.add(exitRadio);
			
			if (this.flag == 0)
				entranceRadio.setSelected(true);
			else
				exitRadio.setSelected(true);
			
			if (this.transaction != null) {
				this.entranceRadio.setEnabled(false);
				this.exitRadio.setEnabled(false);
			}
				
		}
		return radioGroup;
	}
	
	private JRadioButton getEntranceRadio() {
		if (entranceRadio == null) {
			entranceRadio = new JRadioButton("Entrance");
			entranceRadio.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if (entranceRadio.isSelected())
						changeForegroundColor(0);
				}
			});
			entranceRadio.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			entranceRadio.setBounds(47, 22, 82, 23);			
		}
		return entranceRadio;
	}
	
	private JRadioButton getExitRadio() {
		if (exitRadio == null) {
			exitRadio = new JRadioButton("Exit");
			exitRadio.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if (exitRadio.isSelected())
						changeForegroundColor(1);
				}
			});
			exitRadio.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			exitRadio.setBounds(187, 22, 63, 23);
		}
		return exitRadio;
	}
	
	private void changeForegroundColor(int flag) {
		if (flag == 0) {
			currencyLabel.setForeground(active);
			amountText.setForeground(active);
		}
		else {
			currencyLabel.setForeground(passive);
			amountText.setForeground(passive);
		}
	}
	
	private JLabel getEntranceIconLabel() {
		if (entranceIconLabel == null) {
			entranceIconLabel = new JLabel("");
			entranceIconLabel.setIcon(new ImageIcon(getClass().getResource("/icons/entrance16.png")));
			entranceIconLabel.setBounds(130, 26, 16, 16);
		}
		return entranceIconLabel;
	}
	
	private JLabel getExitIconLabel() {
		if (exitIconLabel == null) {
			exitIconLabel = new JLabel("");
			exitIconLabel.setIcon(new ImageIcon(getClass().getResource("/icons/output16.png")));
			exitIconLabel.setBounds(242, 26, 16, 16);
		}
		return exitIconLabel;
	}
	
	private JTextField getDateText() {
		if (dateText == null) {
			dateText = new JTextField();
			dateText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(dateText, amountText);
					if (FieldParser.checkInt(dateText.getText()) && Integer.valueOf(dateText.getText()) > 0)
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
				monthLabel.setText(Date.getMonth(transaction.getMonth()));
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
			changeForegroundColor(flag);
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
	
	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
			comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			comboBox.setBounds(89, 76, 132, 24);
		}
		return comboBox;
	}
	
	private JButton getCausalBtn() {
		if (causalBtn == null) {
			causalBtn = new JButton("");
			causalBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			causalBtn.setToolTipText("Add new causal");
			causalBtn.setIcon(new ImageIcon(getClass().getResource("/icons/add16.png")));
			causalBtn.setBounds(224, 73, 30, 30);
		}
		return causalBtn;
	}
	
	public Transaction getTransaction() {
		return this.transaction;
	}
	
	
}	

