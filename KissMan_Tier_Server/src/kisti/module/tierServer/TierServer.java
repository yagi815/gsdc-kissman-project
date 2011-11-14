package kisti.module.tierServer;


import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import kisti.module.database.ConnectToKissmanDB;
import kisti.module.server.ConnectToGridServer;


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

public class TierServer implements Runnable{

	public static final int PORT = 8197;
	public static final String IPADDR = "150.183.234.167";
	public ConnectToGridServer moduleServer;	
	public ServerSocket serverSocket=null;
		
	
	public TierServer() {
		// TODO Auto-generated constructor stub
		
		moduleServer = new ConnectToGridServer();
		
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


					Object obj = null;
					
					if (requestService.substring(0, 3).equals("DB_")) {//디비요청
						ConnectToKissmanDB kissmanDB = new ConnectToKissmanDB();
//						if(requestService.equals("DB_requestCdfJobTable")){
//							obj = kissmanDB.requestDataToDataBase("CDFJobTable");
//						}else if (requestService.equals("DB_requestJobGraph")) {
//							obj = kissmanDB.requestDataToDataBase("jobGraph");
//						} 
						obj = kissmanDB.requestDataToDataBase("nothing....");
						sendXMLDataToClient(obj, sock);
					}else if(requestService.substring(0, 4).equals("URL_")){ //URL 요청

						System.out.println("URL request ........ >>>>>> ");
						String URL ="http://vobox02.sdfarm.kr/pbswebmon/pbswebmon-ce03/cgi-bin/graph.py?start=-24h";
						
						
						/**
						 * URL 에서 이미지 url 축출하고 이미지를 다운로드 
						 */
						String sendImageName = "ceGraphImage.bmp";
						CeMonGraph cm = new CeMonGraph();
						String imgUrl = cm.getImageUrl(URL);
						if (cm.downloadImage(imgUrl , sendImageName)) {
							System.out.println("download is done.....");
						}
				
						
						sendImgDataToClient(sendImageName, sock);
		
					}else{ // kissman server 요청						
						obj = moduleServer.requestDataToServer(requestService);
						sendXMLDataToClient(obj, sock);
						System.out.println("[TierServer] kissman server 요청");
					}
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
	 * @Method Name : sendImgDataToClient
	 * @param fileName
	 * @param sock
	 *
	 */	
	public void sendImgDataToClient(String fileName, Socket sock){		
		
		try {
			
			File imgFile = new File(fileName);
			FileInputStream fis = new FileInputStream(imgFile);
			OutputStream os = sock.getOutputStream();
			
						
			byte[] buffer = new byte[1024];
			while (fis.available() > 0) {
				int readSize = fis.read(buffer);
				os.write(buffer,0,readSize);
			}			
			fis.close();
			os.close();
			sock.close();
			
			System.out.println("Sending img file  done.......>>>");			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public void sendXMLDataToClient(Object sendData, Socket sock){
		ObjectOutputStream oos=null;
		try {
			if (sendData == null) {
				System.out.println("[TierServer]sendData is null...... ");
			}
			oos = new ObjectOutputStream(sock.getOutputStream());
			oos.writeObject(sendData);
			oos.flush();
			
			System.out.println("[TierServer] sendDataToClient...");
			
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