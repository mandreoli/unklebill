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

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;


public class ErrorLog extends BaseBoundary {
	
	private int wWidth = 320;
	private int wHeight = 280;
	private JDialog mainDialog = null;
	private JPanel mainPane = null;
	private JButton exitBtn = null;
	private JButton sendBtn = null;
	private JLabel logTitle = null;
	private JScrollPane scrollPane = null;
	private JTextArea textArea = null;
	private String record = null;
	private JButton btnCopy;
	
	public ErrorLog(String record) {
		this.record = record;
		getMainDialog().setVisible(true);
	}
	
	private JDialog getMainDialog() {
		if (mainDialog == null) {
			mainDialog = new JDialog();
			mainDialog.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/favico.png")));
			mainDialog.setTitle("Error console");
			mainDialog.setSize(new Dimension(this.wWidth, this.wHeight));		
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			mainDialog.setLocation(new Point((d.width-wWidth)/2, (d.height-wHeight)/2));
			mainDialog.setResizable(false);
			mainDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);			
			mainDialog.setContentPane(getMainPane());
			mainDialog.setModal(false);
		}
		return mainDialog;
	}
	
	private JPanel getMainPane() {
		if (mainPane == null) {
			mainPane = new JPanel();
			mainPane.setLayout(null);
			mainPane.add(getExitBtn());
			mainPane.add(getSendBtn());
			mainPane.add(getLogTitle());
			mainPane.add(getScrollPane());
			mainPane.add(getBtnCopy());
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
			exitBtn.setToolTipText("Close popup");
			exitBtn.setIcon(new ImageIcon(getClass().getResource("/icons/error16.png")));
			exitBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			exitBtn.setBounds(20, 217, 90, 30);
		}
		return exitBtn;
	}
	
	private JButton getSendBtn() {
		if (sendBtn == null) {
			sendBtn = new JButton("Send");
			sendBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Desktop desktop = null;
					
					setClipboard();
				    
					if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
						URI mailto = null;
						try {
							String body = "";
							try {
								body = URLEncoder.encode("Paste here the log message", "UTF-8");
							} catch (UnsupportedEncodingException e0) {
								System.err.println("URI encode error: "+e0);
							}
							mailto = new URI("mailto:drelvan@altervista.org?subject=UnkleBill%20report&body="+body);
						}
						catch (URISyntaxException e1) { 
							System.err.println("URI sintax error: "+e1);
						}
						
						try {
							desktop.mail(mailto);
						}
						catch (IOException e2) {
							System.err.println("I/O error: "+e2);
						}
					} 
					else
					  throw new RuntimeException("Desktop doesn't support mailto.");
				}
			});
			sendBtn.setToolTipText("Send log file");
			sendBtn.setIcon(new ImageIcon(getClass().getResource("/icons/send16.png")));
			sendBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			sendBtn.setLocation(210, 217);
			sendBtn.setSize(new Dimension(90, 30));
		}
		return sendBtn;
	}
	
	private JLabel getLogTitle() {
		if (logTitle == null) {
			logTitle = new JLabel("<html>Unexpected error occurred.<br/>Send the log file at developers to fix this error.</html>");
			logTitle.setIcon(new ImageIcon(ErrorLog.class.getResource("/icons/log24.png")));
			logTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			logTitle.setBounds(6, 6, 308, 45);
		}
		return logTitle;
	}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			scrollPane.setBounds(6, 49, 308, 164);
			scrollPane.setViewportView(getTextArea());
		}
		return scrollPane;
	}
	
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea(record);
			textArea.setWrapStyleWord(true);
			textArea.setFont(new Font("Courier", Font.PLAIN, 10));
			textArea.setLineWrap(true);
		}
		return textArea;
	}
	
	private void setClipboard() {
		StringSelection data = new StringSelection(textArea.getText());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(data, data);
	}
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton("Copy");
			btnCopy.setIcon(new ImageIcon(ErrorLog.class.getResource("/icons/accounts16.png")));
			btnCopy.setToolTipText("Copy log to clipboard");
			btnCopy.setSize(new Dimension(90, 30));
			btnCopy.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			btnCopy.setBounds(115, 217, 90, 30);
		}
		return btnCopy;
	}
	
	public void setErrorText(String record) {
		this.textArea.setText(record);
	}
}
