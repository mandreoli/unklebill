package boundary;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RenderTableBody extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	private Color light = new Color(244, 244, 255);
	private Color dark = new Color(224, 224, 255);
	private Color selected = new Color(194, 194, 255);
	private Color active = new Color(0, 128, 0);
	private Color neutro = new Color(120, 120, 120);
	private Color passive = new Color(128, 0, 0);
	private int flag = 0;
	private int row = -1;
	
	public RenderTableBody(int flag) {
		this.flag = flag;
	}
	
	public RenderTableBody(int flag, int row) {
		this.flag = flag;
		this.row = row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel label = new JLabel("");
	    label.setHorizontalAlignment(JLabel.LEFT);
	    label.setText(value.toString());
	    label.setOpaque(true);
	    
		if (this.flag == 0) {
		   	if (this.row == row)
		   		label.setForeground(neutro);
		   	else	
		   		label.setForeground(active);
		}
		else {
		   	if (this.row == row)
		   		label.setForeground(neutro);
		   	else	
		   		label.setForeground(passive);
		}
		    
		if (row % 2 == 0) {
		   	label.setBackground(light);
		} else {
			label.setBackground(dark);
		}
		    
		Color c = label.getBackground();
		if (isSelected) {
		   	label.setBackground(selected);
		} else {
		   	label.setBackground(c);
		}
	        
	    return label;
	}
}