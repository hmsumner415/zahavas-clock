package org.zahavas.chessclock.source;


import java.io.File;
import java.sql.*;
import java.util.ArrayList;

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
	                   "(TASKDATE       TEXT    NOT NULL," +
	                   " CLIENTSHORTNAME 	TEXT    NOT NULL," +
	                   " PROJECTNAME       TEXT    NOT NULL, " +
	                   " TASKHOUR       INT     NOT NULL," +
	                   " TASKMINUTE     INT     NOT NULL," +
	                   " TASKSECOND     INT     NOT NULL," +
	                   " NAME           TEXT    NOT NULL) "; 
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
	
	public  void SelectFromTASKSUMMARY()
	  {
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM TASKSUMMARY;" );
	      while ( rs.next() ) {
	         
	         String  TASKDATE = rs.getString("TASKDATE");
	         String  CLIENTNAME = rs.getString("CLIENTSHORTNAME");
	         String  TASKNAME = rs.getString("PROJECTNAME");
	         int TASKHOUR  = rs.getInt("TASKHOUR");
	         int TASKMINUTE  = rs.getInt("TASKMINUTE");
	         int TASKSECOND  = rs.getInt("TASKSECOND");
         
	         System.out.println( "TASKDATE = " + TASKDATE );
	          
	         System.out.println( "CLIENTNAME = " + CLIENTNAME );
	         System.out.println( "TASKNAME = " + TASKNAME );
	         System.out.println( "TASKHOUR = " + TASKHOUR );
	         System.out.println( "TASKMINUTE = " + TASKMINUTE );
	         System.out.println( "TASKSECOND = " + TASKSECOND );
	         System.out.println();
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	  }
	
	
	
}
