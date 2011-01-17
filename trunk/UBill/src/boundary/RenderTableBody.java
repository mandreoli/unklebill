package boundary;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RenderTableBody extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel label = new JLabel("");
	    label.setHorizontalAlignment(JLabel.CENTER);
	    label.setText(value.toString());
	    label.setOpaque(true);
	    label.setForeground(Color.black);
	    
	    if (row % 2 == 0) {
	    	label.setBackground(Color.white);
	    } else {
	    	label.setBackground(new Color(204, 204, 255));
	    }
	        
	    return label;
	}
}