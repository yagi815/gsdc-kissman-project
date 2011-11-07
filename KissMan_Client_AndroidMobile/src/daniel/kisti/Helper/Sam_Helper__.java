package daniel.kisti.Helper;

import android.util.Log;
import daniel.kisti.serverModule.RequestDataToTierServer;
import daniel.kisti.serverModule.SAMDiskData;
import daniel.kisti.serverModule.SAMInfoData;

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
public class Sam_Helper__ {

	private SAMDiskData samDiskData;
	private SAMInfoData samInfoData;
	RequestDataToTierServer requestDatatoTierServer ;
	
	public Sam_Helper__() {
		// TODO Auto-generated constructor stub
//		samDiskData = new SAMDiskData();
//		samInfoData = new SAMInfoData();	
		
		 requestDatatoTierServer = new RequestDataToTierServer();
	}
	
	
	
	public double getCpuUsage(){
		double cpuUsage=0;
		samInfoData = (SAMInfoData)requestDatatoTierServer.request("samInfo");
		if (samInfoData == null) {
			Log.d("client", "samInforData is null ");
		}else {
			cpuUsage = samInfoData.cpuUse;	
		}
		
		return cpuUsage;		
	}	
	public double getMemUsage(){
		double memUsage=0;
		samInfoData = (SAMInfoData)requestDatatoTierServer.request("samInfo");
		memUsage = (samInfoData.memUse/samInfoData.memTotal)*100;
		return memUsage;
	}	
	public double getMemTotal(){
		double memTotal=0;
		samInfoData = (SAMInfoData)requestDatatoTierServer.request("samInfo");
		if (samInfoData == null) {
			Log.d("client", "samInforData is null ");
		}else {
			memTotal = samInfoData.memTotal;
		}
//		memTotal = samInfoData.memTotal;
		return memTotal;
	}	
	public double getMemUse(){
		double memUse=0;
		samInfoData = (SAMInfoData)requestDatatoTierServer.request("samInfo");
		memUse = samInfoData.memUse;
		return memUse;
	}
	
	
	public double getCdf01DiskSize(){
		double cdf01Size=0;
		samDiskData = (SAMDiskData)requestDatatoTierServer.request("samDisk");
		cdf01Size = samDiskData.cdf01_size;
		return cdf01Size;
	}
	public double getCdf01DiskUsed(){
		double cdf01Used=0;
		samDiskData = (SAMDiskData)requestDatatoTierServer.request("samDisk");
		cdf01Used = samDiskData.cdf01_used;
		return cdf01Used;
	}
	public String getCdf01DiskUsage(){
		String cdf01Usage="";
		samDiskData = (SAMDiskData)requestDatatoTierServer.request("samDisk");
		cdf01Usage = samDiskData.cdf01_use_percentage;
		return cdf01Usage;
	}	
	
	public double getCdf02DiskSize(){
		double cdf02Size=0;
		samDiskData = (SAMDiskData)requestDatatoTierServer.request("samDisk");
		cdf02Size = samDiskData.cdf02_size;
		return cdf02Size;
	}
	public double getCdf02DiskUsed(){
		double cdf02Used=0;
		samDiskData = (SAMDiskData)requestDatatoTierServer.request("samDisk");
		cdf02Used = samDiskData.cdf02_used;
		return cdf02Used;
	}	
	public String getCdf02DiskUsage(){
		String cdf02Usage="";
		samDiskData = (SAMDiskData)requestDatatoTierServer.request("samDisk");
		cdf02Usage = samDiskData.cdf02_use_percentage;		
		return cdf02Usage;
	}
	
	public double getCdfUserDiskSize(){
		double cdfUserDiskSize=0;
		samDiskData = (SAMDiskData)requestDatatoTierServer.request("samDisk");
		cdfUserDiskSize = samDiskData.general_disk_size;
		return cdfUserDiskSize;
	}
	public double getCdfUserDiskUsed(){
		double cdfUserDiskUsed=0;		
		samDiskData = (SAMDiskData)requestDatatoTierServer.request("samDisk");
		cdfUserDiskUsed = samDiskData.general_disk_used;		
		return cdfUserDiskUsed;
	}
	public String getCdfUserDiskUsage(){
		String cdfUserDiskUsage="";
		samDiskData = (SAMDiskData)requestDatatoTierServer.request("samDisk");
		cdfUserDiskUsage = samDiskData.general_disk_use_percentage;
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
