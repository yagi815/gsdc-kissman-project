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
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import kisti.module.database.KissManDatabase;


/**
 * Tracks Memory allocated & used, displayed in graph form.
 */


public class SAM_MonitoringPanel extends JPanel implements Runnable{
	private int nJob[]={};
	private static int WIDTH = 1095;
	private static int HEIGHT = 676;
	private static int GRAPH_TOTAL_WIDTH = 300;
	
	private int cpu, mem, netIn, netOut, diskIn, diskOut, samCDF01, samCDF02; 
	
	
	/**
	 * 
	 * Desc :
	 * @Method Name : JobSubmitionGraph
	 *
	 */	
	public SAM_MonitoringPanel() {
		// TODO Auto-generated constructor stub

		this.setSize(WIDTH, HEIGHT	);
		//this.setBackground(new Color(147,77,134)); 
		this.setBackground(Color.red); //for test
		
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
		//g.drawLine(WIDTH+50, HEIGHT+250, 350, 250);

		// CPU & Mem 
		g.setColor(Color.black);		
		g.drawString("SAM Station", 15, 20);// or 글자 이미지
		
		g.drawString("CPU", 15, 70);
		g.drawString("Memory", 15, 110);
		
		g.setColor( new Color(211,211,211) );	
		g.fillRect(100, 50, GRAPH_TOTAL_WIDTH, 30);//BG
		g.fillRect(100, 90, GRAPH_TOTAL_WIDTH, 30);//BG
		
		g.setColor( Color.red );
		g.fillRect(100, 50, cpu, 30);
		g.fillRect(100, 90, mem, 30);
		
		
		// Network, Disk
		g.setColor(Color.black);				
		g.drawString("Network IN", 15, 200);
		g.drawString("Network OUT", 15, 240);
		
		g.setColor( new Color(211,211,211) ); //network	
		g.fillRect(100, 180, GRAPH_TOTAL_WIDTH, 30);//BG// y+=130
		g.fillRect(100, 220, GRAPH_TOTAL_WIDTH, 30);//BG
		
		
		g.setColor( new Color(102, 153, 102) );		
		g.fillRect(100, 180, netIn, 30);
		g.fillRect(100, 220, netOut, 30);
		
		g.setColor( new Color(153, 102, 0) ); //disk
		
				
		
		//SAM Cache
		g.setColor(Color.black);		
		g.drawString("SAM cdf01", 15, 330);
		g.drawString("SAM cdf02", 15, 370);
		
		g.setColor( new Color(211, 211, 211) );	//BG
		g.fillRect(100, 310, GRAPH_TOTAL_WIDTH, 30);//BG// y+=130
		g.fillRect(100, 350, GRAPH_TOTAL_WIDTH, 30);//BG
		
		g.setColor( new Color(0, 102, 255) );		
		g.fillRect(100, 310, samCDF01, 30);
		g.fillRect(100, 350, samCDF02, 30);
		
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
		SAM_MonitoringPanel panel = new SAM_MonitoringPanel();		
		panel.setBackground(Color.YELLOW);
		f.setLayout(null);
		f.add(panel);
		f.setVisible(true);
		f.setSize(WIDTH, HEIGHT+50);		
		
//		Thread t = new Thread(panel);
//		t.start();
	}
}
