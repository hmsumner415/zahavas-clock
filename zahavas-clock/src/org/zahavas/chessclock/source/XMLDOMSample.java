package org.zahavas.chessclock.source;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.math.BigInteger;



public class XMLDOMSample {
/*
 * http://www.cafeconleche.org/books/xmljava/
 */
	  public XMLDOMSample () {

	    try {

	      // Find the implementation
	      DocumentBuilderFactory factory 
	       = DocumentBuilderFactory.newInstance();
	      factory.setNamespaceAware(true);
	      DocumentBuilder builder = factory.newDocumentBuilder();
	      DOMImplementation impl = builder.getDOMImplementation();
	      
	      // Create the document
	      Document doc = impl.createDocument(null, 
	       "Fibonacci_Numbers", null);
	       
	      // Fill the document
	      BigInteger low  = BigInteger.ONE;
	      BigInteger high = BigInteger.ONE;

	      Element root = doc.getDocumentElement();

	      for (int i = 0; i < 10; i++) {
	        Element number = doc.createElement("fibonacci");
	        Text text = doc.createTextNode(low.toString());
	        number.appendChild(text);
	        root.appendChild(number);

	        BigInteger temp = high;
	        high = high.add(low);
	        low = temp;
	      }

	      // Serialize the document onto System.out
	      TransformerFactory xformFactory 
	       = TransformerFactory.newInstance();  
	      Transformer idTransform = xformFactory.newTransformer();
	      Source input = new DOMSource(doc);
	      Result output = new StreamResult(System.out);
	      idTransform.transform(input, output);
	      
	    }
	    catch (FactoryConfigurationError e) { 
	      System.out.println("Could not locate a JAXP factory class"); 
	    }
	    catch (ParserConfigurationException e) { 
	      System.out.println(
	        "Could not locate a JAXP DocumentBuilder class"
	      ); 
	    }
	    catch (DOMException e) {
	      System.err.println(e); 
	    }
	    catch (TransformerConfigurationException e) {
	      System.err.println(e); 
	    }
	    catch (TransformerException e) {
	      System.err.println(e); 
	    }
	    
	  }

	}

	
	
	
	
	
 
