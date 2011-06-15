package kisti.gui.main;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import kisti.gui.CDF.DisplayCE03;
import kisti.gui.CDF.JobSubmitionGraph;
import kisti.gui.CDF.MemoryMonitor;




/*********************************************************
 *  CDF tab
 *  1. 현재 job 실행상태(PBS mon CE03)
 *  2. Job 누계 (own DB)
 *  3. WN cpu,mem Usage ( PBS mon or Nagios or Etc...)
 *  4. OSG (OSG 웹페이지.. )
 *  5. SAM station 저장용량 (own Server) 
 * 
 * */
class makeGUI extends JFrame{
	JFrame frame;
	JTabbedPane tabbedpane;
	JPanel pane;
	Component component1, component2, component3, component4;
	ImageIcon imgIcon;
	JTextArea textArea;
	Window window;
	Container container;
	JPanel panel_cdf;
	
	
/*
탭 만드는 방법
1. frame <- tabbedpane <- component(panel,label,layout) 
*/
	public makeGUI() {
		frame = new JFrame("KissMan");
		tabbedpane = new JTabbedPane();
		pane = new JPanel();
		imgIcon = new ImageIcon("aaa.gif");
		textArea = new JTextArea();
	
	
		
		container = new Container();
		container.setLayout(new GridLayout(3,1));

		
		
		// 첫번 째 탭		
		MemoryMonitor pMemory = new MemoryMonitor();
		pMemory.surf.start(); //monitoring start
		DisplayCE03 displayCE03 = new DisplayCE03(); // JPanel
		JobSubmitionGraph jobsubmistion = new JobSubmitionGraph();
		
		
		
		//add components at CDF tab
		
		container.add(pMemory);
		container.add(displayCE03);
		//container.add(new JLabel("--------"));
		container.add(jobsubmistion);
		
		component1 = container;		

		tabbedpane.addTab("CDF", imgIcon, component1, "Does nothing");
		tabbedpane.setSelectedIndex(0); // 첫 화면 탭 설정

		
		
		
		// 두번 째 탭
		component2 = makeTextPanel("Second");
		tabbedpane.addTab("Alice", imgIcon, component2, "Does twice as much nothing");

		// 세번 째 탭
		component3 = makeTextPanel("Thired");
		tabbedpane.addTab("Belle", imgIcon, component3, "Still do nothing");

		// 네번 째 탭
		component4 = makeTextPanel("Four");
		tabbedpane.addTab("KiAF", imgIcon, component4, "nothing");

		// 완성된 JTabbedPane를 패널에 부착한다.
		frame.setLayout(new GridLayout(1, 1));
		frame.add(tabbedpane);
		// frame.add(textArea);
		textArea.setSize(750, 50);
		textArea.setText("added");

		// frame.getContentPane().add(new makeGUI(),BorderLayout.CENTER);
		frame.setSize(1024, 1200);
		frame.setVisible(true);
		System.out.println("------");
	}

	protected Component makeTextPanel(String text){
		JPanel panel = new JPanel(false);
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1,1));
		panel.add(label);
		return panel;
	}
}
public class GuiMain {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		makeGUI m = new makeGUI();		
	}
}
