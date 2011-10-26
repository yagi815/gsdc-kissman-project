package daniel.kisti.serverModule;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;
import android.widget.Toast;

public class connectToTierServer {

	/**
	 * Desc :
	 * 
	 * @Method Name : main
	 * @param args
	 * 
	 */

	private static final int PORT = 8197;
	private static final String IPADDR = "150.183.234.167";
	private static ServerSocket server;
	// private static InetAddress inetaddr;
	private static Socket sock;
	private static InputStream in;
	private static OutputStream out;
	private static BufferedReader br;
	private static PrintWriter pw;

	private String result;

	public connectToTierServer() {
		// TODO Auto-generated constructor stub
		try {
			Log.d("daniel","connectToTierServer");

			sock = new Socket(IPADDR, PORT);

			out = sock.getOutputStream();
			in = sock.getInputStream();

			pw = new PrintWriter(new OutputStreamWriter(out));
			pw.write("this message is from mobile... !!");
			br = new BufferedReader(new InputStreamReader(in));
			String line = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			Log.d("daniel","server....m.m,.m.m.m");

		} catch (Exception e) {
			// TODO: handle exception
			
			Log.d("server", e.toString());
		}		
	}

	public String getResultString() {
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
