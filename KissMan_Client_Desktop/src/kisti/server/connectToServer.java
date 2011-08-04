package kisti.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;

import javax.print.attribute.standard.Severity;


/**
 * <pre>
 * kisti.server
 *   |_ socket2.java
 *
 * </pre>
 *
 * Desc :
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 7. 11. AM 10:34:06
 * @Version:
 *
 */
public class connectToServer {

	//134.75.123.33 ce
	private static final String IPADDR = "150.183.234.168";
	private static final int PORT = 8197;
	
	private Socket socket ;
	private BufferedInputStream bis ;
	private BufferedOutputStream bos ;
	private InputStreamReader isr ;
	private BufferedReader reader;
	/**
	 * 
	 * Desc : Constructor of connectToServer.java class
	 */
	public connectToServer() {
		// TODO Auto-generated constructor stub
	}
	
	public Object requestDataToServer(String serviceName){
		Object obj = null;
		if (serviceName.equals("WorkerNodeStatus")) {
			obj = retiveWorkerNodeStatus();
		}else if (serviceName.equals("")) {
			obj = null;
		}else {
			System.out.println("잘못된 요청");
		}
		
		return obj;
	}
	/**
	 * 
	 * Desc : 
	 * @Method Name : 
	 *
	 */
	public void openServer(){
		try {			
			socket = new Socket(IPADDR,PORT);			
			bis = new BufferedInputStream(socket.getInputStream());
			bos = new BufferedOutputStream( socket.getOutputStream());
			isr = new InputStreamReader(bis,"US-ASCII");
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						
			System.out.println("open server");
			
		} catch (Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
        } 
	}
	/**
	 * 
	 * Desc :
	 * @Method Name : closeServer
	 *
	 */
	public void closeServer(){
		try {
			bis.close();
			bos.close();
			socket.close();
			System.out.println("Close server");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	/**
	 * 
	 * Desc :
	 * @Method Name : retiveWorkerNodeStatus
	 * @return
	 *
	 */
	public Object retiveWorkerNodeStatus(){		
		Object obj = null;
		openServer();
		
		try {
			int n = 0x0102;
	    	bos.write(n);
	    	bos.flush();		
			

			String str1;// = reader.readLine();
			while ((str1 = reader.readLine()) != null) {
				System.out.println("STR : " + str1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		}
				
		closeServer();
		return obj;
	}	
    /**
     * Desc :
     * @Method Name : main
     * @param argv
     * 
     */
    public static void main(String[] argv) {
    	connectToServer c = new connectToServer();
    	//c.connectWithBR();
    }
}
