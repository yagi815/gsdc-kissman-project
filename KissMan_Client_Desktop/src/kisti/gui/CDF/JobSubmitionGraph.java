package kisti.gui.CDF;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class JobSubmitionGraph extends JPanel{

	private int korean;
	private int english;
	private int math;
	
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
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawLine(50, 250, 350, 250);
		
		for (int i = 0; i < 11; i++) {
			g.drawString(i*10+"", 25, 255-20*i);
			g.drawLine(50, 250-20*i, 350, 250-20*i);
		}
		g.drawLine(50, 20, 50, 250);
		g.drawString("1월", 100, 270);//문자열 x,y 값
		g.drawString("2월", 200, 270);
		g.drawString("3월", 300, 270);
		g.setColor(Color.red);
		
		
		if (korean > 0){ 
			g.fillRect(110, 250-korean*2, 10, korean*2);
		}
		if (english >0) {
			g.fillRect(210, 250-english*2, 10, english*2);
		}
		if (math >0) {
			g.fillRect(310, 250-math*2, 10, math*2);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame();
		JobSubmitionGraph j = new JobSubmitionGraph();
		f.add(j);
		f.setSize(400,400);
		f.setVisible(true);
	}
}
