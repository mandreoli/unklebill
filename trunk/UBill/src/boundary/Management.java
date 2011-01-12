package boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;


public class Management {
	
	private JPanel managePane = null;
	private JTabbedPane tabbedPane = null;
	private JPanel monthTab = null;
	private JPanel dayTab = null;
	private JSplitPane splitPane = null;
	private JTable entranceTable = null;
	private JTable exitTable = null;
	private TableModel tableModel = null;
	private JPanel entrancePane = null;
	private JPanel exitPane = null;
	private JButton button = null;
	private JButton button_1 = null;
	private JScrollPane entranceScrollPane = null;
	private JScrollPane exitScrollPane = null;
	
	
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
			tabbedPane.setLocation(2, 73);
			tabbedPane.setSize(new Dimension(477, 358));
			tabbedPane.addTab("Monthly", null, getMonthTab(), null);
			tabbedPane.addTab("Daily", null, getDayTab(), null);
		}
		return tabbedPane;
	}
	
	private JPanel getMonthTab() {
		if (monthTab == null) {
			monthTab = new JPanel();			
			monthTab.setLayout(null);			
			monthTab.add(getSplitPane());
			
		}
		return monthTab;
	}
	
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerSize(4);
			splitPane.setBounds(10, 50, 456, 270);
			splitPane.setLeftComponent(getEntrancePane());
			splitPane.setRightComponent(getExitPane());
			splitPane.setDividerLocation(228);
		}
		return splitPane;
	}
	
	private JPanel getDayTab() {
		if (dayTab == null) {
			dayTab = new JPanel();
			dayTab.setLayout(null);	
		}
		return dayTab;
	}
	
	private TableModel getTableModel() {
		if (tableModel == null) {
			tableModel = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}	
			};
			Object[] headers = new Object[3];
			headers[0] = "Date";
			headers[1] = "Amount";
			headers[2] = "Causal";
			((DefaultTableModel) tableModel).setColumnIdentifiers(headers);
		}
		return tableModel;
	}
	
	private JTable getEntranceTable() {
		if (entranceTable == null) {			
			entranceTable = new JTable(getTableModel());
			entranceTable.setBounds(new Rectangle(20, 70, 216, 235));			
			entranceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			entranceTable.getColumnModel().getColumn(0).setPreferredWidth(30);
			entranceTable.getColumnModel().getColumn(1).setPreferredWidth(50);
			//entranceTable.getColumnModel().getColumn(2).setPreferredWidth(entranceTable.getWidth() - 150);			
			entranceTable.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					
				}
			});
		}
		return entranceTable;
	}
	
	private JTable getExitTable() {
		if (exitTable == null) {			
			exitTable = new JTable(getTableModel());
			exitTable.setBounds(new Rectangle(20, 70, 216, 235));			
			exitTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			exitTable.getColumnModel().getColumn(0).setPreferredWidth(30);
			exitTable.getColumnModel().getColumn(1).setPreferredWidth(50);
			//exitTable.getColumnModel().getColumn(2).setPreferredWidth(exitTable.getWidth() - 150);
			exitTable.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					
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
			entrancePane.add(getButton(), BorderLayout.SOUTH);
			entrancePane.add(getEntranceScrollPane(), BorderLayout.CENTER);
		}
		return entrancePane;
	}
	
	private JPanel getExitPane() {
		if (exitPane == null) {
			exitPane = new JPanel();
			exitPane.setBorder(null);
			exitPane.setLayout(new BorderLayout(0, 0));
			exitPane.add(getButton_1(), BorderLayout.SOUTH);
			exitPane.add(getExitScrollPane(), BorderLayout.CENTER);
		}
		return exitPane;
	}
	
	private JButton getButton() {
		if (button == null) {
			button = new JButton("New button");
		}
		return button;
	}
	
	private JButton getButton_1() {
		if (button_1 == null) {
			button_1 = new JButton("New button");
		}
		return button_1;
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
}
