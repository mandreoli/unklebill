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
	private JLabel formatLabel = null;
	private JComboBox comboBox = null;
	private JButton btnAddNewCausal = null;
	
	
	/**
	 * @wbp.parser.constructor
	 **/
	public InsertTransaction(int flag) {
		this.flag = flag;
		getMainDialog().setVisible(true);
	}
	
	public InsertTransaction(Transaction transaction) {
		this.transaction = transaction;
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
						Transaction transaction = new Transaction(Login.getUser().getName(), Login.getAccount().getAccount(), null, type, Double.valueOf(amountText.getText()), dateText.getText());
						transaction.saveTransaction();
						ok("Transaction added<br/>with success.");
						mainDialog.dispose();
					}
					else {
						transaction.setDate(dateText.getText());
						transaction.setPayment(Double.valueOf(amountText.getText()));
						transaction.setType(type);
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
		if (FieldParser.checkDate(date.getText()) && Date.checkDate(date.getText()) && FieldParser.checkFloat(amount.getText(), false) && Double.valueOf(amount.getText()) > 0) {
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
			transPane.add(getFormatLabel());
			transPane.add(getDateText());
			transPane.add(getAmountText());
			transPane.add(getComboBox());
			transPane.add(getBtnAddNewCausal());
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
			entranceIconLabel.setIcon(new ImageIcon(InsertTransaction.class.getResource("/icons/entrance16.png")));
			entranceIconLabel.setBounds(130, 26, 16, 16);
		}
		return entranceIconLabel;
	}
	
	private JLabel getExitIconLabel() {
		if (exitIconLabel == null) {
			exitIconLabel = new JLabel("");
			exitIconLabel.setIcon(new ImageIcon(InsertTransaction.class.getResource("/icons/output16.png")));
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
					if (FieldParser.checkDate(dateText.getText()) && Date.checkDate(dateText.getText()))
						dateText.setBackground(normalColor);
					else
						dateText.setBackground(errorColor);
				}
			});
			dateText.setToolTipText("Insert date of transaction");
			dateText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			dateText.setBounds(90, 23, 90, 27);
			dateText.setColumns(10);
			Date date = new Date(Date.getCurrentDate());
			dateText.setText(date.getDate('/'));			
		}
		return dateText;
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
	
	private JLabel getFormatLabel() {
		if (formatLabel == null) {
			formatLabel = new JLabel("mm/dd/yyyy");
			formatLabel.setForeground(Color.DARK_GRAY);
			formatLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			formatLabel.setBounds(180, 28, 74, 16);
		}
		return formatLabel;
	}
	
	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
			comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			comboBox.setBounds(89, 76, 132, 24);
		}
		return comboBox;
	}
	
	private JButton getBtnAddNewCausal() {
		if (btnAddNewCausal == null) {
			btnAddNewCausal = new JButton("");
			btnAddNewCausal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnAddNewCausal.setToolTipText("Add new causal");
			btnAddNewCausal.setIcon(new ImageIcon(InsertTransaction.class.getResource("/icons/add16.png")));
			btnAddNewCausal.setBounds(224, 73, 30, 30);
		}
		return btnAddNewCausal;
	}
}	

