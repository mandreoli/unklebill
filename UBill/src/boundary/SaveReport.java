package boundary;

import executor.CreateReport;
import executor.ExtFilter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

public class SaveReport extends BaseBoundary {

	private int wWidth = 480;
	private int wHeight = 320;
	private JDialog mainDialog = null;
	private JPanel mainPane = null;
	private JPanel selectPane = null;
	private File file = null;
	private String month = null;
	private String year = null;
	private JFileChooser fileChooser;
	
	public SaveReport(File file, String month, String year) {
		this.file = file;
		this.month = month;
		this.year = year;
		getMainDialog().setVisible(true);
	}
	
	public SaveReport(File file, String year) {
		this.file = file;
		this.month = "";
		this.year = year;
		getMainDialog().setVisible(true);
	}
	
	public JDialog getMainDialog() {
		if (mainDialog == null) {
			mainDialog = new JDialog();
			mainDialog.setModal(true);
			mainDialog.setResizable(false);
			mainDialog.setTitle("Create "+month+" "+year+" report");
			mainDialog.setContentPane(getMainPane());
			mainDialog.setSize(new Dimension(wWidth, wHeight));
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			mainDialog.setLocation(new Point((d.width-wWidth)/2, (d.height-wHeight)/2));
		}
		return mainDialog;
	}
	
	private JPanel getMainPane() {
		if (mainPane == null) {
			mainPane = new JPanel();
			mainPane.setLayout(new BorderLayout());			
			mainPane.add(getSelectPane(), BorderLayout.CENTER);
		}
		return mainPane;
	}
	
	private JPanel getSelectPane() {
		if (selectPane == null) {			
			selectPane = new JPanel();
			selectPane.setLayout(new BorderLayout());
			selectPane.setPreferredSize(new Dimension(140, 100));			
			selectPane.setSize(new Dimension(458, 400));		  	
			selectPane.add(getFileChooser(), BorderLayout.CENTER);		    
		}
		return selectPane;
	}
	
	private JFileChooser getFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser();			
		    fileChooser.setAcceptAllFileFilterUsed(false);
		    FileFilter filter = new ExtFilter("PDF file", "pdf");
		    fileChooser.setFileFilter(filter);
			fileChooser.setSelectedFile(new File(file.getName()));
			fileChooser.setControlButtonsAreShown(true);		
			fileChooser.setApproveButtonText("Save");
			fileChooser.setApproveButtonToolTipText("Save report");
			fileChooser.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
				        JFileChooser theFileChooser = (JFileChooser) actionEvent.getSource();
				        String command = actionEvent.getActionCommand();
				        
				        if (command.equals(JFileChooser.APPROVE_SELECTION)) {
				        		File f = theFileChooser.getSelectedFile();				        		
				        		try {
				        			new CreateReport(f.getCanonicalPath(), month, year);
				        			ok("Report created with success!");
									mainDialog.dispose();
								} catch (IOException e) {
									System.err.println("Error while getting canonical path: "+e);
								}
				        }
				        else if (command.equals(JFileChooser.CANCEL_SELECTION)){
				        	mainDialog.dispose();
				        }			        
			      }
			});
		}
		return fileChooser;
	}
}
