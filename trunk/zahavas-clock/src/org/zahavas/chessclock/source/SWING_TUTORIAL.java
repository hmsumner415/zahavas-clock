package org.zahavas.chessclock.source;

import javax.swing.*;

import org.zahavas.chessclock.source.JNA.State;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


//import org.zahavas.chessclock.source.JNA.*;

import java.beans.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.*;






//
public class SWING_TUTORIAL 
{

	static JNA Jdemo;
	static TaskFrame Tdemo;
	//static XMLDOMSample XDS;
	 
	
	static EventLogger EV;
	static XMLUtilities XML;
	
	//EV =new EventLogger();
	
	   /**
	    * createAndShowGUI()
	    * 
        * Create and set up the frame. 
        * The string passed as an argument will be displayed 
        * the title.
        * Guidance for adding Window Listeners using WindowAdapter
        * 1)http://www.klickfamily.com/david/school/cis260/gui28.html
        * 2)Core Java Volume I Fundamentals C. Horstmann (Kindle Location: 10161)
        * 	  
        */
	   public static void createAndShowGUI() {    
		   EV =new EventLogger();
		   XML = new XMLUtilities();
           Tdemo = new TaskFrame();
           //XDS =new XMLDOMSample();
           Image img = new ImageIcon("clock.pgn").getImage();
           
		   Tdemo.setIconImage(img);
           Tdemo.setTitle("Task Tracker V 1.4");
           Tdemo.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
           Tdemo.setVisible(true);
           
           Tdemo.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent arg0) {
					if (!printFinalSummary()){
						JOptionPane.showMessageDialog(null,"Please Close the file and try again");
						}
					else {System.exit(0);} 
				}	
					
	        });



	    }


	   
	   public static void main(String[] args) {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.

		   Jdemo =new JNA();		   
		   State state = State.UNKNOWN; 
		   DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		   
	            int idleSec = Jdemo.getIdleTimeMillisWin32() / 1000;

	            State newState =
	                idleSec < 30 ? State.ONLINE :
	                idleSec > 5 * 60 ? State.AWAY : State.IDLE;

	            if (newState != state) {
	                state = newState;
	                System.out.println(dateFormat.format(new Date()) + " # " + state);
	            }
	            try { Thread.sleep(1000); 
	            
	             idleSec = JNA.getIdleTimeMillisWin32() / 1000;

	             newState =
	                idleSec < 30 ? State.ONLINE :
	                idleSec > 5 * 60 ? State.AWAY : State.IDLE;

	            if (newState != state) {
	                state = newState;
	                System.out.println(dateFormat.format(new Date()) + " # " + state);
	            }
	            
	            
	            } catch (Exception ex) {}
	        

		   
	      SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        }
	      );
	      
	    }

	
	   
	   
	   /**
	     *  printFileSummary
	     *  Generates a tab delimited text file that can be opened in Excel and saved as an xls.
	     *  	
	     */
   
	    public static boolean printFinalSummary()
	    {
	    String dbResult;
	    SQLProject  db =new SQLProject();
	    
	    GregorianCalendar d = new GregorianCalendar();
	    try{
	    	String date = Integer.toString(d.get(Calendar.MONTH)+1) + "/" + Integer.toString(d.get(Calendar.DAY_OF_MONTH)) + "/" +  Integer.toString(d.get(Calendar.YEAR));
	    	String FileNameDate = Integer.toString(d.get(Calendar.MONTH)+1)
	    			+ Integer.toString(d.get(Calendar.DAY_OF_MONTH)) 
	    			+ Integer.toString(d.get(Calendar.YEAR))
	    			+ Integer.toString(d.get(Calendar.HOUR_OF_DAY))
	    			+ Integer.toString(d.get(Calendar.MINUTE)) 	;
	    	

	       	File file2 =new File(FileNameDate+"Summary.txt");
	       	
	   	 
    		//if file doesn't exists, then create it
    		if(!file2.exists()){
    			file2.createNewFile();
    		}
    		
    	    
    	    PrintWriter outputStream = null;
    	    outputStream = new PrintWriter(file2.getName());
    	    
    	    for(TaskTime TT :Tdemo.lTaskTime){
         		 
    	    	 outputStream.write(TT.getClientName());
    	    	 outputStream.print("\t");
    	    	 outputStream.write(TT.getProjectName());
    	    	 outputStream.print("\t");
      	         outputStream.write(date + "\t" +TT.getTaskName() +"\t" + TT.convertToHourMinSec() + "\t");
      	            	        
      	        outputStream.print(TT.getHours());
      	        outputStream.print("\t");
      	        outputStream.print(TT.getMinutes());
      	        outputStream.print("\t");
      	        outputStream.println(TT.getSeconds());
      	        
      	        
      	      /**
      	  	 * Method: InsertTaskEvent
      	  	 * @param txtTaskDate
      	  	 * @param txtClientShortName
      	  	 * @param txtProjectNAME
      	  	 * @param txtTaskHour
      	  	 * @param txtTaskMinute
      	  	 * @param txtTaskSecond
      	  	 * @param txtTask
      	  	 * @return
      	  	 */
      	      dbResult = db.InsertTaskEvent(date, TT.getClientName(), 
      	    		  TT.getProjectName(),
      	    		  TT.getHours(),
      	    		  TT.getMinutes(),
      	    		  TT.getSeconds(),
      	    		  TT.getTaskName());
      	        
      	        
  			}

    	    outputStream.close();
      	    return true;    
	    }catch(IOException e){
	    	
    		//e.printStackTrace();
    		JOptionPane.showMessageDialog(null,e.getMessage());
    		return false;
    	}
   	    
	    }
}
