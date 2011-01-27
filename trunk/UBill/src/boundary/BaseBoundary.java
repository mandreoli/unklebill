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

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public abstract class BaseBoundary {
	
	private String[] options = {"Yes", "No"};
	
	public int abort(String message) {
		return JOptionPane.showOptionDialog(new JInternalFrame(), "<html><b>Unkle Bill says:</b><br/>"+message+"</html>", "Abort", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/icons/uWarning64.png")), options, options[0]);
	}
	
	public void ok(String message) {
		JOptionPane.showMessageDialog(new JInternalFrame(), "<html><b>Unkle Bill says:</b><br/>"+message+"</html>", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/icons/uSuccess64.png")));
	};
	
	public void warning(String message) {
		JOptionPane.showMessageDialog(new JInternalFrame(), "<html><b>Unkle Bill says:</b><br/>"+message+"</html>", "Warning", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/icons/uWarning64.png")));
	};
	
	public void fail(String message) {
		JOptionPane.showMessageDialog(new JInternalFrame(), "<html><b>Unkle Bill says:</b><br/>"+message+"</html>", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource("/icons/uError64.png")));
	};
	
	public int confirm(String message) {
		return JOptionPane.showOptionDialog(new JInternalFrame(), "<html><b>Unkle Bill says:</b><br/>"+message+"</html>", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/icons/uHelp64.png")), options, options[0]);
	}
}
