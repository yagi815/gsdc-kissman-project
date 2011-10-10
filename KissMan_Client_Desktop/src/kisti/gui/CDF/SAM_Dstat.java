package kisti.gui.CDF;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import kisti.server.connectToServer;

public class SAM_Dstat extends JPanel implements Runnable{
	private static final int SCROLLBARS_VERTICAL_ONLY = 1;
	JTextArea ta;
	JEditorPane editPane;
	connectToServer server;
	public SAM_Dstat() {
		// TODO Auto-generated constructor stub
		
		server = new connectToServer();
		
//		ta = new TextArea("",10, 50, SCROLLBARS_VERTICAL_ONLY);
		ta = new JTextArea();
		editPane = new JEditorPane();
		
		ta.setBackground(new Color(255, 204, 204));
		this.setBackground(new Color(255, 204, 204));
		String s1;
		this.add(ta);
		
//		editPane.setBackground(new Color(255, 204, 204));
//		editPane.setS
//		this.add(editPane);
//		editPane.setEditable(false);
//		editPane.setText(s1);
		
		Thread t = new Thread(this);
		t.start();
		

//		ta.setText(s1);
		
	}

	public void run() {
		while (true) {
			String temp="";
			String s1 = (String)server.requestDataToServer("getDstat");
			System.out.println("str:"+s1);
			temp = ta.getText();
//			ta.append(s1+temp);
			ta.setText(s1+temp);
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {}
			repaint();
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
		   JFrame f = new JFrame();
		   SAM_Dstat w = new SAM_Dstat();
           f.add(w);
           f.setSize(600,284);
           f.setVisible(true);
           
           f.addWindowListener(
   				new WindowAdapter() {
   					public void windowClosing(WindowEvent e){System.exit(0);}
   					public void windowDeiconified(WindowEvent e){ System.out.println("windowDeiconified");}
   					  public void windowIconified(WindowEvent e) {System.out.println("windowIconified"); }
   				}
   		);
           
	}
}
