package kisti.server;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xmlParser2 {

	
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
				
//				NodeList nodetList = element.getElementsByTagName("Node");				
//				for (int j = 0; j < nodetList.getLength(); j++) {
//					Element eventFiel = (Element) nodetList.item(j);
					String name = getChildren(element, "name");
					String state = getChildren(element, "state");
					String np = getChildren(element, "np");
					String properties = getChildren(element, "properties");
					String ntype = getChildren(element, "ntype");
					String jobs = getChildren(element, "jobs");
//				}
				System.out.println("name:"+name);
				System.out.println("state:"+state);
				System.out.println("np:"+np);
				System.out.println("properties:"+properties);
				System.out.println("ntype:"+ntype);
//				System.out.println("jobs:"+jobs);
				
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
		xmlParser2 p = new xmlParser2();
		try {
			p.parseString("null");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
