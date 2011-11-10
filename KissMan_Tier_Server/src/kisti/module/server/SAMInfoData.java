package kisti.module.server;

import java.io.Serializable;

public class SAMInfoData implements Serializable{
	 private static final long serialVersionUID = 819781978197L;
	public double memUsage;
	public double memTotal;
	public double memUse;
	public double cpuUse;
	public double disk_write;
	public double disk_read;
	public double net_in;
	public double net_out;
	
//	public SAMInfoData() {
//		// TODO Auto-generated constructor stub
//	}
}
