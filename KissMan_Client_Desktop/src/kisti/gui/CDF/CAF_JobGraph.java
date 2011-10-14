package kisti.gui.CDF;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kisti.module.database.KissManDatabase;

/**
 * <pre>
 * kisti.gui.CDF
 *   |_ JobSubmitionGraph.java
 *
 * </pre>
 *
 * Desc :
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 7. 18. AM 10:00:55
 * @Version:
 *
 */


public class CAF_JobGraph extends JPanel{

	private int nJob[];
	private static int WIDTH = 460;
	private static int HEIGHT = 206;
	private static int GRAPH_HEIGHT = 190;

	
	/**
	 * 
	 * Desc :
	 * @Method Name : JobSubmitionGraph
	 *
	 */	
	public CAF_JobGraph() {
		// TODO Auto-generated constructor stub
		
		KissManDatabase db = new KissManDatabase();
		Object obj = db.requestDataToDataBase("jobsubmition");
		nJob = (int[]) obj;

		this.setSize(WIDTH, HEIGHT	);
		this.setBackground(new Color(147,77,134)); //for test		
		this.repaint();
	}

	/**
	 * paint
	 */
	public void paint(Graphics g){

		g.clearRect(0, 0, getWidth(), getHeight());		
		//g.drawLine(WIDTH+50, HEIGHT+250, 350, 250);
		
		//세로 가이드 라인
		g.drawLine(50, 0, 50, GRAPH_HEIGHT);
		//가로 가이드 라인 & 가로 string	   
		int stringY = GRAPH_HEIGHT + 3;
		for (int i = 0; i <= 12; i++) {
			g.drawString(i*2000+"", 15, stringY-15*i);
			g.drawLine(50, GRAPH_HEIGHT-15*i, 400, GRAPH_HEIGHT-15*i);
		}
		
		//가로 string
		int monthY = GRAPH_HEIGHT + 15;
		int monthX = 15;
		g.drawString("month", 15,  monthY);
		monthX += 25;
		
		for (int i = 1; i <= nJob.length; i++) {
			monthX += 25;
			g.drawString(i+"", monthX,  monthY);
			//System.out.print(monthX +"-");
		}

		// 막대 그래프 
		g.setColor(Color.red);		
		int graphX = 35;
		int graphY = 15;
		int graphWidth = 10;
		int graphHeight = 0;		

		for (int i = 0; i < nJob.length; i++) {
			if(nJob[i] > 0){
				graphX += 25;
				//System.out.println(nJob[i]+"-");
				graphHeight = nJob[i]/2000;
				g.fillRect(graphX, GRAPH_HEIGHT, graphWidth, -(graphHeight*15) ); // 그래프				
			}
		}		
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
		CAF_JobGraph panel = new CAF_JobGraph();
		panel.setBackground(Color.YELLOW);
		f.setLayout(null);
		f.add(panel);
		f.setSize(WIDTH, HEIGHT+50);
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
