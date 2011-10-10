package kisti.gui.CDF;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import kisti.server.SAMDiskData;
import kisti.server.connectToServer;


/**
 * Tracks Memory allocated & used, displayed in graph form.
 */


public class SAM_Disk extends JPanel implements Runnable{
	private int nJob[]={};
	private static int WIDTH = 1095;
	private static int HEIGHT = 676;
	private static int GRAPH_TOTAL_WIDTH = 300;
	private static int GRAPH_TOTAL_HEIGHT = 30;
	private static int STRING_X = 15;
	private static int GRAPH_X = 150;
	private static int GRAPH_TEXT_X = 500;
	
	private int  samCDF01, samCDF02, cdfGeneral; 
	private String samCDF01_percentage, samCDF02_percentage, cdfGeneral_percentage;
	private  JTextArea taDstat;
	private connectToServer server;
	private SAMDiskData samdiskData;
	/**
	 * 
	 * Desc :
	 * @Method Name : JobSubmitionGraph
	 */	
	public SAM_Disk() {
		// TODO Auto-generated constructor stub

		this.setSize(WIDTH, HEIGHT	);
//		this.setBackground(Color.RED); //for test


		taDstat = new JTextArea();
		taDstat.setBounds(400, 50, 300, 400);		
		this.add(taDstat);

		server = new connectToServer();
		samdiskData = new SAMDiskData();

		Thread t = new Thread(this);
		t.start();
	}
	
	public Object getDataFromServer(){
//		server = new connectToServer();
//		Object obj = db.requestDataToDataBase("samDisk");
		return null;
	}
	
	public void setDataOld(){
		//getDataFromServer();		

		samCDF01 = 180;
		samCDF02 = 80;		
		cdfGeneral = 250;
						
		repaint();
	}

	public void run(){
		
		Random r = new Random();
		
//		while (true) {
			samdiskData = (SAMDiskData)server.requestDataToServer("samDisk");
			
			samCDF01 = (int) (300*(samdiskData.cdf01_used / samdiskData.cdf01_size));
			samCDF02 = (int) (300*(samdiskData.cdf02_used / samdiskData.cdf02_size));
			cdfGeneral = (int) (300*(samdiskData.general_disk_used / samdiskData.general_disk_size));
			samCDF01_percentage = samdiskData.cdf01_use_percentage;
			samCDF02_percentage = samdiskData.cdf02_use_percentage;
			cdfGeneral_percentage = samdiskData.general_disk_use_percentage;

			repaint();
			
//			try {
//				Thread.sleep(1000);
//			} catch (Exception e) {}			
//		}		
	}
	

	/**
	 * paint
	 */
	public void paint(Graphics g){
		
		g.clearRect(0, 0, getWidth(), getHeight());		

		
		
		
		// SAM cache 
		g.setColor(Color.black);		
		g.drawString("SAM Station", 15, 20);// or 글자 이미지
		
		g.drawString("SAM cache CDF01", STRING_X, 70); //y += 40
		g.drawString("SAM cache CDF02", STRING_X, 110);
		g.drawString("CDF General Storage", STRING_X, 150);
		
		g.setColor( new Color(211,211,211) );	
		g.fillRect(GRAPH_X, 50, GRAPH_TOTAL_WIDTH*2, GRAPH_TOTAL_HEIGHT);//BG
		g.fillRect(GRAPH_X, 90, GRAPH_TOTAL_WIDTH*2, GRAPH_TOTAL_HEIGHT);//BG
		g.fillRect(GRAPH_X, 130, GRAPH_TOTAL_WIDTH*2, GRAPH_TOTAL_HEIGHT);//BG
		
		g.setColor(  new Color(0, 102, 255) );
		g.fillRect(GRAPH_X, 50, samCDF01*2, GRAPH_TOTAL_HEIGHT);
		g.fillRect(GRAPH_X, 90, samCDF02*2, GRAPH_TOTAL_HEIGHT);		
		g.fillRect(GRAPH_X, 130, cdfGeneral*2, GRAPH_TOTAL_HEIGHT);
		
		// percentage
		g.setColor(Color.red);		
//		g.drawString("-------------", 250, 10);
		g.drawString(samCDF01_percentage, 450, 70);
		g.drawString(samCDF02_percentage, 450, 110);
		g.drawString(cdfGeneral_percentage, 450, 150);

	}
	
	
	/**
	 * 
	 * Desc :
	 * @Method Name : main
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame();
		SAM_Disk panel = new SAM_Disk();		
//		panel.setBackground(Color.YELLOW);
		f.setLayout(null);
		f.add(panel);
		f.setVisible(true);
		f.setSize(WIDTH, HEIGHT+50);		
		
		
		f.addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent e){System.exit(0);}
					public void windowDeiconified(WindowEvent e){ System.out.println("windowDeiconified");}
					  public void windowIconified(WindowEvent e) {System.out.println("windowIconified"); }
				}
		);

	}
}
