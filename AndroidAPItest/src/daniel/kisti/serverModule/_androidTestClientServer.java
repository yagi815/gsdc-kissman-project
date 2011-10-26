package daniel.kisti.serverModule;
import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;

class _androidTestClientServer implements Runnable{

	private static final String IPADDR = "150.183.234.167";
	private static final int PORT = 8197;
	
	
	private String serviceName; 
	Object obj;
	
	public _androidTestClientServer(String _msg) {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			InetAddress serverAddr = InetAddress.getByName(IPADDR);
			Log.d("server", "client connection... ");
			Socket socket = new Socket(IPADDR, PORT);
			
			try {
				Log.d("[cleint]", "sending message"						);
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				out.println("this message from client...... ");
//				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				
				
//				String return_msg ="";
//				String tmp="";
//				return_msg = (String)ois.readObject();
				obj = ois.readObject();
				
				Log.d("[cleint]","receive data from tier server !!");				
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("[cleint]", e.toString());
			}finally{
				socket.close();
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("[cleint]", e.toString());
		}
	}
}