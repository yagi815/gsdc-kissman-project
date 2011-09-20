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


public class SAM_Monitoring extends JPanel implements Runnable{
	private int nJob[]={};
	private static int WIDTH = 1095;
	private static int HEIGHT = 676;
	private static int GRAPH_TOTAL_WIDTH = 300;
	private static int GRAPH_TOTAL_HEIGHT = 30;
	private static int STRING_X = 15;
	private static int GRAPH_X = 100;
	private static int GRAPH_TEXT_X = 500;
	
	private int cpu, mem, netIn, netOut, diskIn, diskOut, samCDF01, samCDF02; 
	private  JTextArea taDstat;
	
	/**
	 * 
	 * Desc :
	 * @Method Name : JobSubmitionGraph
	 */	
	public SAM_Monitoring() {
		// TODO Auto-generated constructor stub

		this.setSize(WIDTH, HEIGHT	);
		this.setBackground(Color.red); //for test


		JLabel lb = new JLabel("hello");
		lb.setBounds(400,10,10,10);		
		this.add(lb);


		taDstat = new JTextArea();
		taDstat.setBounds(400, 50, 300, 400);		
		this.add(taDstat);


		Thread t = new Thread(this);
		t.start();
	}
	
	public Object getDataFromServer(){
		KissManDatabase db = new KissManDatabase();
		Object obj = db.requestDataToDataBase("SAM");
		return obj;
	}
	
	public void setDataOld(){
		//getDataFromServer();
		
		cpu = 100;
		mem = 50;
		netIn = 180;
		netOut = 110;
		diskIn = 40;
		diskOut = 80;
		samCDF01 = 180;
		samCDF02 = 80;		
						
		repaint();
	}

