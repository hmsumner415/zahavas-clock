package org.zahavas.chessclock.source;

import javax.swing.*;
import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;

//
public class SWING_TUTORIAL  implements ActionListener {

	int hourCounter = 0;
	int minuteCounter = 0;	
	int secondCounter = 0;
	int ahourCounter = 0;
	int aminuteCounter = 0;	
	int asecondCounter = 0;
	int sitCounter = 0, standCounter =0;   
	JPanel scorePanel, buttonPanel, textPanel,summaryPanel;
	JButton ExitButton, ToggleButton;
	JComboBox taskChooser;
	JLabel workingScore, awayScore, workingLabel, awayLabel, timeLabel, workingLabel2;
	JTextArea taskSummary;
	private TaskTime T;
	private List<TaskTime> l = new ArrayList<TaskTime>();
	private String CurrentTask, SelectedTask;


	boolean bStand = true, bSit = false, taskfound = false ;
	
	static SWING_TUTORIAL demo ;
	
	public SWING_TUTORIAL ()
	{
		
		System.out.println("Swing Tutorial started.");
		
	}
	
	 /**
     * Method: createContentPane
     * Generates the main UI page 
     */
	public JPanel createContentPane(){
		   
	       
	        JPanel totalGUI = new JPanel();
	        
	        // We set the Layout Manager to null so we can manually place
	        // the Panels.
	        //totalGUI.setLayout(null);
	        totalGUI.setVisible(true);
	        //  Position the Task Labels
	        textPanel = new JPanel();
	        //textPanel.setLayout(null);
	        textPanel.setLocation(10, 5);
	        textPanel.setSize(250, 30);
	        totalGUI.add(textPanel);

	        workingLabel = new JLabel("Working");
	        workingLabel.setLocation(0, 0);
	        workingLabel.setSize(80, 40);
	        workingLabel.setHorizontalAlignment(0);
	        textPanel.add(workingLabel);

        
	        // Creation of a Panel to contain the Total Time labels.
	        scorePanel = new JPanel();
	        //scorePanel.setLayout(null);
	        scorePanel.setLocation(10, 40);
	        scorePanel.setSize(250, 30);
	        totalGUI.add(scorePanel);

	        workingScore = new JLabel("0");
	        workingScore.setLocation(0, 0);
	        workingScore.setSize(100, 30);
	        workingScore.setHorizontalAlignment(0);
	        workingScore.setForeground(Color.red);
	        workingScore.setText("j");
	        scorePanel.add(workingScore);

	        awayScore = new JLabel("0");
	        awayScore.setLocation(120, 0);
	        awayScore.setSize(100, 30);
	        awayScore.setHorizontalAlignment(0);
	        awayScore.setForeground(Color.blue);
	        scorePanel.add(awayScore);
	        


	        
	        // Creation of a label to contain all the JButtons.
	        JPanel buttonPanel = new JPanel();
	        //buttonPanel.setLayout(null);
	        //buttonPanel.setLocation(10, 50);
	        buttonPanel.setSize(250, 180);  
	        totalGUI.add(buttonPanel);
	        
	        ToggleButton = new JButton("Toggle");
	        ToggleButton.setLocation(0, 30);
	        ToggleButton.setSize(100, 30);
	        ToggleButton.addActionListener(this);
	        buttonPanel.add(ToggleButton);
	        
	        timeLabel = new JLabel("Current Time");
	        timeLabel.setLocation(10,60);
	        timeLabel.setSize(250,40);
	        timeLabel.setHorizontalAlignment(0);
	        buttonPanel.add(timeLabel);
	        

	        String tasks[] = {"Idle", "DB Work:GDM", "DB Work:GP", "DB Work:Other", "DB Work:TPAT", "Taxes","Torah", "Investment Research", "Financial Management",  "Java Training"};
	        
	        taskChooser = new JComboBox(tasks);
	        taskChooser.setSelectedIndex(0);
	        taskChooser.addActionListener(this);
	        taskChooser.setLocation(0, 100);
	        taskChooser.setSize(150, 30);
	        buttonPanel.add(taskChooser);
	        
	        String story = "";
        
	        summaryPanel = new JPanel();
	        //summaryPanel.setLayout(null);
	       // summaryPanel.setLocation(1, 350);
	        summaryPanel.setSize(850, 230);  
	        totalGUI.add(summaryPanel);
	        
  
	        taskSummary = new JTextArea(story, 10,30);
	        taskSummary.setLineWrap(true);
	        taskSummary.setWrapStyleWord(true);
	        taskSummary.setText("No Tasks Yet");
	        //taskSummary.setLocation(100, 0);
	        summaryPanel.add(taskSummary);
	        
	        
	        

	        
	        // Finally we return the JPanel.
	        totalGUI.setOpaque(true);
	        return totalGUI;
	    }
	
