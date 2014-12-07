package org.zahavas.chessclock.source;

import java.awt.*;
import java.awt.event.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import org.w3c.dom.Document;
import org.zahavas.chessclock.source.JNA.State;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class TaskFrame extends JFrame implements ActionListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int TEXT_ROWS = 10;
	public static final int TEXT_COLUMNS = 20;

	private JComboBox<String> taskChoose;
	private JComboBox<String> clientChoose;
	private JComboBox<String> projectChoose;
	
	public JTextArea taskLog;
	public JButton toggleButton;
	public JLabel currentTaskLabel, workingLog, idleLog, workingScore, awayScore;
	public JLabel timeLabel,activeProgramLabel;
	public JMenuBar menuBar;
	public JMenu editMenu, reportMenu;
	public JMenuItem editTaskMenuItem, editLinkageMenuItem; 
	public JMenuItem reportTaskMenuItem, reportApplicationList, reportClientListMenuItem , reportTimeSummaryItem;
	public EditTaskFrame TEdit;
	public reportTimeSummaryFrame REdit;
	public EditLinkageFrame LEdit;
	public msgInActivityFrame mMsg;
	
	public boolean bStand = true, bSit = false, taskfound = false, isMustHaveLinkageActive = false;
	private TaskTime T;
	public static List<TaskTime> lTaskTime = new ArrayList<TaskTime>();
	private String CurrentTask, SelectedTask, SelectedClient, SelectedProject;
	private String taskChooseItem;
	private String txtApplicationName = "null";

	int hourCounter = 0;
	int minuteCounter = 0;	
	int secondCounter = 0;
	int ahourCounter = 0;
	int aminuteCounter = 0;	
	int asecondCounter = 0;
	int sitCounter = 0, standCounter =0;
	int min= 0;
	int i, j = 0;
	
	private static final int MAX_TITLE_LENGTH = 1024;
	char[] buffer = new char[MAX_TITLE_LENGTH * 2];
    
	private ArrayList taskArray;
	private ArrayList projectArray;
	private ArrayList clientArray2;
	private ArrayList ApplicationTasks;
	
	private SQLProject  db =new SQLProject();
	
	int idleSec = 0;
	State state = State.UNKNOWN;
	boolean stateChanged = false;
	State newState = State.UNKNOWN;
	DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	
	
	 
			
	{
		Timer displayTimer = new Timer(1000, this);
		 displayTimer.start(); 
	}
	/**
	 *  actionPerformed
	 *   
	 */
	public void actionPerformed(ActionEvent e)
	{
	       idleSec = JNA.getIdleTimeMillisWin32() / 1000;
           //System.out.println(idleSec);
           newState =
              idleSec < 30 ? State.ONLINE :
              idleSec > 3 * 60 ? State.AWAY : State.IDLE;

          if (newState != state) {
              state = newState;
              stateChanged = true;
               
              //System.out.println(dateFormat.format(new Date()) + " # " + state);	
          }
          else stateChanged = false;
          
           if (!isMustHaveLinkageActive)
           {	  
        	  if (state ==state.AWAY){
        		  bStand = true;
        		  bSit = false;
        		  toggleButton.setText("Set to Active");
        	  }
        	  else {
        		  bSit = true;
        		  bStand = false;
        		  toggleButton.setText("Set to Idle");  
        	  }
           }
           
           if (state == state.AWAY && stateChanged == true) 
           {
        	   Toolkit.getDefaultToolkit().beep();
        	   mMsg = new msgInActivityFrame();
        	   mMsg.setTitle("Notification");
        	   mMsg.setDefaultCloseOperation(mMsg.DO_NOTHING_ON_CLOSE);
        	   mMsg.setLocation(300,300);
        	  
        	   mMsg.setVisible(true);
        	   mMsg.toFront();
        	   mMsg.repaint();
        	   mMsg.requestFocus();
           }
           
		  
		  
		  JNA.User32.INSTANCE.GetWindowTextW(JNA.User32.INSTANCE.GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
	        //System.out.println("Active window title: " + Native.toString(buffer));
	        activeProgramLabel.setText("Active Window: " + Native.toString(buffer));
	        
	        if (!txtApplicationName.replaceAll("`", "'").equals(Native.toString(buffer)))
	        {	        	
	        	//System.out.println("Application name Changed from " + txtApplicationName + "To:" + Native.toString(buffer));
	        	txtApplicationName = Native.toString(buffer);
	        	txtApplicationName = txtApplicationName.replaceAll("'", "`");
	        	String rslt = db.InsertApplication(txtApplicationName);
	        	if (IsMustHaveLink (clientChoose.getSelectedItem().toString(), projectChoose.getSelectedItem().toString(), taskChoose.getSelectedItem().toString(), txtApplicationName ))
	        	          { bSit = false;
	      	              bStand = true;
	    	              toggleButton.setText("Set to Idle");
	    	              isMustHaveLinkageActive=true;
	    	              }
	        	else {
	        		isMustHaveLinkageActive=false;
	        	 	bSit = true;
	        	 	bStand = false;
	        	}
	        	
	        }
	        
	        
	        PointerByReference pointer = new PointerByReference();
	        JNA.User32.INSTANCE.GetWindowThreadProcessId(JNA.User32.INSTANCE.GetForegroundWindow(), pointer);
	        Pointer process = JNA.Kernel32.INSTANCE.OpenProcess(JNA.Kernel32.INSTANCE.PROCESS_QUERY_INFORMATION | JNA.Kernel32.INSTANCE.PROCESS_VM_READ, false, pointer.getValue());
	    //    JNA.Paspi.INSTANCE.GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
	    //    System.out.println("Active window process: " + Native.toString(buffer));
	        
		  
			String t ="nothing";
			Calendar cal = new GregorianCalendar();
			timeLabel.setText (" "+ cal.getTime());
			t=cal.getTime().toString() + ":" + SelectedTask; ;
        	 
			  if (e.getSource() == editTaskMenuItem)
			  {
				  //System.out.println("editTaskMenuItem clicked");
				  TEdit = new EditTaskFrame();
				  TEdit.setTitle("Edit Tasks");
				  TEdit.setDefaultCloseOperation(TEdit.DO_NOTHING_ON_CLOSE);
				  TEdit.setVisible(true);
				  //TEdit.dispose();
			  }
			  
			  if (e.getSource() == editLinkageMenuItem)
			  {
				  //System.out.println("editLinkageMenuItem clicked");
				  LEdit = new EditLinkageFrame();
				  LEdit.setTitle("Edit Linkage");
				  LEdit.setDefaultCloseOperation(TEdit.DO_NOTHING_ON_CLOSE);
				  LEdit.setVisible(true);
				  //TEdit.dispose();
			  }
			  
			  if (e.getSource() == reportTimeSummaryItem)
			  {
				  //System.out.println("editTaskMenuItem clicked");
				  REdit = new reportTimeSummaryFrame();
				  REdit.setTitle("Time Summary Report");
				  REdit.setDefaultCloseOperation(REdit.DO_NOTHING_ON_CLOSE);
				  REdit.setVisible(true);
				  //REdit.dispose();
			  }
				  
				  
			  if (e.getSource() == reportClientListMenuItem)
			  {
				  db.SelectFromClientReport();
	  
			  }
			  
			  
			  if (e.getSource() == reportTaskMenuItem)
			  {
				  printSummaryToXLTS(lTaskTime);
				  db.SelectFromTASKSUMMARY();
				  
			  }
			  
			  if (e.getSource() == reportApplicationList)
			  {
				  db.SelectFromApplication();
				  
			  }
			  
			  
			  if (e.getSource() == clientChoose)
			  {
				  projectArray = db.SelectDistinctProjectsbyClient(clientChoose.getSelectedItem().toString());
				  projectChoose.removeAllItems();
				  projectChoose.addItem("Idle");
				  taskChoose.removeAllItems();
				  taskChoose.addItem("Idle");
				  //clientArray2 = db.SelectFromClient();
				  i = projectArray.size();
				  String[] projectArr = new String[i];
				    for(i = 0; i < projectArray.size();i++){  
				          for(j = 0; j < ((ArrayList)projectArray.get(i)).size(); j++){  
				             if (j==0) 
				             {	            	
				            	 projectArr[i] = (String)((ArrayList) projectArray.get(i)).get(0) ;
				            	 projectChoose.addItem(projectArr[i]);
				             }            
				          }  
				       }
			  }
			  
			  else if (e.getSource() == projectChoose)
			  {  
				  if (projectChoose.hasFocus())
				  {  
				  //if (projectChoose.getSelectedItem() == null) {return;}
				  taskArray = db.SelectDistinctTasksbyClientProject(clientChoose.getSelectedItem().toString(), projectChoose.getSelectedItem().toString());
				  taskChoose.removeAllItems();
				  taskChoose.addItem("Idle");
				  //clientArray2 = db.SelectFromClient();
				  i = taskArray.size();
				  String[] taskArr = new String[i];
				    for(i = 0; i < taskArray.size();i++){  
				          for(j = 0; j < ((ArrayList)taskArray.get(i)).size(); j++){  
				             if (j==0) 
				             {	            	
				            	 taskArr[i] = (String)((ArrayList) taskArray.get(i)).get(0) ;
				            	 taskChoose.addItem(taskArr[i]);
				             }            
				          }  
				       }
			      }
			  }
			  
			  
			  
			  else if (e.getSource() == taskChoose)
		         {
				  if (taskChoose.hasFocus())
				  {
					  SelectedTask = clientChoose.getSelectedItem().toString() + "|" + 
							  projectChoose.getSelectedItem().toString() + "|" +
							  taskChoose.getSelectedItem().toString();
					  SelectedClient = clientChoose.getSelectedItem().toString();
					  SelectedProject = projectChoose.getSelectedItem().toString();
				  } 
				  else return;
				  
		        	 //System.out.println(SelectedTask);
		        	 t=cal.getTime().toString() + ":" + SelectedTask; 
		        	 timeLabel.setText (" "+ cal.getTime());
		        	 currentTaskLabel.setText(SelectedTask);
		            
		        	 
	        	 //System.out.println(SelectedTask);
	        	 
	        	 CurrentTask = workingLog.getText();
	        	 if (!(CurrentTask.equals(SelectedTask) ))
	        	 { // Different task.  Set end time of CurrentTask.  Set start time of new task
	        		 	taskfound = false;
			         	for(TaskTime TT :lTaskTime){
			         						         			
			         		if (TT.getTaskName().equals(SelectedTask)) 
			         			{taskfound = true;
			         			taskLog.setText(printSummary());
			         			
			         			TT.setIsActive(true);
			         			}
			         		else
			         		{
			         			TT.setIsActive(false);
			         		}
			         	}	
			         	if ( !taskfound) {
			         		for(TaskTime TT :lTaskTime){
			         			TT.setIsActive(false);
				         		}
			         		lTaskTime.add(T = new TaskTime(SelectedClient, SelectedProject, SelectedTask, sitCounter)); 
			         		taskLog.setText(printSummary());
			         		taskfound = true;
			         	}
			         	
	        	 }
	        	 //* temp placement for testing
	        	 if (IsMustHaveLink (clientChoose.getSelectedItem().toString(), projectChoose.getSelectedItem().toString(), taskChoose.getSelectedItem().toString(), txtApplicationName ))
     			{ bSit = false;
   	              bStand = true;
 	              toggleButton.setText("Set to Idle");
 	              isMustHaveLinkageActive=true;
 	              }
	        	 else {isMustHaveLinkageActive=false;
	        	 	bSit = true;
	        	 	bStand = false;
	        	 }
	        	  
	        	 
	        	 
	        	 
		         }
			  
			  else if(e.getSource() == toggleButton)
		        {
		         if (bSit == true)	// Toggle to Active
		         	{bStand = true; 
		         	bSit = false;
		         	toggleButton.setText("Set Active");
		         	currentTaskLabel.setText("Not Working");
		         	}
		         else if (bStand == true)   // toggle to idle
		         {
		        	bStand = false;
		         	bSit = true;
		         	toggleButton.setText("Set to Idle");
		         	currentTaskLabel.setText(SelectedTask);
   	 
		         }
		        } 
		         
		         if (bSit == true) { 
     	  			sitCounter = sitCounter + 1;
     	  			hourCounter = sitCounter /(60*60);
     	  			min = sitCounter - hourCounter * 60*60;
     	  			minuteCounter = min / (60);
     	  			secondCounter =  sitCounter % 60;
     	  			workingScore.setText(" " +  hourCounter + ":" + minuteCounter + ":" + secondCounter);
     	  	        
     	  			for(TaskTime TT :lTaskTime){
			         		if (TT.isIsActive())
			         		{
			         			TT.setTaskTimeCounterIncrement();
			         			break;
			         		}
     	  			}
     	  			taskLog.setText(printSummary());
     	  		}
     	  		
     	  		
     	  		else if (bStand == true) {
     	  			standCounter = standCounter + 1;
     	  			ahourCounter = standCounter /(60*60);
     	  			min = standCounter - ahourCounter * 60*60;
     	  			aminuteCounter = min / (60);
     	  			asecondCounter =  standCounter % 60;
     	  			awayScore.setText(" " +  ahourCounter + ":" + aminuteCounter + ":" + asecondCounter);
     	  			
     	  		}
		         

		         
          } 
     
	
	private boolean IsMustHaveLink(String txtClient, String txtProject,
			String txtTask, String txtApplicationName2) {
		int i = 0;
		boolean boolBrokenLink = false;
		final String sIdle = "Idle";
		String s, lApp, lClient, lProject, lTask;
		//System.out.print(txtClient + ":" + txtProject + ":" + txtTask + ":" + txtApplicationName2);
		//System.out.println(".");
		i = ApplicationTasks.size();
		for(i = 0; i < ApplicationTasks.size();i++){  
	          for(j = 0; j < 4; j++){  
	            	            	
	               s=  ((AbstractList<String>) ApplicationTasks.get(i)).get(j) ;
	               //System.out.print(s);
	               //System.out.print(" ");
	                         
	          } 
	          
	          lApp = (String)((ArrayList) ApplicationTasks.get(i)).get(0);
	          lClient =  (String)((ArrayList)  ApplicationTasks.get(i)).get(1);
	          lProject =  (String)((ArrayList)  ApplicationTasks.get(i)).get(2);
	          lTask =  (String)((ArrayList)  ApplicationTasks.get(i)).get(3);
	          
	           //System.out.println(".");
	           if (    lClient.equals(txtClient)  &&
	        		   lProject.equals(txtProject)  &&
	        		   lTask.equals(txtTask) &&
	        		   lApp.equals(txtApplicationName2)
         		   )   
        		  { return false;  }
	           
	           if (
	        		   lClient.equals(txtClient)  &&
	        		   lProject.equals(txtProject)  &&
	        		   lTask.equals(sIdle) &&
	        		   lApp.equals(txtApplicationName2)
	        	  ) { return false;  }
	           
	           if (
	        		   lClient.equals(txtClient)  &&
	        		   lProject.equals(sIdle)  &&
	        		   lTask.equals(sIdle) &&
	        		   lApp.equals(txtApplicationName2)
	    	           
	    	      ) { return false;  }
	           // cases where linkage rule exists and application is different
	           //System.out.println("Test for linkage starts here");
	           if
	           (			   lClient.equals(txtClient)  &&
	    	        		   lProject.equals(txtProject)  &&
	    	        		   lTask.equals(txtTask) &&
	    	        		   !(lApp.equals(txtApplicationName2))	   
	        	  
	        		   
				)  { boolBrokenLink = true;}	   
	           else if	   
	        		   (
	        				   lClient.equals(txtClient)  &&
	    	        		   lProject.equals(txtProject)  &&
	    	        		   lTask.equals(sIdle) &&
	    	        		   !(lApp.equals(txtApplicationName2))

	        		   )
	        		    { boolBrokenLink = true;} 
	           else if	   
	        		   (
	        				   lClient.equals(txtClient)  &&
	    	        		   lProject.equals(sIdle)  &&
	    	        		   lTask.equals(sIdle) &&
	    	        		   !(lApp.equals(txtApplicationName2))
	        		   )
	        		   
	            
	           { boolBrokenLink = true;}
	           
	       }
		
		
		// boolBroekLink defaulted to false;
		//System.out.println(boolBrokenLink);
		return boolBrokenLink;
	}


	/**
	 * printSummary()
	 * public static
	 * called every second to display the current time usage
	 * @return String
	 */
	public static String printSummary()
	    		{	
	    			GregorianCalendar d = new GregorianCalendar();
	    			String sDate = d.getTime().toString();
	    			String sSummary = "";
	    			String s, u;
	    			TaskTime T;
	    			//System.out.println("printObj");
	    			Iterator<TaskTime> i = lTaskTime.iterator();
	    		while (i.hasNext()) 
	            {
	            	T = i.next();
	            	s = T.getTaskName();
	    			u = T.convertToHourMinSec();
	    			sSummary = sSummary +sDate +"\t" + s + "\t" +  u + "\n";
	    			//System.out.println(sSummary);
	            }
	    		return sSummary;
	   		}
	
	public static void printSummaryToXLTS(List<TaskTime> lTaskTime2)
	{	

		Document u;
		xlts_Transforms xlts = new xlts_Transforms();
		XMLUtilities XML = new XMLUtilities();
		u = XML.toDomXML(lTaskTime2);
		xlts.xltsTaskListing(u);


		 
		 
		

	}
	
	    		
    /**
     *  Constructor: Task Frame
     */
	public TaskFrame()
	{
		
	      GridBagLayout layout = new GridBagLayout();
	      setLayout(layout);

	      //ActionListener listener = EventHandler.create(ActionListener.class, this, "taskFrameListener"); 
		
	      currentTaskLabel = new JLabel("Not Working");
	      workingLog = new JLabel("Working Log");
	      idleLog = new JLabel("Idle log ");
	      timeLabel = new JLabel();
	      workingScore = new JLabel("working Score");
	      awayScore = new JLabel("away Score");
	      timeLabel.setText("Current ---- Time");
	      activeProgramLabel = new JLabel("Unknown Program Label");
          
	  	  menuBar = new JMenuBar();
		  
	  	  editMenu = new JMenu("Edit");
		  this.setJMenuBar(menuBar);
		  menuBar.add(editMenu);
		  editTaskMenuItem = new JMenuItem("Edit Task");
		  editLinkageMenuItem = new JMenuItem("Edit Linkage");
		  editMenu.add(editTaskMenuItem);
		  editMenu.add(editLinkageMenuItem);
		  editTaskMenuItem.addActionListener(this);
		  editLinkageMenuItem.addActionListener(this);
        		  
		  reportMenu = new JMenu("Reports");
		  this.setJMenuBar(menuBar);
		  menuBar.add(reportMenu);
		  reportTaskMenuItem =new JMenuItem("Today's Time");
		  reportApplicationList = new JMenuItem("Application List");
		  reportClientListMenuItem = new JMenuItem("Client List");
		  reportTimeSummaryItem = new JMenuItem("Time Summary");
		  reportMenu.add(reportTaskMenuItem);
		  reportMenu.add(reportApplicationList);
		  reportMenu.add(reportClientListMenuItem);
		  reportMenu.add(reportTimeSummaryItem);
		  reportTaskMenuItem.addActionListener(this);
		  reportApplicationList.addActionListener(this);
		  reportClientListMenuItem.addActionListener(this);
		  reportTimeSummaryItem.addActionListener(this);
		  
		  
		  clientArray2 = db.SelectFromClient();
		  i = clientArray2.size();
		  String[] clients = new String[i];
		    for(i = 0; i < clientArray2.size();i++){  
		          for(j = 0; j < ((ArrayList)clientArray2.get(i)).size(); j++){  
		             if (j==1) 
		             {	            	
		            	 clients[i] = (String)((ArrayList) clientArray2.get(i)).get(0) ;
		             }            
		          }  
		       }
		  
		 
          String idleTask[] = {"Idle"};
          if (clientArray2.size()>0)  {clientChoose = new JComboBox<String>(clients);}
          else clientChoose = new JComboBox<String>(idleTask);
          clientChoose.addActionListener(this);
	      
	      projectChoose = new JComboBox<String>(idleTask);
	      projectChoose.addActionListener(this);
	      
	      taskChoose = new JComboBox<String>(idleTask);
	      taskChoose.addActionListener(this);

	      toggleButton = new JButton("Toggle");
	      toggleButton.addActionListener(this);
	      
	      taskLog = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
	      taskLog.setText("No Tasks Yet");
	      taskLog.setEditable(false);
	      taskLog.setLineWrap(true);
	      taskLog.setBorder(BorderFactory.createEtchedBorder());
	      
	      add(currentTaskLabel, new GBC(0, 0).setAnchor(GBC.WEST));
	      add(workingLog, new GBC(1, 0).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(workingScore, new GBC(2, 0).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(idleLog, new GBC(3, 0).setAnchor(GBC.EAST));
	      add(awayScore, new GBC(4, 0).setAnchor(GBC.EAST));
	      
	      //add(toggleButton, new GBC(0, 1,1,1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	       
	      add(clientChoose, new GBC(0, 1, 1, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
	      add(projectChoose, new GBC(1, 1, 1, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
	      add(taskChoose, new GBC(2, 1, 1, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
	      
	      add(taskLog, new GBC(0, 2, 4, 1).setFill(GBC.BOTH).setWeight(100, 100));
	      add(timeLabel, new GBC(1, 3 ,2,1).setAnchor(GBC.CENTER).setWeight(100, 100));
	      add(activeProgramLabel, new GBC(1, 4 ,2,1).setAnchor(GBC.CENTER).setWeight(100, 100));
	      
	      pack();
		      
	  	
		   
	      ApplicationTasks = db.SelectApplicationTask();
		
		
	      
	}
}
 