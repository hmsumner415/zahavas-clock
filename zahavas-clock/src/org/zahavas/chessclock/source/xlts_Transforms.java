package org.zahavas.chessclock.source;

import java.io.FileOutputStream;

import org.w3c.*;
import org.w3c.dom.*;


import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.dom.DOMSource;
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
"  <h2>ClientListing</h2> " + 
"    <table border=\"1\"> " +
"      <tr bgcolor=\"#9acd32\"> " +
"        <th style=\"text-align:left\">Id</th> " +
"        <th style=\"text-align:left\">Artist</th>" +
"	<th style=\"text-align:left\">Id</th>" +
"      </tr> " +
"      <xsl:for-each select=\"Resultset/nextRow\">" +
"      <tr>" +
"        <td><xsl:value-of select=\"ID\"/></td>" +
"        <td><xsl:value-of select=\"CLIENTSHORTNAME\"/></td>" +
"	<td><xsl:value-of select=\"CLIENTNAME\"/></td>" +
"      </tr>" +
"      </xsl:for-each>" +
"    </table>" +
"  </body>" +
"  </html> " +
"</xsl:template>" +
"</xsl:stylesheet> ";
	

public void xltsClientListing(Document xmlString) {
	  try {
		 //http://stackoverflow.com/questions/4722482/java-document-to-streamsource-conversion 
		// Create DOM source for the document
		    DOMSource domSource=new DOMSource(xmlString);

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
		   System.out.println("debug start print message: ");
		   System.out.println(s);
		   System.out.println("Debug end");
		  
		    /*tFactory = TransformerFactory.newInstance();

		     transformer =
		      tFactory.newTransformer
		         (new StreamSource("xltsClientList.xsl"));

		    transformer.transform (new StreamSource( stringWriter.toString()), null);
		      
		       new javax.xml.transform.stream.StreamResult ( new FileOutputStream("howto.html")) ;
		       
		       tFactory = TransformerFactory.newInstance();
		       transformer = tFactory.newTransformer    (new StreamSource("xltsClientList.xsl"));
		       
		       Source request = new StreamSource(s);
		       Result response = new StreamResult(new FileOutputStream("howto.html"));
		       transformer.transform (request, response);
		       
*/
		   StringReader xltsreader = new StringReader(xltsClientListing);
		 
		   tFactory = TransformerFactory.newInstance();
	      // transformer = tFactory.newTransformer    (new StreamSource("xltsClientList.xsl"));
		   transformer = tFactory.newTransformer    (new StreamSource(xltsreader));
		   
		   StringReader reader = new StringReader(s);   
		   transformer.transform
		         (new javax.xml.transform.stream.StreamSource(reader),
		               new javax.xml.transform.stream.StreamResult
		               ( new FileOutputStream("howto2.html")));
		       
		    }
	    
		  catch (Exception e) {
		    e.printStackTrace( );
		    }
}
  



}
