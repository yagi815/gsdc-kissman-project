package daniel.kisti.Helper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.graphics.Bitmap;
import android.os.Environment;
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
	
	private RequestDataToTierServer requestDataToTierServer ;
	private String[][] workernodeStatus;
	private String[][] cdfJobs ;
	
	/**
	 * Desc : Constructor of Caf_Helper.java class
	 */
	public Caf_Helper() {
		// TODO Auto-generated constructor stub
		requestDataToTierServer = new RequestDataToTierServer();
	}
		
	/**
	 * 
	 * Desc :
	 * @Method Name : getCeMonURL
	 * @return
	 *
	 */
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
	/**
	 * 
	 * Desc :
	 * @Method Name : getCeMonGraphImage
	 * @return
	 *
	 */
	public String getCeMonGraphImage(){
		String imgPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		imgPath += "/ce02Graph.bmp";
		requestDataToTierServer.requestImgData("URL_requestURL");
		
		return imgPath;		
	}
	/**
	 * 
	 * Desc :
	 * @Method Name : getQueueStatus
	 * @return
	 *
	 */
	public String getQueueStatus(){
		String queueStatus="";
		queueStatus = (String)requestDataToTierServer.request("QueueStatus");
		return queueStatus;
	}
	/**
	 * 
	 * Desc :
	 * @Method Name : getWorkernodeStatus
	 * @return
	 *
	 */
	public String[][] getWorkernodeStatus(){
		String wkStatus = null;
		wkStatus = (String)requestDataToTierServer.request("WorkerNodeStatus");

		/**
		 * XML 파싱
		 */
		
		Log.d("[Caf_Helper]", "parsing....................");
		
		 workernodeStatus = new String[36][6];
		
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

				workernodeStatus[i][0] = name;
				workernodeStatus[i][1] = state;
				workernodeStatus[i][2] = np;

				int start, end;
				start = status.indexOf("jobs");
				end = status.indexOf("varattr");
				String jobs = status.substring(start+5, end-1);				
				jobs = jobs.replace(" ", "\n");				

				workernodeStatus[i][3] = jobs;
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return workernodeStatus;
	}

	/**
	 * 
	 * Desc :
	 * @Method Name : requestJobStatus
	 * @return
	 *
	 */
	public String[][] requestJobStatus(){
				
		String result = (String)requestDataToTierServer.request("DB_requestCdfJobTable");
		//파싱
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream istream = new ByteArrayInputStream(result.getBytes("utf-8"));
			Document doc = builder.parse(istream);
			
			Log.d("[Caf_Helper]", result);
			
			Element order = doc.getDocumentElement();
			NodeList items = order.getElementsByTagName("Node");
			
			cdfJobs = new String[items.getLength()][8];
			
			for (int i = 0; i < items.getLength(); i++) {
				Element element = (Element)items.item(i);
				
				
				String month = getChildren(element, "month");
				String period = getChildren(element, "period1");
				period +="~";
				period += getChildren(element, "period2");
				String user  = getChildren(element, "user");
				String sucessJob = getChildren(element, "sucessJob");
				String ratioSucessJob = getChildren(element, "ratioSucessJob");
				String wallTime = getChildren(element, "wallTime");
				String status = getChildren(element, "status");

				cdfJobs[i][0] = month;
				cdfJobs[i][1] = period;
				cdfJobs[i][2] = user;
				cdfJobs[i][3] = sucessJob;
				cdfJobs[i][4] = ratioSucessJob;
				cdfJobs[i][5] = wallTime;
				cdfJobs[i][6] = status;
				cdfJobs[i][7] = "";
				
				
			}
			
			
			}catch (Exception e) {
				// TODO: handle exception
			}
		return cdfJobs;
	}

	/**
	 * 
	 * Desc :
	 * @Method Name : getJobStatus
	 * @return
	 *
	 */
	public String[][] getJobStatus(){
		requestJobStatus();
		return cdfJobs;
	}
	/**
	 * 
	 * Desc :
	 * @Method Name : getJobGraphData
	 * @return
	 *
	 */
	public int[] getJobGraphData(){
		
		requestJobStatus();	
		
		
		int[] jobGraphData = new int[36];
		for (int i = 0; i < jobGraphData.length; i++) {			jobGraphData[i]=0;		}
		for (int i = 0; i < cdfJobs.length; i++) {
			int data = Integer.parseInt(cdfJobs[i][0]);
			jobGraphData[data-1] += Integer.parseInt(cdfJobs[i][3]);
		}
		
		
		return jobGraphData;
	}

	/**
	 * 
	 * Desc :
	 * @Method Name : getChildren
	 * @param element
	 * @param tagName
	 * @return
	 *
	 */
	public static String getChildren(Element element, String tagName) {
		NodeList list = element.getElementsByTagName(tagName);
		Element cElement = (Element) list.item(0);

		if (cElement.getFirstChild() != null) {
			return cElement.getFirstChild().getNodeValue();
		} else {
			return "";
		}
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
		
	}
}
