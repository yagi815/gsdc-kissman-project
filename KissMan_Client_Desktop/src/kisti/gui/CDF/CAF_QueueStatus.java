package kisti.gui.CDF;
import java.awt.Color;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CAF_QueueStatus extends JPanel /*implements Runnable*/{
	JTextArea ta;
	public CAF_QueueStatus() {
		// TODO Auto-generated constructor stub
		
		ta = new JTextArea();
		ta.setBackground(new Color(255, 204, 204));
		this.setBackground(new Color(255, 204, 204));
		String s1;
		this.add(ta);
//		
//		Thread t = new Thread(this);
//		t.start();
		
		s1 = getDataFromServer();
		ta.setText(s1);
		
	}

//	public void run() {
//		while (true) {
//			String s1 = getDataFromServer();
//			ta.setText(s1);
//		}
//	}
	String getDataFromServer()
	{
		String data = null;
		//String data = getDataFromServer();
		
		
		
		String s1="Queue              Max   Tot   Ena   Str   Que   Run   Hld   Wat   Trn   Ext T \n"
				+ "----------------   ---   ---   ---   ---   ---   ---   ---   ---   ---   ---		- \n"
				+ "belle                0     0   yes   yes     0     0     0     0     0     0		E \n"
				+ "osgcdf               0   235   yes   yes     0   234     0     0     0     1 	E \n";
		
		
		return s1;
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
		   CAF_QueueStatus w = new CAF_QueueStatus();
           f.add(w);
           f.setSize(600,284);
           f.setVisible(true);
	}
}
