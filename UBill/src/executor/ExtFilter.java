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

package executor;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ExtFilter extends FileFilter{
	  String description;
	  String extensions[];

	  public ExtFilter(String description, String extension) {
	    this(description, new String[] { extension });
	  }

	  public ExtFilter(String description, String extensions[]) {
	    if (description == null) {
	      this.description = extensions[0];
	    } else {
	      this.description = description;
	    }
	    this.extensions = (String[]) extensions.clone();
	    toLower(this.extensions);
	  }

	  private void toLower(String array[]) {
	    for (int i = 0, n = array.length; i < n; i++) {
	      array[i] = array[i].toLowerCase();
	    }
	  }

	  public String getDescription() {
	    return description;
	  }

	  public boolean accept(File f) {
	    if (f.isDirectory()) {
	      return true;
	    } else {
	      String path = f.getAbsolutePath().toLowerCase();
	      for (int i = 0, n = extensions.length; i < n; i++) {
	        String extension = extensions[i];
	        if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
	          return true;
	        }
	      }
	    }
	    return false;
	  }
}
