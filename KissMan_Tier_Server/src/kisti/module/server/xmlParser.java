package kisti.module.server;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <pre>
 * kisti.module.server
 *   |_ xmlParser.java
 *
 * </pre>
 *
 * Desc :
 * @Company : KISTI
 * @Author :daniel
 * @Date   :2011. 10. 29. 오후 11:54:41
 * @Version:
 *
 */
public class xmlParser {

	String result[][] = null;

	// public String[][] parseString(File xmlFile){
	public String[][] parseString(String xmlFile) {
		result = new String[45][6];
		System.out.println("---in xmlPaser");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(xmlFile));
			System.out.println("---in xmlPaser   before parser");

			NodeList list = doc.getElementsByTagName("Node");

			for (int i = 0; i < list.getLength(); i++) {
				Element element = (Element) list.item(i);

				String name = getChildren(element, "name");
				String state = getChildren(element, "state");
				String np = getChildren(element, "np");
//			String properties = getChildren(element, "properties");
//			String ntype = getChildren(element, "ntype");
				String status = getChildren(element, "status");

				result[i][0] = name;
				result[i][1] = state;
				result[i][2] = np;
//				result[i][3] = properties;
				// result[i][4] = ntype;
				//  job 부분만 파싱
				
				int start, end;
				start = status.indexOf("jobs");
				end = status.indexOf("varattr");
				String jobs = status.substring(start+5, end-1);				
				jobs = jobs.replace(" ", "\n");
				
//				result[i][3] = 1
				result[i][3] = jobs;

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
		// p.parseString("null");
	}
}