	public void run(){
		//getDataFromServer();		
		Random r = new Random();
		
		while (true) {		
			cpu = r.nextInt(300);
			mem = r.nextInt(300);
			netIn = r.nextInt(300);
			netOut = r.nextInt(300);
			diskIn = r.nextInt(300);
			diskOut = r.nextInt(300);
			samCDF01 = r.nextInt(300);
			samCDF02 = r.nextInt(300);
			
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
		// DSTAT
		taDstat.setText("----total-cpu-usage---- -dsk/total- -net/total- ---paging-- ---system--"				
+"usr sys idl wai hiq siq| read  writ| recv  send|  in   out | int   csw"
+"2   0  98   0   0   0| 921B  262k|   0     0 |   0     0 |1060   284"
+"0   0 100   0   0   0|   0     0 |1575B 1937B|   0     0 |1020   175"
+"0   0 100   0   0   0|   0    80k|1820B  999B|   0     0 |1044   224"
+"0   0 100   0   0   0|   0     0 |1478B  991B|   0     0 |1024   179"
+"0   0 100   0   0   0|   0     0 |1135B  983B|   0     0 |1040   205"
+"0   0 100   0   0   0|   0   320k|1135B 1047B|   0     0 |1030   202"
+"0   0 100   0   0   0|   0     0 |1199B  983B|   0     0 |1043   219"
+"0   0 100   0   0   0|   0    48k|4312B 1559B|   0     0 |1043   231"
+"5   1  94   0   0   0|   0     0 |7388B 4993B|   0     0 |1088   757"
+"2   5  93   0   0   0|   0     0 |3975B 6386B|   0     0 |1036   818"
+"5   2  93   0   0   0|   0   520k| 111k 3574B|   0     0 |1192   349"
+"12   0  88   0   0   0|   0     0 |2426B 1365B|   0     0 |1025   176"
+"12   1  87   0   0   0|   0   440k|1990B 1631B|   0     0 |1064  1323"
+"12   1  88   0   0   0|   0   944k|2394B 1767B|   0     0 |1044   205"
+" 8   2  90   0   0   0|   0     0 |  10k 9112B|   0     0 |1152   361"
+"2  17  82   0   0   0|   0   952k|  20k   17k|   0     0 |1307   404"
+"3  14  83   0   0   0|   0     0 |6966B 7823B|   0     0 | 921   338"				);
		

		// CPU & Mem 
		g.setColor(Color.black);		
		g.drawString("SAM Station", 15, 20);// or 글자 이미지
		
		g.drawString("CPU", STRING_X, 70);
		g.drawString("Memory", STRING_X, 110);
		
		g.setColor( new Color(211,211,211) );	
		g.fillRect(GRAPH_X, 50, GRAPH_TOTAL_WIDTH, GRAPH_TOTAL_HEIGHT);//BG
		g.fillRect(GRAPH_X, 90, GRAPH_TOTAL_WIDTH, GRAPH_TOTAL_HEIGHT);//BG
		
		g.setColor( Color.red );
		g.fillRect(GRAPH_X, 50, cpu, GRAPH_TOTAL_HEIGHT);
		g.fillRect(GRAPH_X, 90, mem, GRAPH_TOTAL_HEIGHT);
		
		
		// NETWORK
		g.setColor(Color.black);				
		g.drawString("Network IN", STRING_X, 200);
		g.drawString("Network OUT", STRING_X, 240);
		
		g.setColor( new Color(211,211,211) ); //network	
		g.fillRect(GRAPH_X, 180, GRAPH_TOTAL_WIDTH, GRAPH_TOTAL_HEIGHT);//BG// y+=130
		g.fillRect(GRAPH_X, 220, GRAPH_TOTAL_WIDTH, GRAPH_TOTAL_HEIGHT);//BG
				
		g.setColor( new Color(102, 153, 102) );		
		g.fillRect(GRAPH_X, 180, netIn, GRAPH_TOTAL_HEIGHT);
		g.fillRect(GRAPH_X, 220, netOut, GRAPH_TOTAL_HEIGHT);		
		
				
		
		//DISK
		g.setColor(Color.black);		
		g.drawString("Disk IN", STRING_X, 330);
		g.drawString("Disk OUT", STRING_X, 370);
		
		g.setColor( new Color(211, 211, 211) );	//BG
		g.fillRect(GRAPH_X, 310, GRAPH_TOTAL_WIDTH, GRAPH_TOTAL_HEIGHT);//BG// y+=130
		g.fillRect(GRAPH_X, 350, GRAPH_TOTAL_WIDTH, GRAPH_TOTAL_HEIGHT);//BG
		
		g.setColor( new Color(0, 102, 255) );		
		g.fillRect(GRAPH_X, 310, diskIn, GRAPH_TOTAL_HEIGHT);
		g.fillRect(GRAPH_X, 350, diskOut, GRAPH_TOTAL_HEIGHT);

		
		// SAM
		int DIST = 50;
		g.setColor(Color.black);		
		g.drawString("SAM CDF01", STRING_X, 460+DIST);
		g.drawString("SAM CDF02", STRING_X, 500+DIST);
		
		g.setColor( new Color(211, 211, 211) );	//BG
		g.fillRect(GRAPH_X, 440+DIST, GRAPH_TOTAL_WIDTH*2, GRAPH_TOTAL_HEIGHT);//BG// y+=130
		g.fillRect(GRAPH_X, 480+DIST, GRAPH_TOTAL_WIDTH*2, GRAPH_TOTAL_HEIGHT);//BG
		
		g.setColor( new Color(0, 102, 255) );		
		g.fillRect(GRAPH_X, 440+DIST, samCDF01, GRAPH_TOTAL_HEIGHT);
		g.fillRect(GRAPH_X, 480+DIST, samCDF02, GRAPH_TOTAL_HEIGHT);
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
		SAM_Monitoring panel = new SAM_Monitoring();		
		panel.setBackground(Color.YELLOW);
		f.setLayout(null);
		f.add(panel);
		f.setVisible(true);
		f.setSize(WIDTH, HEIGHT+50);		
		
//		Thread t = new Thread(panel);
//		t.start();
	}
}
