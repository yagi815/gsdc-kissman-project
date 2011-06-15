package test_code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Graph extends JFrame{
	
	private JPanel p1;
	private JTextField text1;
	private JTextField text2;
	private JTextField text3;	
	private JButton button ;
	private DrawingPanel drawingPanel;
	
	
	public Graph() {
		// TODO Auto-generated constructor stub
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//x버튼
		this.setTitle("graph test");
		this.setLocation(500,300);
		this.setSize(550,400);
		drawingPanel = new DrawingPanel();
		this.add(drawingPanel);
		
		p1 = new JPanel();
		text1 = new JTextField(3);
		text2 = new JTextField(3);
		text3 = new JTextField(3);
		button = new JButton("그래프 그리기");
		
		p1.add(new JLabel("국어"));
		p1.add(text1);
		p1.add(new JLabel("영어"));
		p1.add(text2);
		p1.add(new JLabel("수학"));
		p1.add(text3);
		p1.add(button);
		
		this.add(p1,BorderLayout.SOUTH);
		
		//버튼시 동작
		button.addActionListener(new DrawActionListener(text1,text2,text3,drawingPanel));
		this.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g = new Graph();
	}

}


class DrawingPanel extends JPanel{
	int korean, english, math;
	public void paint(Graphics g){
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawLine(50, 250, 350, 250);
		
		for (int i = 0; i < 11; i++) {
			g.drawString(i*10+"", 25, 255-20*i);
			g.drawLine(50, 250-20*i, 350, 250-20*i);
		}
		g.drawLine(50, 20, 50, 250);
		g.drawString("국어", 100, 270);//문자열 x,y 값
		g.drawString("영어", 200, 270);
		g.drawString("수학", 300, 270);
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
	
	void setScores(int korean, int english, int math)
	{
		this.korean = korean;
		this.english = english;
		this.math = math;		
	}	
}

class DrawActionListener implements ActionListener{

	JTextField text1, text2, text3;
	DrawingPanel drawingPanel;
	public DrawActionListener(JTextField text1, JTextField text2, JTextField text3, DrawingPanel drawingPanel){
		this.text1 = text1;
		this.text2 = text2;
		this.text3 = text3;
		this.drawingPanel = drawingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			int korean = Integer.parseInt(text1.getText());
			int english = Integer.parseInt(text2.getText());
			int math = Integer.parseInt(text3.getText());
			drawingPanel.setScores(korean, english, math);
			drawingPanel.repaint();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}