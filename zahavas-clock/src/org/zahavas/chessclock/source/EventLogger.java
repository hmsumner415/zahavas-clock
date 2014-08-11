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
	
	public EventLogger ()
	{
		
		try {
			 fh=new FileHandler("loggerExample.log", false);
			 fh.setFormatter(new SimpleFormatter());
			 mylogger.addHandler(fh);
			 mylogger.setLevel(Level.CONFIG);
			 Handler handler = new FileHandler("%h/myapp.log", 0 , LOG_ROTATION_COUNT);
			 mylogger.getLogger("org.zahavas.chessclock").addHandler(handler);
			 } 
		catch (IOException e) {
			 e.printStackTrace();
			 }
			 
		mylogger.severe("Construct Logger");
 
		
		
	 }
		
	public void LogEvent(String msg, String Severity )	
	{
		
		
		mylogger.log(Level.INFO, msg);
	
		
	}	
	
	
}
