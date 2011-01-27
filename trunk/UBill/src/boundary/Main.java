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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import datatype.Users;

import executor.Login;

public class Main extends BaseBoundary {
	
	private int wWidth = 600;
	private int wHeight = 480;
	private SplashScreen splash = null;
	private JFrame mainFrame = null;
	private JPanel mainPane = null;
	private JPanel leftPane = null;
	private JButton homeBtn = null;
	private JButton transBtn = null;
	private JButton exitBtn = null;
	private JMenuBar menuBar = null;
	private JButton statBtn = null;
	private JButton logoutBtn = null;
	private Main main = null;
	private JMenu fileMenu = null;
	private JMenu editMenu = null;
	private JMenu helpMenu = null;
	
	
	public Main(SplashScreen splash) {
		this.splash = splash;
		this.main = this;
	}
	
	public void START() {		
		getMainFrame().setVisible(true);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private JFrame getMainFrame() {
		if (mainFrame == null) {
			mainFrame = new JFrame();
			mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/favico.png")));
			mainFrame.setTitle("..::UnkleBill v1.0::.. I want your... bills!");
			mainFrame.setSize(new Dimension(this.wWidth, this.wHeight));		
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			mainFrame.setLocation(new Point((d.width-wWidth)/2, (d.height-wHeight)/2));
			mainFrame.setResizable(false);
			mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			mainFrame.setJMenuBar(getMenuBar());
			mainFrame.setContentPane(getMainPane());			
			mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					int flag = confirm("Are you sure to exit from UBill?");
					if (flag == 0)
						getMainFrame().dispose();
						splash.dispose();
				}
			});			
		}
		return mainFrame;
	}
	
	public JPanel getMainPane() {
		if (mainPane == null) {
			mainPane = new JPanel();			
			mainPane.setLayout(new BorderLayout());			
			mainPane.add(getLeftPane(), BorderLayout.WEST);
			enableNavigationButtons(false);
			Users users = Users.loadUsers();
			if (users.getNumUsers() == 1) {
				if (!users.getUsers().getFirst().isAuto())
					new Lock(mainPane, this);
				else {
					Login.setUser(users.getUsers().getFirst());
					Login.login(main, mainPane, new JPanel());				
				}
			}
			else
				new Lock(mainPane, this);
			
			mainPane.repaint();
		}
		return mainPane;
	}
	
	private JPanel getLeftPane() {
		if (leftPane == null) {			
			leftPane = new JPanel();
			leftPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			leftPane.setLayout(null);
			leftPane.setPreferredSize(new Dimension(120, 431));				
			leftPane.add(getHomeBtn());
			leftPane.add(getTransBtn());
			leftPane.add(getStatBtn());
			leftPane.add(getLogoutBtn());
			leftPane.add(getExitBtn());
		}
		return leftPane;
	}
	
	private JButton getHomeBtn() {
		if (homeBtn == null) {
			homeBtn = new JButton("Home");
			homeBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			homeBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			homeBtn.setHorizontalTextPosition(SwingConstants.CENTER);
			homeBtn.setIcon(new ImageIcon(getClass().getResource("/icons/brief48.png")));
			homeBtn.setBounds(10, 10, 100, 80);
			homeBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {					
					if (mainPane.getComponentCount() > 1)
						mainPane.remove(mainPane.getComponent(1));
					toggleNaviButtons(false, true, true);
					new Home(mainPane, main);
					mainPane.repaint();
				}
			});
		}
		return homeBtn;
	}
	
	private JButton getTransBtn() {
		if (transBtn == null) {
			transBtn = new JButton("Payments");
			transBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			transBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			transBtn.setHorizontalTextPosition(SwingConstants.CENTER);
			transBtn.setIcon(new ImageIcon(getClass().getResource("/icons/coin48.png")));
			transBtn.setBounds(10, 95, 100, 80);
			transBtn.setEnabled(false);
			transBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (mainPane.getComponentCount() > 1)
						mainPane.remove(mainPane.getComponent(1));
					toggleNaviButtons(true, false, true);
					new Management(mainPane);
					mainPane.repaint();
				}
			});
		}
		return transBtn;
	}
	
	private JButton getStatBtn() {
		if (statBtn == null) {
			statBtn = new JButton("Summary");
			statBtn.setIcon(new ImageIcon(getClass().getResource("/icons/stats48.png")));
			statBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			statBtn.setHorizontalTextPosition(SwingConstants.CENTER);
			statBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			statBtn.setEnabled(false);
			statBtn.setBounds(10, 180, 100, 80);
			statBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (mainPane.getComponentCount() > 1)
						mainPane.remove(mainPane.getComponent(1));
					toggleNaviButtons(true, true, false);
					new Statitics(mainPane);
					mainPane.repaint();
				}
			});
		}
		return statBtn;
	}
	
	private JButton getLogoutBtn() {
		if (logoutBtn == null) {
			logoutBtn = new JButton("Logout");
			logoutBtn.setIcon(new ImageIcon(getClass().getResource("/icons/logout16.png")));
			logoutBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Login.logout(main, mainPane, (JPanel)mainPane.getComponent(1));
				}
			});
			logoutBtn.setToolTipText("Logout");
			logoutBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			logoutBtn.setBounds(10, 280, 100, 32);
		}
		return logoutBtn;
	}
	
	public void enableNavigationButtons(boolean flag) {
		this.homeBtn.setEnabled(flag);
		this.transBtn.setEnabled(flag);
		this.statBtn.setEnabled(flag);
		this.logoutBtn.setEnabled(flag);
		this.editMenu.setEnabled(flag);
	}
	
	public void enableLogButtons(boolean flag) {
		this.statBtn.setEnabled(flag);
		this.transBtn.setEnabled(flag);
	}
	
	private JButton getExitBtn() {
		if (exitBtn == null) {
			exitBtn = new JButton("Exit");			
			exitBtn.setToolTipText("Exit from UBill");
			exitBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			exitBtn.setIcon(new ImageIcon(getClass().getResource("/icons/exit16.png")));
			exitBtn.setBounds(10, 390, 100, 32);
			exitBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int flag = confirm("Are you sure to exit from UBill?");
					if (flag == 0) {
						getMainFrame().dispose();
						splash.dispose();
					}
				}
			});
		}
		return exitBtn;
	}
	
	private JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();			
			menuBar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			
			fileMenu = new JMenu("File");
			fileMenu.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			editMenu = new JMenu("Edit");
			editMenu.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			helpMenu = new JMenu("?");
			helpMenu.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			
			JMenuItem exitMenuItem = new JMenuItem("Exit");			
			exitMenuItem.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			exitMenuItem.setIcon(new ImageIcon(getClass().getResource("/icons/exit16.png")));
			JMenuItem profileMenuItem = new JMenuItem("Profile");
			profileMenuItem.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			profileMenuItem.setIcon(new ImageIcon(getClass().getResource("/icons/user16.png")));
			JMenuItem categoryMenuItem = new JMenuItem("Labels");
			categoryMenuItem.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			categoryMenuItem.setIcon(new ImageIcon(getClass().getResource("/icons/category16.png")));
			JMenuItem homeMenuItem = new JMenuItem("Home Page");
			homeMenuItem.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			homeMenuItem.setIcon(new ImageIcon(getClass().getResource("/icons/internet16.png")));
			JMenuItem donationMenuItem = new JMenuItem("Donation");
			donationMenuItem.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			donationMenuItem.setIcon(new ImageIcon(getClass().getResource("/icons/paypal16.png")));
			JMenuItem aboutMenuItem = new JMenuItem("Credits");
			aboutMenuItem.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			aboutMenuItem.setIcon(new ImageIcon(getClass().getResource("/icons/info16.png")));
			
			menuBar.add(fileMenu);
			menuBar.add(editMenu);
			menuBar.add(helpMenu);
			fileMenu.add(exitMenuItem);
			editMenu.add(profileMenuItem);
			editMenu.add(categoryMenuItem);
			helpMenu.add(homeMenuItem);
			helpMenu.add(donationMenuItem);
			helpMenu.add(aboutMenuItem);
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int flag = confirm("Are you sure to exit from UBill?");
					if (flag == 0) {
						getMainFrame().dispose();
						splash.dispose();
					}
				}
			});
			profileMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ModifyProfile(Login.getUser());
				}
			});
			categoryMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ModifyLabels(Login.getUser());
				}
			});
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return menuBar;
	}
	
	public void toggleNaviButtons(boolean home, boolean manage, boolean stats) {		
		homeBtn.setEnabled(home);		
		transBtn.setEnabled(manage);
		statBtn.setEnabled(stats);
	}
}
