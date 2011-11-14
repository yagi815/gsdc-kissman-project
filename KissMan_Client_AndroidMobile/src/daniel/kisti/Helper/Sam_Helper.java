package daniel.kisti.Helper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;
import daniel.kisti.serverModule.RequestDataToTierServer;


/**
 * <pre>
 * daniel.kisti.Helper
 *   |_ Sam_Helper.java
 *
 * </pre>
 *
 * Desc :
 * @Company : KISTI
 * @Author :daniel
 * @Date   :2011. 10. 31. 오전 11:33:45
 * @Version:
 *
 */
public class Sam_Helper {


	RequestDataToTierServer requestDatatoTierServer ;
	
	private double cpuUse;
	private double memUsage;
	private double memTotal;
	private double memUse;
	
	private double diskIn;
	private double diskOut;
	private double netIn;
	private double netOut;
	
	private double cdf01DiskSize;
	private double cdf01DiskUsed;
	private String cdf01DiskUsage;
	
	private double cdf02DiskSize;
	private double cdf02DiskUsed;
	private String cdf02DiskUsage;
	
	private double cdfUserDiskSize;
	private double cdfUserDiskUsed;
	private String cdfUserDiskUsage;
	
	private String status;
	
	
	public Sam_Helper() {
		// TODO Auto-generated constructor stub
		
		 requestDatatoTierServer = new RequestDataToTierServer();
	}
	/**
	 * 
	 * Desc :
	 * @Method Name : loadDstatData
	 *
	 */
	public void loadDstatData(){
		String result = (String)requestDatatoTierServer.request("getDstat");		
		//파싱
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream istream = new ByteArrayInputStream(result.getBytes("utf-8"));
			Document doc = builder.parse(istream);
			
			Log.d("[Sam_Helper]","[loadDstatData] : "+ result);			
			Element order = doc.getDocumentElement();
			NodeList items = order.getElementsByTagName("Node");
			Element element = (Element) items.item(0);
			
			String dstat = getChildren(element, "dstat");
			
			status = dstat;			
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}		
	}
	
	public void loadSamInfoData(){
		String result = (String)requestDatatoTierServer.request("samInfo");		
		//파싱
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream istream = new ByteArrayInputStream(result.getBytes("utf-8"));
			Document doc = builder.parse(istream);
			
			Log.d("[Sam_Helper]","[loadSamInfoData] : "+ result);
			
			Element order = doc.getDocumentElement();
			NodeList items = order.getElementsByTagName("Node");
			Element element = (Element) items.item(0);
			
			memTotal = Double.parseDouble( getChildren(element, "memTotal") );
			memUse = Double.parseDouble( getChildren(element, "memUse") );
			memUsage = (memUse/memTotal)*100;
			
			
			diskIn = Double.parseDouble( getChildren(element, "diskRead") );
			diskOut = Double.parseDouble( getChildren(element, "diskWrite") );
			netIn = Double.parseDouble( getChildren(element, "netIn") );
			netOut = Double.parseDouble( getChildren(element, "netOut") );
			
			
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}		
	}
	public void loadSamDiskData(){
		Log.d("[Sam_Helper]","[loadSamDiskData]........>>> : ");
		String result = (String)requestDatatoTierServer.request("samDisk");		
		//파싱
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream istream = new ByteArrayInputStream(result.getBytes("utf-8"));
			Document doc = builder.parse(istream);			
			Log.d("[Sam_Helper]","[loadSamDiskData] : "+ result);
			
			Element order = doc.getDocumentElement();
			NodeList items = order.getElementsByTagName("Node");
			Element element = (Element) items.item(0);
			
			cdf01DiskSize = Double.parseDouble( getChildren(element, "cdf01Size") );
			cdf01DiskUsed = Double.parseDouble( getChildren(element, "cdf01Used") );
			cdf01DiskUsage = getChildren(element, "cdf01Usage") ;
			cdf02DiskSize = Double.parseDouble( getChildren(element, "cdf02Size") );
			cdf02DiskUsed = Double.parseDouble( getChildren(element, "cdf02Used") );
			cdf02DiskUsage = getChildren(element, "cdf02Usage") ;			
			cdfUserDiskSize = Double.parseDouble( getChildren(element, "generalDiskSize") );
			cdfUserDiskUsed = Double.parseDouble( getChildren(element, "generalDiskUsed") );
			cdfUserDiskUsage = getChildren(element, "generalDiskUsage") ;
			
			
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}		
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
	
	
	
	
	

	public double getCpuUse() {
		loadSamInfoData();
		return cpuUse;
	}
	public double getMemUsage() {
//		loadSamInfoData();
		return memUsage;
	}
	public double getMemTotal() {
//		loadSamInfoData();
		return memTotal;
	}
	public double getMemUse() {
//		loadSamInfoData();
		return memUse;
	}
	public double getDiskIn() {
//		loadSamInfoData();
		return diskIn;
	}
	public double getDiskOut() {
//		loadSamInfoData();
		return diskOut;
	}
	public double getNetIn() {
//		loadSamInfoData();
		return netIn;
	}
	public double getNetOut() {
//		loadSamInfoData();
		return netOut;
	}

	
	
	
	public double getCdf01DiskSize() {
		loadSamDiskData();
		return cdf01DiskSize;
	}
	public double getCdf01DiskUsed() {	
		loadSamDiskData();
		return cdf01DiskUsed;
	}
	public String getCdf01DiskUsage() {
		return cdf01DiskUsage;
	}
	public double getCdf02DiskSize() {
		return cdf02DiskSize;
	}
	public double getCdf02DiskUsed() {
		return cdf02DiskUsed;
	}
	public String getCdf02DiskUsage() {
		return cdf02DiskUsage;
	}
	public double getCdfUserDiskSize() {
		return cdfUserDiskSize;
	}
	public double getCdfUserDiskUsed() {
		return cdfUserDiskUsed;
	}
	public String getCdfUserDiskUsage() {
		return cdfUserDiskUsage;
	}
	
	
	
	
	
	public String getStatus(){
		loadDstatData();
		return status;
	}


	
	
	
	
	
	
	
	
	
	
	


	/**
	 * Desc :
	 * @Method Name : main
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
