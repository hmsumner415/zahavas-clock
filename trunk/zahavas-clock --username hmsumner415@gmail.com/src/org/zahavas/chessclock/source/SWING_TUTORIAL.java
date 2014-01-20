package org.zahavas.chessclock.source;

import javax.swing.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;

//
public class SWING_TUTORIAL  implements ActionListener {
	int i = 0;
	int hourCounter = 0;
	int minuteCounter = 0;	
	int secondCounter = 0;
	int ahourCounter = 0;
	int aminuteCounter = 0;	
	int asecondCounter = 0;
	int sitCounter = 0, standCounter =0;   
	JPanel scorePanel, buttonPanel, textPanel;
	JButton ExitButton, ToggleButton;
	JLabel workingScore, awayScore, workingLabel, awayLabel, timeLabel;
	static Writer fWriter = null;
	static PrintWriter outputFile = null;

	boolean bStand = true, bSit = false;
	
	static SWING_TUTORIAL demo;
	
	public SWING_TUTORIAL ()
	{
		
		System.out.println("Swing Tutorial started.");


	}
	
	public JPanel createContentPane(){
		   
	        // We create a bottom JPanel to place everything on.
	        JPanel totalGUI = new JPanel();
	        
	        // We set the Layout Manager to null so we can manually place
	        // the Panels.
	        totalGUI.setLayout(null);
	        totalGUI.setVisible(true);
	        
	        	        
	        // Creation of a Panel to contain the score labels.
	        scorePanel = new JPanel();
	        scorePanel.setLayout(null);
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
	        
	        textPanel = new JPanel();
	        textPanel.setLayout(null);
	        textPanel.setLocation(10, 5);
	        textPanel.setSize(260, 30);
	        totalGUI.add(textPanel);

	        // First JLabel, outputs "Red".
	        // Added to the 'textPanel' JPanel
	        workingLabel = new JLabel("Working");
	        workingLabel.setLocation(0, 0);
	        workingLabel.setSize(50, 40);
	        workingLabel.setHorizontalAlignment(0);
	        textPanel.add(workingLabel);

	        awayLabel = new JLabel("Away");
	        awayLabel.setLocation(210, 0);
	        awayLabel.setSize(50, 40);
	        awayLabel.setHorizontalAlignment(0);
	        textPanel.add(awayLabel);
	        

	        
	        // Creation of a label to contain all the JButtons.
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.setLayout(null);
	        buttonPanel.setLocation(10, 100);
	        buttonPanel.setSize(570, 180);  
	        totalGUI.add(buttonPanel);

	        // We create a button and manipulate it using the syntax we have
	        // used before.


	        
	        ExitButton = new JButton("Exit");
	        ExitButton.setLocation(170, 40);
	        ExitButton.setSize(100, 30);
	        ExitButton.addActionListener(this);
	        buttonPanel.add(ExitButton);
	        
	        ToggleButton = new JButton("Toggle");
	        ToggleButton.setLocation(0, 40);
	        ToggleButton.setSize(100, 30);
	        ToggleButton.addActionListener(this);
	        buttonPanel.add(ToggleButton);
	        
	        timeLabel = new JLabel("Current Time");
	        timeLabel.setLocation(10,110);
	        timeLabel.setSize(250,40);
	        timeLabel.setHorizontalAlignment(0);
	        buttonPanel.add(timeLabel);
	        
	        
	        // Finally we return the JPanel.
	        totalGUI.setOpaque(true);
	        return totalGUI;
	    }
	
	   public void actionPerformed(ActionEvent e) {
		     System.out.println("Hi");
		 
		
		        if(e.getSource() == ExitButton)
		        {
		           System.exit(0);		        	
		        	       	 
		        }
			 
			 
		        else if(e.getSource() == ToggleButton)
		        {
		         if (bSit == true){bStand = true; bSit = false;}
		         else if (bStand == true){bStand = false; bSit = true;}
		        	       	 
		        }
			
		}  
	   
	   public static void createAndShowGUI() {    
	        //Create and set up the frame. 
	        //The string passed as an argument will be displayed 
	        //as the title.
		    JFrame.setDefaultLookAndFeelDecorated(true);
	        JFrame frame = new JFrame("Task Clock");
	        
	       //SWING_TUTORIAL demo = new SWING_TUTORIAL(); 
	        demo = new SWING_TUTORIAL();
	        frame.setContentPane(demo.createContentPane());
	        
	        //Display the window.
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400,400);
	        frame.setVisible(true);
	    }

	   public static void main(String[] args) {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	       
	      SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	               
	                
	            }
	           
	        });

       
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
        	  			//demo.outputFile.println("TimeWorking "+ demo.hourCounter + ":" + demo.minuteCounter + ":" + demo.secondCounter );
    	  			    demo.workingScore.setText(" " +  demo.hourCounter + ":" + demo.minuteCounter + ":" + demo.secondCounter);
        	  	    
        	  		}
        	  		
        	  		
        	  		else if (demo.bStand == true) {
        	  			demo.standCounter = demo.standCounter + 1;
        	  			demo.ahourCounter = demo.standCounter /(60*60);
        	  			min = demo.standCounter - demo.ahourCounter * 60*60;
        	  			demo.aminuteCounter = min / (60);
        	  			demo.asecondCounter =  demo.standCounter % 60;
        	  			//demo.outputFile.println("TimeAway " + demo.ahourCounter + ":" + demo.aminuteCounter + ":" + demo.asecondCounter );
        	  			demo.awayScore.setText(" " +  demo.ahourCounter + ":" + demo.aminuteCounter + ":" + demo.asecondCounter);
        	  		
        	  		}
       			
        	  	}
        	 };
        	  	Timer displayTimer = new Timer(1000, listener);
        	    displayTimer.start();
            
	    }
			 
		

}
