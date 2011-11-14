package kisti.module.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
public class ConnectToGridServer {

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
	public ConnectToGridServer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * Desc :
	 * 
	 * @Method Name : requestDataToServer
	 * @param serviceName
	 * @return
	 * 
	 */
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
			// JOptionPane.showMessageDialog(this, "Message");
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

		try {
			openServer(IPADDR_SAM);

			int n = 16;
			n <<= 8;
			n |= 1;
			String s = Integer.toString(n);

			byte[] b = s.getBytes();

			String cmdString = "cat /proc/meminfo | sed '3,$d' | awk '{print $2\" \"}';  \\"
					+ "dstat 1 1 | sed '1,3d' | awk '{print $3\" \"$7\" \"$8\" \"$9\" \"$10}'; \0";

			System.out.println(cmdString);

			dos.write(b, 0, b.length);
			dos.write(cmdString.getBytes(), 0, cmdString.getBytes().length);

			char[] cbuf = new char[1024];

			String resultData = "";
			while (reader.read(cbuf, 0, 1024) != -1) {
				String str = new String(cbuf);
				str = str.trim();
				str += " ";

				resultData += str;
				resultData = resultData.replace("|", " ");

				for (int i = 0; i < 1024; i++)
					cbuf[i] = '\0';
			}
			StringTokenizer st = new StringTokenizer(resultData, "  ");

			// make XML
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			System.out.println("getSamInfo:"+resultData);
			// root element

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Data");
			doc.appendChild(rootElement);

			Element node = doc.createElement("Node");
			rootElement.appendChild(node);

			Element memTotal = doc.createElement("memTotal");
			memTotal.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(memTotal);
			Element memUse = doc.createElement("memUse");
			memUse.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(memUse);
//			Element cpuUse = doc.createElement("cpuUse");
//			cpuUse.appendChild(doc.createTextNode(st.nextToken()));
//			node.appendChild(cpuUse);
			Element diskRead = doc.createElement("diskRead");
			diskRead.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(diskRead);
			Element diskWrite = doc.createElement("diskWrite");
			diskWrite.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(diskWrite);
			Element netIn = doc.createElement("netIn");
			netIn.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(netIn);
			Element netOut = doc.createElement("netOut");
			netOut.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(netOut);

			// 파일에 쓰기
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "No");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileOutputStream(
					new File("SamInfo.xml")));
			transformer.transform(source, result);
			System.out.println("File saved!");

			// 스트링으로 축출
			File xmlFile = new File("SamInfo.xml");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(xmlFile.getPath())));
			String xmlString = "";
			String str = "";
			while ((str = br.readLine()) != null) {
				xmlString += str;
			}

			obj = xmlString;

			br.close();
			dos.close();

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
		} else if (str.contains("M")) {
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
			String resultData = "";
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

			if (xmlFile.exists()) {
				xmlFile.delete();
			}
			xmlFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(xmlFile.getPath()), "UTF-8"));

			// String tmp = resultData;
			resultData = resultData.replace("\000", "");
			resultData = resultData.replace("\0x0", "");
			// resultData.replace("\r","");
			// resultData.replace("\n","");
			// resultData.replace("\0x10","");
			// resultData.replace("\0x13","");

			// resultData = stripNonValidXMLCharacters(resultData);

			System.out.println(resultData);

			obj = resultData;

			// bw.write(resultData );
			// bw.flush();
			// bw.close();

			// xmlParser parser = new xmlParser();
			// obj = parser.parseString("pbsnode_org.xml");
			// obj = parser.parseString("pbsnodes.xml");
			// obj = parser.parseString("xxx.xml");

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
			String resultData = "";
			String str = "";

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

			StringTokenizer st = new StringTokenizer(str, " ");

			/**
			 * xml 만드리
			 */
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Data");
			doc.appendChild(rootElement);

			Element node = doc.createElement("Node");
			rootElement.appendChild(node);

			Element cdf01Size = doc.createElement("cdf01Size");
			cdf01Size.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(cdf01Size);
			Element cdf01Used = doc.createElement("cdf01Used");
			cdf01Used.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(cdf01Used);
			Element cdf01Usage = doc.createElement("cdf01Usage");
			cdf01Usage.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(cdf01Usage);
			Element cdf02Size = doc.createElement("cdf02Size");
			cdf02Size.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(cdf02Size);
			Element cdf02Used = doc.createElement("cdf02Used");
			cdf02Used.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(cdf02Used);
			Element cdf02Usage = doc.createElement("cdf02Usage");
			cdf02Usage.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(cdf02Usage);
			Element generalDiskSize = doc.createElement("generalDiskSize");
			generalDiskSize.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(generalDiskSize);
			Element generalDiskUsed = doc.createElement("generalDiskUsed");
			generalDiskUsed.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(generalDiskUsed);
			Element generalDiskUsage = doc.createElement("generalDiskUsage");
			generalDiskUsage.appendChild(doc.createTextNode(st.nextToken()));
			node.appendChild(generalDiskUsage);

			/**
			 * XML 파일로 쓰기
			 */
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "No");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileOutputStream(
					new File("SamDisk.xml")));
			transformer.transform(source, result);
			
			/**
			 * String 으로 축출... 
			 */
			File xmlFile = new File("SamDisk.xml");
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(xmlFile.getPath())));
	        String xmlString="";
	        String tmp="";
	        while ( (tmp = br.readLine()) != null) {
				xmlString += tmp;					
			}
			
	        obj = xmlString;			
	        br.close();
			
			dos.close();
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

			/**
			 * XML 데이터 생성
			 */		
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Data");
			doc.appendChild(rootElement);
			Element node = doc.createElement("Node");
			rootElement.appendChild(node);
			Element dstat = doc.createElement("dstat");
			dstat.appendChild(doc.createTextNode(resultData));
			node.appendChild(dstat);
			
			/**
			 * XML 파일로 저장			 * 
			 */		
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.setOutputProperty(OutputKeys.INDENT, "No");       
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(new FileOutputStream(new File("SamDstat.xml")));	        
	        transformer.transform(source, result);

	        /**
	         * String 으로 축출... 
	         */
	        File xmlFile = new File("SamDstat.xml");
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(xmlFile.getPath())));
	        String xmlString="";
	        String str="";
	        while ( (str = br.readLine()) != null) {
				xmlString += str;					
			}
			
	        
	        obj = xmlString;
			
			
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
		ConnectToGridServer c = new ConnectToGridServer();
		// c.requestDataToServer("WorkerNodeStatus");
		// c.requestDataToServer("QueueStatus");
		c.requestDataToServer("samInfo");
		// c.requestDataToServer("getDstat");
		// c.requestDataToServer("samDisk");
		// c.requestDataToServer("test");

	}
}
