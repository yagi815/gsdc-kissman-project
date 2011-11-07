package kisti.module.tierServer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import kisti.module.database.KissManDatabase;
//import kisti.module.server.SAMInfoData;
import kisti.module.server.connectToServer;

/**
 * <pre>
 * kisti.module.tierServer
 *   |_ TierServer.java
 *
 * </pre>
 *
 * Desc : 
 * 1. android api 와 TCP 통신을 한다..
 * 2. android 에서 서비스 번호를 요청하면 구분하여 해달 모듈을 호출하고. 데이터를 받아 온다. 
 * 3. 받은 데이터를 스트링 형식으로 android api 모듈로 전송한다.  
 * 
 * @Company : KISTI
 * @Author :daniel
 * @Date   :2011. 10. 26. 오후 3:30:12
 * @Version:
 *
 */
 class SAMInfoData implements Serializable{
	
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

public class TierServer implements Runnable{

	public static final int PORT = 8197;
	public static final String IPADDR = "150.183.234.167";
	public connectToServer moduleServer;	
	public ServerSocket serverSocket=null;
	
	
	
	public TierServer() {
		// TODO Auto-generated constructor stub
		
		moduleServer = new connectToServer();
		
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	
		
		try {
			while (true) {
			System.out.println("[server]------- Server Connecting ------ ");
			
			
			
				Socket sock = serverSocket.accept();
				System.out.println("[server]server reciving....");
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					String requestService = in.readLine();
					System.out.println("[server] requestServer: "+ requestService);
//					Object obj = moduleServer.requestDataToServer(str);
				
					Object obj = null;
					
					if (requestService.substring(0, 3).equals("DB_")) {//디비요청
						KissManDatabase kissmanDB = new KissManDatabase();
						if(requestService.equals("DB_requestCdfJobTable")){
							obj = kissmanDB.requestDataToDataBase("CDFJobTable");
						}else if (requestService.equals("DB_requestJobGraph")) {
							obj = kissmanDB.requestDataToDataBase("jobGraph");
						} 
					}else if(requestService.substring(0, 4).equals("URL_")){ //URL 요청

						/**
						 * URL 객체 web형태로 저장하여 넘겨주는 방법!!!!
						 */	

					}else{ // kissman server 요청						
						obj = moduleServer.requestDataToServer(requestService);
						
						
						System.out.println("[TierServer] kissman server 요청");
					}
					
					SAMInfoData sam = new SAMInfoData();
					sam.cpuUse = 1;
					sam.disk_read = 19;
					sam.net_in = 34;
					sam.memTotal = 189;
					String str = "adgasdgadsgs";
					class A implements Serializable{
						int a;
						int b;
					}
					
					A a = new A();
					a.a = 1;
					a.b = 10;
					
					obj = sam;
				
					sendDataToClient(obj, sock);
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.toString());
					e.printStackTrace();
				}finally{
					System.out.println("[server]------ close socket...------ ");
					sock.close();					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
	}
	
	/**
	 * 
	 * Desc :
	 * @Method Name : sendDataToClient
	 * @param sendData
	 * @param sock
	 *
	 */
	public void sendDataToClient(Object sendData, Socket sock){
		ObjectOutputStream oos=null;
		try {
//			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));
//			out.println(sendData);
//			out.flush();	
			if (sendData == null) {
				System.out.println("[TierServer]sendData is null...... ");
			}
			oos = new ObjectOutputStream(sock.getOutputStream());
			oos.writeObject(sendData);
			oos.flush();
			
			System.out.println("[TierServer] sendDataToClient...");
//			System.out.println("[TierServer] senddata: "+sendData.toString());			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			e.printStackTrace();
		}finally{
			try {
				oos.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
	}
	
	public static void main(String[] args){
		TierServer t = new TierServer();
	}	
}