package kisti.gui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import kisti.gui.CDF.DisplayCE03;
import kisti.gui.CDF.JobSubmitionGraph;
import kisti.gui.CDF.CpuMemoryMonitor;
import kisti.gui.CDF.WorkerNodeStatus;



/**
 * <pre>
 * kisti.gui.main
 *   |_ GuiMain.java
 *
 * </pre>
 *
 * Desc : main class of KissMan for GUI
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 6. 29. 오후 11:30:40
 * @Version: 1.0
 *
 */
public class GuiMain_gridLayout extends JFrame{	

	//menu	
	MenuBar  mb;
	Menu editMenu;
		
	
	//gui size
	private static int KISSMAN_WIDTH = 1680;
	private static int KISSMAN_HEIGHT = 1050; 

	//tab
	JFrame frame;
	JTabbedPane tabbedpane;
	JPanel pane;
	Component component1, component2, component3, component4;
	ImageIcon imgIcon;
	JTextArea textArea;
	Container container;
	
	
	//layout
	BorderLayout borderLayout;
	JPanel cdfPanelTree, cdfPanelBottom, cdfPanelRightTop, cdfPanelRightBottom; 
	
	
	
	/**
	 * 
	 * Desc : Constructor of GuiMain.java class
	 * 
	 */
	public GuiMain_gridLayout() {
			
		frame = new JFrame("KissMan");		
		frame.setMenuBar(makeMenu());// call makeManu method


		frame.add(makeTab());
		
		
		frame.setSize(KISSMAN_WIDTH, KISSMAN_HEIGHT);
		frame.setVisible(true);
		System.out.println("------");
	}

	
	
	/**
	 * 
	 * Desc : make main layout using panel
	 * @Method Name : settingMainLayout
	 *
	 */
	public void settingMainLayout(){
		
	}
	
	/**
	 * 
	 * Desc : make main menu
	 * @Method Name : makeMenu
	 * @return
	 *
	 */
	public MenuBar makeMenu(){
		//메뉴바 객체 생성
		  mb = new MenuBar();
		
		// 파일 메뉴 만들기
		Menu fileMenu = new Menu("파일");
		fileMenu.add("새 파일");
		fileMenu.add("열기");
		fileMenu.addSeparator();		
		fileMenu.add("종료");
		
		// 편집 메뉴
		 editMenu = new Menu("편집");
		editMenu.add("취소");
		editMenu.add("...");
		
		// about 메뉴
		Menu about = new Menu("About");
		about.add("About KissMan");
		
		mb.add(fileMenu);
		mb.add(editMenu);
		mb.add(about);
	
		

		
		return mb;
	}
	
	/**
	 * 
	 * Desc : make main tab
	 * @Method Name : makeTab
	 *
	 */
	public JTabbedPane  makeTab(){
		tabbedpane = new JTabbedPane();
		pane = new JPanel();
		imgIcon = new ImageIcon("aaa.gif");
		textArea = new JTextArea();
	
	
		
		container = new Container();
		container.setLayout(new GridLayout(4,1));

		
		
		// 첫번 째 탭		
		CpuMemoryMonitor pMemory 	= new CpuMemoryMonitor();          
		pMemory.monitoringCPU.start(); //monitoring start
		DisplayCE03 displayCE03 = new DisplayCE03(); // JPanel
		JobSubmitionGraph jobsubmistion = new JobSubmitionGraph();
		WorkerNodeStatus wnStatus = new WorkerNodeStatus();
				
		
		//add components at CDF tab		
		container.add(pMemory);
		container.add(displayCE03);
		//container.add(new JLabel("--------"));
		container.add(jobsubmistion);
		container.add(wnStatus);
		
		component1 = container;		

		tabbedpane.addTab("CDF", imgIcon, component1, "Does nothing");
		tabbedpane.setSelectedIndex(0); // 첫 화면 탭 설정
				
		
		// 두번 째 탭
		//component2 = makeTextPanel("Second");
		component2 = makeCDFPanel("CDFPanel");
		tabbedpane.addTab("Alice", imgIcon, component2, "Does twice as much nothing");

		// 세번 째 탭
		component3 = makeTextPanel("Thired");
		tabbedpane.addTab("Belle", imgIcon, component3, "Still do nothing");

		// 네번 째 탭
		component4 = makeTextPanel("Four");
		tabbedpane.addTab("KiAF", imgIcon, component4, "nothing");


		
		return tabbedpane;

		//textArea.setSize(750, 50);
		//textArea.setText("added");
	}
	
	
	/**
	 * 
	 * Desc : make Text Panle
	 * @Method Name : makeTextPanel
	 * @param text
	 * @return panel
	 *
	 */
	protected Component makeTextPanel(String text){
		JPanel panel = new JPanel(false);
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1,1));
		panel.add(label);
		return panel;
	}
	
	/**
	 * 
	 * Desc : make CDF panle
	 * @Method Name : makeCDFPanel
	 * @param text
	 * @return panel
	 *
	 */
	protected Component makeCDFPanel(String text){
		JPanel panel = new JPanel(false);
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(borderLayout);
		
		
		cdfPanelTree = new JPanel();
		cdfPanelBottom = new JPanel();
		cdfPanelRightTop = new JPanel();
		cdfPanelRightBottom = new JPanel();
		
		try {
			cdfPanelTree.setSize(400,900);
			cdfPanelTree.setBackground(Color.red);
			cdfPanelTree.add(new JLabel("cdfPanelTree"));
			
			cdfPanelBottom.setSize(1680,30);
			cdfPanelBottom.setBackground(Color.blue);
			cdfPanelBottom.add(new JLabel("cdfPanelBootm"));
			
			cdfPanelRightTop.setSize(KISSMAN_WIDTH-400,500);			
			cdfPanelRightTop.setBackground(Color.green);
			cdfPanelRightTop.add(new JLabel("cdfPanelRightTop"));
			
			cdfPanelRightBottom.setSize(KISSMAN_WIDTH-400,500);
			cdfPanelRightBottom.setBackground(Color.pink);
			cdfPanelRightBottom.add(new JLabel("cdfPanelRightBottm"));


			panel.add("West", cdfPanelTree);
			panel.add("Center",cdfPanelRightTop);
			panel.add(cdfPanelRightBottom);
			panel.add("South", cdfPanelBottom);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());

		}
		panel.add(label);
		
		return panel;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	/**
	 * 
	 * Desc : main 
	 * @Method Name : main
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GuiMain_gridLayout m = new GuiMain_gridLayout();		
	}
}
