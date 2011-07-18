package kisti.gui.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MenuBar;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import kisti.gui.CDF.DisplayCE03;
import kisti.gui.CDF.JobSubmitionGraph;




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
 * @Date   :2011. 6. 29. AM 11:30:40
 * @Version: 1.0
 *
 */
public class GuiMain extends JFrame{	

	private static int KISSMAN_WIDTH = 1366;
	private static int KISSMAN_HEIGHT = 768;
	private JPanel panelTree = null;
	private JPanel panelCDF01, panelCDF02, panelAlice01, panelAlice02, panelBelle01, panelBelle02, panelKistiCI, panelBottom;
	
	private JTable tableCDFWorkernodes = null;
	private JTable tableCDFJobTotal = null;
	private JobSubmitionGraph jobSubmitionGraph = null;
	
	private JTree treeRoot = null;

		
	
	/**
	 * 
	 * Desc : Constructor of GuiMain.java class
	 * 
	 */
	public GuiMain() {
		
		this.setTitle("KissMan");
		this.setSize(KISSMAN_WIDTH, KISSMAN_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
						
		/**
		 * 2. treePanel		 
		 */
		panelTree = makeTreePanel();
		panelTree.setBackground(Color.WHITE);
		panelTree.setBounds(12, 12, 219-19, 553);
		this.add(panelTree);
		
		/**
		 * 3. CDF01 ... CDF02 ....
		 */
		panelCDF01 = MakeCDF01Panel();
		panelCDF01.setBackground(Color.WHITE);
		panelCDF01.setBounds(234, 12, 1095, 676);
		this.add(panelCDF01);
		
		/**
		 * 4. KISTI CI panel
		 */
		panelKistiCI = makeKistiCI();
		panelKistiCI.setBounds(10, 612, 221-19, 76);
		this.add(panelKistiCI);

		/**
		 * 5. BottmPanel
		 */
		panelBottom = makeBottomPanel();
		panelBottom.setBounds(0, 700, 1350, 30);
		this.add(panelBottom);
		
		
		
		this.setVisible(true);		
	}
	
	private MenuBar makeMenu() {		
		MenuBar menubar = new MenuBar();
		
		return menubar;
	}
	
	
	/**
	 * 
	 * Desc : makeTreePanel
	 * @Method Name : makeTreePanel
	 * @return panel
	 *
	 */
	private JPanel makeTreePanel() {
		JPanel panel = new JPanel();	
		panel.setLayout(null);
		
		DefaultMutableTreeNode treeTop = new DefaultMutableTreeNode("KISTI System Monitoring");
		DefaultMutableTreeNode treeCDF = new DefaultMutableTreeNode("CDF");
		DefaultMutableTreeNode treeBelle = new DefaultMutableTreeNode("Belle");
		DefaultMutableTreeNode treeAlice = new DefaultMutableTreeNode("Alice");
				
		treeTop.add(treeCDF);
		treeTop.add(treeBelle);
		treeTop.add(treeAlice);
		
		JTree jtreeMain = new JTree(treeTop);
		jtreeMain.setBounds(12, 12, 176, 280);
		
		
		panel.add(jtreeMain);		
				
		return panel;
	}
	/**
	 * 
	 * Desc : makeCDF01Panel
	 * @Method Name : MakeCDF01Panel
	 * @return panel
	 *
	 */
	private JPanel MakeCDF01Panel(){
		JPanel panel = new JPanel();		
		panel.setLayout(null);
		
		/** 
		 * display CE03 activity
		 */
		DisplayCE03 displayCE03 = new DisplayCE03();
		displayCE03.setBounds(12, 12, 445, 330);
		panel.add(displayCE03);
		
		/**
		 *  display ce03 queue jobs		  
		 */
		JTextArea textareaDisplayQueue = new JTextArea();
		textareaDisplayQueue.setText("Queue              Max   Tot   Ena   Str   Que   Run   Hld   Wat   Trn   Ext T \n"
				+ "----------------   ---   ---   ---   ---   ---   ---   ---   ---   ---   ---		- \n"
				+ "belle                0     0   yes   yes     0     0     0     0     0     0		E \n"
				+ "osgcdf               0   235   yes   yes     0   234     0     0     0     1 	E \n"
				);
		textareaDisplayQueue.setBackground(new Color(255, 204, 204));
		textareaDisplayQueue.setBounds(12, 354, 445, 70);		
		panel.add(textareaDisplayQueue);
		
		/** 
		 * display status of workernodes
		 */
		JLabel labelCDFWokerNodes = new JLabel("Kisti Workernode's Status for CDF");
		labelCDFWokerNodes.setBounds(484, 36 ,205 ,18);
		panel.add(labelCDFWokerNodes);
		
		
		/**
		 * display wokernode table
		 */
		String wokerNodeTableheader[] = {"Name","Power","running","Server","Etc"};
		String wokerNodeTablecells[][] ={
				{"WN1017","ON","Running","Blade",".."},				
				{"WN1018","ON","Running","Blade",".."},
				{"WN1019","ON","Null","Blade",".."},
				{"WN1020","ON","Running","Blade",".."},
				{"WN2001","OFF","Null","1U Server",".."},
				{"WN2002","ON","Running","1U Server",".."},
				{"WN2003","ON","Running","1U Server",".."},
				{"WN2004","OFF","Null","1U Server",".."}
		};		
		DefaultTableModel tablemodelWN = new  DefaultTableModel(wokerNodeTablecells, wokerNodeTableheader);
		DefaultTableColumnModel tablecolumnmodelWN = new DefaultTableColumnModel();
		JTable tableWN = new JTable(tablemodelWN, tablecolumnmodelWN);
		
		
//		TableColumn tc, tc1, tc2, tc3;
//		tc = new TableColumn();
//		tc.setHeaderValue("naem");
//		table.addColumn(tc);
		
				
		tableWN.setBounds(484, 60, 599, 353);
		tableWN.setBackground(new Color(255, 255, 204));		
		//panel.add(new JScrollPane(table), BorderLayout.CENTER);
		panel.add(tableWN);
		
		/**
		 * display Seperator panel
		 */
		JPanel panelSeperator = new JPanel();
		panelSeperator.setBackground(new Color(153, 153, 153));
		panelSeperator.setBounds(0, 436, 1095, 10);
		panel.add(panelSeperator);
		
		
		/**
		 * display Jobtable
		 */
		String jobTableheader[] = {"Period","Users","Jobs(Total)","Success","Wall Time(Hours)"};
		String jobTablecells[][] ={
				{"2011-01-03 ~ 2011-01-09","4","11741","100%","31908"},				
				{"2011-01-10 ~ 2011-01-16","3","7126","100%","10255"},
				{"2011-02-07 ~ 2011-02-13","2","4181","100%","30361"},
				{"2011-02-14 ~ 2011-02-20","2","1894","100%","78254"},
				{"2011-02-21 ~ 2011-02-27","2","8051","100%","20156"},
				{"2011-02-28 ~ 2011-03-06","2","13154","100%","34588"},
				{"2011-04-04 ~ 2011-04-10","2","2744","100%","15488"},
				{"2011-04-11 ~ 2011-04-17","2","2365","100%","30447"},
				{"2011-04-18 ~ 2011-04-24","2","6702","100%","34888"},
				{"2011-04-25 ~ 2011-05-01","4","10408","100%","36555"},
				{"2011-05-02 ~ 2011-05-08","3","10408","100%","32333"},
				{"2011-05-09 ~ 2011-05-15","2","10408","100%","33659"},
				{"2011-05-16 ~ 2011-05-22","3","10408","100%","33789"},
				{"2011-05-23 ~ 2011-05-29","1","10408","100%","33987"},
				{"2011-05-30 ~ 2011-06-05","1","10408","100%","33256"},				
		};
		DefaultTableModel tablemodelJob = new  DefaultTableModel(jobTablecells, jobTableheader);
		DefaultTableColumnModel tablecolumnmodelJob = new DefaultTableColumnModel();
		JTable tableJob = new JTable(tablemodelJob, tablecolumnmodelJob);
		tableJob.setBounds(12,458, 569, 206);
		tableJob.setBackground(new Color(255, 255, 204));
		panel.add(tableJob);
				
		
		/**
		 * display number of total jobs 
		 */
		JobSubmitionGraph jobSubmitionGraph = new JobSubmitionGraph();
		jobSubmitionGraph.setBounds(593, 458, 490, 206);
		panel.add(jobSubmitionGraph);		
		
		
		return panel;
	}
	/**
	 * 
	 * Desc : make cdf 02 panel 
	 * @Method Name : MakeCDF02Panel
	 * @return panel
	 *
	 */
	private JPanel MakeCDF02Panel(){
		JPanel panel = new JPanel();		
		panel.setLayout(null);
		return panel;
	}
	/**
	 * 
	 * Desc : make alice01 panel
	 * @Method Name : MakeAlice01Panel
	 * @return panel
	 *
	 */
	private JPanel MakeAlice01Panel(){
		JPanel panel = new JPanel();		
		panel.setLayout(null);
		return panel;
	}
	
	/**
	 * 
	 * Desc : make Kisti CI
	 * @Method Name : makeKistiCI
	 * @return panel
	 *
	 */
	private JPanel makeKistiCI(){
		JPanel panel = new JPanel();
//		Image imgKISTI = Toolkit.getDefaultToolkit().getImage("./Resource/KISTI.gif");
//		JPanel panel = new JPanel()
//		{
//			public void paintComponent(Graphics g){
//				g.drawImage(imgKISTI, 10+5, 612+5, 221-19, 76, null );
//			}
//		};
		//panel.add(new JLabel("hello"));
		//E:\Java_workspace\KissMan_Client_Desktop\src\kisti\gui\main\Images
		panel.add(new JLabel(new ImageIcon("Images/KISTI.gif")));
		panel.setBackground(Color.WHITE);
		return panel;
	}
	
	/**
	 * 
	 * Desc : makeBottomPanle
	 * @Method Name : makeBottomPanel
	 * @return panel
	 *
	 */
	private JPanel makeBottomPanel(){
		JPanel panel = new JPanel();		
		panel.setBackground(new Color(153, 51, 51 ));	
		panel.add(new JLabel("Kisti System Monitoring (KissMan) V 1.0                      Copylight \u24D2 2011 KISTI, Org. All rights reserved.  ",SwingConstants.RIGHT));		
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
		GuiMain m = new GuiMain();		
	}
}
