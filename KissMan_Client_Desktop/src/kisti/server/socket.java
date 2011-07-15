package kisti.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class socket {

    public static void main(String[] argv) {
        DataOutputStream dos = null;
        DataInputStream dis = null;

         
        try {
            Socket socket = new Socket("150.183.234.168", 8197);
            dos = new DataOutputStream(socket.getOutputStream());
            //dos.writeBytes("1");]
            dos.writeInt(1);

            
            byte[] str =  new byte[1024];
            dis = new DataInputStream(socket.getInputStream());

            dis.read(str);
            
            System.out.println("InputStream = " +str);
            
            
            dis.close();
            
            dos.close();
            socket.close();

        } catch (UnknownHostException e) {
            System.out.println("Unkonw exception " + e.getMessage());

        } catch (IOException e) {
            System.out.println("IOException caught " + e.getMessage());
            e.printStackTrace();
        } 
    }
}
