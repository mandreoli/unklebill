/**
 * Copyright 2011 Michele Andreoli
 * 
 * This file is part of UnkleBill.
 *
 * UnkleBill is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * UnkleBill is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with UnkleBill; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 **/

package boundary;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import store.Entry;
import store.User;
import javax.swing.JScrollPane;
import javax.swing.JList;
import datatype.Entries;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;



public class ModifyLabels extends BaseBoundary {
	
	private int wWidth = 320;
	private int wHeight = 260;
	private JDialog mainDialog = null;
	private JPanel mainPane = null;
	private JButton exitBtn = null;
	private User user = null;
	private JScrollPane entryScroll = null;
	private JList entryList = null;
	private JButton addBtn = null;
	private JButton modBtn = null;
	private JButton delBtn = null;
	private Entries entries = null;
	private JLabel titleLabel;
	
	
	public ModifyLabels(User user) {
		this.user = user;
		getMainDialog().setVisible(true);
	}
	
	private JDialog getMainDialog() {
		if (mainDialog == null) {
			mainDialog = new JDialog();
			mainDialog.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/favico.png")));
			mainDialog.setTitle("Modify labels");
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
			mainPane.add(getAddBtn());
			mainPane.add(getModBtn());
			mainPane.add(getDelBtn());
			mainPane.add(getEntryScroll());
			mainPane.add(getTitleLabel());
		}
		return mainPane;
	}
	
	private JButton getExitBtn() {
		if (exitBtn == null) {
			exitBtn = new JButton("Close");
			exitBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getMainDialog().dispose();
				}
			});
			exitBtn.setToolTipText("Close window");
			exitBtn.setIcon(new ImageIcon(getClass().getResource("/icons/error16.png")));
			exitBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			exitBtn.setBounds(213, 185, 90, 30);
		}
		return exitBtn;
	}
	
	private JScrollPane getEntryScroll() {
		if (entryScroll == null) {
			entryScroll = new JScrollPane(getEntryList());
			entryScroll.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			entryScroll.setBounds(20, 40, 181, 175);
		}
		return entryScroll;
	}
	
	private JButton getAddBtn() {
		if (addBtn == null) {
			addBtn = new JButton("Add");
			addBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new InsertEntry();
					populateList();
				}
			});
			addBtn.setIcon(new ImageIcon(getClass().getResource("/icons/add16.png")));
			addBtn.setToolTipText("Add new label");
			addBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			addBtn.setBounds(213, 40, 90, 30);
		}
		return addBtn;
	}

	private JButton getModBtn() {
		if (modBtn == null) {
			modBtn = new JButton("Modify");
			modBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new InsertEntry(entries.getEntries().get(entryList.getSelectedIndex()));
					populateList();
				}
			});
			modBtn.setEnabled(false);
			modBtn.setIcon(new ImageIcon(getClass().getResource("/icons/edit16.png")));
			modBtn.setToolTipText("Modify selected label");
			modBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			modBtn.setBounds(213, 70, 90, 30);
		}
		return modBtn;
	}

	private JButton getDelBtn() {
		if (delBtn == null) {
			delBtn = new JButton("Delete");
			delBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Entry entry = entries.getEntries().get(entryList.getSelectedIndex());
					if (abort("<hmtl>You are deleting <b>"+entry.getName()+"</b>.<br/>Are you sure?") == 0) {
						entry.deleteEntry();						
					}
					populateList();
				}
			});
			delBtn.setEnabled(false);
			delBtn.setIcon(new ImageIcon(getClass().getResource("/icons/del16.png")));
			delBtn.setToolTipText("Delete selected label");
			delBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			delBtn.setBounds(213, 111, 90, 30);
		}
		return delBtn;
	}

	private JList getEntryList() {
		if (entryList == null) {
			entryList = new JList();
			entryList.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			entryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			entryList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (!entryList.isSelectionEmpty()) {
						modBtn.setEnabled(true);
						delBtn.setEnabled(true);
					}
				}
			});
			populateList();
		}
		return entryList;
	}
	
	private void populateList() {
		entries = Entries.loadEntries(user.getUser());
		Vector<String> list = new Vector<String>();
		String descr = null;		
		for (Entry e : entries.getEntries()) {
			if (e.getDescription() != null && e.getDescription() != "")
				descr = "<i>("+e.getDescription()+")</i>";
			else
				descr = "";
			list.add("<html>"+e.getName()+" "+descr+"</html>");
		}
		entryList.setListData(list);
		entryList.setSelectedIndex(-1);
		modBtn.setEnabled(false);
		delBtn.setEnabled(false);
	}
	private JLabel getTitleLabel() {
		if (titleLabel == null) {
			titleLabel = new JLabel("List of categories");
			titleLabel.setIcon(new ImageIcon(ModifyLabels.class.getResource("/icons/category16.png")));
			titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			titleLabel.setBounds(20, 18, 150, 15);
		}
		return titleLabel;
	}
}
