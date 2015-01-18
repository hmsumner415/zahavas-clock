package org.zahavas.chessclock.source;

import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;

import org.w3c.*;
import org.w3c.dom.*;


import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;




import java.net.*;
import java.io.*;
import org.xml.sax.*;
public class xlts_Transforms {

String xltsClientListing = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
"<!-- Edited by XMLSpy® -->" +
"<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
"<xsl:template match=\"/\">" + 
" <html> " + 
"  <body> " +
"  <h2>ClientListing:</h2> " + 
"    <table border=\"1\"> " +
"      <tr bgcolor=\"#9acd32\"> " +
"        <th style=\"text-align:left\">Id</th> " +
"        <th style=\"text-align:left\">Client Short Name</th>" +
"	<th style=\"text-align:left\">Client Name</th>" +
"      </tr> " +
"      <xsl:for-each select=\"Resultset/nextRow\">" +
"      <tr>" +
"        <td><xsl:value-of select=\"ID\"/></td>" +
"        <td><xsl:value-of select=\"CLIENTSHORTNAME\"/></td>" +
"	     <td><xsl:value-of select=\"CLIENTNAME\"/></td>" +
"      </tr>" +
"      </xsl:for-each>" +
"    </table>" +
"  </body>" +
"  </html> " +
"</xsl:template>" +
"</xsl:stylesheet> ";
	

public void xltsClientListing(Document xmlDocument) {
	  try {
		 //http://stackoverflow.com/questions/4722482/java-document-to-streamsource-conversion 
		 // Create DOM source for the document
		 DOMSource domSource=new DOMSource(xmlDocument);

		    // Create a string writer
		    StringWriter stringWriter=new StringWriter();

		    // Create the result stream for the transform
		    StreamResult result = new StreamResult(stringWriter);

		    // Create a Transformer to serialize the document
		    TransformerFactory tFactory =TransformerFactory.newInstance();
		    Transformer transformer = tFactory.newTransformer();
		    transformer.setOutputProperty("indent","yes");

		    // Transform the document to the result stream
		    transformer.transform(domSource, result);  
    
		   String s = stringWriter.toString();
		   StringReader reader = new StringReader(s);
		   StringReader xltsreader = new StringReader(xltsClientListing);
		 
		   tFactory = TransformerFactory.newInstance();
		   transformer = tFactory.newTransformer    (new StreamSource(xltsreader));

		   transformer.transform
		         (new javax.xml.transform.stream.StreamSource(reader),
		          new javax.xml.transform.stream.StreamResult ( new FileOutputStream("ClientListing.html")));
		       
		    }
	    
		  catch (Exception e) {
		    e.printStackTrace( );
		    }
	  
	  	File htmlFile = new File("ClientListing.html");
	  	try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
}

String xltsTaskListing = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
"<!-- Edited by XMLSpy® -->" +
"<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
"<xsl:template match=\"/\">" + 
" <html> " + 
"  <body> " +
"  <h2>Task Listing:</h2> " + 
"    <table border=\"1\"> " +
"      <tr bgcolor=\"#9acd32\"> " +
"        <th style=\"text-align:left\">Task Name</th> " +
"        <th style=\"text-align:left\">Client</th>" +
"		 <th style=\"text-align:left\">Project</th>" +
"		 <th style=\"text-align:left\">Time Counter (seconds)</th>" +
"		 <th style=\"text-align:left\">Active</th>" +
"      </tr> " +
"      <xsl:for-each select=\"Resultset/nextRow\">" +
"      <tr>" +
"        <td><xsl:value-of select=\"taskName\"/></td>" +
"        <td><xsl:value-of select=\"clientName\"/></td>" +
"		 <td><xsl:value-of select=\"projectName\"/></td>" +
"		 <td><xsl:value-of select=\"taskTimecounter\"/></td>" +
"		 <td><xsl:value-of select=\"IsActive\"/></td>" +
"      </tr>" +
"      </xsl:for-each>" +
"    </table>" +
"  </body>" +
"  </html> " +
"</xsl:template>" +
"</xsl:stylesheet> ";



public void xltsTaskListing(Document xmlDocument) {
	  try {

		 DOMSource domSource=new DOMSource(xmlDocument);
         StringWriter stringWriter=new StringWriter();
         StreamResult result = new StreamResult(stringWriter);
	
         TransformerFactory tFactory =TransformerFactory.newInstance();
		 Transformer transformer = tFactory.newTransformer();
		 transformer.setOutputProperty("indent","yes");
         transformer.transform(domSource, result);  
	      
	    
         String s = stringWriter.toString();
		 StringReader reader = new StringReader(s);
		 StringReader xltsreader = new StringReader(xltsTaskListing);
		 
		 tFactory = TransformerFactory.newInstance();
		 transformer = tFactory.newTransformer    (new StreamSource(xltsreader));

		 transformer.transform
		         (new javax.xml.transform.stream.StreamSource(reader),
		          new javax.xml.transform.stream.StreamResult ( new FileOutputStream("TaskListing.html")));
		       
		 }
	    
		 catch (Exception e) {
		    e.printStackTrace( );
		 }
	  
	  	File htmlFile = new File("TaskListing.html");
	  	try {
			Desktop.getDesktop().browse(htmlFile.toURI());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
}


String xltsTaskSummyReport = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
"<!-- Edited by XMLSpy® -->" +
"<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
"<xsl:template match=\"/\">" + 
" <html> " + 
"  <body> " +
"  <h2>Time Summary Report</h2> " + 
"    <table border=\"0\"> " +
"      <tr bgcolor=\"#9acd32\"> " +
"        <th style=\"text-align:left\">Client</th> " +
"        <th style=\"text-align:left\">Project</th>" +
"		 <th style=\"text-align:left\">Task</th>" +
"		 <th style=\"text-align:left\">Month</th>" +
"		 <th style=\"text-align:left\">Week of Year</th>" +
"		 <th style=\"text-align:left\">Days in Week</th>" +
"		 <th style=\"text-align:left\">Hours</th>" +
"		 <th style=\"text-align:left\">Minutes</th>" +
"      </tr> " +
"      <xsl:for-each select=\"Resultset/nextRow\">" +
"      <tr>" +
"        <td><xsl:value-of select=\"CLIENTSHORTNAME\"/></td>" +
"        <td><xsl:value-of select=\"PROJECTNAME\"/></td>" +
"		 <td><xsl:value-of select=\"TASKNAME\"/></td>" +
"		 <td><xsl:value-of select=\"MONTHOFYEAR\"/></td>" +
"		 <td><xsl:value-of select=\"WEEKOFYEAR\"/></td>" +
"		 <td><xsl:value-of select=\"DAYSINWEEK\"/></td>" +
"		 <td><xsl:value-of select=\"hours\"/></td>" +
"		 <td><xsl:value-of select=\"minutes\"/></td>" +
"      </tr>" +
"      </xsl:for-each>" +
"    </table>" +
"  </body>" +
"  </html> " +
"</xsl:template>" +
"</xsl:stylesheet> ";

public void xltsTaskSummaryReport(Document xmlDocument) {
	try {

		 DOMSource domSource=new DOMSource(xmlDocument);
        StringWriter stringWriter=new StringWriter();
        StreamResult result = new StreamResult(stringWriter);
	
        TransformerFactory tFactory =TransformerFactory.newInstance();
		 Transformer transformer = tFactory.newTransformer();
		 transformer.setOutputProperty("indent","yes");
        transformer.transform(domSource, result);  
	      
	    
        String s = stringWriter.toString();
		 StringReader reader = new StringReader(s);
		 StringReader xltsreader = new StringReader(xltsTaskSummyReport);
		 
		 tFactory = TransformerFactory.newInstance();
		 transformer = tFactory.newTransformer    (new StreamSource(xltsreader));

		 transformer.transform
		         (new javax.xml.transform.stream.StreamSource(reader),
		          new javax.xml.transform.stream.StreamResult ( new FileOutputStream("TaskSummaryReport.html")));
		       
		 }
	    
		 catch (Exception e) {
		    e.printStackTrace( );
		 }
	  
	  	File htmlFile = new File("TaskSummaryReport.html");
	  	try {
			Desktop.getDesktop().browse(htmlFile.toURI());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
}


}
