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
