package boundary;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public abstract class BaseBoundary {
	
	private String[] options = {"Yes", "No"};
	
	public int abort(String message) {
		return JOptionPane.showOptionDialog(new JInternalFrame(), "<html><b>Unkle Bill says:</b><br/>"+message+"</html>", "Abort", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/icons/used/uWarning64.png")), options, options[0]);
	}
	
	public void ok(String message) {
		JOptionPane.showMessageDialog(new JInternalFrame(), "<html><b>Unkle Bill says:</b><br/>"+message+"</html>", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/icons/used/uSuccess64.png")));
	};
	
	public void fail(String message) {
		JOptionPane.showMessageDialog(new JInternalFrame(), "<html><b>Unkle Bill says:</b><br/>"+message+"</html>", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource("/icons/used/uError64.png")));
	};
	
	public int confirm(String message) {
		return JOptionPane.showOptionDialog(new JInternalFrame(), "<html><b>Unkle Bill says:</b><br/>"+message+"</html>", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/icons/used/uHelp64.png")), options, options[0]);
	}
}
