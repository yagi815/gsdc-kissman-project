package daniel.kisti.serverModule;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.os.Environment;
import android.util.Log;


public class RequestDataToTierServer {

	
	private static final String IPADDR = "150.183.234.167";
	private static final int PORT = 8197;
	
	
	public RequestDataToTierServer() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * Desc :
	 * @Method Name : request
	 * @param serviceName
	 * @return
	 *
	 */
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
				Log.d("[client]","receive data from tier server !!");	
				
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("[client]", e.toString());
				e.printStackTrace();
				
			}finally{				socket.close();			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("[client]", e.toString());
			e.printStackTrace();
		}		
		return obj;
	}
	/**
	 * 
	 * Desc :
	 * @Method Name : requestImgData
	 * @param serviceName
	 *
	 */
	public void requestImgData(String serviceName) {
		try {
			InetAddress serverAddr = InetAddress.getByName(IPADDR);
			Log.d("[client]", "client connection... ");
			Socket socket = new Socket(IPADDR, PORT);			
			try {
				Log.d("[client]", "sending message"						);
				//요청
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()),8192  ),true);
				out.println(serviceName);
	
				InputStream is = socket.getInputStream();
				String path = Environment.getExternalStorageDirectory().getAbsolutePath();
				FileOutputStream fos = new FileOutputStream(path+"/ce02Graph.bmp");
				Log.d("[Caf_Helper]", "Externel path:"+path);
			
				byte[] buffer = new byte[1024];
				int  readSize = 0;
				while ( (readSize = is.read(buffer)) > 0 ) {
					fos.write(buffer,0,readSize);
				}
				fos.close();
				is.close();
				socket.close();
				
				
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("[client]", e.toString());
				e.printStackTrace();
				
			}finally{				socket.close();			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("[client]", e.toString());
			e.printStackTrace();
		}
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


