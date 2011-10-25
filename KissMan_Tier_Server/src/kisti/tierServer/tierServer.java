package kisti.tierServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class tierServer implements Runnable{

	/**
	 * Desc : API(client) 와 통신하기 위한 서버 모듈
	 * API로부터 서비스 이름을 스트링 형태로 받아서 모듈서버에 데이터를 요청하고 
	 * 스티링 형태로 받아서 API 모듈측으로 보내준다. 
	 * 1. ServerScoket 생성
	 * 2. ServerSocket으 ㅣaccept() 매소드 대기
	 * 3. 클라이언트가 접속을 시도하면 accept 매소드가 클라이언트의 Socket을 반환
	 * 4. Socket으로부터 InputStream과 OutputStream을 구함
	 * 5. InputStream과 OutputStrea으 ㄹ이요한 통신
	 * 6. Socket의 close()매소드 반환
	 * @Method Name : main
	 * @param args
	 * 
	 */	
	
	
	private static final int PORT = 8197;
	private static ServerSocket server;
	private static InetAddress inetaddr;
	private static Socket sock;
	private static InputStream in;
	private static OutputStream out;
	private static BufferedReader br;
	private static PrintWriter pw;
	
	public tierServer() {
		// TODO Auto-generated constructor stub
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {	
	
//		while (true){
			execution();
//		}
		
	}
	
	public void execution(){
		try {
			open();
			
			String data="";
			
//			while ((data = br.readLine()) != null) {
//				System.out.println("클라이언트 요청:"+data);
//			}
			String requestCode = "";
			requestCode = br.readLine();
			System.out.println("requestCode:"+requestCode);
			
			// 클라이언트에서 요청한 서비스 코드를 try cache 하여
			// 데이터를 받고 API로 되돌려준다. 
			Calendar c = Calendar.getInstance();
			pw.print(c.getTime());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		close();
	}
	
	public void open(){
		try {
			server = new ServerSocket(8197);			
			sock = server.accept();
			inetaddr = sock.getInetAddress();

			in = sock.getInputStream();
			out = sock.getOutputStream();			
			pw = new PrintWriter(new OutputStreamWriter(out));
			br = new BufferedReader(new InputStreamReader(in));
			System.out.println("open server... ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
	}
	
	public void close(){
		try {
			pw.close();
			br.close();			
			sock.close();
			
			System.out.println("close server ...");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		tierServer t = new tierServer();
	}
}
