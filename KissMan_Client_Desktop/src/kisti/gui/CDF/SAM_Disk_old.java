package kisti.gui.CDF;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import kisti.module.database.KissManDatabase;


/**
 * Tracks Memory allocated & used, displayed in graph form.
 */


public class SAM_Disk_old extends JPanel implements Runnable{
	private int nJob[]={};
	private static int WIDTH = 1095;
	private static int HEIGHT = 676;
	private static int GRAPH_TOTAL_WIDTH = 300;
	private static int GRAPH_TOTAL_HEIGHT = 30;
	private static int STRING_X = 15;
	private static int GRAPH_X = 150;
	private static int GRAPH_TEXT_X = 500;
	
	private int  samCDF01, samCDF02, cdfGeneral; 
	private  JTextArea taDstat;
	
	/**
	 * 
	 * Desc :
	 * @Method Name : JobSubmitionGraph
	 */	
	public SAM_Disk_old() {
		// TODO Auto-generated constructor stub

		this.setSize(WIDTH, HEIGHT	);
		this.setBackground(Color.red); //for test


		taDstat = new JTextArea();
		taDstat.setBounds(400, 50, 300, 400);		
		this.add(taDstat);


		Thread t = new Thread(this);
		t.start();
	}
	
	public Object getDataFromServer(){
		KissManDatabase db = new KissManDatabase();
		Object obj = db.requestDataToDataBase("samDisk");
		return obj;
	}
	
	public void setDataOld(){
		//getDataFromServer();		

		samCDF01 = 180;
		samCDF02 = 80;		
		cdfGeneral = 250;
						
		repaint();
	}

	public void run(){
		//getDataFromServer();		
		Random r = new Random();
		
		while (true) {	
			samCDF01 = r.nextInt(300);
			samCDF02 = r.nextInt(300);
			cdfGeneral = r.nextInt(600); 
			
			repaint();				
			try {
				Thread.sleep(1000);
			} catch (Exception e) {}			
		}		
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
		g.fillRect(GRAPH_X, 50, samCDF01, GRAPH_TOTAL_HEIGHT);
		g.fillRect(GRAPH_X, 90, samCDF02, GRAPH_TOTAL_HEIGHT);		
		g.fillRect(GRAPH_X, 130, cdfGeneral, GRAPH_TOTAL_HEIGHT);

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
		SAM_Disk_old panel = new SAM_Disk_old();		
		panel.setBackground(Color.YELLOW);
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
//		Thread t = new Thread(panel);
//		t.start();
	}
}
