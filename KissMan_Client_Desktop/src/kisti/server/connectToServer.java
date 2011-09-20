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
 * 
 * @Company : KISTI
 * @Author : grkim
 * @Date : 2011. 7. 11. AM 10:34:06
 * @Version:
 * 
 */
public class connectToServer {

	// 134.75.123.33 ce
	private static final String IPADDR_DANIEL_SERVER	= "150.183.234.168";
	private static final String IPADDR_SAM = "sam0.sdfarm.kr";
	private static final String IPADDR_CE03 = "ce03.sdfarm.kr";
	private static final int PORT = 9734;

	private Socket socket;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	private InputStreamReader isr;
	private BufferedReader reader;

	/**
	 * 
	 * Desc : Constructor of connectToServer.java class
	 */
	public connectToServer() {
		// TODO Auto-generated constructor stub
	}

	public Object requestDataToServer(String serviceName) {
		Object obj = null;
		if (serviceName.equals("WorkerNodeStatus")) {
			obj = getWorkerNodeStatus();
		} else if (serviceName.equals("QueueStatus")) {
			obj = getQueueStatus();
		}else if(serviceName.equals("samInfo")){
			obj = getSamInfo();
		}else if(serviceName.equals("SAM_monText")){
			obj = getDstat();
		}else if(serviceName.equals("test"))		{
			test();			
		}else{
			System.out.println("잘못된 요청");
		}

		return obj;
	}