	   public void actionPerformed(ActionEvent e) {
		     System.out.println("GUI Action");

		   	       	        
		        if (e.getSource() == taskChooser)
		         {
		        	 System.out.println(taskChooser.getSelectedItem());
		        	 SelectedTask = (String) taskChooser.getSelectedItem();
		        	 CurrentTask = demo.workingLabel.getText();
		        	 if (!(CurrentTask.equals(SelectedTask) ))
		        	 { // Different task.  Set end time of CurrentTask.  Set start time of new task
		        		 	taskfound = false;
				         	for(TaskTime TT :demo.l){
				         						         			
				         		if (TT.getTaskName() == SelectedTask) 
				         			{taskfound = true;
				         			demo.taskSummary.setText(printSummary());
				         			TT.setIsActive(true);
				         			}
				         		else
				         		{
				         			TT.setIsActive(false);
				         		}
				         	}	
				         	if ( !taskfound) {
				         		for(TaskTime TT :demo.l){
				         			TT.setIsActive(false);
					         		}
				         		l.add(demo.T = new TaskTime(SelectedTask, demo.sitCounter)); 
				         		taskSummary.setText(printSummary());
				         		taskfound = true;
				         	}
				         	
		        	 }
				         	
		        	 // Set new Task
				     demo.workingLabel.setText(SelectedTask);
				     printObj(); 
				 
		         }
		        
		        
		        
		        else if(e.getSource() == ToggleButton)
		        {
		         if (bSit == true)	// Toggle to Active
		         	{bStand = true; 
		         	bSit = false;
		         	ToggleButton.setText("Set Active");
		         	}
		         else if (bStand == true)   // toggle to idle
		         {
		        	bStand = false;
		         	bSit = true;
		         	ToggleButton.setText("Set to Idle");
		         	printTime("Work Break", demo.hourCounter, demo.minuteCounter, demo.secondCounter);
		         	System.out.println("Idle time started");
		         }
		         	       	 
		        }
			
		}  
	   
	   
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
	       
