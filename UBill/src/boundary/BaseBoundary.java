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

import executor.Languages;

public abstract class BaseBoundary {
	
	protected String[] options = {"Yes", "No"};
	protected String abortTitle = "Abort";
	protected String okTitle = "Success";
	protected String warningTitle = "Warning";
	protected String failTitle = "Error";
	protected String confirmTitle = "Confirm";
	protected String phrase = "Unkle Bill says";
	
	public int abort(String message) {
		return JOptionPane.showOptionDialog(new JInternalFrame(), "<html><b>"+phrase+":</b><br/>"+message+"</html>", abortTitle, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/icons/uWarning64.png")), options, options[0]);
	}
	
	public void ok(String message) {
		JOptionPane.showMessageDialog(new JInternalFrame(), "<html><b>"+phrase+":</b><br/>"+message+"</html>", okTitle, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/icons/uSuccess64.png")));
	};
	
	public void warning(String message) {
		JOptionPane.showMessageDialog(new JInternalFrame(), "<html><b>"+phrase+":</b><br/>"+message+"</html>", warningTitle, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/icons/uWarning64.png")));
	};
	
	public void fail(String message) {
		JOptionPane.showMessageDialog(new JInternalFrame(), "<html><b>"+phrase+":</b><br/>"+message+"</html>", failTitle, JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource("/icons/uError64.png")));
	};
	
	public int confirm(String message) {
		return JOptionPane.showOptionDialog(new JInternalFrame(), "<html><b>"+phrase+":</b><br/>"+message+"</html>", confirmTitle, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/icons/uHelp64.png")), options, options[0]);
	}
	
	public void setLanguage(Languages lang) {
		this.options[0] = lang.yes;
		this.options[1] = lang.no;
		this.phrase = lang.base_phrase;
		this.abortTitle = lang.base_abort;
		this.okTitle = lang.base_ok;
		this.failTitle = lang.base_fail;
		this.warningTitle = lang.base_warning;
		this.confirmTitle = lang.base_confirm;
	}
}
