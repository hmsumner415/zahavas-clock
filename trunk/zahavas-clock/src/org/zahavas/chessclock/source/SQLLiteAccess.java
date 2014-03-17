package org.zahavas.chessclock.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLLiteAccess {

	/**
	 * SQLMakeTable
	 * @param String containing a Create Table statement 
	 * @param boolean
	 * @author  HSumner
	 * 
	 * eg: "CREATE TABLE COMPANY " +
	 *                  "(ID INT PRIMARY KEY     NOT NULL," +
	 *                  " NAME           TEXT    NOT NULL, " + 
	 *                  " AGE            INT     NOT NULL, " + 
	 *                  " ADDRESS        CHAR(50), " + 
	 *                  " SALARY         REAL)"
	 */
	public boolean SQLLiteCreateTable (String aStmt)
	{

		   Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      String sql =  aStmt; 
		     	      
		      stmt.executeUpdate(sql);
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Table created successfully");
		 return true;
		
	}
	
	 /**
		 * SQLLiteExecStatement
		 * @param Input String containing a statement 
		 * @param Return boolean
		 * @author  HSumner
		 * 
		 * Eg: "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
        *     "VALUES (1, 'Paul', 32, 'California', 20000.00 );"
        *     
        *     "DELETE from COMPANY where ID=2;"
        *     
        *     "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";

		 */
	
	 public boolean SQLLiteExecStatement (String aStmt) 
	  {

		 
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = aStmt; 
	      stmt.executeUpdate(sql);

	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      return false;
	    }
	    System.out.println("Statement executed successfully");
	    return true;
	  }
	
}
