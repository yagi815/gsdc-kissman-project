package daniel.kisti.Helper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.text.GetChars;
import android.util.Log;
import daniel.kisti.serverModule.RequestDataToTierServer;



/**
 * <pre>
 * daniel.kisti.Helper
 *   |_ Caf_Helper.java
 *
 * </pre>
 *
 * Desc :
 * @Company : KISTI
 * @Author :daniel
 * @Date   :2011. 10. 26. 오후 5:29:56
 * @Version:
 *
 */
public class Caf_Helper {
	
	RequestDataToTierServer requestDataToTierServer ;
	
	/**
	 * Desc : Constructor of Caf_Helper.java class
	 */
	public Caf_Helper() {
		// TODO Auto-generated constructor stub
		requestDataToTierServer = new RequestDataToTierServer();
	}
		
	public String getCeMonURL(){
		String URL="";
		try {
		
			URL = (String)requestDataToTierServer.request("URL_requestURL");
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("[Caf_Helper]", e.toString());
		}
		
		return URL;
	}
	
	public String getQueueStatus(){
		String queueStatus="";
		queueStatus = (String)requestDataToTierServer.request("QueueStatus");
		return queueStatus;
	}
	
	public String[][] getWorkernodeStatus(){
		String wkStatus = null;
		wkStatus = (String)requestDataToTierServer.request("WorkerNodeStatus");

		/**
		 * XML 파싱
		 */
		
		Log.d("[Caf_Helper]", "parsing....................");
		
		String[][] result = new String[36][6];
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream istream = new ByteArrayInputStream(wkStatus.getBytes("utf-8"));
			Document doc = builder.parse(istream);
			
			Log.d("[Caf_Helper]", wkStatus);
			
			Element order = doc.getDocumentElement();
			NodeList items = order.getElementsByTagName("Node");
			
			for (int i = 0; i < items.getLength(); i++) {
				Element element = (Element)items.item(i);
				
				
//				NameNodeMap attrs = items.get
				
				String name = getChildren(element, "name");
				String state = getChildren(element, "state");
				String np = getChildren(element, "np");
				String status = getChildren(element, "status");

				result[i][0] = name;
				result[i][1] = state;
				result[i][2] = np;

				int start, end;
				start = status.indexOf("jobs");
				end = status.indexOf("varattr");
				String jobs = status.substring(start+5, end-1);				
				jobs = jobs.replace(" ", "\n");				

				result[i][3] = jobs;
//				Log.d("[Caf_Helper]", result[i][0]+":"+result[i][1]+result[i][2]+result[i][3]);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
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
	
	public void xmlParsingTest(){
		 
	}
	
	
	
	public Vector getJobStatus(){
		Vector jobStatus = null;		
		jobStatus = (Vector)requestDataToTierServer.request("DB_requestCdfJobTable");
		return jobStatus;
	}
	
	public int[] getGraphData(){
		int[] jobGraphData = null;
		jobGraphData = (int[])requestDataToTierServer.request("DB_requestJobGraph");		
		return jobGraphData;
	}


	/**
	 * Desc :
	 * @Method Name : main
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Caf_Helper ch = new Caf_Helper();
		System.out.println("--------------------------------------");
		System.out.println(ch.getQueueStatus());
		System.out.println("--------------------------------------");
		System.out.println(ch.getWorkernodeStatus());
		System.out.println("--------------------------------------");
		System.out.println(ch.getJobStatus());
		System.out.println("--------------------------------------");
		System.out.println(ch.getGraphData());
		System.out.println("--------------------------------------");
		
		
		
		
	}
}
