package daniel.kisti.Helper;

import java.util.Vector;

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
	
	RequestDataToTierServer requestDatatoTierServer ;
	
	/**
	 * Desc : Constructor of Caf_Helper.java class
	 */
	public Caf_Helper() {
		// TODO Auto-generated constructor stub
		requestDatatoTierServer = new RequestDataToTierServer();
	}
		
	public String getCeMonURL(){
		String URL="";
		try {
		
			URL = (String)requestDatatoTierServer.request("URL_requestURL");
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("[Caf_Helper]", e.toString());
		}
		
		return URL;
	}
	
	public String getQueueStatus(){
		String queueStatus="";
		queueStatus = (String)requestDatatoTierServer.request("QueueStatus");
		return queueStatus;
	}
	
	public String[][] getWorkernodeStatus(){
		String[][] wkStatus = null;
		wkStatus = (String[][])requestDatatoTierServer.request("WorkerNodeStatus");
		return wkStatus;
	}
	
	public Vector getJobStatus(){
		Vector jobStatus = null;		
		jobStatus = (Vector)requestDatatoTierServer.request("DB_requestCdfJobTable");
		return jobStatus;
	}
	
	public int[] getGraphData(){
		int[] jobGraphData = null;
		jobGraphData = (int[])requestDatatoTierServer.request("DB_requestJobGraph");		
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
