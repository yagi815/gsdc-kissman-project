package kisti.gui.CDF;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import kisti.server.connectToServer;

public class SAM_monText extends JPanel implements Runnable{
	JTextArea ta;
	connectToServer server;
	public SAM_monText() {
		// TODO Auto-generated constructor stub
		
		server = new connectToServer();
		
		ta = new JTextArea();
		ta.setBackground(new Color(255, 204, 204));
		this.setBackground(new Color(255, 204, 204));
		String s1;
		this.add(ta);
		
		Thread t = new Thread(this);
		t.start();
		
		s1 = getDataFromServer();
		ta.setText(s1);
		
	}

	public void run() {
		while (true) {
			String s1 = getDataFromServer();
			ta.setText(s1);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {}
		}
	}
	String getDataFromServer()
	{
		Object obj = server.requestDataToServer("SAM_monText");
		String data = (String)obj;
		
		
		
//		String s1 = "----total-cpu-usage---- -dsk/total- -net/total- ---paging-- ---system--"+"\n"				
//				+"usr sys idl wai hiq siq| read  writ| recv  send|  in   out | int   csw"+"\n"
//				+"2   0  98   0   0   0| 921B  262k|   0     0 |   0     0 |1060   284"+"\n"
//				+"0   0 100   0   0   0|   0     0 |1575B 1937B|   0     0 |1020   175"+"\n"
//				+"0   0 100   0   0   0|   0    80k|1820B  999B|   0     0 |1044   224"+"\n"
//				+"0   0 100   0   0   0|   0     0 |1478B  991B|   0     0 |1024   179"+"\n"
//				+"0   0 100   0   0   0|   0     0 |1135B  983B|   0     0 |1040   205"+"\n"
//				+"0   0 100   0   0   0|   0   320k|1135B 1047B|   0     0 |1030   202"+"\n"
//				+"0   0 100   0   0   0|   0     0 |1199B  983B|   0     0 |1043   219"+"\n"
//				+"0   0 100   0   0   0|   0    48k|4312B 1559B|   0     0 |1043   231"+"\n"
//				+"5   1  94   0   0   0|   0     0 |7388B 4993B|   0     0 |1088   757"+"\n"
//				+"2   5  93   0   0   0|   0     0 |3975B 6386B|   0     0 |1036   818"+"\n"
//				+"5   2  93   0   0   0|   0   520k| 111k 3574B|   0     0 |1192   349"+"\n"
//				+"12   0  88   0   0   0|   0     0 |2426B 1365B|   0     0 |1025   176"+"\n"
//				+"12   1  87   0   0   0|   0   440k|1990B 1631B|   0     0 |1064  1323"+"\n"
//				+"12   1  88   0   0   0|   0   944k|2394B 1767B|   0     0 |1044   205"+"\n"
//				+" 8   2  90   0   0   0|   0     0 |  10k 9112B|   0     0 |1152   361"+"\n"
//				+"2  17  82   0   0   0|   0   952k|  20k   17k|   0     0 |1307   404"+"\n"
//				+"3  14  83   0   0   0|   0     0 |6966B 7823B|   0     0 | 921   338";
		
		return data;
	}
	

	/**
	 * Desc :
	 * @Method Name : main
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   JFrame f = new JFrame();
		   SAM_monText w = new SAM_monText();
           f.add(w);
           f.setSize(600,284);
           f.setVisible(true);
	}
}
