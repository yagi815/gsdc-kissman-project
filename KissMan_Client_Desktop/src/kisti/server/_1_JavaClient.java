package kisti.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class _1_JavaClient {
	public static void main(String[] args) {
	  Socket socket = null;
	  
	  InputStream is = null;
	  DataInputStream dis = null;
	  OutputStream os = null;
	  DataOutputStream dos = null;
	  
	  String addr = args[0];
	  String serverMsg = null;
	  String msg = "Hello";
	  
	  try {
	    socket = new Socket(addr, 8090);
	    System.out.println("Server Connection Success");
	    System.out.println("client socket : " + socket);
	    
	    is = socket.getInputStream();
	    dis = new DataInputStream(is);
	    
	    os = socket.getOutputStream();
	    dos = new DataOutputStream(os);
	    
	    //serverMsg = new String(dis.readUTF());
	    dos.writeUTF(msg);
	    
	    //System.out.println("서버에서 받은 메세지 : " + serverMsg);
	    
	    dis.close();
	    socket.close();
	  }catch(Exception e) {
	    e.printStackTrace();
	  }
	}
}