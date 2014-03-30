package org.zahavas.chessclock.source;


import java.io.File;
import java.sql.*;

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
	    	
	    	File file =new File("chessclock.db");
	   	 
    		//if file exists, then exit
	    	if(file.exists()){
    			return;
    		}	
	    	
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:chessclock.db");
	      System.out.println("Opened database successfully");
  
	      
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE CLIENT " +
	                   "(ID 			INT 	PRIMARY KEY     NOT NULL," +
	                   " CLIENTNAME     TEXT    NOT NULL) "; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      System.out.println("Table Client added successfully");
	      
	      stmt = c.createStatement();
	      sql = 	   "CREATE TABLE TASK " +
	                   "(ID 					INT PRIMARY KEY     NOT NULL," +
	                   " CLIENTNAME 			TEXT NOT NULL," +
	                   " TASKNAME           	TEXT NOT NULL) "; 
	      SQLLiteCreateTable (sql);
	      System.out.println("Table Task added successfully");
	      
	      stmt = c.createStatement();
	      sql = "CREATE TABLE TASKSUMMARY " +
	                   "(TASKDATE       TEXT    NOT NULL," +
	                   " CLIENTNAME 	TEXT    NOT NULL," +
	                   " TASKNAME       TEXT    NOT NULL, " +
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
	
	public  void SelectFromTASKSUMMARY()
	  {
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:chessclock.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM TASKSUMMARY;" );
	      while ( rs.next() ) {
	         
	         String  TASKDATE = rs.getString("TASKDATE");
	         String  CLIENTNAME = rs.getString("CLIENTNAME");
	         String  TASKNAME = rs.getString("TASKNAME");
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
