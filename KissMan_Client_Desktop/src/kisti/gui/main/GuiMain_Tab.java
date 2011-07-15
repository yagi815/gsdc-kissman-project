package kisti.gui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import kisti.gui.CDF.CpuMemoryMonitor;
import kisti.gui.CDF.DisplayCE03;
import kisti.gui.CDF.JobSubmitionGraph;
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
public class GuiMain_Tab extends JFrame{	

	//menu	
	MenuBar  mb;
	Menu editMenu;
		
	
	//gui size
	private static int KISSMAN_WIDTH = 1366;
	private static int KISSMAN_HEIGHT = 768; 

	//tab 
	JFrame frame;
	JTabbedPane tabbedpane;
	JPanel pane;
	Component component1, component2, component3, component4;
	ImageIcon imgIcon;
	JTextArea textArea;
	Container container, cdfContainer;
	
	
	//layout
	BorderLayout borderLayout;
	GridBagLayout gridBagLayout;
	GridBagConstraints gbConstraints;
	
	JPanel cdfTreePanel, cdfCe03JobPanel, cdfCe03CpuPanel, cdfCe03MemPanel, cdfWnsPanel, cdfJobStatusPanel, cdfBottomPanel;
	
			
	
	/**
	 * 
	 * Desc : Constructor of GuiMain.java class
	 * 
	 */
	public GuiMain_Tab() {
			
		frame = new JFrame("KissMan");		
		frame.setMenuBar(makeMenu());// call makeManu method


		frame.add(makeTab());
		
		
		frame.setSize(KISSMAN_WIDTH, KISSMAN_HEIGHT);
		frame.setVisible(true);
		System.out.println("------");
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
		container.setLayout(new GridLayout(2,2));
		
		
		// 첫번 째 탭		
		//MemoryMonitor pMemory 	= new MemoryMonitor(); 			 //JPanel
		//pMemory.surf.start(); //monitoring start
		DisplayCE03 displayCE03 = new DisplayCE03(); 			// JPanel
		JobSubmitionGraph jobsubmistion = new JobSubmitionGraph(); //JPanel
		WorkerNodeStatus wnStatus = new WorkerNodeStatus(); 	//JPanel
		CpuMemoryMonitor cpuMemMonitor = new CpuMemoryMonitor();
		
		//add components at CDF tab		
		//container.add(pMemory);
		JPanel tempPanle = new JPanel();
		tempPanle.setBackground(Color.gray);
		container.add(tempPanle);
		
		JPanel cePanel = new JPanel();
		cePanel.setBorder(new TitledBorder(new EtchedBorder(), "CE03"));
		cePanel.setLayout(new GridLayout(1,2));
		
		cePanel.add(cpuMemMonitor);
		cpuMemMonitor.monitoring.start();
		cePanel.add(displayCE03);
		
		
	
		//container.add(displayCE03);
		container.add(cePanel);
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
	 * Desc : make CDF panel
	 * @Method Name : makeCDFPanel
	 * @param text
	 * @return panel
	 *
	 */
	protected Component makeCDFPanel(String text){
		JPanel panel = new JPanel(false);
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(JLabel.CENTER);
		//panel.setLayout(borderLayout);
		
		
		gridBagLayout =  new GridBagLayout();
		gbConstraints = new GridBagConstraints();
		
		
		
		cdfContainer = new Container();
		cdfContainer.setLayout(gridBagLayout);
		cdfContainer.setLayout(gridBagLayout);
		
				
		
		cdfTreePanel = new JPanel();
		cdfCe03JobPanel  = new JPanel();
		cdfCe03CpuPanel  = new JPanel();
		cdfCe03MemPanel  = new JPanel();
		cdfWnsPanel  = new JPanel();
		cdfJobStatusPanel  = new JPanel();
//		cdfBottomPanel = new JPanel();
//		cdfBottomPanel.setSize(1680,30);
		
		
		cdfTreePanel.setBackground(Color.red);
		cdfTreePanel.add(new JLabel("cdfTreePanel"));
		//gbConstraints = new GridBagConstraints(0,0,1,1,0.2,1.0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 10, 20);
		gbConstraints.fill = GridBagConstraints.BOTH;
		gbConstraints.weightx = 0.2;
		gbConstraints.weighty = 1.0;
		addComponent(cdfTreePanel, 0, 0, 1, 3);
				
				
		cdfCe03JobPanel.setBackground(Color.green);
		cdfCe03JobPanel.add(new JLabel("cdfCe03JobPanel"));
		gbConstraints.fill = GridBagConstraints.NONE;
		gbConstraints.weightx = 0.8;
		gbConstraints.weighty = 1.0;
		addComponent(cdfCe03JobPanel, 1, 0, 1, 2);
		
		cdfCe03CpuPanel.setBackground(Color.blue);
		cdfCe03CpuPanel.add(new JLabel("cdfCe03CpuPanel"));
		gbConstraints.fill = GridBagConstraints.BOTH;
		addComponent(cdfCe03CpuPanel, 2, 0, 1, 1);
		
		cdfCe03MemPanel.setBackground(Color.cyan);
		cdfCe03MemPanel.add(new JLabel("cdfCe03MemPanel"));
		gbConstraints.fill = GridBagConstraints.BOTH;
		addComponent(cdfCe03MemPanel, 3, 0, 1, 1);
		
		cdfWnsPanel.setBackground(Color.gray);
		cdfWnsPanel.add(new JLabel("cdfWnsPanel"));
		gbConstraints.fill = GridBagConstraints.BOTH;
		addComponent(cdfWnsPanel, 2, 1, 2, 1);
		
		cdfJobStatusPanel.setBackground(Color.yellow);
		cdfJobStatusPanel.add(new JLabel("cdfJobStatusPanel"));
		gbConstraints.fill = GridBagConstraints.BOTH;
		addComponent(cdfJobStatusPanel, 1, 2, 3, 1);
		
//		cdfBottomPanel.setBackground(Color.orange);
//		cdfBottomPanel.add(new JLabel("cdfBottomPanel"));
//		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
//		addComponent(cdfBottomPanel, 0, 4, 4, 1);
//		

//		panel.add(label);
//		
//		return panel;
		cdfContainer.setBackground(Color.yellow);
		return cdfContainer;
	}
	
	private void addComponent ( Component c,  int column, int row, int width, int height){
		//gridx, gridy 셋팅
		gbConstraints.gridx = column;
		gbConstraints.gridy = row;
		
		//gridwidth, gridheight  셋팅
		gbConstraints.gridwidth = width;
		gbConstraints.gridheight = height;
		
		//제한 조건 세팅
		gridBagLayout.setConstraints(c, gbConstraints);
		cdfContainer.add(c);	//add component
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
		GuiMain_Tab m = new GuiMain_Tab();		
	}
}
