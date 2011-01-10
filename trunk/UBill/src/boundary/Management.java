package boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;


public class Management {
	
	private JPanel managePane = null;
	private JTabbedPane tabbedPane = null;
	private JPanel monthTab = null;
	private JPanel dayTab = null;
	
	
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
			managePane.add(getTabbedPane());
		}
		return managePane;
	}
	
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
			tabbedPane.setLocation(2, 68);
			tabbedPane.setSize(new Dimension(471, 358));
			tabbedPane.addTab("Monthly", null, getMonthTab(), null);
			tabbedPane.addTab("Daily", null, getDayTab(), null);
		}
		return tabbedPane;
	}
	
	private JPanel getMonthTab() {
		if (monthTab == null) {
			monthTab = new JPanel();			
			monthTab.setLayout(null);			
			
			JSplitPane splitPane = new JSplitPane();
			splitPane.setBounds(6, 186, 438, 100);
			monthTab.add(splitPane);
		}
		return monthTab;
	}
	
	private JPanel getDayTab() {
		if (dayTab == null) {
			dayTab = new JPanel();
			dayTab.setLayout(null);	
		}
		return dayTab;
	}
}
