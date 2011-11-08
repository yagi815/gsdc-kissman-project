package daniel.kisti.Helper;

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
	
	private double cdf01DiskSize;
	private double cdf01DiskUsed;
	private String cdf01DiskUsage;
	
	private double cdf02DiskSize;
	private double cdf02DiskUsed;
	private String cdf02DiskUsage;
	
	private double cdfUserDiskSize;
	private double cdfUserDiskUsed;
	private String cdfUserDiskUsage;
	
	
	public Sam_Helper() {
		// TODO Auto-generated constructor stub
		
		 requestDatatoTierServer = new RequestDataToTierServer();
//		 reflushData();
	}
	
	
	public void reflushData(){
	
		Log.d("[Sam_Helper]", ".... ");
//		Log.d("[Sam_Helper]", samInfoData.toString());
//		Log.d("[Sam_Helper]", samDiskData.toString());
		
		
		
	}
	
	
	

	public double getCpuUse() {
		return cpuUse;
	}

	public double getMemUsage() {
		return memUsage;
	}

	public double getMemTotal() {
		return memTotal;
	}

	public double getMemUse() {
		return memUse;
	}

	public double getCdf01DiskSize() {
		return cdf01DiskSize;
	}

	public double getCdf01DiskUsed() {
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
