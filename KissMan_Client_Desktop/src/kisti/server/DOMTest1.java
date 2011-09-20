package kisti.server;





import org.w3c.dom.*;
import javax.xml.parsers.*;

public class DOMTest1 {
	
	
	public static void main(String[] args) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse("pbsnode.xml");
		Element root = doc.getDocumentElement();
		System.out.println(root.getNodeName());
		
		Node n = root.getLastChild();
		System.out.println(n.getNodeName());
		
		NodeList list = root.getChildNodes();
		System.out.println(list.getLength());
		
		for (int i = 0; i < list.getLength(); i++) {
			Node c = list.item(i);
			System.out.println(c.getNodeType());
		}
		
		System.out.println("-------------------");
		for (int i = 0; i < list.getLength(); i++) {
			Node cc = list.item(i);
			if(cc.getNodeType() == 1){
				if (cc.getChildNodes().getLength() == 1) {
					Node cc_c = cc.getFirstChild();
					System.out.println(cc_c.getNodeValue());
				}
//				System.out.println(cc.getNodeName());
			}
		}
	}
}