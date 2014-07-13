package org.zahavas.chessclock.source;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

public class SQLProject extends SQLLiteAccess
{
	/**
	 *  Class constructor
	 *  
	 *  Generates Database schema for first time use 	 *  
	 *  
	 */
	public SQLProject()
	{
		Connection c = null;
	    Statement stmt = null;
	    try {
	    	
	    	File file =new File("tasktracker.db");
	   	 
    		//if file exists, then exit
	    	if(file.exists()){
    			return;
    		}	
	    	
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      System.out.println("Opened database successfully");
  
	      
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE CLIENT " +
	                   "(ID 					INT 	PRIMARY KEY     NOT NULL," +
	                   " CLIENTSHORTNAME 		TEXT 	NOT NULL," +
	                   " CLIENTNAME     		TEXT    NOT NULL) "; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      System.out.println("Table Client added successfully");
	      
	      stmt = c.createStatement();
	      sql = 	   "CREATE TABLE TASK " +
	                   "(ID 					INT PRIMARY KEY     NOT NULL," +
	                   " CLIENTSHORTNAME 		TEXT NOT NULL," +
	                   " PROJECTNAME 			TEXT NOT NULL," +
	                   " TASKNAME           	TEXT NOT NULL) "; 
	      SQLLiteCreateTable (sql);
	      System.out.println("Table Task added successfully");
	      
	      stmt = c.createStatement();
	      sql = "CREATE TABLE TASKSUMMARY " +
	                   "(ID						INT PRIMARY KEY		NOT NULL," +
	                   "TASKDATE       TEXT    NOT NULL," +
	                   " CLIENTSHORTNAME 	TEXT    NOT NULL," +
	                   " PROJECTNAME       TEXT    NOT NULL, " +
	                   " TASKHOUR       INT     NOT NULL," +
	                   " TASKMINUTE     INT     NOT NULL," +
	                   " TASKSECOND     INT     NOT NULL," +
	                   " TASKNAME           TEXT    NOT NULL) "; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      System.out.println("Table tasksummary successfully");
	      
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
		
		
	}
	public String  InsertNewClient(String txtClientShortName, String txtClient)
	  {
	    Connection c = null;
	    Statement stmt = null;
	    int ID = 0;
	    int COUNT = 0;
	    String SQLStatement;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs0 = stmt.executeQuery( "SELECT COUNT(*) as COUNT FROM CLIENT WHERE CLIENTSHORTNAME = '" + txtClientShortName  + "';" );
	      while ( rs0.next() ) {
	           
	         COUNT = rs0.getInt("COUNT");
             System.out.println(COUNT);
             if (COUNT > 0) {
            	 return "Client Already Exists";
           	 
             }
	      }
	      rs0.close();
	      
	      
	      
	      ResultSet rs1 = stmt.executeQuery( "SELECT MAX(ID) as MAXID FROM CLIENT;" );
	      while ( rs1.next() ) {
	           
	         ID = rs1.getInt("MAXID");
             System.out.println(ID);
	      }
	      rs1.close();
	      stmt.close();
	      c.close();
	      ID++;
	      SQLStatement = "INSERT INTO CLIENT (ID, CLIENTSHORTNAME, CLIENTNAME) VALUES ( '" + ID + "' ,'" + txtClientShortName + "' ,'" + txtClient + "');";
	      SQLLiteExecStatement (SQLStatement); 	 
	      SelectFromClient();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
		return "SUCCESS";
	  }	
	
	
	/**
	 * Method: InsertNewTask 
	 * @param txtClientShortName
	 * @param txtPROJECTNAME
	 * @param txtTask
	 * @return
	 */
	public String  InsertNewTask(String txtClientShortName, String txtPROJECTNAME, String txtTask)
	  {
	    Connection c = null;
	    Statement stmt = null;
	    int ID = 0;
	    int COUNT = 0;
	    String SQLStatement;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      
	      ResultSet rs0 = stmt.executeQuery( "SELECT COUNT(*) as COUNT FROM TASK WHERE TASKNAME = '" + txtTask  + "' and PROJECTNAME = '" + txtPROJECTNAME +"';" );
	      
	      while ( rs0.next() ) {
	           
	         COUNT = rs0.getInt("COUNT");
             System.out.println(COUNT);
             if (COUNT > 0) {
            	 return "Task " + txtTask + " in Project" + txtPROJECTNAME +  " Already Exists";
           	 
             }
	      }
	      rs0.close();
	      
	      
	      
	      
	      
	      ResultSet rs = stmt.executeQuery( "SELECT MAX(ID) as MAXID FROM TASK;" );
	      while ( rs.next() ) {
	           
	         ID = rs.getInt("MAXID");
             System.out.println(ID);
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	      ID++;
	      SQLStatement = "INSERT INTO TASK (ID, CLIENTSHORTNAME, PROJECTNAME, TASKNAME) VALUES ( '" + ID + "' ,'" + txtClientShortName + "' ,'" + txtPROJECTNAME + "','" + txtTask + "');";
	      SQLLiteExecStatement (SQLStatement); 	 
	      SelectFromClient();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	    return ("Success");
	  }	
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
	public String  InsertTaskEvent(
			String txtTaskDate, 
			String txtClientShortName, 
			String txtProjectNAME,
			int txtTaskHour,
			int txtTaskMinute,
			int txtTaskSecond, 
			String txtTask)
	{
		 Connection c = null;
		    Statement stmt = null;
		    int ID = 0;
		    int COUNT = 0;
		    String SQLStatement;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
 		      
		      ResultSet rs = stmt.executeQuery( "SELECT MAX(ID) as MAXID FROM TASKSUMMARY;" );
		      while ( rs.next() ) {
		           
		         ID = rs.getInt("MAXID");
	             System.out.println(ID);
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		      ID++;
		      SQLStatement = "INSERT INTO TASKSUMMARY" +
		      		" (" +
		      		" ID, " +
		      		" TASKDATE," +
		    		" CLIENTSHORTNAME," +
		      		" PROJECTNAME, " +
		      	    " TASKHOUR," +
                    " TASKMINUTE," +
                    " TASKSECOND," +
		      		" TASKNAME" +
		      	    " )" +
		      		" VALUES ( '"
		      		+ ID + "' ,'"
		      		+ txtTaskDate + "','"
			        + txtClientShortName+ "','"
			        + txtProjectNAME+ "',"
			        + txtTaskHour+ ","
			        + txtTaskMinute+ ","
			        + txtTaskSecond + ",'" 
	 		      	+ txtTask + "');";
		      System.out.println(SQLStatement);
		      SQLLiteExecStatement (SQLStatement); 	 
		     
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Operation done successfully");
		    return ("Success");
		
	}
	
	public ArrayList<String> SelectFromClient()
	 {
	    Connection c = null;
	    Statement stmt = null;
	    int i = 0;
	    ArrayList returnMatrix = null;
        returnMatrix = new ArrayList();
	   // returnMatrix.addAll(new ArrayList());
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	     
	      
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM CLIENT ORDER BY CLIENTSHORTNAME;" );
	      while ( rs.next() ) {
	         
	         int   ID = rs.getInt("ID");
	         String CLIENTSHORTNAME  = rs.getString("CLIENTSHORTNAME");
	         String CLIENTNAME  = rs.getString("CLIENTNAME");
	         System.out.println( "ID = " + ID );
	         System.out.println( "CLIENTSHORTNAME = " + CLIENTSHORTNAME );
	         System.out.println( "CLIENTNAME = " + CLIENTNAME );
	         System.out.println();
	         returnMatrix.add(new ArrayList());
	         ((ArrayList)returnMatrix.get(i)).add(CLIENTSHORTNAME);
	         ((ArrayList)returnMatrix.get(i)).add(CLIENTNAME);
	         i++;
	         System.out.println("loop");
	         
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
		
		
		
		return returnMatrix;
	}
	
	public ArrayList<String> SelectDistinctTasksbyClientProject(String txtClientShortname, String txtProjectName)
	{
		Connection c = null;
	    Statement stmt = null;
	    String sqlstmt;
	    int i = 0;
	    ArrayList returnMatrix = null;
        returnMatrix = new ArrayList();
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      
	      sqlstmt =  "SELECT TASKNAME FROM TASK WHERE CLIENTSHORTNAME = '"+ txtClientShortname + "' AND PROJECTNAME = '"+ txtProjectName + "';";
	      System.out.println(sqlstmt);
	      
	      ResultSet rs = stmt.executeQuery(sqlstmt);
	      
	     
	      
	      while ( rs.next() ) {
	         String TASKNAME  = rs.getString("TASKNAME");
	         System.out.println( "TASKNAME = " + TASKNAME );
	         System.out.println();
	         returnMatrix.add(new ArrayList());
	         ((ArrayList)returnMatrix.get(i)).add(TASKNAME);
	         i++;
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	    return returnMatrix;
		
	}
	  
	public ArrayList<String> SelectDistinctProjectsbyClient (String txtClientShortname)
	{
		 Connection c = null;
		    Statement stmt = null;
		    String sqlstmt;
		    int i = 0;
		    ArrayList returnMatrix = null;
	        returnMatrix = new ArrayList();
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      
		      sqlstmt =  "SELECT PROJECTNAME,COUNT(*) FROM TASK WHERE CLIENTSHORTNAME = '"+ txtClientShortname + "' GROUP BY PROJECTNAME;";
		      System.out.println(sqlstmt);
		      
		      ResultSet rs = stmt.executeQuery(sqlstmt);
		      
		     
		      
		      while ( rs.next() ) {
		         String PROJECTNAME  = rs.getString("PROJECTNAME");
		         System.out.println( "PROJECTNAME = " + PROJECTNAME );
		         System.out.println();
		         returnMatrix.add(new ArrayList());
		         ((ArrayList)returnMatrix.get(i)).add(PROJECTNAME);
		         i++;
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Operation done successfully");
		    return returnMatrix;
	}
	
	
	
	

	public ArrayList<String> SelectFromTask()
	 {
	    Connection c = null;
	    Statement stmt = null;
	    int i = 0;
	    ArrayList returnMatrix = null;
        returnMatrix = new ArrayList();
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM TASK ORDER BY CLIENTSHORTNAME, PROJECTNAME, TASKNAME;" );
	      while ( rs.next() ) {
	         
	         int   ID = rs.getInt("ID");
	         String CLIENTSHORTNAME  = rs.getString("CLIENTSHORTNAME");
	         String PROJECTNAME  = rs.getString("PROJECTNAME");
	         String TASKNAME  = rs.getString("TASKNAME");
	         System.out.println( "ID = " + ID );
	         System.out.println( "CLIENTSHORTNAME = " + CLIENTSHORTNAME );
	         System.out.println( "PROJECTNAME = " + PROJECTNAME );
	         System.out.println( "TASKNAME = " + TASKNAME );
	         System.out.println();
	         returnMatrix.add(new ArrayList());
	         ((ArrayList)returnMatrix.get(i)).add(CLIENTSHORTNAME);
	         ((ArrayList)returnMatrix.get(i)).add(PROJECTNAME);
	         ((ArrayList)returnMatrix.get(i)).add(TASKNAME);
	         i++;
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	    return returnMatrix;
	}
	
	public void PrintTaskSummary()
	{
		
	}
	
	
	public  void SelectFromTASKSUMMARY()
	  {
	    Connection c = null;
	    Statement stmt = null;
	    GregorianCalendar d = new GregorianCalendar();
	    try {
	    	
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
	    	
	    	
	    	
	    	
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM TASKSUMMARY;" );
	      while ( rs.next() ) {
	         
	    	  /*
	    	   "(ID						INT PRIMARY KEY		NOT NULL," +
	                   "TASKDATE       TEXT    NOT NULL," +
	                   " CLIENTSHORTNAME 	TEXT    NOT NULL," +
	                   " PROJECTNAME       TEXT    NOT NULL, " +
	                   " TASKHOUR       INT     NOT NULL," +
	                   " TASKMINUTE     INT     NOT NULL," +
	                   " TASKSECOND     INT     NOT NULL," +
	                   " TASKNAME           TEXT    NOT NULL) "
	    	  */
	    	 int ID = rs.getInt("ID"); 
	         String  TASKDATE = rs.getString("TASKDATE");
	         String  CLIENTNAME = rs.getString("CLIENTSHORTNAME");
	         String  PROJECTNAME = rs.getString("PROJECTNAME");
	         String  TASKNAME = rs.getString("TASKNAME");
	         int TASKHOUR  = rs.getInt("TASKHOUR");
	         int TASKMINUTE  = rs.getInt("TASKMINUTE");
	         int TASKSECOND  = rs.getInt("TASKSECOND");
         
	         System.out.println( "ID = " + ID );
	         System.out.println( "TASKDATE = " + TASKDATE );
	         System.out.println( "CLIENTNAME = " + CLIENTNAME );
	         System.out.println( "PROJECTNAME = " + PROJECTNAME );
	         System.out.println( "TASKNAME = " + TASKNAME );
	         System.out.println( "TASKHOUR = " + TASKHOUR );
	         System.out.println( "TASKMINUTE = " + TASKMINUTE );
	         System.out.println( "TASKSECOND = " + TASKSECOND );
	         System.out.println();
	         
	         outputStream.print(ID);
   	    	 outputStream.print("\t");
   	    	 outputStream.write(TASKDATE);
   	    	 outputStream.print("\t");
     	     outputStream.write(CLIENTNAME + "\t" + PROJECTNAME +"\t"+ TASKNAME +"\t" );
     	     outputStream.print(TASKHOUR);
     	     outputStream.print("\t");
     	     outputStream.print(TASKMINUTE);
     	     outputStream.print("\t");
     	     outputStream.println(TASKSECOND);
	         
	         
	         
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	      outputStream.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.out.println("Operation done with error");
	      System.exit(0);
	    
	  }
	/*
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
   	    
   	    for(TaskTime TT :Tdemo.l){
        		 
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
   		*/
   	}
  	    
	
}