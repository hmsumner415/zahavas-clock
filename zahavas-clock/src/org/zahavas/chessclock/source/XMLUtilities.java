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


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;


 


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
	        	    	 
	 //public class   ObjectAnalyzer
	 	 private ArrayList<Object> visited = new ArrayList<Object>();	
	 
	 
	 
	 
	 
	 
	 /**
	  *  toStringXML
	  *  Built on ObjectAnalyzer.java from  Core Java Volume I Listing 5.15
	  *  Builds an XML Document using DOM
	  *  Displays result on console using a string transform 
	  *  @param Object
	  *  @returns DOM Document 
	  *  
	  */
	 public Document toDomXML	(Object obj)
	 {   Document doc = null;
		 String r = "/n";
		 try
		 {
			 	
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();
			  
	        doc = impl.createDocument(null,"Resultset", null);
	        Element root = doc.getDocumentElement();
	        
	       
	        
	        // Call objToString function to convert class instance to a string
	        r = objToString(obj);
	        
            StringReader sr = new StringReader(r);
            StringBuilder s2 = new StringBuilder();
            String S = "";
            
            try
            {    
            	for (int i = 0; i < r.length(); i++) {
                    char c = (char) sr.read();
                                     
                    S= String.valueOf(c);
                    if ( S.equals("{"))
                    	{ do
                    	  {	  c = (char) sr.read();
                    		  S=String.valueOf(c);
                    		  if (!S.equals("[")) s2.append(c);
                    		  i++;       		 
                    	  } while (!S.equals("[") | i>= r.length());
                    	  System.out.println(s2.toString());
                    	  s2.delete(0, s2.length());
                     	}
                     if ( S.equals("["))
                    	{ 
                    	
                    	 do
                    		{	  c = (char) sr.read();
                    			  S=String.valueOf(c);
              		              if (!S.equals("]")) s2.append(c);
              		              i++;       		 
                    		} while (!S.equals("]") | i>= r.length());
              	        if (s2.length()>0)
              	        {
              	        	 Element nextRow = doc.createElement("nextRow");
                         	 root.appendChild(nextRow);
              	       
                    	 
                    	 System.out.println(s2.toString());
              	         S=s2.toString();
              	         String[] SS =  S.split(",");
              	         for (int SSi = 0; SSi < SS.length; SSi++) {
              	        	  System.out.println(SS[SSi]);
              	        	  String[] SS2 = SS[SSi].split("=");
              	        	  if (SS2.length == 2)
              	        	  {
              	        	  System.out.println((SS2[0]));
              	        	  System.out.println((SS2[1]));
              	        	  
              	        	Element ResultElement = doc.createElement(SS2[0].toString());
                            Text text = doc.createTextNode(SS2[1].toString());
                	        ResultElement.appendChild(text);
                	        nextRow.appendChild(ResultElement);
                	        Text lineBreak = doc.createTextNode("\n");
                		    nextRow.appendChild(lineBreak);  
              	        	  
              	        	  
              	        	  }
              	         	}
              	         
              	         s2.delete(0, s2.length());
              	        }
                    }
            	}
		 
            	sr.close();
            }
            catch (IOException ex) {ex.printStackTrace();}
            
            // Create the result stream for a transform to a String for display on the console
            StringWriter stringWriter=new StringWriter();
		    StreamResult r1 = new StreamResult(stringWriter);
            
	        TransformerFactory xformFactory = TransformerFactory.newInstance();  
	        Transformer idTransform = xformFactory.newTransformer();
		    Source input = new DOMSource(doc);
		    Result output = new StreamResult(System.out);

		    try {
		    	idTransform.transform(input, r1);
		    	System.out.println(r1);
				idTransform.transform(input, output);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	      
		 }
		 catch (FactoryConfigurationError e) {System.out.println("Could not locate a JAXP factory class");    	    }
	   	 catch (ParserConfigurationException e) {System.out.println("Could not locate a JAXP DocumentBuilder class" );	   	    }
	   	 catch (DOMException e) {System.err.println(e);} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		 return doc;
		 
	   }

	 	


	    /**
	     * Built on ObjectAnalyzer.java from  Core Java Volume I Listing 5.15
	     * Converts an object to a string representation that lists all fields.
	     * @param obj an object
	     * @return a string with the object's class name and all field names and
	     * values
	     */
	    public String objToString(Object obj)
	    {
	       if (obj == null) return "null";
	       //if (visited.contains(obj)) return "...";
	       //visited.add(obj);
	       Class cl = obj.getClass();
	       if (cl == String.class) return (String) obj;
	       if (cl.isArray())
	       {
	          String r = cl.getComponentType() + "[]{";
	          for (int i = 0; i < Array.getLength(obj); i++)
	          {
	             if (i > 0) r += ",";
	             Object val = Array.get(obj, i);
	             if (cl.getComponentType().isPrimitive()) r += val;
	             else r += objToString(val);
	          }
	          return r + "}";
	       }

	       String r = cl.getName();
	       // inspect the fields of this class and all superclasses
	       do
	       {
	          r += "\n[";
	          Field[] fields = cl.getDeclaredFields();
	          AccessibleObject.setAccessible(fields, true);
	          // get the names and values of all fields
	          for (Field f : fields)
	          {
	             if (!Modifier.isStatic(f.getModifiers()))
	             {
	                if (!r.endsWith("[")) r += ",";
	                r += f.getName() + "=";
	                try
	                {
	                   Class t = f.getType();
	                   Object val = f.get(obj);
	                   if (t.isPrimitive()) r += val;
	                   else r += objToString(val);
	                }
	                catch (Exception e)
	                {
	                   e.printStackTrace();
	                }
	             }
	          }
	          r += "]";
	          cl = cl.getSuperclass();
	       }
	       while (cl != null);

	       return r;
	    }
	 

	    
}
