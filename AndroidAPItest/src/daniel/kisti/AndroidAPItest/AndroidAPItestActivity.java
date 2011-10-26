package daniel.kisti.AndroidAPItest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AndroidAPItestActivity extends Activity {
    /** Called when the activity is first created. */
//	Button btn;
//    EditText edit;
    
    
    @Override    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        setContentView(R.layout.main);
        
        final EditText et = (EditText)findViewById(R.id.edit);
        Button btn = (Button)findViewById(R.id.btn1);
        final TextView view = (TextView)findViewById(R.id.textview1);
//        
//        
//        btn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//								
//			}
//		});
//        
//        
    
//        TCPClient t = new TCPClient("aaaa");
//        t.run();

    }
}

class TCPClient implements Runnable{

	private static final String IPADDR = "150.183.234.167";
	private static final int PORT = 8197;
	
	public TCPClient(String _msg) {
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
				Log.d("cleint", "sending message"						);
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				out.println("this message from client...... ");
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String return_msg = in.readLine();
				Log.d("client", return_msg);
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("client", e.toString());
			}finally{
				socket.close();
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("client", e.toString());
		}
	}
	
}