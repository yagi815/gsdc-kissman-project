package kisti.module.tierServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TierServer_ {

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
		
	final static int PORT = 8197;
	final static String IPADDR = "150.183.234.167";
		
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket server = new ServerSocket(PORT);
			System.out.println("접속을 기다립니다.. ");
			
			while (true) {
				Socket sock = server.accept(); //요청 대기
				ListenerThread  listenerThread = new ListenerThread (sock);  
				listenerThread.start();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
}
class ListenerThread   extends Thread{
	private Socket sock;
	private String id;
	private BufferedReader br;
	
	public ListenerThread(Socket sock) {
		// TODO Auto-generated constructor stub
		this.sock = sock;
		try{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			String requestString = br.readLine();
			System.out.println(requestString);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
}
