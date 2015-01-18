package org.zahavas.chessclock.source;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EditLinkageFrame extends JFrame implements ActionListener,  ListSelectionListener


{
	
	private static final long serialVersionUID = 1L;
	public static final int TEXT_ROWS = 10;
	public static final int TEXT_COLUMNS = 20;

	private JComboBox<String> taskChoose;
	private JComboBox<String> clientChoose;
	private JComboBox<String> projectChoose;
	private JList<String> Applications;
	private JTextField applicationChoose; 


	private JButton addMustUseButton, addCanUseButton, closeButton, searchButton;
    private JScrollPane scrollApplicationList;
   
	
	int i, j = 0;
	
	private DefaultListModel listApplications;

    
	private ArrayList taskArray;
	private ArrayList projectArray;
	private ArrayList clientArray2;
	private ArrayList applicationArray;
	
	private SQLProject  db =new SQLProject();
	
	String selectedApplication = null, selectedClient = null , selectedProject = null, selectedTask = null;

	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource() == closeButton)
		  {
			 
			  this.dispose();
		  }
		 
		 if (e.getSource() == searchButton)
		  {
			 listApplications.removeAllElements(); 
			 
			 String apptext = applicationChoose.getText();
			 applicationArray = db.SelectFromApplicationFiltered(apptext);
			 //applicationArray = db.SelectFromApplication();
			  i = applicationArray.size();
			  String[] applications = new String[i];
			    for(i = 0; i < applicationArray.size();i++){  
			          for(j = 0; j < ((ArrayList)applicationArray.get(i)).size(); j++){  
			             if (j==0) 
			             {	            	
			            	 applications[i] = (String)((ArrayList) applicationArray.get(i)).get(0) ;
			            	 listApplications.addElement(applications[i]);
			             }            
			          }  
			       }
			    
			   
		  }
		
		  if (e.getSource()== addMustUseButton)
		  {
			  selectedTask =   taskChoose.getSelectedItem().toString();
			  selectedClient = clientChoose.getSelectedItem().toString();
			  selectedProject = projectChoose.getSelectedItem().toString();
			  
			  if (selectedApplication == null) {
				  JOptionPane.showMessageDialog(null, "Please Pick an Application");
				  return;
			  }
			  if (selectedClient == null)
			  {
				  JOptionPane.showMessageDialog(null, "Please Pick a Client");
				  return;
			  }
				  if (selectedProject == null) 
			  {
				  JOptionPane.showMessageDialog(null, "Please Pick a Project");
				  return;
			  }
			  if (selectedTask == null) 
			  {
				  JOptionPane.showMessageDialog(null, "Please Pick a Task");
				  return;
			  }
			  
			  
			  
			  
			  
			  String Result = db.InsertMustUseApplicationForTask(selectedClient, selectedProject, selectedTask, selectedApplication);
				// db.InsertNewTask(txtClient.getText(), txtTask.getText(), title);
				 JOptionPane.showMessageDialog(null, Result);
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
		 
		 
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
			 if (e.getValueIsAdjusting() == false) {
	            if (e.getSource() == Applications) {
	            	System.out.println(Applications.getSelectedValue());
	            	selectedApplication = Applications.getSelectedValue();
	              
	            }
	        }
		
	}
	
	public EditLinkageFrame()
	{
	 GridBagLayout layout = new GridBagLayout();
     setLayout(layout);
     addMustUseButton = new JButton("Add 'Must Use' Linkge");
     addMustUseButton.addActionListener(this);
     
     addCanUseButton = new JButton("Add 'Can Use' Linkge");
     addCanUseButton.addActionListener(this);
     
     closeButton = new JButton("Close");
     closeButton.addActionListener(this);
     
     searchButton = new JButton("Search");
     searchButton.addActionListener(this);
     

	  clientArray2 = db.SelectFromClient();
	  i = clientArray2.size();
	  String[] clients = new String[i];
	    for(i = 0; i < clientArray2.size();i++){  
	          for(j = 0; j < ((ArrayList)clientArray2.get(i)).size(); j++){  
	            // if (j==1) 
	            // {	            	
	            	 clients[i] = (String)((ArrayList) clientArray2.get(i)).get(0) ;
	            // }            
	          }  
	       }
	 
	  applicationArray = db.SelectFromApplication();
	  i = applicationArray.size();
	  listApplications = new DefaultListModel();
	  String[] applications = new String[i];
	    for(i = 0; i < applicationArray.size();i++){  
	          for(j = 0; j < ((ArrayList)applicationArray.get(i)).size(); j++){  
	             if (j==0) 
	             {	            	
	            	 applications[i] = (String)((ArrayList) applicationArray.get(i)).get(0) ;
	            	 listApplications.addElement(applications[i]) ;
	             }            
	          }  
	       }
	    
	     Applications = new JList<String>(listApplications);
	     
	     Applications.setVisibleRowCount(5);
	     Applications.setFixedCellHeight(20);
	     Applications.setFixedCellWidth(640);
	     Applications.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     Applications.addListSelectionListener(this);
	     
	  
	 
     String idleTask[] = {"Idle"};
     if (clientArray2.size()>0)  {clientChoose = new JComboBox<String>(clients);}
     else clientChoose = new JComboBox<String>(idleTask);
     clientChoose.addActionListener(this);
     
     projectChoose = new JComboBox<String>(idleTask);
     projectChoose.addActionListener(this);
     
     taskChoose = new JComboBox<String>(idleTask);
     taskChoose.addActionListener(this);

     applicationChoose = new JTextField(20);
     
     
     scrollApplicationList = new JScrollPane(Applications, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
             JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     scrollApplicationList.setPreferredSize(new Dimension(600, 500));
    
     
      
     add(clientChoose,   new GBC(0, 1, 1, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
     add(projectChoose,  new GBC(1, 1, 1, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
     add(taskChoose,     new GBC(2, 1, 1, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
     
     
     add(applicationChoose, new GBC(0, 2, 1, 1).setAnchor(GBC.EAST).setWeight(100, 100));
     add(searchButton, new GBC(1, 2, 1, 1).setAnchor(GBC.WEST).setWeight(100, 100));
     
     
     add(scrollApplicationList, new GBC(0, 3, 4, 4).setAnchor(GBC.CENTER).setWeight(100, 100));
     
     
     add(closeButton,    new GBC(0, 8,1,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(0, 0).setInsets(1));
    
     add(addMustUseButton,new GBC(1, 8,1,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(0, 0).setInsets(1));
     add(addCanUseButton,new GBC(2, 8,1,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(0, 0).setInsets(1));

     
     pack();
	      
 	
	   

	
	
     
}
	

}
