package kisti.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * <pre>
 * kisti.server
 *   |_ socket2.java
 *
 * </pre>
 *
 * Desc :
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 7. 11. AM 10:34:06
 * @Version:
 *
 */
public class connectToServer {

	
	
	/**
	 * 
	 * Desc :
	 * @Method Name : main
	 * @param argv
	 *
	 */
    public static void main(String[] argv) {
    	Socket socket = null;
    	BufferedOutputStream bos = null;
    	BufferedInputStream bis = null;
    	OutputStreamWriter osw = null;
    	InputStreamReader isr = null;
    	
    	ObjectInputStream ois = null;
    	         
        try {
        	socket = new Socket("150.183.234.168",8197);
        	        
        	// server -> client
        	bos = new BufferedOutputStream(socket.getOutputStream());
        	osw = new OutputStreamWriter(bos,"UTF-8");
        	//osw.write(257);
        	
        	
//        	byte[] b = new byte[1024];
//        	b = "abc".getBytes();
//        	//bos.write(b);        	
        	bos.write(257);
//        	bos.flush();
        	
        	
        	
        	//Client--> server       	
        	bis = new BufferedInputStream(socket.getInputStream());
        	isr = new InputStreamReader(bis,"UTF-8");
        	int c;
            while ( (c = isr.read()) != 13)
              System.out.print(c);
//        	byte[] c = new byte[1024];
//        	bis.read(c);
//        	System.out.println(c);

//        	int s = 0;
//        	while ( (s = bis.read()) != -1) {
//				System.out.print((char)s);				
//			}
        	
        	
        	
//        	String data = bis.readLine();
//        	System.out.println(data);
        	
        	bos.close();
        	bis.close();
        	socket.close();
          System.out.println("stream closed");  

        } catch (UnknownHostException e) {
            System.out.println("Unkonw exception " + e.getMessage());

        } catch (IOException e) {
            System.out.println("IOException caught " + e.getMessage());
            e.printStackTrace();
        } 
    }

	
}