		    JFrame.setDefaultLookAndFeelDecorated(true);
	        JFrame frame = new JFrame("Task Clock");
	        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	        frame.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent arg0) {
					if (!printFinalSummary()){
						JOptionPane.showMessageDialog(null,"Please Close the file and try again");
						}
					else {System.exit(0);} 
				}	
					
	        });

	       //SWING_TUTORIAL demo = new SWING_TUTORIAL(); 
	        demo = new SWING_TUTORIAL();
	        frame.setContentPane(demo.createContentPane());
	        frame.setSize(450,450);
	        frame.setVisible(true);
	        
	        //Display the window.
	        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    }

	   public static void main(String[] args) {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.

		   
		  SQLProject  db =new SQLProject();
  
		  
		  db.SQLLiteExecStatement("INSERT INTO TASKSUMMARY (TASKDATE,CLIENTNAME,TASKNAME,TASKHOUR,TASKMINUTE, TASKSECOND,NAME) VALUES ( '3/23/2014', 'DB', 'DB OTHER', 0,30,2,'a name' );");
		  db.SelectFromTASKSUMMARY(); 
		   
	      SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	               
	                
	            }
	           
	        }
	      
		   
	      
	      
	      
	      
	      );
	      
       
        	 ActionListener listener = new ActionListener(){
        		 
        		 public void actionPerformed(ActionEvent e){
        			int min= 0; 

        	  	    Calendar cal = new GregorianCalendar();
        	  	    demo.timeLabel.setText(" " + cal.getTime());
        	  		        	  		
        	  		if (demo.bSit == true) { 
        	  			demo.sitCounter = demo.sitCounter + 1;
        	  			demo.hourCounter = demo.sitCounter /(60*60);
        	  			min = demo.sitCounter - demo.hourCounter * 60*60;
        	  			demo.minuteCounter = min / (60);
        	  			demo.secondCounter =  demo.sitCounter % 60;
        	  			demo.workingScore.setText(" " +  demo.hourCounter + ":" + demo.minuteCounter + ":" + demo.secondCounter);
        	  	        
        	  			for(TaskTime TT :demo.l){
			         		if (TT.isIsActive())
			         		{
			         			TT.setTaskTimeCounterIncrement();
			         			break;
			         		}
        	  			}
        	  			demo.taskSummary.setText(printSummary());
        	  		}
        	  		
        	  		
        	  		else if (demo.bStand == true) {
        	  			demo.standCounter = demo.standCounter + 1;
        	  			demo.ahourCounter = demo.standCounter /(60*60);
        	  			min = demo.standCounter - demo.ahourCounter * 60*60;
        	  			demo.aminuteCounter = min / (60);
        	  			demo.asecondCounter =  demo.standCounter % 60;
        	  			demo.awayScore.setText(" " +  demo.ahourCounter + ":" + demo.aminuteCounter + ":" + demo.asecondCounter);
        	  			
        	  		}
       			
        	  	}
        	 };
        	 Timer displayTimer = new Timer(1000, listener);
        	 displayTimer.start();
            
	    }
	   
	   /**
	     *  printFileSummary
	     *  Generates a tab delimited text file that can be opened in Excel and saved as an xls.
	     *  	
	     */
	    public static boolean printFinalSummary()
	    {
	    
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
    	    
    	    for(TaskTime TT :demo.l){
         		 
   	        
      	        outputStream.write(date + "\t" +TT.getTaskName() +"\t" + TT.convertToHourMinSec() + "\t");
      	        outputStream.print(TT.getHours());
      	        outputStream.print("\t");
      	        outputStream.print(TT.getMinutes());
      	        outputStream.print("\t");
      	        outputStream.println(TT.getSeconds());
  			}

    	    outputStream.close();
    	    
    	    
    	    
    	    
    	    
    	    return true;    
	    }catch(IOException e){
	    	
    		//e.printStackTrace();
    		JOptionPane.showMessageDialog(null,e.getMessage());
    		return false;
    	}
        
	    
	    
	    
	    
	    
	    }
	   
		public static void printTime(String Desc, int hC, int mC, int sC)
		{
			GregorianCalendar d = new GregorianCalendar();
	    	try{
	    		String data = d.getTime()+ "\t" + Desc + "\t" + Integer.toString(hC) + "\t" +  Integer.toString(mC) + "\t" +Integer.toString(sC);
	    		
	 
	    		File file =new File("WorkTime.txt");
	 
	    		//if file doesn't exists, then create it
	    		if(!file.exists()){
	    			file.createNewFile();
	    		}
	 
	    		//true = append file
	    		FileWriter fileWritter = new FileWriter(file.getName(),true);
	    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	    	        bufferWritter.write(data);
	    	        bufferWritter.newLine();
	    	        bufferWritter.close();
	 
		        System.out.println("Done");
	 
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}	
			
		}
		
		public static String printSummary()
		{	
			GregorianCalendar d = new GregorianCalendar();
			String sDate = d.getTime().toString();
			String sSummary = "";
			String s, u;
			TaskTime T;
			//System.out.println("printObj");
			Iterator<TaskTime> i = demo.l.iterator();
		while (i.hasNext()) 
        {
        	T = i.next();
        	s = T.getTaskName();
			u = T.convertToHourMinSec();
			sSummary = sSummary +sDate +"\t" + s + "\t" +  u + "\n";
			System.out.println(sSummary);
        
        }
		
			return sSummary;
		}
		
		public static void printObj()
		{   
			int f, p, q ;
			String s, u;
			TaskTime T;
			//System.out.println("printObj");
			Iterator<TaskTime> i = demo.l.iterator();
			while (i.hasNext()) 
            {
            	T = i.next();
            	p = T.getEndTimeCounter();
            	q = T.getTaskTimecounter();
				f = T.getStartTimeCounter();
				s = T.getTaskName();
				u = T.convertToHourMinSec();
				System.out.println(u + " /total time:" + Integer.toString(q) + ".  start time:  " + Integer.toString(f) +  " Task:" +s + " End Time:" + Integer.toString(p) );
            
            }

						
		}

}
