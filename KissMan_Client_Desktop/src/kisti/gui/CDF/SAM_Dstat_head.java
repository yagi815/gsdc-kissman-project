package kisti.gui.CDF;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import kisti.server.connectToServer;

/**
 * <pre>
 * kisti.gui.CDF
 *   |_ SAM_Dstat.java
 *
 * </pre>
 *
 * Desc :
 * @Company : KISTI
 * @Author :daniel
 * @Date   :2011. 9. 21. 오후 12:19:48
 * @Version:
 *
 */
public class SAM_Dstat_head extends JPanel {
	JTextArea ta;
	connectToServer server;
	public SAM_Dstat_head() {
		// TODO Auto-generated constructor stub
		
		server = new connectToServer();
		
		ta = new JTextArea();
		ta.setBackground(new Color(255, 204, 204));
		this.setBackground(new Color(255, 204, 204));		
		this.add(ta);
		String header = 
			"----total-cpu-usage---- -dsk/total- -net/total- ---paging-- ---system--"+"\n"
		   +"usr sys idl wai hiq siq| read  writ| recv  send|  in   out | int   csw";
		ta.setText(header);
				
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
		   SAM_Dstat_head w = new SAM_Dstat_head();
           f.add(w);
           f.setSize(600,100);
           f.setVisible(true);
	}
}
