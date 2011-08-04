package kisti.gui.CDF;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <pre>
 * kisti.gui.CDF
 *   |_ KistiCI.java
 *
 * </pre>
 *
 * Desc :
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 7. 21. 오후 2:52:58
 * @Version:
 *
 */
public class KistiCI extends JPanel{

	public KistiCI() {
		// TODO Auto-genera
		//this.setLayout(null);
		JLabel label = new JLabel(new ImageIcon("E:/Java_workspace/KissMan_Client_Desktop/src/kisti/gui/main/Images/KISTI_CI.jpg"));
		//label.setBounds(0,0,100,200);
		this.add(label);
		this.setBackground(Color.WHITE);		
	}
	/**
	 * Desc :
	 * @Method Name : main
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame();
		KistiCI panel = new KistiCI();
		panel.setBackground(Color.YELLOW);		
		f.add(panel);
		f.setSize(250, 150);
		f.setVisible(true);

	}
}
