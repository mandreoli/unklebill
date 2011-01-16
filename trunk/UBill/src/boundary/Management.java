package boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import executor.Login;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;


public class Management {
	
	private JPanel managePane = null;
	private JTabbedPane tabbedPane = null;
	private JPanel dayTab = null;
	private JLabel accountLabel = null;
	private JLabel balanceLabel = null;
	private JLabel accountBalanceLabel = null;
	private Color active = new Color(0, 128, 0);
	private Color neutro = new Color(0, 0, 0);
	private Color passive = new Color(128, 0, 0);
	private JButton button;
	
	
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
			MonthView mv = new MonthView(this.balanceLabel);
			managePane.add(mv.getTab());			
			managePane.add(getButton());
		}
		return managePane;
	}
	
	private JLabel getAccountLabel() {
		if (accountLabel == null) {
			accountLabel = new JLabel(Login.getAccount().getAccount());
			accountLabel.setFont(new Font("Lucida Grande", Font.BOLD, 22));
			accountLabel.setBounds(15, 40, 300, 41);
		}
		return accountLabel;
	}
	
	private JLabel getAccountBalanceLabel() {
		if (accountBalanceLabel == null) {
			accountBalanceLabel = new JLabel("Account balance");
			accountBalanceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			accountBalanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			accountBalanceLabel.setBounds(312, 63, 149, 16);
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
			balanceLabel.setBounds(180, 75, 281, 30);
		}
		return balanceLabel;
	}
	
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
			tabbedPane.setLocation(2, 87);
			tabbedPane.setSize(new Dimension(477, 344));
			MonthView mv = new MonthView(this.balanceLabel);
			tabbedPane.addTab("Monthly", null, mv.getTab(), null);
			tabbedPane.addTab("Daily", null, getDayTab(), null);
		}
		return tabbedPane;
	}
	
	private JPanel getDayTab() {
		if (dayTab == null) {
			dayTab = new JPanel();
			dayTab.setLayout(null);	
		}
		return dayTab;
	}
	private JButton getButton() {
		if (button == null) {
			button = new JButton("New button");
			button.setBounds(371, 6, 90, 30);
		}
		return button;
	}
}
