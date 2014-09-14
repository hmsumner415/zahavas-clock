package org.zahavas.chessclock.source;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.*;

public class EventLogger {
    
	private static FileHandler fh = null;
	private static final Logger mylogger = Logger.getLogger("org.zahavas.chessclock");
	final int LOG_ROTATION_COUNT = 10;
	private boolean APPENDFLAG = true;
	private int FILESIZE = 0;
	
	public EventLogger ()
	{
		
		try {
			 fh=new FileHandler("loggerExample.log", false);
			 fh.setFormatter(new SimpleFormatter());
			 mylogger.addHandler(fh);
			 mylogger.setLevel(Level.CONFIG);
			 Handler handler = new FileHandler("%h/myapp.log", FILESIZE , LOG_ROTATION_COUNT, APPENDFLAG);
			 mylogger.getLogger("org.zahavas.chessclock").addHandler(handler);
			 } 
		catch (IOException e) {
			 e.printStackTrace();
			 mylogger.log(Level.SEVERE, "Can't create log file handler", e);
			 }
			 
		mylogger.info("Constructed Logger");
 
		
		
	 }
		
	public void LogEvent(String msg, String Severity )	
	{
		
		if (Severity.equals("ALL")){
		mylogger.log(Level.ALL, msg);
		}
		if (Severity.equals("CONFIG")){
		mylogger.log(Level.CONFIG, msg);
		}
		if (Severity.equals("INFO")){
			mylogger.log(Level.INFO, msg);
		}
		if (Severity.equals("FINE")){		 
		mylogger.log(Level.FINE, msg);
		}
		if (Severity.equals("FINER")){
		mylogger.log(Level.FINER, msg);
		}
		if (Severity.equals("FINEST")){
		mylogger.log(Level.FINEST, msg);
		}
		if (Severity.equals("SEVERE")){
		mylogger.log(Level.SEVERE, msg);
		}
		if (Severity.equals("WARNING")){
		mylogger.log(Level.WARNING, msg);
		}
		
		 
		
		
		
		
	}	
	
	
}
