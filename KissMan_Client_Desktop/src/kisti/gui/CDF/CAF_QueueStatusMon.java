package kisti.gui.CDF;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import kisti.server.connectToServer;

public class CAF_QueueStatusMon extends JPanel implements Runnable{
	JTextArea ta;
	connectToServer server ;
	
	
	public CAF_QueueStatusMon() {
		// TODO Auto-generated constructor stub
		
		ta = new JTextArea();
		ta.setBackground(new Color(255, 204, 204));
		this.setBackground(new Color(255, 204, 204));
		String s1;
		this.add(ta);
		
		
		server = new connectToServer(); 
		
		s1 = getDataFromServer();
		ta.setText(s1);
		
		Thread t = new Thread(this);
		t.start();		
	}

	public void run() {
		while (true) {
//			System.out.println("caf_queuestatus");
			String s1 = getDataFromServer();
			ta.setText(s1);			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {}
		}
	}
	String getDataFromServer()
	{		
		Object obj = server.requestDataToServer("QueueStatus");
		String str = (String)obj;
//		System.out.println("getDataFromServer");
		return str;
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
		   CAF_QueueStatusMon w = new CAF_QueueStatusMon();
           f.add(w);
           f.setSize(600, 284);
           f.setVisible(true);
	}
}
