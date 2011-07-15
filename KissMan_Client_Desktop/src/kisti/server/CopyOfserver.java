package kisti.server;


import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.SwingUtilities;

public class CopyOfserver {

	/**
	 * @param args
	 */
	String[] buf;
	Reader reader;
	BufferedReader br;
	PrintWriter pw;
	String IPADDR;
	int PORT;

	

	
	public CopyOfserver(){
		IPADDR = "150.183.234.168";
		PORT = 8197;
		buf = new String[2048];
		ConnectToServer();
		
		
		System.out.println("server.....");
	}
	
	public void ConnectToServer(){
		try {
			//서버 소켓 생성 
			Socket socket = new Socket(IPADDR,PORT);
			
			//Server --> Client: 서버에서 보내온 데이터를 읽기 위한 스트림
			reader = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(reader);
			
			System.out.println(" --- InputStreamreader ");
			//Clinet --> SErver: 클라이언트에서 서버로 데이터를 보내기 위한 출력용 스트림
			pw = new PrintWriter(socket.getOutputStream());
			pw.write("1");
			System.out.println(" ---- PrintWriter ");
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in ConnectToServer(){}"+e);
			System.out.println(e.toString());
			e.printStackTrace();
			System.exit(1);
		}
	}
	

	
	
	public String[] GetMessage(){
		//서버로 부터 메시지를 읽어서 버퍼에 저장
		// 버퍼는 GUI 에서 불려 들여짐.
		
		int i = 0;
		try {
			buf[i++] = br.readLine();
			System.out.println(buf[i]);
			//gui.setText(buf);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in Public void Run() -->"+e);
			System.exit(1);
		}finally{
			// TODO: handle exception
			System.out.println("Finally");
		}
		return buf;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CopyOfserver s = new CopyOfserver();		;

	}
}
