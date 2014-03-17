package org.zahavas.chessclock.source;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLProject extends SQLLiteAccess
{
	/**
	 *  Class constructor
	 *  
	 *  Generates Database schema for first time use 	 *  
	 *  
	 */
	public void SQLProject()
	{
		Connection c = null;
	    Statement stmt = null;
	    try {
	    	
	    	File file =new File("org.zahavas.chessclock.db");
	   	 
    		//if file exists, then exit
	    	if(file.exists()){
    			return;
    		}	
	    	
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:org.zahavas.chessclock.db");
	      System.out.println("Opened database successfully");
  
	      
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE Client " +
	                   "(ID 			INT 	PRIMARY KEY     NOT NULL," +
	                   " CLIENTNAME     TEXT    NOT NULL) "; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      System.out.println("Table Client added successfully");
	      
	      stmt = c.createStatement();
	      sql = 	   "CREATE TABLE Task " +
	                   "(ID 					INT PRIMARY KEY     NOT NULL," +
	                   " CLIENTNAME 			TEXT NOT NULL," +
	                   " TASKNAME           	TEXT NOT NULL) "; 
	      SQLLiteCreateTable (sql);
	      System.out.println("Table Task added successfully");
	      
	      stmt = c.createStatement();
	      sql = "CREATE TABLE TaskSummary " +
	                   "(ID INT PRIMARY KEY     NOT NULL," +
	                   " TASKDATE       TEXT    NOT NULL" +
	                   " CLIENTNAME 			TEXT NOT NULL," +
	                   " TASKNAME           	TEXT NOT NULL, " +
	                   " TASKHOUR       INT     NOT NULL" +
	                   " TASKMINUTE     INT     NOT NULL" +
	                   " TASKSECOND     INT     NOT NULL" +
	                   " NAME           TEXT    NOT NULL, "; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      System.out.println("Table Client successfully");
	      
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
		
		
	}
	
	
	
}
