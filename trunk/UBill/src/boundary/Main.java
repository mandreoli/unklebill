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
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Main extends BaseBoundary {
	
	private int wWidth = 600;
	private int wHeight = 480;
	private SplashScreen splash = null;
	private JFrame mainFrame = null;
	private JPanel mainPane = null;
	private JPanel leftPane = null;
	private JButton homeBtn = null;
	private JButton transBtn = null;
	private JButton exitBtn;
	private JMenuBar menuBar;
	
	
	public Main(SplashScreen splash) {
		this.splash = splash;
	}
	
	public void START() {
		getMainFrame();
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private JFrame getMainFrame() {
		if (mainFrame == null) {
			mainFrame = new JFrame();
			mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icons/favico.png")));
			mainFrame.setTitle("UnkleBill v1.0");
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
			mainFrame.setVisible(true);
		}
		return mainFrame;
	}
	
	private JPanel getMainPane() {
		if (mainPane == null) {
			mainPane = new JPanel();
			//mainPane.setSize(new Dimension(this.wWidth, this.wHeight));
			mainPane.setLayout(new BorderLayout());
			mainPane.add(getLeftPane(), BorderLayout.WEST);	
			//new Home(mainPane);
		}
		return mainPane;
	}
	
	private JPanel getLeftPane() {
		if (leftPane == null) {			
			leftPane = new JPanel();
			leftPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			leftPane.setPreferredSize(new Dimension(120, 431));			
			leftPane.setLayout(null);			
			leftPane.add(getHomeBtn());
			leftPane.add(getTransBtn());
			leftPane.add(getExitBtn());
		}
		return leftPane;
	}
	
	private JButton getHomeBtn() {
		if (homeBtn == null) {
			homeBtn = new JButton("Home");
			homeBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			homeBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			homeBtn.setHorizontalTextPosition(SwingConstants.CENTER);
			homeBtn.setIcon(new ImageIcon(Main.class.getResource("/icons/briefcase48.png")));
			homeBtn.setBounds(10, 10, 100, 80);
			homeBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//mainPane.remove(mainPane.getComponent(1));					
					JPanel p = new JPanel();
					JButton b = new JButton("center");
					p.setLocation(new Point(120, 0));
					p.setSize(new Dimension(480, 432));
					p.setLayout(new BorderLayout());
					p.add(b, BorderLayout.NORTH);
					mainPane.add(p, BorderLayout.CENTER);
					mainPane.repaint();
				}
			});
		}
		return homeBtn;
	}
	
	private JButton getTransBtn() {
		if (transBtn == null) {
			transBtn = new JButton("Transactions");
			transBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			transBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			transBtn.setHorizontalTextPosition(SwingConstants.CENTER);
			transBtn.setIcon(new ImageIcon(Main.class.getResource("/icons/wallet48.png")));
			transBtn.setBounds(10, 102, 100, 80);
			transBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mainPane.remove(mainPane.getComponent(1));
					//new Management(mainPane);
					mainPane.repaint();
				}
			});
		}
		return transBtn;
	}
	
	private JButton getExitBtn() {
		if (exitBtn == null) {
			exitBtn = new JButton("Exit");			
			exitBtn.setToolTipText("Exit from UBill");
			exitBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			exitBtn.setIcon(new ImageIcon(Main.class.getResource("/icons/exit16.png")));
			exitBtn.setBounds(10, 385, 100, 32);
			exitBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
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
			JMenu fileMenu = new JMenu("File");
			JMenu helpMenu = new JMenu("?");
			JMenuItem exitMenuItem = new JMenuItem("Exit");			
			exitMenuItem.setIcon(new ImageIcon(Main.class.getResource("/icons/exit16.png")));
			JMenuItem aboutMenuItem = new JMenuItem("About");
			menuBar.add(fileMenu);
			menuBar.add(helpMenu);
			fileMenu.add(exitMenuItem);
			helpMenu.add(aboutMenuItem);
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int flag = confirm("Are you sure to exit from UBill?");
					if (flag == 0) {
						getMainFrame().dispose();
						splash.dispose();
					}
				}
			});
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		}
		return menuBar;
	}
}
