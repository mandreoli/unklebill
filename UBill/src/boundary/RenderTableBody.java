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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
	private Color normal = new Color(0, 0, 0);
	private Color passive = new Color(128, 0, 0);
	private Color highlightDark = new Color(255, 255, 0);
	private Color highlightLight = new Color(255, 255, 156);
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
	    label.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
	    label.setText(value.toString());
	    label.setOpaque(true);
	    
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
		else {			
			switch(col) {
				case 0: label.setForeground(normal);
						break;
				case 1: label.setForeground(active);
						break;
				case 2: label.setForeground(passive);
						break;
				case 3: label.setForeground(neutro);
						break;
				case 4: label.setForeground(neutro);
						break;
				case 5: label.setFont(new Font("Lucida Grande", Font.BOLD, 11));
						String[] text = value.toString().split(" ");
						double num = 0.0;
						try {
							num = Double.parseDouble(text[0]);
						}
						catch(NumberFormatException nfe) {
							System.err.println("Number format exception "+nfe);
						}
						
						if (num > 0.0)
							label.setForeground(active);
						else if (num < 0.0)
							label.setForeground(passive);
						else
							label.setForeground(neutro);
						
						if (row % 2 == 0) {
						   	label.setBackground(highlightLight);
						} else {
							label.setBackground(highlightDark);
						}
						
						break;
				default: label.setForeground(normal);
			}
			if (isRow(row)) {
				label.setBackground(highlightDark);				
				label.setFont(new Font("Lucida Grande", Font.BOLD, 11));
			}
		}		
	        
	    return label;
	}
}