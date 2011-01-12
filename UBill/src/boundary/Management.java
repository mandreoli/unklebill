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
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JComboBox;

import datatype.Date;
import datatype.Month;


public class Management {
	
	private JPanel managePane = null;
	private JTabbedPane tabbedPane = null;
	private JPanel monthTab = null;
	private JPanel dayTab = null;
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
	private JLabel dateLabel = null;
	private JComboBox monthBox = null;
	private JComboBox yearBox = null;
	private JButton delTransBtn = null;
	
	
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
			monthTab.add(getDateLabel());
			monthTab.add(getMonthBox());
			monthTab.add(getYearBox());
			monthTab.add(getDelTransBtn());
			
		}
		return monthTab;
	}
	
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerSize(4);
			splitPane.setBounds(10, 50, 456, 262);
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
			headers[1] = "Date";
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
			headers[1] = "Date";
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
			entranceTable.getColumnModel().getColumn(1).setPreferredWidth(30);
			//entranceTable.getColumnModel().getColumn(2).setPreferredWidth(entranceTable.getWidth() - 150);			
			entranceTable.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (entranceTable.getSelectedRowCount() > 0) {
						delTransBtn.setEnabled(true);
						exitTable.clearSelection();
					}
					else
						delTransBtn.setEnabled(false);
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
			exitTable.getColumnModel().getColumn(1).setPreferredWidth(30);
			//exitTable.getColumnModel().getColumn(2).setPreferredWidth(exitTable.getWidth() - 150);
			exitTable.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {					
					if (exitTable.getSelectedRowCount() > 0) {
						delTransBtn.setEnabled(true);
						entranceTable.clearSelection();
					}
					else
						delTransBtn.setEnabled(false);
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
					entranceTableModel.addRow(new String[]{"100", "12", "prva"});
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
					exitTableModel.addRow(new String[]{"100", "12", "prva"});
				}
			});
		}
		return addExitBtn;
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
			entranceAmount = new JLabel("Entrance");
			entranceAmount.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		}
		return entranceAmount;
	}
	
	private JLabel getExitAmount() {
		if (exitAmount == null) {
			exitAmount = new JLabel("Exit");
			exitAmount.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		}
		return exitAmount;
	}
	
	private JLabel getDateLabel() {
		if (dateLabel == null) {
			dateLabel = new JLabel("Date");
			dateLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
			dateLabel.setBounds(10, 19, 44, 15);
		}
		return dateLabel;
	}
	
	private JComboBox getMonthBox() {
		if (monthBox == null) {
			monthBox = new JComboBox(Month.values());
			monthBox.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			monthBox.setBounds(50, 14, 89, 24);
			Date date = new Date(Date.getCurrentDate());
			monthBox.setSelectedIndex(date.getMonth()-1);
		}
		return monthBox;
	}
	
	private JComboBox getYearBox() {
		if (yearBox == null) {
			int[] years = new int[50];
			int curr = 2010;
			for (int i = 0; i < 50; i++) {
				years[i] = curr;
				curr++;
			}
			yearBox = new JComboBox();
			yearBox.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			yearBox.setBounds(140, 14, 89, 24);
			Date date = new Date(Date.getCurrentDate());			
		}
		return yearBox;
	}
	
	private JButton getDelTransBtn() {
		if (delTransBtn == null) {
			delTransBtn = new JButton("Delete");
			delTransBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			delTransBtn.setEnabled(false);
			delTransBtn.setToolTipText("Delete selected transaction");
			delTransBtn.setIcon(new ImageIcon(Management.class.getResource("/icons/del16.png")));
			delTransBtn.setBounds(376, 11, 90, 30);
		}
		return delTransBtn;
	}
}
