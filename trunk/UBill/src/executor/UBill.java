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

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import boundary.SplashScreen;

public class UBill {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {		
		
		// initialize logging to go to rolling log file
        LogManager logManager = LogManager.getLogManager();
        logManager.reset();

        // log file max size 10K, 3 rolling files, append-on-open
        Handler fileHandler = null;
		try {
			fileHandler = new FileHandler(System.getProperty("user.home")+"/.UBill/log.txt", 10000, 3, true);
		} catch (SecurityException e1) {
			System.err.println("Security exception: "+e1);
		} catch (IOException e1) {
			System.err.println("I/O exception: "+e1);
		}
        fileHandler.setFormatter(new SimpleFormatter());
        Logger.getLogger("").addHandler(fileHandler);        

		// preserve old stdout/stderr streams in case they might be useful
		@SuppressWarnings("unused")
		PrintStream stdout = System.out;
		@SuppressWarnings("unused")
		PrintStream stderr = System.err;
		
		// now rebind stdout/stderr to logger
		Logger logger;
		LoggingOutputStream los;
	
		logger = Logger.getLogger("stdout");
		los = new LoggingOutputStream(logger, StdOutErrLevel.STDOUT);
		System.setOut(new PrintStream(los, true));
		
		logger = Logger.getLogger("stderr");
		los = new LoggingOutputStream(logger, StdOutErrLevel.STDERR);
		System.setErr(new PrintStream(los, true));
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        	UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }
		new SplashScreen();
	}

}
