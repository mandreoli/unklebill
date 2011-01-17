package boundary;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RenderTableBody extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	private JLabel label = new JLabel("");
	
	public JLabel getLabel() {
		return this.label;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {		
		
		String text[] = {"", "", ""}; 		
	
	    label.setHorizontalAlignment(JLabel.CENTER);
	    if (col == 0) {
	    	label.setText(((Vector)value).get(1).toString());
	    }
	    label.setText(value.toString());
	    label.setOpaque(true);	    
	    
	    if (col == 2) {
	    	text = value.toString().split(" ");	    	
	    }	    
	    
	    if (text[0].equals("Transfer")) {	    	
	    	label.setForeground(Color.gray);
	    }
	    else
	    	label.setForeground(Color.black);
	    
	    
	    if (row % 2 == 0) {
	    	label.setBackground(Color.white);
	    } else {
	    	label.setBackground(new Color(204, 204, 255));
	    }
	    
	    
	    if (hasFocus) {
	    	
	    }
	        
	    return label;
	}
	
	public void coloro() {
		setForeground(Color.red);
	}
}