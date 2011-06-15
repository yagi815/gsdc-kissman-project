package test_code;

import java.awt.*;
import javax.swing.*;

public class ButtonGrid {
	public static void main(String[] args){
		JFrame frame = new JFrame();
		Container c = frame.getContentPane();
		
		JLabel label1 = new JLabel("[1][1]", SwingConstants.CENTER);
		JLabel label2 = new JLabel("[1][2]", SwingConstants.CENTER);
		JLabel label3 = new JLabel("[2][1]", SwingConstants.CENTER);
		JLabel label4 = new JLabel("[2][2]", SwingConstants.CENTER);
		JLabel label5 = new JLabel("[3][1]", SwingConstants.CENTER);
		JLabel label6 = new JLabel("[3][2]", SwingConstants.CENTER);
		
		
		c.setLayout(new GridLayout(3,2));
		c.add(label1);
		c.add(label2);
		c.add(label3);
		c.add(label4);
		c.add(label5);
		c.add(label6);
		
		frame.setLocation(500,400);
		frame.setPreferredSize(new Dimension(400,340));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
