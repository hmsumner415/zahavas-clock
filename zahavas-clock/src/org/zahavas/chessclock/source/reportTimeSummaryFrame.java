package org.zahavas.chessclock.source;

import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;


public class reportTimeSummaryFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton closeButton;
	private JButton runButton;
	
	private JLabel lblClient;
	private JLabel lblChooseClient2;
	private JLabel lblProject;
	private JLabel lblTask;
	private JLabel lblMonth;
	private JLabel lblWeekofYear;
	private JLabel lblDateRange;
	private JLabel lblDateTo;
	
	
	private JCheckBox cbClient;
	private JCheckBox cbProject;
	private JCheckBox cbTask;	
	private JCheckBox cbMonth;
	private JCheckBox cbWeekofYear;
	
	private SQLProject  db =new SQLProject(); 
	private JTextField txtMonthFrom ; 
	private JTextField txtYearFrom;  
	private JTextField txtMonthTo;  
	private JTextField txtYearTo;
	
	private JComboBox<String> clientChoose;
	private ArrayList clientArray2;
	int i, j;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeButton)
		  {
			  this.dispose();
		  }
		
		if (e.getSource() == runButton)
		  {
			  if (!cbClient.isSelected() && !cbProject.isSelected() && !cbTask.isSelected() )
			  {JOptionPane.showMessageDialog(null, "Please provide a Selection");}
			  else
			  {
				String strClientChoose = (String) clientChoose.getSelectedItem();  
		  	  db.SelectTaskSummaryReport(
		  			  	  
		  			cbClient.isSelected(),
		  			cbProject.isSelected(),  
				  	cbTask.isSelected(),
				  	cbMonth.isSelected(),
				  	cbWeekofYear.isSelected(), 
				  	 txtMonthFrom.getText(),  
		  			 txtYearFrom.getText(),
		  			 txtMonthTo.getText(),
		  			 txtYearTo.getText(),
		  			strClientChoose
		  			 
		  		  );
		  	   }
		  }
		
	}

	public reportTimeSummaryFrame(){
		GridBagLayout layout = new GridBagLayout();
	      setLayout(layout);

	            
	      lblClient 	= new JLabel("By Client:");
	      lblChooseClient2 = new JLabel ("Choose Client:");
	      lblTask 		= new JLabel("By Task:");
	      lblProject 	= new JLabel("By Project:");
	      lblMonth 		= new JLabel ("Report By Month:");
	  	  lblWeekofYear = new JLabel ("Report by Week:");
	  	  lblDateRange = new JLabel ("Month/Year From:");
	  	  lblDateTo	= new JLabel("Month To");
	  	      
	      closeButton = new JButton("Close");
	      closeButton.addActionListener(this);
	      runButton = new JButton("Run");
	      runButton.addActionListener(this);
	      
	      cbClient = new JCheckBox();
	  	  cbProject = new JCheckBox();
	  	  cbTask = new JCheckBox();	
	  	  cbMonth = new JCheckBox();
	  	  cbWeekofYear = new JCheckBox();
	  	  
	  	    
	  	  
	  	   txtMonthFrom = new JTextField(2); 
	       txtYearFrom = new JTextField(4);
	       txtMonthTo = new JTextField(2);
	       txtYearTo = new JTextField(4);
	      
	 	  clientArray2 = db.SelectFromClient();
		  i = clientArray2.size();
		  
		  String[] clients = new String[i+1];
		  clients[0] = "All";
		    for(i = 0; i < clientArray2.size();i++){  
		          for(j = 0; j < ((ArrayList)clientArray2.get(i)).size(); j++){  
		            // if (j==1) 
		            // {	            	
		            	 clients[i+1] = (String)((ArrayList) clientArray2.get(i)).get(0) ;
		            // }            
		          }  
		       }
		    String idleTask[] = {"Idle"};
		     if (clientArray2.size()>0)  {clientChoose = new JComboBox<String>(clients);}
		     else clientChoose = new JComboBox<String>(idleTask);
		     clientChoose.addActionListener(this); 
	       
	      
	      add(lblClient,          new GBC(0,0,1,1).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(cbClient, new GBC(2,0,1,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      
	      add(lblProject,		  new GBC(0,1,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(cbProject, new GBC(2,1,1,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
		  
	      
	      add(lblProject,        new GBC(0,2,1,1).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(cbProject, new GBC(2,2,1,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      
	      add(lblTask,		  new GBC(0,3,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(cbTask, new GBC(2,3,1,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
		 	      
	      add(lblMonth,			  new GBC(0,4,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(cbMonth, new GBC(2,4,1,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
		    
	      add(lblWeekofYear,			  new GBC(0,5,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(cbWeekofYear, new GBC(2,5,1,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
		  
	      add(lblDateRange,			  new GBC(0,6,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(txtMonthFrom,			  new GBC(1,6,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(txtYearFrom,			  new GBC(2,6,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      
	      add(lblDateTo,			  new GBC(0,7,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(txtMonthTo,			  new GBC(1,7,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      // add(txtYearTo,			  new GBC(2,7,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      
	      add(lblChooseClient2, 	  new GBC(0,8,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
		  add(clientChoose,			  new GBC(1,8,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	       
	       
	      add(runButton,      new GBC(1,9,1,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(1, 0).setInsets(1));
	      add(closeButton, new GBC(0, 9,1,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(0, 0).setInsets(1));
	     
	      
	      pack();
	      
	     
	} 
	
	
}
