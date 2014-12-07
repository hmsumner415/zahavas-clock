package org.zahavas.chessclock.source;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;

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
	    	EventLogger EV = new EventLogger();
	    	
			 
			EV.LogEvent("Construct SQL Project", "INFO");
			
	    	File file =new File("tasktracker.db");
	   	 
    		//if file exists, then exit
	    	if(file.exists()){
    			return;
    		}	
	    	
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      //////System.out.println("Opened database successfully");
  
	      
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE CLIENT " +
	                   "(ID 					INT 	PRIMARY KEY     NOT NULL," +
	                   " CLIENTSHORTNAME 		TEXT 	NOT NULL," +
	                   " CLIENTNAME     		TEXT    NOT NULL) "; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      //////System.out.println("Table Client added successfully");
	      
	      stmt = c.createStatement();
	      sql = 	   "CREATE TABLE TASK " +
	                   "(ID 					INT PRIMARY KEY     NOT NULL," +
	                   " CLIENTSHORTNAME 		TEXT NOT NULL," +
	                   " PROJECTNAME 			TEXT NOT NULL," +
	                   " TASKNAME           	TEXT NOT NULL) "; 
	      SQLLiteCreateTable (sql);
	      ////System.out.println("Table Task added successfully");
	      
	      stmt = c.createStatement();
	      sql = "CREATE TABLE TASKSUMMARY " +
	                   "(ID						INT PRIMARY KEY		NOT NULL," +
	                   "TASKDATE       TEXT    NOT NULL," +
	                   " CLIENTSHORTNAME 	TEXT    NOT NULL," +
	                   " PROJECTNAME       TEXT    NOT NULL, " +
	                   " TASKHOUR       INT     NOT NULL," +
	                   " TASKMINUTE     INT     NOT NULL," +
	                   " TASKSECOND     INT     NOT NULL," +
	                   " TASKNAME       TEXT    NOT NULL," +
	                   " TASKCOUNTER    REAL NOT NULL," +
	                   " WEEKOFYEAR     INT NOT NULL," +
	                   " MONTHOFYEAR    INT NOT NULL," +
	                   " YEAR    		INT NOT NULL" +
	                   ") "; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      ////System.out.println("Table tasksummary successfully");
	      
	      
	      
	      stmt = c.createStatement();
	      sql = "CREATE TABLE APPLICATION " +
	                   "(ID					INT PRIMARY KEY		NOT NULL," +
	                   " APPLICATIONAME        TEXT    NOT NULL) "; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      ////System.out.println("Table Application successfully");
	      
	      stmt = c.createStatement();
	      sql = "CREATE TABLE APPLICATIONTASK " +
	                   "(ID					INT PRIMARY KEY		NOT NULL," +
	                   " APPLICATIONAME        TEXT    NOT NULL," +
	                   " CLIENTSHORTNAME 		TEXT NOT NULL," +
	                   " PROJECTNAME 			TEXT NOT NULL," +
	                   " TASKNAME           	TEXT NOT NULL," +
	                   " LINKAGETYPE	        TEXT NOT NULL)"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      ////System.out.println("Table Application successfully");
	      
	      
	      c.close();
	    } catch ( Exception e ) {
	    	
	     String Error  =  e.getClass().getName() + ": " + e.getMessage();	
		 System.err.println( Error );
		 EV.LogEvent(Error, "SEVERE");	
	     System.exit(0);
	    }
	    
		
		
	}
	/**
	 * Method: InsertApplication
	 * @param txtApplication
	 * @return
	 */
	public String InsertApplication(String txtApplication)
	{	//////System.out.println("insert application");
		Connection c = null;
	    Statement stmt = null;
	    int ID = 0;
	    int COUNT = 0;
	    String SQLStatement;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      c.setAutoCommit(false);
	     // ////System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs0 = stmt.executeQuery( "SELECT COUNT(*) as COUNT FROM APPLICATION WHERE APPLICATIONAME = '" + txtApplication  + "';" );
	      while ( rs0.next() ) {
	           
	         COUNT = rs0.getInt("COUNT");
             //////System.out.println(COUNT);
             if (COUNT > 0) {
            	 rs0.close();
       	         stmt.close();
    	         c.close();
            	// ////System.out.println("Application Already Exists");
    	         return "Application Already Exists";
           	 
             }
	      }
	      rs0.close();
	      //////System.out.println("Here1");
	      
	      
	      ResultSet rs1 = stmt.executeQuery( "SELECT MAX(ID) as MAXID FROM APPLICATION;" );
	      while ( rs1.next() ) {
	           
	         ID = rs1.getInt("MAXID");
             //////System.out.println(ID);
	      }
	      rs1.close();
	      stmt.close();
	      c.close();
	      ID++;
	      SQLStatement = "INSERT INTO APPLICATION (ID, APPLICATIONAME) VALUES ( '" + ID + "' ,'" + txtApplication + "');";
	      SQLLiteExecStatement (SQLStatement); 	 
	      
	    } catch ( Exception e ) {
	      
	      String Error  =  e.getClass().getName() + ": " + e.getMessage();	
	      System.err.println( Error );
	      EV.LogEvent(Error, "SEVERE");
	      System.exit(0);
	    }
	  //  ////System.out.println("Operation done successfully");
		
		return "Success";
	}
	
	/**
	 * Method: InsertNewClient
	 * @param txtClientShortName
	 * @param txtClient
	 * @return
	 */
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
	      ////System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs0 = stmt.executeQuery( "SELECT COUNT(*) as COUNT FROM CLIENT WHERE CLIENTSHORTNAME = '" + txtClientShortName  + "';" );
	      while ( rs0.next() ) {
	           
	         COUNT = rs0.getInt("COUNT");
             ////System.out.println(COUNT);
             if (COUNT > 0) {
            	 rs0.close();
       	         stmt.close();
    	         c.close();
            	 return "Client Already Exists";
           	 
             }
	      }
	      rs0.close();
	      
	      
	      
	      ResultSet rs1 = stmt.executeQuery( "SELECT MAX(ID) as MAXID FROM CLIENT;" );
	      while ( rs1.next() ) {
	           
	         ID = rs1.getInt("MAXID");
             ////System.out.println(ID);
	      }
	      rs1.close();
	      stmt.close();
	      c.close();
	      ID++;
	      SQLStatement = "INSERT INTO CLIENT (ID, CLIENTSHORTNAME, CLIENTNAME) VALUES ( '" + ID + "' ,'" + txtClientShortName + "' ,'" + txtClient + "');";
	      SQLLiteExecStatement (SQLStatement); 	 
	      SelectFromClient();
	    } catch ( Exception e ) {
	    	 String Error  =  e.getClass().getName() + ": " + e.getMessage();	
		      System.err.println( Error );
		      EV.LogEvent(Error, "SEVERE");
	      System.exit(0);
	    }
	    ////System.out.println("Operation done successfully");
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
	      ////System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      
	      ResultSet rs0 = stmt.executeQuery( "SELECT COUNT(*) as COUNT FROM TASK WHERE TASKNAME = '" + txtTask  + "' and PROJECTNAME = '" + txtPROJECTNAME +"';" );
	      
	      while ( rs0.next() ) {
	           
	         COUNT = rs0.getInt("COUNT");
             ////System.out.println(COUNT);
             if (COUNT > 0) {
            	 rs0.close();
       	         stmt.close();
    	         c.close();
            	 return "Task " + txtTask + " in Project" + txtPROJECTNAME +  " Already Exists";
           	 
             }
	      }
	      rs0.close();
	      
	      
	      
	      
	      
	      ResultSet rs = stmt.executeQuery( "SELECT MAX(ID) as MAXID FROM TASK;" );
	      while ( rs.next() ) {
	           
	         ID = rs.getInt("MAXID");
             ////System.out.println(ID);
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	      ID++;
	      SQLStatement = "INSERT INTO TASK (ID, CLIENTSHORTNAME, PROJECTNAME, TASKNAME) VALUES ( '" + ID + "' ,'" + txtClientShortName + "' ,'" + txtPROJECTNAME + "','" + txtTask + "');";
	      SQLLiteExecStatement (SQLStatement); 	 
	      SelectFromClient();
	    } catch ( Exception e ) {
	    	 String Error  =  e.getClass().getName() + ": " + e.getMessage();	
		      System.err.println( Error );
		      EV.LogEvent(Error, "SEVERE");
	      System.exit(0);
	    }
	    ////System.out.println("Operation done successfully");
	    return ("Success");
	  }	
	
	
	/**
	 * Method InsertMustUseApplicationForTask
	 * "Insert TABLE APPLICATIONTASK " +
	                   "(ID					INT PRIMARY KEY		NOT NULL," +
	                   " APPLICATIONAME        TEXT    NOT NULL," +
	                   " CLIENTSHORTNAME 		TEXT NOT NULL," +
	                   " PROJECTNAME 			TEXT NOT NULL," +
	                   " TASKNAME           	TEXT NOT NULL," +
	                   " LINKAGETYPE	        'MUST USE')"; 
	      
	 */
	
	public String InsertMustUseApplicationForTask(String txtClientShortName, String txtPROJECTNAME, String txtTask, String txtApplication)
	{
		////System.out.println("I1");
		Connection c = null;
	    Statement stmt = null;
	    int ID = 0;
	    int COUNT = 0;
	    String SQLStatement;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      c.setAutoCommit(false);
	      ////System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ////System.out.println("I2");
	      
	      ////System.out.println(txtClientShortName);
	      
	      SQLStatement =   "SELECT COUNT(*) as COUNT FROM APPLICATIONTASK" +
		      		" WHERE " +
		    		" CLIENTSHORTNAME = '" + txtClientShortName + "' and " +
		      		" APPLICATIONAME = '" + txtApplication + "' and " +
		      		" TASKNAME = '" + txtTask  + "' and" +
		      		" PROJECTNAME = '" + txtPROJECTNAME +"';" ;
	      ////System.out.println(SQLStatement);
	      
	      ResultSet rs0 = stmt.executeQuery(SQLStatement  );
	      
	      while ( rs0.next() ) {
	           
	         COUNT = rs0.getInt("COUNT");
             ////System.out.println(COUNT);
             if (COUNT > 0) {
            	 rs0.close();
       	         stmt.close();
    	         c.close();
            	 return "Duplicate Must use Linkage";
           	 
             }
	      }
	      rs0.close();
	      
	      ////System.out.println("I3");
	      
	      
	      
	      ResultSet rs = stmt.executeQuery( "SELECT MAX(ID) as MAXID FROM APPLICATIONTASK;" );
	      while ( rs.next() ) {
	           
	         ID = rs.getInt("MAXID");
             ////System.out.println(ID);
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	      ID++;
	      SQLStatement = "INSERT INTO APPLICATIONTASK (ID, APPLICATIONAME, LINKAGETYPE,  CLIENTSHORTNAME, PROJECTNAME, TASKNAME) " +
	      		"VALUES ( '" + ID + "' ,'" +
	      		  txtApplication + "', 'MUSTUSE', '" +
	    		  txtClientShortName + "' ,'" + 
	      		  txtPROJECTNAME + "','" + 
	    		  txtTask + "');";
	      ////System.out.println(SQLStatement);
	      SQLLiteExecStatement (SQLStatement); 	 
	      //SelectFromClient();
	    } catch ( Exception e ) {
	    	 String Error  =  e.getClass().getName() + ": " + e.getMessage();	
		      System.err.println( Error );
		      EV.LogEvent(Error, "SEVERE");
	      System.exit(0);
	    }
	    ////System.out.println("Operation done successfully");
		
		
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
			int intTaskHour,
			int intTaskMinute,
			int intTaskSecond, 
			String txtTask)
	{
		 Connection c = null;
		    Statement stmt = null;
		    int ID = 0;
		    int intTaskCounter =  intTaskHour*60*60 + 	 intTaskMinute * 60 + intTaskSecond;
		    
		     
		    String datepiece[] = txtTaskDate.split("/");
		    	       
		    int intDay = Integer.parseInt(datepiece[1]);
		    int intYear = Integer.parseInt(datepiece[2]);
		    int intMonthofYear =  Integer.parseInt(datepiece[0]);
		   // GregorianCalendar d = new GregorianCalendar(intYear,intMonthofYear, intDay );
		    
		    GregorianCalendar ee = new GregorianCalendar();
		    int intWeekofYear = ee.get(Calendar.WEEK_OF_YEAR);
		    String SQLStatement;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
		      c.setAutoCommit(false);
		      ////System.out.println("Opened database successfully");

		      stmt = c.createStatement();
 		      
		      ResultSet rs = stmt.executeQuery( "SELECT MAX(ID) as MAXID FROM TASKSUMMARY;" );
		      while ( rs.next() ) {
		           
		         ID = rs.getInt("MAXID");
	             ////System.out.println(ID);
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
                    " TASKCOUNTER, " +
                    " WEEKOFYEAR," +
                    " MONTHOFYEAR," +
                    " YEAR," +
		      		" TASKNAME" +
                     " )" +
		      		" VALUES ( '"
		      		+ ID + "' ,'"
		      		+ txtTaskDate + "','"
			        + txtClientShortName+ "','"
			        + txtProjectNAME+ "',"
			        + intTaskHour+ ","
			        + intTaskMinute+ ","
			        + intTaskSecond + ","
			        + intTaskCounter + ","
			        + intWeekofYear + ","
		      		+ intMonthofYear + "," 
			        + intYear + ",'"
	 		      	+ txtTask + "');";
		      ////System.out.println(SQLStatement);
		      SQLLiteExecStatement (SQLStatement); 	 
		     
		    } catch ( Exception e ) {
		    	 String Error  =  e.getClass().getName() + ": " + e.getMessage();	
			      System.err.println( Error );
			      EV.LogEvent(Error, "SEVERE");
		      System.exit(0);
		    }
		    ////System.out.println("Operation done successfully");
		    return ("Success");
		
	}
	
	public void SelectTaskSummaryReport(boolean bCl, boolean bP, boolean bT, boolean bRptByMonth, boolean bRptByWeek,String dateFromMonth, String dateFromYear, String dateToMonth, String dateToYear)
	{
		
		if (bT) {bCl = true; bP=true;} 
		if (bP) {bCl = true;}
		
		String sClient = bCl ? "CLIENTSHORTNAME " : "";
		String sProject = bP ? "PROJECTNAME " : "";
		String sTask = bT ? "TASKNAME " : "";
		
		
		String sRptByMonth = bRptByMonth ? "MONTHOFYEAR "  : "";
		String sRptByWeek = bRptByWeek ? "WEEKOFYEAR "  : "";
		
		String baseQuery = " cast( sum(TASKCOUNTER)/(3600) as int) 'hours'," +
				" cast( (sum(TASKCOUNTER) -  3600* cast(sum(TASKCOUNTER)/(3600) as int))/60 as int)  'minutes'" +
				"  from TASKSUMMARY ";
		 
		String whereClause = "";
		if (!dateFromMonth.isEmpty() && !dateFromYear.isEmpty()  && !dateToMonth.isEmpty() ) 
		{whereClause = " where MONTHOFYEAR >= " +dateFromMonth + " and Year >= " + dateFromYear + " and MONTHOFYEAR <= " + dateToMonth ;} 
		
		if (!dateFromMonth.isEmpty() && !dateFromYear.isEmpty()  && dateToMonth.isEmpty()  ) 
		{whereClause = " where MONTHOFYEAR >= " +dateFromMonth + " and Year >= " + dateFromYear;} 
		
		
		String builtQuery = "";
		String groupbyQuery = "";
		if (bCl &&  bP &&   bT) 
			{builtQuery = "select " + sClient +   "," + sProject + "," + sTask + ","; 
			 groupbyQuery = " group by "  + sClient +   "," + sProject + "," + sTask;
			}
		
		if (bCl &&  bP &&  !bT) 
			{builtQuery = "select " + sClient +   "," + sProject + ",";
			groupbyQuery = " group by " + sClient +   "," + sProject ;
			
			}
		if (bCl && !bP &&  !bT) 
			{builtQuery = "select " + sClient + ",";
			groupbyQuery = " group by " + sClient;
			}
		
		if (bRptByMonth) 
			{
			builtQuery += sRptByMonth + ",";
			groupbyQuery += "," + sRptByMonth;
			}
		
		if (bRptByWeek) 
			{
			builtQuery +=  sRptByWeek + ",";
			groupbyQuery += "," + sRptByWeek;
			}
		
		
		
		builtQuery += baseQuery + whereClause  + groupbyQuery +";";
		System.out.println(builtQuery);
		
		Connection c = null;
	    Statement stmt = null;
	    
	    
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      c.setAutoCommit(false);
          
	      stmt = c.createStatement();
	      ResultSet rs1 = stmt.executeQuery( builtQuery );
	      
	      XMLUtilities XML = new XMLUtilities();
	      xlts_Transforms  XLTS = new xlts_Transforms();
	      Document xmlString = XML.SQLResultSettoXMLDocument(rs1);
	      XLTS.xltsTaskSummaryReport(xmlString);
	      EV.LogEvent(xmlString.toString(), "INFO");
	      
	      
	   
	      rs1.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	     String Error  =  e.getClass().getName() + ": " + e.getMessage();	
		 System.err.println( Error );
		 EV.LogEvent(Error, "SEVERE");
	     System.exit(0);
	    }
		
		
		
		
	}
	
	/**
	 * 
	 */
	public void SelectFromClientReport()
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
          
	      stmt = c.createStatement();
	      ResultSet rs1 = stmt.executeQuery( "SELECT * FROM CLIENT ORDER BY CLIENTSHORTNAME;" );
	      
	      XMLUtilities XML = new XMLUtilities();
	      xlts_Transforms  XLTS = new xlts_Transforms();
	      Document xmlString = XML.SQLResultSettoXMLDocument(rs1);
	      XLTS.xltsClientListing(xmlString);
	      EV.LogEvent(xmlString.toString(), "INFO");
	      
	      
	   
	      rs1.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	     String Error  =  e.getClass().getName() + ": " + e.getMessage();	
		 System.err.println( Error );
		 EV.LogEvent(Error, "SEVERE");
	     System.exit(0);
	    }
	    
				
		 
	}
	
	/**
	 * SelectFromClient()
	 * Used for Filling Client Dropdown
	 * 
	 * @return ArrayList<String>
	 */
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
          
	      stmt = c.createStatement();
 
	      
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM CLIENT ORDER BY CLIENTSHORTNAME;" );
	      while ( rs.next() ) {
	         
	         int   ID = rs.getInt("ID");
	         String CLIENTSHORTNAME  = rs.getString("CLIENTSHORTNAME");
	         String CLIENTNAME  = rs.getString("CLIENTNAME");

	         returnMatrix.add(new ArrayList());
	         ((ArrayList)returnMatrix.get(i)).add(CLIENTSHORTNAME);
	         ((ArrayList)returnMatrix.get(i)).add(CLIENTNAME);
	         i++;
	         
	         
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	     String Error  =  e.getClass().getName() + ": " + e.getMessage();	
		 System.err.println( Error );
		 EV.LogEvent(Error, "SEVERE");
	     System.exit(0);
	    }
	    ////System.out.println("Operation done successfully");
				
		return returnMatrix;
	}

	public ArrayList<String> SelectFromApplication()
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
		      ////System.out.println("Opened database successfully");
		     
		      
		      stmt = c.createStatement();
		      ResultSet rs1 = stmt.executeQuery( "SELECT * FROM APPLICATION ORDER BY APPLICATIONAME;" );
		      XMLUtilities XML = new XMLUtilities();
		      //Document xmlString = XML.SQLResultSettoXMLDocument(rs1);
		      //EV.LogEvent(xmlString.toString(), "INFO");
		      ////System.out.println("Start loop");
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM APPLICATION ORDER BY APPLICATIONAME;" );
		      while ( rs.next() ) {
		         
		         int   ID = rs.getInt("ID");
		         String APPLICATIONAME  = rs.getString("APPLICATIONAME");
		         
		         ////System.out.println( "ID = " + ID );
		         ////System.out.println( "APPLICATIONNAME = " + APPLICATIONAME );
		         ////System.out.println();
		         returnMatrix.add(new ArrayList());
		         ((ArrayList)returnMatrix.get(i)).add(APPLICATIONAME);
		         i++;
		         ////System.out.println("loop");
		         
		      }
		      
			  
		      rs.close();
		      stmt.close();
		      c.close();
		      //////System.out.println(xmlString.toString());
			  //  EV.LogEvent(xmlString.toString(), "INFO");
		    } catch ( Exception e ) {
		    	 String Error  =  e.getClass().getName() + ": " + e.getMessage();	
			      System.err.println( Error );
			      EV.LogEvent(Error, "SEVERE");
		      System.exit(0);
		    }
		    ////System.out.println("Operation done successfully");
		    
		   
		    
		    
		    
		    
		    return returnMatrix;
		
	}
	
	/**
	 * "CREATE TABLE APPLICATIONTASK " +
	                   "(ID					INT PRIMARY KEY		NOT NULL," +
	                   " APPLICATIONAME        TEXT    NOT NULL," +
	                   " CLIENTSHORTNAME 		TEXT NOT NULL," +
	                   " PROJECTNAME 			TEXT NOT NULL," +
	                   " TASKNAME           	TEXT NOT NULL," +
	                   " LINKAGETYPE	        TEXT NOT NULL)"
	 */
	public ArrayList<String> SelectApplicationTask()
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
		      ////System.out.println("Opened database successfully");
		     
		      
		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM APPLICATIONTASK WHERE LINKAGETYPE = 'MUSTUSE' ORDER BY APPLICATIONAME;" );
		      ////System.out.println("Start loop");
		      while ( rs.next() ) {
		         
		         int   ID = rs.getInt("ID");
		         String APPLICATIONAME  = rs.getString("APPLICATIONAME");
		         String CLIENTSHORTNAME  = rs.getString("CLIENTSHORTNAME");
		         String PROJECTNAME  = rs.getString("PROJECTNAME");
		         String TASKNAME  = rs.getString("TASKNAME");
		         String LINKAGETYPE  = rs.getString("LINKAGETYPE");
		         
		         
		         ////System.out.print( "ID = " + ID );
		         ////System.out.print( ":APPLICATIONNAME = " + APPLICATIONAME );
		         ////System.out.print(" " + CLIENTSHORTNAME + " " + PROJECTNAME + " " + TASKNAME);
		         ////System.out.println( " " + LINKAGETYPE );
		         ////System.out.println();
		         returnMatrix.add(new ArrayList());
		         ((ArrayList)returnMatrix.get(i)).add(APPLICATIONAME);
		         ((ArrayList)returnMatrix.get(i)).add(CLIENTSHORTNAME);
		         ((ArrayList)returnMatrix.get(i)).add(PROJECTNAME);
		         ((ArrayList)returnMatrix.get(i)).add(TASKNAME);
		         ((ArrayList)returnMatrix.get(i)).add(LINKAGETYPE);
		         i++;
		         
		         
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		      
		      
		    } catch ( Exception e ) {
		    	 String Error  =  e.getClass().getName() + ": " + e.getMessage();	
			      System.err.println( Error );
			      EV.LogEvent(Error, "SEVERE");
		      System.exit(0);
		    }
		    ////System.out.println("Operation done successfully");
		    
		    return returnMatrix;
		
	}
	
	
	
	/**
	 * 
	 * @param txtClientShortname
	 * @param txtProjectName
	 * @return
	 */
	
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
	      ////System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      
	      sqlstmt =  "SELECT TASKNAME FROM TASK WHERE CLIENTSHORTNAME = '"+ txtClientShortname + "' AND PROJECTNAME = '"+ txtProjectName + "' ORDER BY TASKNAME;";
	      ////System.out.println(sqlstmt);
	      
	      ResultSet rs = stmt.executeQuery(sqlstmt);
	      
	     
	      
	      while ( rs.next() ) {
	         String TASKNAME  = rs.getString("TASKNAME");
	         ////System.out.println( "TASKNAME = " + TASKNAME );
	         ////System.out.println();
	         returnMatrix.add(new ArrayList());
	         ((ArrayList)returnMatrix.get(i)).add(TASKNAME);
	         i++;
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	    	 String Error  =  e.getClass().getName() + ": " + e.getMessage();	
		      System.err.println( Error );
		      EV.LogEvent(Error, "SEVERE");
	      System.exit(0);
	    }
	    ////System.out.println("Operation done successfully");
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
		      ////System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      
		      sqlstmt =  "SELECT PROJECTNAME,COUNT(*) FROM TASK WHERE CLIENTSHORTNAME = '"+ txtClientShortname + "' GROUP BY PROJECTNAME;";
		      ////System.out.println(sqlstmt);
		      
		      ResultSet rs = stmt.executeQuery(sqlstmt);
		      
		     
		      
		      while ( rs.next() ) {
		         String PROJECTNAME  = rs.getString("PROJECTNAME");
		         ////System.out.println( "PROJECTNAME = " + PROJECTNAME );
		         ////System.out.println();
		         returnMatrix.add(new ArrayList());
		         ((ArrayList)returnMatrix.get(i)).add(PROJECTNAME);
		         i++;
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		    	 String Error  =  e.getClass().getName() + ": " + e.getMessage();	
			      System.err.println( Error );
			      EV.LogEvent(Error, "SEVERE");
		      System.exit(0);
		    }
		    ////System.out.println("Operation done successfully");
		    return returnMatrix;
	}
	
	
	public boolean IsMustHaveLink (String txtClientShortName, String txtProjectName, String txtTaskName, String txtApplicationName )
	{
		////System.out.println("I1");
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs0; 
	    int ID = 0;
	    int COUNT = 0;
	    String SQLStatement;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	      c.setAutoCommit(false);
	      ////System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ////System.out.println("I2");
	      
	      ////System.out.println(txtClientShortName);
	      // Test exact match
	      SQLStatement =   "SELECT COUNT(*) as COUNT FROM APPLICATIONTASK" +
		      		" WHERE LINKAGETYPE = 'MUSTHAVE' and" +
	    		    " APPLICATIONAME = '" + txtApplicationName + "' and " +
		      		" CLIENTSHORTNAME = '" + txtClientShortName + "' and " +
		      		" TASKNAME = '" + txtTaskName  + "' and" +
		      		" PROJECTNAME = '" + txtProjectName +"';" ;
	      ////System.out.println(SQLStatement);
	      
	      rs0 = stmt.executeQuery(SQLStatement  );
	      
	      while ( rs0.next() ) {
	           
	         COUNT = rs0.getInt("COUNT");
             ////System.out.println(COUNT);
             if (COUNT > 0) {
            	 rs0.close();
       	         stmt.close();
    	         c.close();
            	 return true;
           	 
             }
	      }
	      // Test for Idle Task
	      SQLStatement =   "SELECT COUNT(*) as COUNT FROM APPLICATIONTASK" +
	    		  " WHERE LINKAGETYPE = 'MUSTHAVE' and" +
	    		  " APPLICATIONAME = '" + txtApplicationName + "' and " +
		    		" CLIENTSHORTNAME = '" + txtClientShortName + "' and " +
		      		" TASKNAME in ( 'Idle', 'Any') and" +
		      		" PROJECTNAME = '" + txtProjectName +"';" ;
	      ////System.out.println(SQLStatement);
	      
	      rs0 = stmt.executeQuery(SQLStatement  );
	      
	      while ( rs0.next() ) {
	           
	         COUNT = rs0.getInt("COUNT");
           ////System.out.println(COUNT);
           if (COUNT > 0) {
          	 rs0.close();
     	     stmt.close();
  	         c.close();
          	 return true;
         	 
           }
	      }
	      
	      SQLStatement =   "SELECT COUNT(*) as COUNT FROM APPLICATIONTASK" +
	    		  " WHERE LINKAGETYPE = 'MUSTHAVE' and" +
	    		  " APPLICATIONAME = '" + txtApplicationName + "' and " +
		    		" CLIENTSHORTNAME = '" + txtClientShortName + "' and " +
		      		" TASKNAME in ( 'Idle', 'Any') and" +
		      		" PROJECTNAME in ( 'Idle', 'Any');" ;
	      ////System.out.println(SQLStatement);
	      
	      rs0 = stmt.executeQuery(SQLStatement  );
	      
	      while ( rs0.next() ) {
	           
	         COUNT = rs0.getInt("COUNT");
         ////System.out.println(COUNT);
         if (COUNT > 0) {
        	 rs0.close();
   	         stmt.close();
	         c.close();
        	 return true;
       	 
         }
	      }
	      
	      
	      
	    }
	      catch ( Exception e ) {
	    	  String Error  =  e.getClass().getName() + ": " + e.getMessage();	
		      System.err.println( Error );
		      EV.LogEvent(Error, "SEVERE");
		      System.exit(0);
		    }
	     return false; 
		
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
	      ////System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM TASK ORDER BY CLIENTSHORTNAME, PROJECTNAME, TASKNAME;" );
	      while ( rs.next() ) {
	         
	         int   ID = rs.getInt("ID");
	         String CLIENTSHORTNAME  = rs.getString("CLIENTSHORTNAME");
	         String PROJECTNAME  = rs.getString("PROJECTNAME");
	         String TASKNAME  = rs.getString("TASKNAME");
	         ////System.out.println( "ID = " + ID );
	         ////System.out.println( "CLIENTSHORTNAME = " + CLIENTSHORTNAME );
	         ////System.out.println( "PROJECTNAME = " + PROJECTNAME );
	         ////System.out.println( "TASKNAME = " + TASKNAME );
	         ////System.out.println();
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
	    	 String Error  =  e.getClass().getName() + ": " + e.getMessage();	
		      System.err.println( Error );
		      EV.LogEvent(Error, "SEVERE");
	      System.exit(0);
	    }
	    ////System.out.println("Operation done successfully");
	     
	    return returnMatrix;
	}
	
	
	public  void  SelectFromTASKSUMMARY  ()
	  {
	    Connection c = null;
	    Statement stmt = null;
	    GregorianCalendar d = new GregorianCalendar();
	    
	    try {
	    	
	    	  File file2 =new File("TaskSummary.txt");		    	   	 
	     		
	     		if(!file2.exists()){
	     			file2.createNewFile();
	     		}
	     		PrintWriter outputStream = null;
	     	    outputStream = new PrintWriter(file2.getName());
	     	    
	     	   Class.forName("org.sqlite.JDBC");
	 	      c = DriverManager.getConnection("jdbc:sqlite:tasktracker.db");
	 	      c.setAutoCommit(false);
	 	      ////System.out.println("Opened database successfully");

	 	      stmt = c.createStatement();
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
	 	      String QUERY = "SELECT CLIENTSHORTNAME," +
	 	      		" sum(60*60*TASKHOUR + 60*TASKMINUTE +TASKSECOND) as SUM," +
	 	      		" sum(TASKHOUR) as SH," +
	 	      		" sum(TASKMINUTE) as SM," +
	 	      		" sum(TASKSECOND) as SS," +
	 	      		" TASKDATE," +
	 	      		
  					" sum(60*60*TASKHOUR + 60*TASKMINUTE +TASKSECOND)/(60*60) as dbAggregateHOURS, " + 
  					" (sum(60*60*TASKHOUR + 60*TASKMINUTE +TASKSECOND) - sum(60*60*TASKHOUR + 60*TASKMINUTE +TASKSECOND)/(60*60*60))  as dbAggregateMINUTES, " +  
  					" (sum(60*60*TASKHOUR + 60*TASKMINUTE +TASKSECOND) - sum(60*60*TASKHOUR + 60*TASKMINUTE +TASKSECOND)/(60*60*60*60)) as  dbAggregateSeconds" +
			          	
	 	      		
	 	      		" from TASKSUMMARY" +
	 	    		" group by TASKDATE, CLIENTSHORTNAME" +  
	 	      		" ;";
	 	     ResultSet rs = stmt.executeQuery(QUERY); 
	 	     while ( rs.next() ) {
		         
	 	    	
		    	 String CLIENTSHORTNAME = rs.getString("CLIENTSHORTNAME"); 
		         Integer  SUM = rs.getInt("SUM");
		         Integer  SH = rs.getInt("SH");
		         Integer  SM = rs.getInt("SM");
		         Integer  SS = rs.getInt("SS");
		         
		         Integer  dbAggregateHOURS = rs.getInt("dbAggregateHOURS");
		         Integer  dbAggregateMINUTES = rs.getInt("dbAggregateMINUTES");
		         Integer  dbAggregateSECONDS = rs.getInt("dbAggregateSECONDS");
		         
		         
		         String  TASKDATE = rs.getString("TASKDATE");
		         
		         Integer AggregateHOURS = SUM/(60*60);
		         Integer AggregateMINUTES = (SUM - AggregateHOURS*(60*60))/60;
		         Integer AggregateSECONDS = SUM - AggregateHOURS*(60*60) - AggregateMINUTES*60;

		         outputStream.print(CLIENTSHORTNAME);
	   	    	 outputStream.print("\t");
	   	    	 outputStream.write(SUM.toString());
	   	    	 outputStream.print("\t");
	   	    	 outputStream.write(SH.toString());
	   	    	 outputStream.print("\t");
	   	    	 outputStream.write(SM.toString());
	   	    	 outputStream.print("\t");
	   	    	 outputStream.write(SS.toString());
	   	    	 outputStream.print("\t");
	   	    	 outputStream.print("JAVA Generated: ");
	   	    	 outputStream.write(AggregateHOURS.toString());
	   	    	 outputStream.print("\t");
	   	    	 outputStream.write(AggregateMINUTES.toString());
	   	    	 outputStream.print("\t");
	   	    	 outputStream.write(AggregateSECONDS.toString());
	   	    	 outputStream.print("\t");
	   	    	 outputStream.print("SQLLiteGenerated:");
	   	    	 outputStream.write(dbAggregateHOURS.toString());
	   	    	 outputStream.print("\t");
	   	    	 outputStream.write(dbAggregateMINUTES.toString());
	   	    	 outputStream.print("\t");
	   	    	 outputStream.write(dbAggregateSECONDS.toString());
	   	    	 outputStream.print("\t");
	   	    	    	    	 
	     	     outputStream.println(TASKDATE);
	     	      	
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		      outputStream.close();  
	    }
	    catch ( Exception e ) {
		     String Error  =  e.getClass().getName() + ": " + e.getMessage();	
			 System.err.println( Error );
			 EV.LogEvent(Error, "SEVERE");
		     System.exit(0);
	    }
	    finally {}
	    
	    
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
	      ////System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM TASKSUMMARY;" );
	      while ( rs.next() ) {
	         
	    	
	    	 int ID = rs.getInt("ID"); 
	         String  TASKDATE = rs.getString("TASKDATE");
	         String  CLIENTNAME = rs.getString("CLIENTSHORTNAME");
	         String  PROJECTNAME = rs.getString("PROJECTNAME");
	         String  TASKNAME = rs.getString("TASKNAME");
	         int TASKHOUR  = rs.getInt("TASKHOUR");
	         int TASKMINUTE  = rs.getInt("TASKMINUTE");
	         int TASKSECOND  = rs.getInt("TASKSECOND");
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
	     String Error  =  e.getClass().getName() + ": " + e.getMessage();	
		 System.err.println( Error );
		 EV.LogEvent(Error, "SEVERE");
	     System.exit(0);
	    
	  }
	
   	}
  	    
	
}
