package daniel.kisti.serverModule;

import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;
import android.widget.MultiAutoCompleteTextView.Tokenizer;

import daniel.kisti.serverModule.SAMInfoData;
import daniel.kisti.serverModule.SAMDiskData;;

public class RequestDataToTierServer {

	
	private static final String IPADDR = "150.183.234.167";
	private static final int PORT = 8197;
	
//	private String serviceName; 
//	Object obj;
	
	
	public RequestDataToTierServer() {
		// TODO Auto-generated constructor stub
		SAMInfoData samInfoData;
		SAMDiskData samDiskData;
		
	}
	
	public Object request(String serviceName) {
		// TODO Auto-generated method stub
		Object obj = null;
		
		try {
			InetAddress serverAddr = InetAddress.getByName(IPADDR);
			Log.d("[client]", "client connection... ");
			Socket socket = new Socket(IPADDR, PORT);
			
			try {
				Log.d("[client]", "sending message"						);
				//요청
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()),8192  ),true);
				out.println(serviceName);

				//데이터 접수
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				
				//object 에 저장
				Log.d("[client]","starting readObject.................");
				obj = ois.readObject();
				
				if (obj == null) {
					Log.d("[client]","obj is null!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
//				while ( (obj = ois.readObject())!= null ) {		
//					Log.d("[client]","obj....:"+obj.toString());
//				}
				
				Log.d("[client]","receive data from tier server !!");	
				
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("[client]", e.toString());
				e.printStackTrace();
				
			}finally{
				socket.close();
			}			
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("[client]", e.toString());
			e.printStackTrace();
		}
		
		return obj;
	}
	
	/**
	 * Desc :
	 * @Method Name : main
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}


