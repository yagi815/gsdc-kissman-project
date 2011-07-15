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
			//���� ���� ���� 
			Socket socket = new Socket(IPADDR,PORT);
			
			//Server --> Client: �������� ������ �����͸� �б� ���� ��Ʈ��
			reader = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(reader);
			
			System.out.println(" --- InputStreamreader ");
			//Clinet --> SErver: Ŭ���̾�Ʈ���� ������ �����͸� ������ ���� ��¿� ��Ʈ��
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
//	//������ �����ϰ� �����Ѵ�. +
//	public void runServer(){
//		//������ ���� ��û�� �����ϵ��� �����ϰ�, ������ ó���Ѵ�. 
//		try {
//			//�ܰ� 1 : ServerSocketd�� �����Ѵ�. 
//			ServerSocket server = new ServerSocket(IPADDR,PORT);
//			
//			while (true ) {
//				try {
//					// �ܰ� 2: ���� ��û�� ��ٸ���. 
//					waitForConnection();
//					// �ܰ� 3: ��/��� ��Ʈ���� ���´�. 
//					getStream();
//					// /�ܰ� 4: ������ ó���Ѵ�. 
//					processConnection();					
//				}catch (EOFException eofException){
//					// Ŭ���̾�Ʈ�� ������ �ݾ��� �� �߻��ϴ� EOFExecption�� ó���Ѵ�.
//					System.err.println("Server terminated connection");
//				}catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}finally{
//					//�ܰ� 5: ������  �ݴ´�. 
//					closeConnection();
//					++counter;
//				}				
//			}// while �� ��
//		}catch (IOException ioException ){
//			ioException.printStackTrace();
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//	//���� ��û�� ������ ������ ��ٸ���, �����ϸ� ���� ������ ǥ���ϳ�. 
//	private void waitForConnection() throws IOException{
//		System.out.println("waiting for connection ");
//		connection = server.accept(); // ������ ���� ��û�� �����ϵ��� �Ѵ�. 
//		System.out.println("connection "+counter +" received form:"+ connection.getInetAddress().getHostName());		
//	}
//	
//	//������ ��/������ ���� ��Ʈ���� ���´�. 
//	private void getStream() throws IOException{
//		//��ü�� ���� ��� ��Ʈ���� �����Ѥ�. 
//		output = new ObjectOutputStream(connection.getOutputStrem());
//		output.flush() ; // ��� ������ �۽��ϱ� ���� ��� ���۸� ���� 
//		//��ü�� ���� �Է� ��Ʈ���� �����Ѵ�. 
//		input = new ObjectInputStream(connection.getInputStream());
//		
//		System.out.println("Got I/O stream");
//	}
//	
//	//Ŭ���̾�Ʈ���� ������ ó���Ѵ�. 
//	private void processConnection() throws IOException{
//		//���� ��û�� �����ߴٴ� �޽����� Ŭ���̾�Ʈ�� �۽��Ѵ�. 
//		String message = " Connection successful ";
//		sendData(message);
//		
//		//�޽����� �а� ǥ���Ѵ�. 
//		do {
//			try {
//				message = (String) input.readObject();
//				System.out.println("\n "+message);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		} while (!message.equals("CLIENT>>> TERMINATE"));
//	}//processConnection �޼ҵ� ��
//	
//	
//	// ��Ʈ����� ������ �ݴ´�. 
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
//	//Ŭ���̾�Ʈ�� �޽����� �۽��Ѵ�. 
//	private void sendData(String message){
//		//Ŭ���̾�Ʈ�� ��ü�� �۽��Ѵ�. 
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
//	//�̺�Ʈ ����ġ �����忡 �ִ� displayArea�� �����ϱ� ���� 
//	//�ٸ� ��������� ȣ���ϴ� ��ƿ��Ƽ �޼ҵ�
//	private void displayMessage(final String messageToDisplay){
//		//�̺�Ʈ ����ġ ������κ��� ���� ���� �޽����� ǥ���Ѵ�. 
////		SwingUtilities.invokeLater(
////				new Runnable(){ // displayArea
////					public void run(){
////						displayArea.appen(messageToDisplay);
////						displayArea.setCarePosition( displayArea.getText().length() );
////					}
////				}//���� Ŭ������ ��
////				);//SwingUtilities.invokerLater�� ���� ȣ���� �� 
//	}
//	
//	
	
	
	
	//===============================================================================================
	
	
	
	public String[] GetMessage(){
		//������ ���� �޽����� �о ���ۿ� ����
		// ���۴� GUI ���� �ҷ� �鿩��.
		
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
