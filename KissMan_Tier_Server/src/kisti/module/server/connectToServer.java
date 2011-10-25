package kisti.module.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import kisti.module.log.ErrorPopup;

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
	private static final String IPADDR_DANIEL_SERVER = "150.183.234.168";
	private static final String IPADDR_SAM = "sam0.sdfarm.kr";
	private static final String IPADDR_CE03 = "ce03.sdfarm.kr";
	private static final int PORT = 9734;

	private Socket socket;
	private BufferedInputStream bis;
	// private BufferedOutputStream bos;
	private InputStreamReader isr;
	private BufferedReader reader;
	private DataOutputStream dos;

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
			obj = getCAFWorkerNodeStatus();
		} else if (serviceName.equals("QueueStatus")) {
			obj = getCAFQueueStatus();
		} else if (serviceName.equals("samInfo")) {
			obj = getSamInfo();
		} else if (serviceName.equals("getDstat")) {
			obj = getSamDstat();
		} else if (serviceName.equals("samDisk")) {
			obj = getSamDisk();
		} else if (serviceName.equals("test")) {
			test();
		} else {
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
			// bos = new BufferedOutputStream(socket.getOutputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			dos.flush();
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			System.out.println("open server");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
//			JOptionPane.showMessageDialog(this, "Message");
			ErrorPopup popup = new ErrorPopup("Can't open server");
			System.exit(-1);
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
			dos.close();
			reader.close();
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
			BufferedInputStream bis1 = new BufferedInputStream(
					socket1.getInputStream());
			BufferedOutputStream bos1 = new BufferedOutputStream(
					socket1.getOutputStream());
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
			bos1.write(cmd, 0, cmd.length);

			bos1.flush();

			String str1;// = reader.readLine();
			char[] cbuf = new char[1024];

			while (isr1.read(cbuf, 0, 1024) != -1) {
				String str = new String(cbuf);
				str = str.trim();

				System.out.println("str: " + str);

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
	 * 
	 * @Method Name : getSamInfo
	 * @return
	 * 
	 */
	public Object getSamInfo() {

		Object obj = null;
		SAMInfoData data = new SAMInfoData();

		try {
			openServer(IPADDR_SAM);

			int n = 16;
			n <<= 8;
			n |= 1;
			String s = Integer.toString(n);

			byte[] b = s.getBytes();

			String cmdString = "cat /proc/meminfo | sed '3,$d' | awk '{print $2\" \"}';  \\" +			
			"dstat 1 1 | sed '1,3d' | awk '{print $3\" \"$7\" \"$8\" \"$9\" \"$10}'; \0";
			
			System.out.println(cmdString);


			dos.write(b, 0, b.length);
			dos.write(cmdString.getBytes(), 0, cmdString.getBytes().length);

			// dos.flush();

			char[] cbuf = new char[1024];
			

			String resultData = "";
			while (reader.read(cbuf, 0, 1024) != -1) {
				String str = new String(cbuf);
				str = str.trim();
				str +=" ";

				resultData += str;				
				resultData = resultData.replace("|", " ");
				
				
				
								
			

				for (int i = 0; i < 1024; i++)
					cbuf[i] = '\0';
			}

			
			
			System.out.println("resultData : "+resultData);
			
			
			
			StringTokenizer st = new StringTokenizer(resultData,"  ");
			System.out.println("n of tokens : "+st.countTokens());

//			for (int i = 0; i <7; i++) {
//				System.out.println("-> "+st.nextToken());
//				System.out.println(":");
//			}
			
			if (st.countTokens() > 6) {			
				data.memTotal= Double.parseDouble(st.nextToken());
				data.memUse = Double.parseDouble(st.nextToken());
				data.cpuUse = 100 - Double.parseDouble(st.nextToken());
				
				data.disk_read = Double.parseDouble(parseString(st.nextToken()));
				data.disk_write = Double.parseDouble(parseString(st.nextToken()));
				data.net_in= Double.parseDouble(parseString(st.nextToken()));
				data.net_out = Double.parseDouble(parseString(st.nextToken()));
			}else {
				data.memTotal= 1.0;
				data.memUse = 1.0;
				data.cpuUse = 0.0;
				
				data.disk_read = 0.0;
				data.disk_write = 0.0;
				data.net_in = 0.0;
				data.net_out = 0.0;
			}


			
			
			obj = data;

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
 * @Method Name : parseString
 * @param str
 * @return
 *
 */
	String parseString(String str) {

		if (str.contains("B")) {			
			str = str.replace("B", "");			
		} else if (str.contains("k")) {			
			str = str.replace("k", "000");			
		} else if(str.contains("M")){
			str = str.replace("M", "000000");
		} else {
			System.out.println(str);
		}

		return str;
	}
	
	/**
	 * 
	 * Desc :
	 * 
	 * @Method Name : getQueueStatus
	 * @return
	 * 
	 */
	public Object getCAFQueueStatus() {

		Object obj = null;

		try {
			openServer(IPADDR_CE03);

			int n = 16;
			n <<= 8;
			n |= 1;
			String s = Integer.toString(n);

			byte[] b = s.getBytes();
			String cmdString = "qstat -Qa\0";
			System.out.println(cmdString);
			// byte[] cmd = cmdString.getBytes();

			dos.write(b, 0, b.length);
			dos.write(cmdString.getBytes(), 0, cmdString.getBytes().length);

			// dos.flush();

			char[] cbuf = new char[1024];
			String resultData = null;
			while (reader.read(cbuf, 0, 1024) != -1) {
				String str = new String(cbuf);
				str = str.trim();
				str += "\n";
				System.out.println("str: " + str);
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
	public Object getCAFWorkerNodeStatus() {
		Object obj = null;

		try {
			openServer(IPADDR_CE03);

			int n = 16;
			n <<= 8;
			n |= 1;
			String s = Integer.toString(n);

			byte[] b = s.getBytes();
			String cmdString = "pbsnodes -x\0";
			System.out.println(cmdString);
			// byte[] cmd = cmdString.getBytes();

			dos.write(b, 0, b.length);
			dos.write(cmdString.getBytes(), 0, cmdString.getBytes().length);
			// dos.flush();

			char[] cbuf = new char[1024];
			String resultData = "";

			while (reader.read(cbuf, 0, 1024) != -1) {
				String str = new String(cbuf);
				
				str = str.trim();
				resultData += str;

				for (int i = 0; i < 1024; i++)
					cbuf[i] = '\0';
			}


			
			File xmlFile = new File("pbsnodes.xml");
			
			if(xmlFile.exists()){
				xmlFile.delete();
			}
			xmlFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(xmlFile.getPath()),"UTF-8"));
			
//			String tmp = resultData;
			resultData = resultData.replace("\000", "");
			resultData  = resultData.replace("\0x0","");
//			 resultData.replace("\r","");
//			 resultData.replace("\n","");
//			 resultData.replace("\0x10","");
//			 resultData.replace("\0x13","");
			
//			resultData = stripNonValidXMLCharacters(resultData);



			System.out.println(resultData );
		
		
			bw.write(resultData	);			
			bw.flush();
			bw.close();
			
			
			
			
			xmlParser parser = new xmlParser();
//			obj = parser.parseString("pbsnode_org.xml");
			obj = parser.parseString("pbsnodes.xml");
//			obj = parser.parseString("xxx.xml");

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
	 * @Method Name : stripNonValidXMLCharacters
	 * @param in
	 * @return
	 * 
	 */
	public String stripNonValidXMLCharacters(String in) {
		StringBuffer out = new StringBuffer(); // Used to hold the output.
		char current; // Used to reference the current character.

		if (in == null || ("".equals(in)))
			return ""; // vacancy test.
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught
									// here; it should not happen.
			if ((current == 0x9) || (current == 0xA) || (current == 0xD)
					|| ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD))
					|| ((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}

	/**
	 * 
	 * Desc :
	 * 
	 * @Method Name : getSamDstat
	 * @return
	 * 
	 */
	public Object getSamDisk() {
		Object obj = null;
		SAMDiskData data = new SAMDiskData();

		try {
			openServer(IPADDR_SAM);
			// bos.flush();
			int n = 16;
			n <<= 8;
			n |= 1;
			String s = Integer.toString(n);

			byte[] b = s.getBytes();
			// df /cdf01 | sed '1d' | awk '{print $2 " "$3}'
			String cmdString = "df  /cdf01 | sed '1d' | awk '{print $2\" \" $3\" \" $5}'; \\"
					+ "df  /cdf02 | sed '1d' | awk '{print $2\" \" $3\" \" $5}'; \\"
					+ "df  /home-osg/CDF | sed '1,2d' | awk '{print $1\" \" $2\" \" $4}'; \0";
			char[] cbuf = new char[1024];
			String resultData = null;
			String str ="";

			System.out.println(cmdString);
			dos.write(b, 0, b.length);
			dos.write(cmdString.getBytes(), 0, cmdString.getBytes().length);
			// dos.flush();

			while (reader.read(cbuf, 0, 1024) != -1) {
				String temp = new String(cbuf);
				temp = temp.trim();
				temp += " ";

				System.out.println(temp);
				str += temp;

				for (int j = 0; j < 1024; j++)
					cbuf[j] = '\0';
			}

			System.out.println("--" + str);

			StringTokenizer st = new StringTokenizer(str, " ");


			data.cdf01_size = Double.parseDouble(st.nextToken());
			data.cdf01_used = Double.parseDouble(st.nextToken());
			data.cdf01_use_percentage = st.nextToken();

			data.cdf02_size = Double.parseDouble(st.nextToken());
			data.cdf02_used = Double.parseDouble(st.nextToken());
			data.cdf02_use_percentage = st.nextToken();

			data.general_disk_size = Double.parseDouble(st.nextToken());
			data.general_disk_used = Double.parseDouble(st.nextToken());
			data.general_disk_use_percentage = st.nextToken();

			closeServer();
			obj = data;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

		return data;
	}

	/**
	 * 
	 * Desc :
	 * 
	 * @Method Name : retiveWorkerNodeStatus
	 * @return
	 * 
	 */
	public Object getSamDstat() {
		Object obj = null;

		try {
			openServer(IPADDR_SAM);
			// bos.flush();
			int n = 16;
			n <<= 8;
			n |= 1;
			String s = Integer.toString(n);

			byte[] b = s.getBytes();
			String cmdString = "dstat 1 1 | sed '1,3d' \0";
			System.out.println(cmdString);

			dos.write(b, 0, b.length);
			dos.write(cmdString.getBytes(), 0, cmdString.getBytes().length);
			// dos.flush();

			char[] cbuf = new char[1024];
			String resultData = "";

			while (reader.read(cbuf, 0, 1024) != -1) {

				String str = new String(cbuf);
				str = str.trim();
				str += "\n";

				resultData += str;

				System.out.println("str:" + str);

				for (int i = 0; i < 1024; i++)
					cbuf[i] = '\0';

			}

			closeServer();
			obj = resultData;

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
//		 c.requestDataToServer("WorkerNodeStatus");
		// c.requestDataToServer("QueueStatus");
		 c.requestDataToServer("samInfo");
		// c.requestDataToServer("getDstat");
		// c.requestDataToServer("samDisk");
		// c.requestDataToServer("test");

	}
}
