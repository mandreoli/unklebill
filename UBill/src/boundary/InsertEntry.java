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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import store.Entry;
import executor.FieldParser;
import executor.Login;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InsertEntry extends BaseBoundary {

	private int wWidth = 300;
	private int wHeight = 220;
	private JDialog mainDialog = null;
	private JPanel mainPane = null;
	private JButton saveBtn = null;
	private JButton exitBtn = null;
	private JLabel categoryLabel = null;
	private JTextField categoryText = null;
	private JLabel descrLabel = null;
	private JScrollPane scrollDescrText = null;
	private JTextArea descrText = null;
	private Color errorColor = new Color(255, 99, 99);
	private Color normalColor = new Color(255, 255, 255);
	private Entry entry = null;
	
	
	/**
	 * @wbp.parser.constructor
	 **/
	public InsertEntry() {
		getMainDialog().setVisible(true);
	}
	
	public InsertEntry(Entry entry) {
		this.entry = entry;
		getMainDialog().setVisible(true);
	}

	private JDialog getMainDialog() {
		if (mainDialog == null) {
			mainDialog = new JDialog();
			mainDialog.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/favico.png")));
			if (entry != null)
				mainDialog.setTitle("Modify category");
			else
				mainDialog.setTitle("Add new category");
			mainDialog.setSize(new Dimension(this.wWidth, this.wHeight));		
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			mainDialog.setLocation(new Point((d.width-wWidth)/2, (d.height-wHeight)/2));
			mainDialog.setResizable(false);			
			mainDialog.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					mainDialog.dispose();
				}
			});	
			mainDialog.setContentPane(getMainPane());
			mainDialog.setModal(true);
			catchTypedField(categoryText, descrText);
		}
		return mainDialog;
	}
	
	private JPanel getMainPane() {
		if (mainPane == null) {
			mainPane = new JPanel();
			mainPane.setLayout(null);
			mainPane.add(getExitBtn());
			mainPane.add(getSaveBtn());
			mainPane.add(getCategoryLabel());
			mainPane.add(getCategoryText());
			mainPane.add(getDescrLabel());
			mainPane.add(getScrollDescrText());
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
			exitBtn.setToolTipText("Back");
			exitBtn.setIcon(new ImageIcon(getClass().getResource("/icons/error16.png")));
			exitBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			exitBtn.setBounds(20, 157, 90, 30);
		}
		return exitBtn;
	}

	private JButton getSaveBtn() {
		if (saveBtn == null) {
			saveBtn = new JButton();
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (entry == null) {
						if (Entry.checkFreeEntry(Login.getUser().getUser(), categoryText.getText())) {
							entry = new Entry(categoryText.getText(), Login.getUser().getUser(), descrText.getText());
							entry.saveEntry();
							ok("Category added<br/>with success.");
							mainDialog.dispose();						
						}
						else
							fail("This category<br/>is already in use!");
					}
					else {
						if (Entry.checkUpdatableEntry(Login.getUser().getUser(), categoryText.getText(), entry.getId())) {
							String oldEntry = entry.getName();
							entry.setName(categoryText.getText());
							entry.setDescription(descrText.getText());
							entry.updateEntry(oldEntry);
							ok("Category modified<br/>with success.");
							mainDialog.dispose();
						}
						else
							fail("This category<br/>is already in use!");
					}
				}
			});
			saveBtn.setIcon(new ImageIcon(getClass().getResource("/icons/ok16.png")));
			saveBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			saveBtn.setLocation(190, 157);
			saveBtn.setEnabled(false);
			saveBtn.setSize(new Dimension(90, 30));
			if (this.entry == null) {
				saveBtn.setToolTipText("Add this category");
				saveBtn.setText("Add");
			}
			else {
				saveBtn.setToolTipText("Modify this category");
				saveBtn.setText("Modify");
			}
		}
		return saveBtn;
	}
	
	public Entry getEntry() {
		return this.entry;
	}
	
	private JLabel getCategoryLabel() {
		if (categoryLabel == null) {
			categoryLabel = new JLabel("Category");
			categoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			categoryLabel.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			categoryLabel.setBounds(30, 29, 61, 16);
		}
		return categoryLabel;
	}
	
	private JTextField getCategoryText() {
		if (categoryText == null) {
			categoryText = new JTextField();			
			categoryText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(categoryText, descrText);
					if (FieldParser.checkCustomUser(categoryText.getText(), 2, 25))
						categoryText.setBackground(normalColor);
					else
						categoryText.setBackground(errorColor);
				}
			});
			categoryText.setToolTipText("Insert a new category");
			categoryText.setFont(new Font("Lucida Grande", Font.BOLD, 12));
			categoryText.setBounds(95, 23, 160, 27);
			categoryText.setColumns(10);
			if (entry != null) {
				categoryText.setText(entry.getName());
			}
		}
		return categoryText;
	}
	
	private JLabel getDescrLabel() {
		if (descrLabel == null) {
			descrLabel = new JLabel("Description:");
			descrLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			descrLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			descrLabel.setBounds(20, 60, 77, 16);			
		}
		return descrLabel;
	}
	
	private JScrollPane getScrollDescrText() {
		if (scrollDescrText == null) {
			scrollDescrText = new JScrollPane();
			scrollDescrText.setBounds(30, 80, 240, 60);
			scrollDescrText.setViewportView(getDescrText());
		}
		return scrollDescrText;
	}
	
	private JTextArea getDescrText() {
		if (descrText == null) {
			descrText = new JTextArea();
			descrText.setLineWrap(true);
			descrText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					catchTypedField(categoryText, descrText);
					if (FieldParser.checkCustomName(descrText.getText(), 4, 80))
						descrText.setBackground(normalColor);
					else
						descrText.setBackground(errorColor);
				}
			});
			descrText.setToolTipText("Insert a short description");
			descrText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			if (entry != null) {
				descrText.setText(entry.getDescription());
			}
		}
		return descrText;
	}
	
	private void catchTypedField(JTextField category, JTextArea descr) {
		if (FieldParser.checkCustomUser(category.getText(), 2, 25)) {
			saveBtn.setEnabled(true);
		}
		else {			 
			saveBtn.setEnabled(false);
		}
	}
}
