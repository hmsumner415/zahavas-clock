package org.zahavas.chessclock.source;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MaintainLinkageFrame extends JFrame implements ActionListener,  ListSelectionListener



{
	
	private static final long serialVersionUID = 1L;
	public static final int TEXT_ROWS = 10;
	public static final int TEXT_COLUMNS = 20;

	//private JComboBox<String> taskChoose;
	//private JComboBox<String> clientChoose;
	//private JComboBox<String> projectChoose;
	private JList<String> Applications;
	private JTextField applicationChoose; 


	private JButton  closeButton, deleteButton;
    private JScrollPane scrollApplicationList;
   
    private JLabel CLIENTSHORTNAME,	 PROJECTNAME, TASKNAME, LINKAGETYPE;
	private JLabel contentCSN, contentPN, contentTN, contentLT;
    
	int i, j = 0;
	
	private DefaultListModel listApplications;

    
	//private ArrayList taskArray;
	//private ArrayList projectArray;
	//private ArrayList clientArray2;
	private ArrayList applicationArray;
	
	private SQLProject  db =new SQLProject();
	
	String selectedApplication = null;


	

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
            if (e.getSource() == Applications) {
            	System.out.println(Applications.getSelectedValue());
            	selectedApplication = Applications.getSelectedValue();
            	if (selectedApplication != null)  
            		
            	{        
         	    for(i = 0; i < applicationArray.size();i++){  
         	    	
         	    	if (selectedApplication.equals((String)((ArrayList) applicationArray.get(i)).get(0)))
         	    	{	
         	            contentCSN.setText((String)((ArrayList) applicationArray.get(i)).get(1));  
         	            contentPN.setText((String)((ArrayList) applicationArray.get(i)).get(2));  
         	            contentTN.setText((String)((ArrayList) applicationArray.get(i)).get(3));  
         	            contentLT.setText((String)((ArrayList) applicationArray.get(i)).get(4));  
         	    	}
         	         
         	       }
            	
            	}
            	
            	
            }
        }
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeButton)
		  {
			 
			  this.dispose();
		  }
		if (e.getSource() == deleteButton)
		  {
			 
			int n = JOptionPane.showConfirmDialog(
				    this,
				    "You are about to delete the Selected Application-Task Linkage.  This activity can not be undone.  Are you sure?",
				    "Are you sure?",
				    JOptionPane.YES_NO_OPTION);
			System.out.println(n);
			if (n==0)
			{	
			  String Result = db.deleteApplicationTask(selectedApplication);
			  JOptionPane.showMessageDialog(null,					    Result);
			  listApplications.removeAllElements();
			  applicationArray = db.SelectApplicationTask();
			  i = applicationArray.size();
			  ///listApplications = new DefaultListModel();
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
			  
			  
			  
			  
			}
		  }
		
	}

	public MaintainLinkageFrame()
	{
	 GridBagLayout layout = new GridBagLayout();
     setLayout(layout);

     
     closeButton = new JButton("Close");
     closeButton.addActionListener(this);
     
     deleteButton = new JButton("Delete");
     deleteButton.addActionListener(this);
     
     CLIENTSHORTNAME = new JLabel("Client: ");
     PROJECTNAME = new JLabel("Project: ");
     TASKNAME = new JLabel ("Task: ");
     LINKAGETYPE = new JLabel("Linkage: ");
     
 	 contentCSN = new JLabel("");
 	 contentPN = new JLabel("");
 	 contentTN = new JLabel("");
 	 contentLT = new JLabel("");

	 
	  applicationArray = db.SelectApplicationTask();
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
	     Applications.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     Applications.addListSelectionListener(this);
   
     
     scrollApplicationList = new JScrollPane(Applications, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
             JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     scrollApplicationList.setPreferredSize(new Dimension(300, 200));
     add(scrollApplicationList, new GBC(0, 3, 4, 4).setAnchor(GBC.CENTER).setWeight(100, 100));
     
     
     add(CLIENTSHORTNAME,   new GBC(0, 7, 1, 1).setAnchor(GBC.EAST).setWeight(100, 100)); 
     add(PROJECTNAME , new GBC(0, 8, 1, 1).setAnchor(GBC.EAST).setWeight(100, 100)); 
     add(TASKNAME    , new GBC(0, 9, 1, 1).setAnchor(GBC.EAST).setWeight(100, 100));
     add(LINKAGETYPE    , new GBC(0, 10, 1, 1).setAnchor(GBC.EAST).setWeight(100, 100));
 	 add(contentCSN  , new GBC(1, 7, 1, 1).setAnchor(GBC.WEST).setWeight(100, 100));
 	 add(contentPN  , new GBC(1, 8, 1, 1).setAnchor(GBC.WEST).setWeight(100, 100));
 	 add(contentTN  , new GBC(1, 9, 1, 1).setAnchor(GBC.WEST).setWeight(100, 100));
 	 add(contentLT  , new GBC(1, 10, 1, 1).setAnchor(GBC.WEST).setWeight(100, 100));
     add(closeButton,    new GBC(0, 11,1,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(0, 0).setInsets(1));
     add(deleteButton, new GBC(1, 11, 1, 1).setAnchor(GBC.WEST).setWeight(100, 100));
     pack();
   
}
	
	
}
