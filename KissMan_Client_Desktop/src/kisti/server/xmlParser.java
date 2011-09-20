package kisti.server;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xmlParser {

	
	String result[][] = null;
	
	public String[][] parseString(String parsString){
		result  = new String[42][6];
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("pbsnode.xml"));

			
			NodeList list = doc.getElementsByTagName("Node");
			
			for (int i = 0; i < list.getLength(); i++) {
				Element element = (Element) list.item(i);		
				
				String name = getChildren(element, "name");
				String state = getChildren(element, "state");
				String np = getChildren(element, "np");
				String properties = getChildren(element, "properties");
//				String ntype = getChildren(element, "ntype");
				String status = getChildren(element, "status");
				
				result[i][0] = name;
				result[i][1] = state;
				result[i][2] = np;
				result[i][3] = properties;
//				result[i][4] = ntype;
				result[i][4] = status;
			
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	public static String getChildren(Element element, String tagName) {
		NodeList list = element.getElementsByTagName(tagName);
		Element cElement = (Element) list.item(0);

		if (cElement.getFirstChild() != null) {
			return cElement.getFirstChild().getNodeValue();
		} else {
			return "";
		}
	}
	public static void main(String[] args) {
		xmlParser p = new xmlParser();
		p.parseString("null");
	}
}
