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


public class SAM_CpuMonitoring extends JPanel {
	private int nJob[]={};
	private static int WIDTH = 750;
	private static int HEIGHT = 500;
	private static int GRAPH_TOTAL_WIDTH = 300;
	
	
	/**
	 * 
	 * Desc :
	 * @Method Name : JobSubmitionGraph
	 *
	 */	
	public SAM_CpuMonitoring() {
		// TODO Auto-generated constructor stub

//		KissManDatabase db = new KissManDatabase();
//		Object obj = db.requestDataToDataBase("jobsubmition");
//		nJob = (int[]) obj;

		this.setSize(WIDTH, HEIGHT	);
		//this.setBackground(new Color(147,77,134)); 
		this.setBackground(Color.red); //for test
		this.repaint();
	}

	/**
	 * paint
	 */
	public void paint(Graphics g){
		g.clearRect(0, 0, getWidth(), getHeight());		
		//g.drawLine(WIDTH+50, HEIGHT+250, 350, 250);

		// CPU & Mem 
		g.setColor(Color.black);		
		g.drawString("CPU", 15, 70);
		g.drawString("Memory", 15, 110);
		
		g.setColor( new Color(211,211,211) );	
		g.fillRect(100, 50, GRAPH_TOTAL_WIDTH, 30);//BG
		g.fillRect(100, 90, GRAPH_TOTAL_WIDTH, 30);//BG
		
		g.setColor( Color.red );
		g.fillRect(100, 50, 100, 30);
		g.fillRect(100, 90, 50, 30);
		
		
		// Network, Disk
		g.setColor(Color.black);				
		g.drawString("Network IN", 15, 200);
		g.drawString("Network OUT", 15, 240);
		
		g.setColor( new Color(211,211,211) ); //network	
		g.fillRect(100, 180, GRAPH_TOTAL_WIDTH, 30);//BG// y+=130
		g.fillRect(100, 220, GRAPH_TOTAL_WIDTH, 30);//BG
		
		
		g.setColor( new Color(102, 153, 102) );		
		g.fillRect(100, 180, 180, 30);
		g.fillRect(100, 220, 110, 30);
		
		g.setColor( new Color(153, 102, 0) ); //disk
		
		
		
		
		//SAM Cache
		g.setColor(Color.black);		
		g.drawString("SAM cdf01", 15, 330);
		g.drawString("SAM cdf02", 15, 370);
		
		g.setColor( new Color(211, 211, 211) );	//BG
		g.fillRect(100, 310, GRAPH_TOTAL_WIDTH, 30);//BG// y+=130
		g.fillRect(100, 350, GRAPH_TOTAL_WIDTH, 30);//BG
		
		g.setColor( new Color(0, 102, 255) );		
		g.fillRect(100, 310, 180, 30);
		g.fillRect(100, 350, 80, 30);
		
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
		SAM_CpuMonitoring panel = new SAM_CpuMonitoring();
		panel.setBackground(Color.YELLOW);
		f.setLayout(null);
		f.add(panel);
		f.setVisible(true);
		f.setSize(WIDTH, HEIGHT+50);		
	}
}
