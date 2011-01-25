package boundary;

import java.awt.Color;
import java.awt.Component;
import java.util.LinkedList;

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
	private LinkedList<Integer> row = new LinkedList<Integer>();
	
	public RenderTableBody(int flag) {
		this.flag = flag;
	}
	
	public RenderTableBody() {
		this.flag = -1;
	}
	
	public void setRow(int row) {
		this.row.add(row);
	}
	
	public void resetRows() {
		this.row.clear();
	}
	
	private boolean isRow(int row) {
		for (int n : this.row) {
			if (n == row)
				return true;
		}
		return false;
	}
	
	public void delRow(int row) {
		for (int i = 0; i < this.row.size(); i++) {
				if (this.row.get(i) == row)
					this.row.remove(i);
		}
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel label = new JLabel("");
	    label.setHorizontalAlignment(JLabel.LEFT);
	    label.setText(value.toString());
	    label.setOpaque(true);
	    
		if (this.flag == 0) {
		   	if (isRow(row))
		   		label.setForeground(neutro);
		   	else	
		   		label.setForeground(active);
		}
		else if (this.flag == 1){
		   	if (isRow(row))
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