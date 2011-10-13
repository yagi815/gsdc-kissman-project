package kisti.gui.CDF;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import kisti.server.connectToServer;

public class SAM_Dstat extends JPanel implements Runnable {
	private static final int SCROLLBARS_VERTICAL_ONLY = 1;
	JTextArea taHead, taBody;	
	connectToServer server;

	public SAM_Dstat() {
		// TODO Auto-generated constructor stub
		String header = 
			"----total-cpu-usage---- -dsk/total- -net/total- ---paging-- ---system--"+"\n"
		   +"usr sys idl wai hiq siq| read  writ| recv  send|  in   out | int   csw";
		

		server = new connectToServer();		

		taHead = new JTextArea();
		taHead.setBackground(new Color(255, 204, 204));
		taHead.setBounds(0, 0, 500, 50);		
		taHead.setText(header);
		
		taBody = new JTextArea();
		taBody.setBackground(new Color(255, 204, 204));
		taHead.setBounds(0, 50, 500, 300);

		this.setBackground(new Color(255, 204, 204));
		this.add(taHead);
		this.add(taBody);

		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		while (true) {
			String temp = "";
			String s1 = (String) server.requestDataToServer("getDstat");
			System.out.println("str:" + s1);
			temp = taBody.getText();

			taBody.setText(s1 + temp);

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			repaint();
		}
	}

	/**
	 * Desc :
	 * 
	 * @Method Name : main
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame();
		SAM_Dstat w = new SAM_Dstat();
		f.add(w);
		f.setSize(600, 450);
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			public void windowDeiconified(WindowEvent e) {
				System.out.println("windowDeiconified");
			}

			public void windowIconified(WindowEvent e) {
				System.out.println("windowIconified");
			}
		});

	}
}
