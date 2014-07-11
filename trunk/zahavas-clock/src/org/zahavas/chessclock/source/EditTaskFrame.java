package org.zahavas.chessclock.source;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class EditTaskFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton closeButton;
	private JButton addButtonClient;
	private JButton addButtonTask;
	private JTextField txtClient;
	private JTextField txtClientShortName;
	private JTextField txtTask;
	private JTextField txtTaskShortName;
	private JLabel lblClient;
	private JLabel lblTask;
	private JLabel lblTaskDetail;
	private JLabel lblProject;
	private JLabel lblLine;
	private JLabel lblShortName;
	private JLabel lblLongName;
	private JComboBox<String> clientChoose;
	
	
	private SQLProject  db =new SQLProject();
	private ArrayList clientArray1;
	private int i,j;
	private String s;
	private String Result;
	private String[] clients;
	
	public EditTaskFrame(){
		GridBagLayout layout = new GridBagLayout();
	      setLayout(layout);

	      //ActionListener listener = EventHandler.create(ActionListener.class, this, "taskFrameListener"); 
	      
	      lblClient = new JLabel("Client:");
	      lblTask = new JLabel("Task:");
	      lblTaskDetail = new JLabel("Task Detail:");
	      
	      lblProject = new JLabel("Project:");
	      lblLine = new JLabel("----------------------------------------------------------");
	      lblShortName = new JLabel("Short Name");
	      lblLongName = new JLabel("Long Name");
	      
	      txtClient = new JTextField();
	      txtTask = new JTextField();
	      txtClientShortName = new JTextField();
	      txtTaskShortName = new JTextField();
	      
	      closeButton = new JButton("Close");
	      closeButton.addActionListener(this);
	      addButtonClient = new JButton("Add Client");
	      addButtonClient.addActionListener(this);
	      addButtonTask = new JButton("Add Task");
	      addButtonTask.addActionListener(this);
	      
	      
	      
	      
	      clientArray1 = db.SelectFromClient();
	      i = clientArray1.size();
	      clients = new String[i]; 
	       
	      for(i = 0; i < clientArray1.size();i++){  
	          for(j = 0; j < ((ArrayList)clientArray1.get(i)).size(); j++){  
	             if (j==1) 
	             {
	            	 clients[i] = (String)((ArrayList) clientArray1.get(i)).get(j);
	             }            
	          }  
	       } 
	      	      
	      clientChoose = new JComboBox<String>(clients);
	      clientChoose.addActionListener(this);
	      
	      
	      add(lblShortName,       new GBC(1,0,1,1).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(lblLongName,        new GBC(2,0,1,1).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      
	      
	      add(lblClient,          new GBC(0,1,1,1).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(txtClientShortName, new GBC(1,1,1,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(txtClient,          new GBC(2,1,3,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(addButtonClient,    new GBC(6,1,1,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(1, 0).setInsets(1));
	      
	      add(lblLine,			  new GBC(2,2,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      	      
	      add(lblTask,            new GBC(0,3,1,1).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(clientChoose,       new GBC(1,3,1,1).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      
	      add(lblProject,         new GBC(1,4,1,1).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(lblTaskDetail,      new GBC(2,4,2,1).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      
	      add(txtTaskShortName,   new GBC(1,5,1,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(txtTask,            new GBC(2,5,3,1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	      add(addButtonTask,      new GBC(6,5,1,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(1, 0).setInsets(1));
	      
	      add(closeButton, new GBC(0, 6,1,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(0, 0).setInsets(1));
	     
	      
	      pack();
	      
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		  if (e.getSource() == closeButton)
		  {
			  this.dispose();
		  }
		  if (e.getSource() == addButtonClient)
		  {
			 System.out.println(txtClientShortName.getText());
			 System.out.println(txtClient.getText()); 
			 Result = db.InsertNewClient(txtClientShortName.getText(), txtClient.getText());	
			// db.InsertNewTask(txtClient.getText(), txtTask.getText(), title);
			 JOptionPane.showMessageDialog(null, Result);
		  }
		  
		  if (e.getSource() == addButtonTask)
		  {
			 i = clientChoose.getSelectedIndex();
			 s = (String) ((ArrayList) clientArray1.get(i)).get(0);
			 Result = db.InsertNewTask(s, txtTaskShortName.getText(), txtTask.getText());	
			 JOptionPane.showMessageDialog(null, Result);
			 db.SelectFromTask();
		  }
		
	}

}
