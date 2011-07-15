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

public class server {

	/**
	 * @param args
	 */
	String[] buf;
	Reader reader;
	BufferedReader br;
	PrintWriter pw;
	String IPADDR;
	int PORT;

	
	
	
	
	
	//==============================================================================================
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ServerSocket 	server;
	private Socket 			connection;
	private int 			counter = 0;	
	//==============================================================================================
	
	
	public server(){
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
	
	//===============================================================================================
//	//서버를 설정하고 실행한다. +
//	public void runServer(){
//		//서버가 연결 요청을 수신하도록 설정하고, 연결을 처리한다. 
//		try {
//			//단계 1 : ServerSocketd을 생성한다. 
//			ServerSocket server = new ServerSocket(IPADDR,PORT);
//			
//			while (true ) {
//				try {
//					// 단계 2: 연결 요청을 기다린다. 
//					waitForConnection();
//					// 단계 3: 입/출력 스트림을 얻어온다. 
//					getStream();
//					// /단계 4: 연결을 처리한다. 
//					processConnection();					
//				}catch (EOFException eofException){
//					// 클라이언트가 연결을 닫았을 때 발생하는 EOFExecption을 처리한다.
//					System.err.println("Server terminated connection");
//				}catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}finally{
//					//단계 5: 연결을  닫는다. 
//					closeConnection();
//					++counter;
//				}				
//			}// while 의 끝
//		}catch (IOException ioException ){
//			ioException.printStackTrace();
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//	//연결 요청이 도착할 때까지 기다리고, 도착하면 여결 정보를 표시하낟. 
//	private void waitForConnection() throws IOException{
//		System.out.println("waiting for connection ");
//		connection = server.accept(); // 서버가 연결 요청을 승인하도록 한다. 
//		System.out.println("connection "+counter +" received form:"+ connection.getInetAddress().getHostName());		
//	}
//	
//	//데이터 송/수신을 위한 스트림을 얻어온다. 
//	private void getStream() throws IOException{
//		//객체에 대한 출력 스트림을 설정한ㄷ. 
//		output = new ObjectOutputStream(connection.getOutputStrem());
//		output.flush() ; // 헤더 정보를 송신하기 위해 출력 버퍼를 비운다 
//		//객체에 대한 입력 스트림을 설정한다. 
//		input = new ObjectInputStream(connection.getInputStream());
//		
//		System.out.println("Got I/O stream");
//	}
//	
//	//클라이언트와의 연결을 처리한다. 
//	private void processConnection() throws IOException{
//		//연결 요청이 성공했다는 메시지를 클라이언트에 송신한다. 
//		String message = " Connection successful ";
//		sendData(message);
//		
//		//메시지를 읽고 표시한다. 
//		do {
//			try {
//				message = (String) input.readObject();
//				System.out.println("\n "+message);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		} while (!message.equals("CLIENT>>> TERMINATE"));
//	}//processConnection 메소드 끝
//	
//	
//	// 스트림들과 소켓을 닫는다. 
//	private void closeConnection(){
//		System.out.println("\n Terminating connection");
//		
//		
//		try {
//			output.close();
//			input.close();
//			connection.close();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	
//	//클라이언트에 메시지를 송신한다. 
//	private void sendData(String message){
//		//클라이언트에 객체를 송신한다. 
//		try{
//			output.writeObject("SERVER>> "+message);
//			output.flush();
//			System.out.println("SERVER>> "+message);
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	 
//	
//	//이벤트 디스패치 스레드에 있는 displayArea를 조작하기 우해 
//	//다른 스레드들이 호출하는 유틸리티 메소드
//	private void displayMessage(final String messageToDisplay){
//		//이벤트 디스패치 스레드로부터 받은 실행 메시지를 표시한다. 
////		SwingUtilities.invokeLater(
////				new Runnable(){ // displayArea
////					public void run(){
////						displayArea.appen(messageToDisplay);
////						displayArea.setCarePosition( displayArea.getText().length() );
////					}
////				}//내부 클래스의 끝
////				);//SwingUtilities.invokerLater에 대한 호출의 끝 
//	}
//	
//	
	
	
	
	//===============================================================================================
	
	
	
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
		server s = new server();		;

	}
}