	/**
	 * 
	 * Desc :
	 * 
	 * @Method Name :
	 * 
	 */
	public void openServer(String IPADDR) {
		try {
			socket = new Socket(IPADDR, PORT);
			bos = new BufferedOutputStream(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("open server");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	
	/**
	 * 
	 * Desc :
	 * 
	 * @Method Name : closeServer
	 * 
	 */
	public void closeServer() {
		try {
			bos.close();
			socket.close();
			System.out.println("Close server");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

	
	public void test() {
		try {
			Socket socket1 = new Socket(IPADDR_DANIEL_SERVER, PORT);
			BufferedInputStream bis1 = new BufferedInputStream( socket1.getInputStream());
			BufferedOutputStream bos1 = new BufferedOutputStream(socket1.getOutputStream());
			InputStreamReader isr1 = new InputStreamReader(bis1, "US-ASCII");

			System.out.println("\nopen server");

			int n = 16;

			n <<= 8;
			n |= 1;

			String s = Integer.toString(n);
			
			byte[] b = s.getBytes();

			String cmdString = "df -h";
			byte[] cmd = cmdString.getBytes();
			
			bos1.write(b, 0, b.length);
			bos1.write(cmd,0, cmd.length);
			
			
			bos1.flush();
			

			String str1;// = reader.readLine();
			char[] cbuf = new char[1024];

			
			while (isr1.read(cbuf, 0, 1024) != -1) {
				String str = new String(cbuf);
				str = str.trim();
				
				System.out.println("str: "+str);

				for (int i = 0; i < 1024; i++)
					cbuf[i] = '\0';
			}

			
			System.out.println("close server");
			bis1.close();
			bos1.close();
			isr1.close();
			socket1.close();
			
			
			
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	
 
	/**
	 * 
	 * Desc :
	 * @Method Name : getSamInfo
	 * @return
	 *
	 */
	public Object getSamInfo() {
		
		Object obj = null;
		
		try {			
			openServer(IPADDR_SAM);

			int n = 16;
			n <<= 8;
			n |= 1;
			String s = Integer.toString(n);
			
			byte[] b = s.getBytes();
			String cmdString = "ps -ef";
			System.out.println(cmdString);
			byte[] cmd = cmdString.getBytes();
			
			bos.write(b, 0, b.length);			
			bos.write(cmd,0, cmd.length);
			
						
			bos.flush();

			char[] cbuf = new char[1024];

			String resultData = null;
			while (reader.read(cbuf, 0, 1024) != -1) {
				String str = new String(cbuf);
				str = str.trim();
				
				//System.out.println("str: "+str);
				resultData += str;
				obj = resultData;

				for (int i = 0; i < 1024; i++)
					cbuf[i] = '\0';
			}

		closeServer();
			
			
			
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
		return obj;
	}
	
	/**
	 * 
	 * Desc :
	 * @Method Name : getQueueStatus
	 * @return
	 *
	 */
	public Object getQueueStatus() {
		
		Object obj = null;
		
		try {			
			openServer(IPADDR_CE03);

			int n = 16;
			n <<= 8;
			n |= 1;
			String s = Integer.toString(n);
			
			byte[] b = s.getBytes();
			String cmdString = "qstat -Qa";
			System.out.println(cmdString);
			byte[] cmd = cmdString.getBytes();
			
			bos.write(b, 0, b.length);			
			bos.write(cmd,0, cmd.length);
			
						
			bos.flush();

			char[] cbuf = new char[1024];

			String resultData = null;
			while (reader.read(cbuf, 0, 1024) != -1) {
				String str = new String(cbuf);
				str = str.trim();
				
				//System.out.println("str: "+str);
				resultData += str;
				obj = resultData;

				for (int i = 0; i < 1024; i++)
					cbuf[i] = '\0';
			}

		closeServer();
			
			
			
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
		return obj;
	}
	
	/**
	 * 
	 * Desc :
	 * 
	 * @Method Name : retiveWorkerNodeStatus
	 * @return
	 * 
	 */
	public Object getWorkerNodeStatus() {
		Object obj = null;

		try {
			openServer(IPADDR_CE03);

			int n = 16;
			n <<= 8;
			n |= 1;
			String s = Integer.toString(n);

			byte[] b = s.getBytes();
			String cmdString = "qstat -Qa";
			System.out.println(cmdString);
//			byte[] cmd = cmdString.getBytes();

			bos.write(b, 0, b.length);
			bos.write(cmdString.getBytes(), 0,cmdString.getBytes().length );
			bos.flush();

			char[] cbuf = new char[1024];
			String resultData = null;
			
			while (reader.read(cbuf, 0, 1024) != -1) {
				String str = new String(cbuf);
				str = str.trim();
				resultData += str;

				for (int i = 0; i < 1024; i++)
					cbuf[i] = '\0';
			}
			
			xmlParser  parser = new xmlParser();
			obj = parser.parseString(resultData);

			closeServer();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

		return obj;
	}

	
	/**
	 * 
	 * Desc :
	 * 
	 * @Method Name : retiveWorkerNodeStatus
	 * @return
	 * 
	 */
	public Object getDstat() {
		Object obj = null;

		try {
			openServer(IPADDR_SAM);

			int n = 16;
			n <<= 8;
			n |= 1;
			String s = Integer.toString(n);

			byte[] b = s.getBytes();
			String cmdString = "dstat";
			System.out.println(cmdString);


			bos.write(b, 0, b.length);
			bos.write(cmdString.getBytes(), 0,cmdString.getBytes().length );
			bos.flush();

			char[] cbuf = new char[1024];
			String resultData = null;
			
			int c = 0;
			while (reader.read(cbuf, 0, 1024) != -1) {
				String str = new String(cbuf);
				str = str.trim();
				resultData += str;

				for (int i = 0; i < 1024; i++)
					cbuf[i] = '\0';
				
				
				if(c >10){
					closeServer();
				}
				c++;
			}
			
			
			closeServer();
			

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

		return obj;
	}
	/**
	 * Desc :
	 * 
	 * @Method Name : main
	 * @param argv
	 * 
	 */
	public static void main(String[] argv) {
		connectToServer c = new connectToServer();
//		c.requestDataToServer("WorkerNodeStatus");
//		c.requestDataToServer("QueueStatus");
//		c.requestDataToServer("samInfo");
		c.requestDataToServer("test");
		
		
	}
}
