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

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import javax.swing.JTextArea;
import java.awt.Font;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class About {

	private int wWidth = 320;
	private int wHeight = 240;
	private JDialog mainDialog = null;  //  @jve:decl-index=0:visual-constraint="339,109"
	private JPanel mainPane = null;
	private JScrollPane scroll = null;
	private JTextArea textArea = null;
	private JLabel infoLabel = null;
	public About() {
		getMainDialog().setVisible(true);
	}
	
	/**
	 * This method initializes mainDialog	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private JDialog getMainDialog() {
		if (mainDialog == null) {
			mainDialog = new JDialog();
			mainDialog.setSize(new Dimension(wWidth, wHeight));			
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			mainDialog.setLocation(new Point((d.width-wWidth)/2, (d.height-wHeight)/2));
			mainDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			mainDialog.setResizable(false);
			mainDialog.setModal(true);
			mainDialog.setTitle("About this program");
			mainDialog.setContentPane(getMainPane());
		}
		return mainDialog;
	}

	/**
	 * This method initializes mainPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMainPane() {
		if (mainPane == null) {
			infoLabel = new JLabel();
			infoLabel.setBounds(new Rectangle(10, 20, 301, 16));
			infoLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			infoLabel.setText("<html><a href=\"http://www.drelvan.altervista.org/ubill/\">Copyright © 2011 Michele Andreoli</a><html>");
			infoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseReleased(java.awt.event.MouseEvent e) {
					try {
						open(new URI("http://www.drelvan.altervista.org/ubill/"));
					} catch (URISyntaxException e1) {
						System.err.println("Url syntax error "+e1);
					}
				}
			});
			mainPane = new JPanel();
			mainPane.setLayout(null);
			mainPane.add(getScroll(), null);
			mainPane.add(infoLabel, null);
		}
		return mainPane;
	}
	
	private void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
        	Desktop desktop = Desktop.getDesktop();
            try {
            	desktop.browse(uri);
            }
            catch (IOException e) {
            }
        }
    }

	/**
	 * This method initializes scroll	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScroll() {
		if (scroll == null) {
			scroll = new JScrollPane();
			scroll.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			scroll.setBounds(new Rectangle(10, 45, 301, 160));
			scroll.setViewportView(getTextArea());
			scroll.setViewportView(getTextArea());
		}
		return scroll;
	}

	/**
	 * This method initializes textArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setWrapStyleWord(true);
			textArea.setLineWrap(true);
			textArea.setEditable(false);
			textArea.setFont(new Font("Courier", Font.PLAIN, 10));
			textArea.setText("UnkleBill is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.\n\nUnkleBill is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.\n\nYou should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.");
		}
		return textArea;
	}
}
