package kisti.server;


import java.net.*;
import java.io.*;

public class server {

	/**
	 * @param args
	 */
	String[] buf;
	Reader reader;
	BufferedReader br;
	String IPADDR;
	int PORT;
	
	public server(){
		IPADDR = "150.183.234.168";
		PORT = 8197;
		buf = new String[2048];
		ConnectToServer();
	}
	
	public void ConnectToServer(){
		try {
			//���� ���� ���� 
			Socket socket = new Socket(IPADDR,PORT);
			
			//Server --> Client: �������� ������ �����͸� �б� ���� ��Ʈ��
			reader = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(reader);
			
			//Clinet --> SErver: ũ���̾�Ʈ���� ������ �����͸� ������ ���� ��¿� ��Ʈ��
			//pw = new BufferedReader(socket.getOutputStream());
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in ConnectToServer(){}"+e);
			System.exit(1);
		}
	}
	
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

	}
}
