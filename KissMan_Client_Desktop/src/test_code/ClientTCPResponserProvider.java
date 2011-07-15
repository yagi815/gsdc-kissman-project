package test_code;

package org.postgresql.discovery;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
* Offers a socket for receiving repsonses from remote services. Once the client 
sent  a location
* request, it waits for messages to arrive on this Socket.
* 
* 
*
*/
public class ClientTCPResponseProvider {

private ServerSocket providerSocket;
private Socket connection = null;
private ObjectOutputStream out;
private ObjectInputStream in;

private boolean endConnection = false;

private String keyword = null;
private String finalResult = "";

public ClientTCPResponseProvider(String keyword){
    System.out.println("\nIn ClientTCPResponseProvider");
    this.keyword = keyword;
    Thread t = new Thread(new ThreadedProvider());
    t.start();
}

public void destroy(){
    endConnection = true;
    try {
        providerSocket.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

void goConnect(){
    try{
        System.out.println("\nIn goConnect.");
        //1. creating a server socket
        providerSocket = new ServerSocket(8888);
        //2. Wait for connection

        Object message = null;

        long timeout = 1000;
        long initTime = System.currentTimeMillis();

        do{
            //System.out.println("Waiting for connection...");
            connection = providerSocket.accept();
            //System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            //3. get Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            //4. The two parts communicate via the input and output streams
            try{
                message = in.readObject();
                String response = (String)message;
                //System.out.println("received: " + response);
                if(response.startsWith(keyword) && response.split(":")[0].equals(keyword)){
                    finalResult += connection.getInetAddress().getHostAddress()+";";
                    System.out.println(" IVAN " +finalResult);
                    //finalResult += connection.getLocalAddress().getHostAddress()+ ";";
                    //endConnection = true;
                }
            }
            catch(ClassNotFoundException classnot){
                System.err.println("Data received in unknown format");
            }
            in.close();
            out.close();
            connection.close();
        } while(System.currentTimeMillis()-initTime < timeout);
        providerSocket.close();
    }
    catch(IOException ioException){
        ioException.printStackTrace();
    }
}

private class ThreadedProvider implements Runnable{
    //@Override
    public void run() {
        goConnect();    
    }
}

public String getFinalResult() {
    return finalResult;
}
