package kisti.gui.CDF;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
public class JobSubmitionGraph extends JPanel{

	private int korean;
	private int english;
	private int math;
	private int nJob[];
	
	public JobSubmitionGraph() {
		// TODO Auto-generated constructor stub
		korean = 55;
		english = 66;
		math = 10;
		
		this.setBackground(new Color(147,77,134)); //for test
		this.setBorder(new TitledBorder(new EtchedBorder(),"JobTotal"));
		this.repaint();
	}


	public void paint(Graphics g){
		
		nJob = new int[100];
		nJob[0]=11741;
		nJob[1]=7126;
		nJob[2]=4181;
		nJob[3]=1894;
		nJob[4]=8051;
		
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawLine(50, 250, 350, 250);
		
		for (int i = 0; i < 16; i++) {
			g.drawString(i*1000+"", 15, 330-20*i);
			g.drawLine(50, 330-20*i, 370, 330-20*i);
		}
		g.drawLine(50, 20, 50, 330);
		g.drawString("week", 15, 350);
		g.drawString("1", 60, 350);
		g.drawString("2", 80, 350);
		g.drawString("3", 100, 350);
		g.drawString("4", 120, 350);
		g.drawString("5", 140, 350);
		g.drawString("6", 160, 350);
		g.drawString("7", 180, 350);
		g.drawString("8", 200, 350);
		g.drawString("9", 220, 350);
		g.drawString("10", 240, 350);
		g.drawString("11", 260, 350);
		g.drawString("12", 280, 350);
		g.drawString("13", 300, 350);
		g.drawString("14", 320, 350);
		g.drawString("15", 340, 350);
		g.drawString("16", 360, 350);
		
		g.setColor(Color.red);
		int distance = 60;
		int x,y,width,height;
				
		for (int i = 0; i < nJob.length; i++) {
			if(nJob[i] > 0){
				
				x = distance;
				y = (nJob[i]/1000);
				width = 10;
				height = nJob[i]/1000;
				//g.fillRect(distance, (330-((330/15000)*nJob[i])), 10, (330/15000)*nJob[i] );
				g.fillRect(x, y, width, height);
				System.out.println(x+"-"+y+"-"+width+"-"+height+"-"+nJob[i]);
				distance += 20;
			}
		}		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame();
		JobSubmitionGraph j = new JobSubmitionGraph();
		f.add(j);
		f.setSize(450,400);
		f.setVisible(true);
	}
}
