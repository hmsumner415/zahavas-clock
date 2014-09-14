package org.zahavas.chessclock.source;
/**
 * example of XLTS transforms
 * http://www.rgagnon.com/javadetails/java-0407.html
 * http://javaprogramming.language-tutorial.com/2012/10/convert-java-object-to-xml-and-convert.html
 * http://www.developer.com/xml/article.php/3329001/Converting-JDBC-Result-Sets-to-XML.htm
 * http://www.developer.com/xml/article.php/10929_3329001_2/Converting-JDBC-Result-Sets-to-XML.htm
 * 
 */


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.io.*;  
import java.beans.XMLDecoder;  
import java.beans.XMLEncoder;  



 
import org.w3c.*;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;





public class XMLUtilities {

	public XMLUtilities()
	{
		 
	}
	  public static String objectToXML(Object voObj)  
	    {  
	            ByteArrayOutputStream stream = new ByteArrayOutputStream();  
	            XMLEncoder xmlEncoder = null;  
	            try  
	            {  
	                xmlEncoder = new XMLEncoder(new BufferedOutputStream(stream));  
	                xmlEncoder.writeObject(voObj);  
	                xmlEncoder.close();  
	                return stream.toString("UTF-8");   
	            }catch(Exception e)  
	            {                  
	                System.out.println("Inside exception from pymtHistToXML : " + e.getMessage());  
	            }  
	            return null;  
	    }  
	  
	    public static Object XMLToObject(String dataXML) {  
	           XMLDecoder d = null;  
	           try {  
	               d = new XMLDecoder(new ByteArrayInputStream(dataXML.getBytes("UTF-8")));  
	               Object voObj = d.readObject();  
	               d.close();  
	               return voObj;  
	           } catch (Exception e) {  
	               System.out.println("Error while Converting XML to VO : " + e);  
	           }  
	           return null;  
	       }  
	
	    public static Document SQLResultSettoXMLDocument(ResultSet rs)
	    {	  
	        Document doc = null;	    	 
	    	try {
	    	
	    	ResultSetMetaData rsmd = rs.getMetaData();
	      	int colCount = rsmd.getColumnCount();
	      	
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    factory.setNamespaceAware(true);
		    DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();
		      
		    doc = impl.createDocument(null, "Resultset", null);
		    Element root = doc.getDocumentElement();
		    
		    

        while (rs.next())
        { 
        	Element nextRow = doc.createElement("nextRow");
        	root.appendChild(nextRow);
        	
        	
        	for (int i = 1; i <= colCount; i++)
            {
                String columnName = rsmd.getColumnName(i);
                Object value = rs.getObject(i);
                
                Element ResultElement = doc.createElement(columnName.toString());
                Text text = doc.createTextNode(value.toString());
    	        ResultElement.appendChild(text);
    	        nextRow.appendChild(ResultElement);
    	        
    	        Text lineBreak = doc.createTextNode("\n");
    		    nextRow.appendChild(lineBreak);
             }
     
       	    	
        }
        Text lineBreak = doc.createTextNode("\n");
	    root.appendChild(lineBreak);
        
        TransformerFactory xformFactory = TransformerFactory.newInstance();  
        Transformer idTransform = xformFactory.newTransformer();
	    Source input = new DOMSource(doc);
	    Result output = new StreamResult(System.out);
	    idTransform.transform(input, output);
        
	    }
	    	catch (FactoryConfigurationError e) {System.out.println("Could not locate a JAXP factory class");    	    }
	   	    catch (ParserConfigurationException e) {System.out.println("Could not locate a JAXP DocumentBuilder class" );	   	    }
	   	    catch (DOMException e) {System.err.println(e);}
	   	    catch (TransformerConfigurationException e) {System.err.println(e);}
	   	    catch (TransformerException e) {System.err.println(e);}
	    	catch (SQLException e) {e.printStackTrace();}
	    	
	    	return doc; 
	    }
	        	    	 
						 
						
        
	    
	    
	    
	    public static void writexlts ()
	    {
	    	  try {

	    	    TransformerFactory tFactory = TransformerFactory.newInstance();

	    	    /*Transformer transformer =
	    	      tFactory.newTransformer
	    	         (new javax.xml.transform.stream.StreamSource
	    	        		 javax.xml.transform.StreamSource();
	    	            ("howto.xsl"));
 
	    	    transformer.transform
	    	      (new javax.xml.transform.stream.StreamSource
	    	            ("howto.xml"),
	    	       new javax.xml.transform.stream.StreamResult
	    	            ( new FileOutputStream("howto.html")));
	    	            */
	    	    }
	    	  catch (Exception e) {
	    	    e.printStackTrace( );
	    	    
	    	  }
	    }
}
