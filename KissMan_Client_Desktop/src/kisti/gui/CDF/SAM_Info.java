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
import kisti.server.SAMDiskData;
import kisti.server.SAMInfoData;
import kisti.server.connectToServer;


/**
 * Tracks Memory allocated & used, displayed in graph form.
 */


public class SAM_Info extends JPanel implements Runnable{
	private int nJob[]={};
	private static int WIDTH = 500;
	private static int HEIGHT = 400;
	private static int GRAPH_TOTAL_WIDTH = 300;
	private static int GRAPH_TOTAL_HEIGHT = 30;
	private static int STRING_X = 15;
	private static int GRAPH_X = 100;
	private static int GRAPH_TEXT_X = 500;
	private static int TOTAL_NET_BANDWITH =  100000000; // 100M
	private static int TOTAL_DISK_BANDWITH = 500000000;	// 100M
	
	private int cpu, mem, mem_total, mem_use, net_in, net_out, disk_read, disk_write; 
	private  JTextArea taDstat;
	private SAMInfoData saminfoData;
	private connectToServer server;
	
	/**
	 * 
	 * Desc :
	 * @Method Name : JobSubmitionGraph
	 */	
	public SAM_Info() {
		// TODO Auto-generated constructor stub
		this.setSize(WIDTH, HEIGHT	);

		JLabel lb = new JLabel("getDstat");
		lb.setBounds(400,10,10,10);		
		this.add(lb);
		
		server = new connectToServer();
		saminfoData = new SAMInfoData();
				
		Thread t = new Thread(this);
		t.start();
	}
	
	
	public void run(){		
		
		while (true) {		
			
			saminfoData = (SAMInfoData)server.requestDataToServer("samInfo");
			cpu = (int) (3*(saminfoData.cpuUse / 1) );
			mem_total = (int) (saminfoData.memTotal / 1) ;
			mem_use = (int) (saminfoData.memUse / 1) ;
			mem = (int ) (300*(saminfoData.memUse/saminfoData.memTotal));
			
			net_in = (int) (300*(saminfoData.net_in / TOTAL_NET_BANDWITH ) );
			net_out = (int) (300*(saminfoData.net_out / TOTAL_NET_BANDWITH) );
			disk_read = (int) (300*(saminfoData.disk_read / TOTAL_DISK_BANDWITH) );
			disk_write = (int) (300*(saminfoData.disk_write / TOTAL_DISK_BANDWITH) );

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
		g.fillRect(GRAPH_X, 180, net_in, GRAPH_TOTAL_HEIGHT);
		g.fillRect(GRAPH_X, 220, net_out, GRAPH_TOTAL_HEIGHT);		
		
				
		
		//DISK
		g.setColor(Color.black);		
		g.drawString("Disk IN", STRING_X, 330);
		g.drawString("Disk OUT", STRING_X, 370);
		
		g.setColor( new Color(211, 211, 211) );	//BG
		g.fillRect(GRAPH_X, 310, GRAPH_TOTAL_WIDTH, GRAPH_TOTAL_HEIGHT);//BG// y+=130
		g.fillRect(GRAPH_X, 350, GRAPH_TOTAL_WIDTH, GRAPH_TOTAL_HEIGHT);//BG
		
		g.setColor( new Color(0, 102, 255) );		
		g.fillRect(GRAPH_X, 310, disk_read, GRAPH_TOTAL_HEIGHT);
		g.fillRect(GRAPH_X, 350, disk_write, GRAPH_TOTAL_HEIGHT);


		
		g.setColor(Color.black);		
//		g.drawString("-------------", 250, 10);
		g.drawString(cpu+"%", 230, 70);
		g.drawString(mem_use+"/"+mem_total, 190, 110);
		
//		g.setColor(Color.red);
		g.drawString(saminfoData.net_in+"b / 100Mb", 190, 200);
		g.drawString(saminfoData.net_out+"b / 100Mb", 190, 240);
		g.drawString(saminfoData.disk_read+"b / 500Mb", 190, 330);
		g.drawString(saminfoData.disk_write+"b / 500Mb", 190, 370);
		

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
		SAM_Info panel = new SAM_Info();		
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
		

	}
}
