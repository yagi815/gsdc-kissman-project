package kisti.gui.main;

import java.awt.Color;
import java.awt.MenuBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import kisti.gui.CDF.CAF_CDFJobTable;
import kisti.gui.CDF.CAF_DisplayCE03;
import kisti.gui.CDF.CAF_JobSubmitionGraph;
import kisti.gui.CDF.CAF_QueueStatus;
import kisti.gui.CDF.CAF_WorkerNodeStatus;
import kisti.gui.CDF.KistiCI;
import kisti.gui.CDF.SAM_CpuMonitoring;




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
public class GuiMain extends JFrame   {	
	

	private static int KISSMAN_WIDTH = 1366;
	private static int KISSMAN_HEIGHT = 768;
	private JPanel panelTree = null;
	private JPanel currentMainPanel, panelCDF01, panelCDF02, panelAlice01, panelAlice02, panelBelle01, panelBelle02, panelKistiCI, panelBottom;
	
	private JTable tableCDFWorkernodes = null;
	private JTable tableCDFJobTotal = null;
	private CAF_JobSubmitionGraph jobSubmitionGraph = null;
	
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
		this.setResizable(false);
						
		/**
		 * 2. treePanel		 
		 */
		panelTree = makeTreePanel();
		panelTree.setBackground(Color.WHITE);
		panelTree.setBounds(12, 12, 219-19, 553);
		this.add(panelTree);
		
		/**
		 * 3. CDF01 ... CDF02 .... Alice..
		 */
		panelCDF01 = MakeCDF01Panel();
		//System.out.println("panelCDF01 name="+panelCDF01.getName());
		currentMainPanel = panelCDF01;
		panelCDF01.setBackground(Color.WHITE);
		panelCDF01.setBounds(234, 12, 1095, 676);
		
		panelCDF02 = MakeCDF02Panel();
		panelCDF02.setBackground(Color.WHITE);
		panelCDF02.setBounds(234, 12, 1095, 676);
		
		
		//currentPanel = panelCDF01;
		this.add(panelCDF02);
		
		/**
		 * 4. KISTI CI panel
		 */
		panelKistiCI = makeKistiCI();
		panelKistiCI.setBounds(10, 612, 221-19, 76);
		this.add(panelKistiCI);

		/**
		 * 5. BottomPanel
		 */
		panelBottom = makeBottomPanel();
		panelBottom.setBounds(0, 700, 1350, 30);
		this.add(panelBottom);
		
		
		
		this.setVisible(true);		
	}
	
	
	/**
	 * 
	 * Desc :
	 * @Method Name : makeMenu
	 * @return
	 *
	 */
	private MenuBar makeMenu() {		
		MenuBar menubar = new MenuBar();		
		return menubar;
	}
	
	/**
	 * 
	 * Desc :
	 * @Method Name : drawMainPanel
	 * @param panelName
	 *
	 */
	private void changeMainPanel(MouseEvent e){		

		JTree t = (JTree)e.getSource();
		String sourceName = t.getLastSelectedPathComponent().toString();
		System.out.println("tree:"+sourceName);
		
		System.out.println( "no of component="+this.getComponentCount() );
		
		//System.out.println("current Panel name "+ currentMainPanel.getName() );
		
		
		if (sourceName.equals("CDF_CAF")) {	
			this.add(panelCDF02);			
		} else if (sourceName.equals("CDF_SAM")) {	
			this.add(panelCDF01);			
		} else if (sourceName.equals("Alice")) {
			this.add(panelAlice01);			
		}		
		System.out.println("nothing");
		repaint();
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
				DefaultMutableTreeNode treeCDF_CAF = new DefaultMutableTreeNode("CDF_CAF");
				DefaultMutableTreeNode treeCDF_SAM = new DefaultMutableTreeNode("CDF_SAM");
			
			DefaultMutableTreeNode treeBelle = new DefaultMutableTreeNode("Belle");
			DefaultMutableTreeNode treeAlice = new DefaultMutableTreeNode("Alice");
		

		treeTop.add(treeCDF);
		treeCDF.add(treeCDF_CAF);
		treeCDF.add(treeCDF_SAM);
		
		treeTop.add(treeBelle);
		treeTop.add(treeAlice);
		
		JTree jtreeMain = new JTree(treeTop);
		jtreeMain.setBounds(12, 12, 176, 280);

		jtreeMain.addMouseListener(
				new MouseAdapter() {
					public void mouseReleased (MouseEvent e){		
						changeMainPanel(e);						
					}
				}
		);		

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
		panel.setName("panelCDF01");
		
		/** 
		 * display CE03 activity
		 */
		CAF_DisplayCE03 displayCE03 = new CAF_DisplayCE03();
		displayCE03.setBounds(12, 12, 445, 330);
		panel.add(displayCE03);
		
		/**
		 *  display ce03 queue jobs		  
		 */	
		JPanel panelQueueStatus = new CAF_QueueStatus();
		panelQueueStatus.setBounds(12, 354, 445, 70);
		panel.add(panelQueueStatus);
		
		/** 
		 * display label for status of workernodes
		 */
		JLabel labelCDFWokerNodes = new JLabel("Kisti Workernode's Status for CDF");
		labelCDFWokerNodes.setBounds(484, 36 ,205 ,18);
		panel.add(labelCDFWokerNodes);

		/**
		 * display wokernode table
		 */
		JPanel wnStatus = new CAF_WorkerNodeStatus();
		wnStatus.setBounds(484, 60, 599, 353);
		panel.add(wnStatus);
		
		/**
		 * display Seperator panel
		 */
		JPanel panelSeperator = new JPanel();
		panelSeperator.setBackground(new Color(153, 153, 153));
		panelSeperator.setBounds(0, 426, 1095, 10);
		panel.add(panelSeperator);
				
		/**
		 * display label for Jobtotal 
		 */
		JLabel labelJobTotal = new JLabel("CDF job status");
		labelJobTotal.setBounds(12, 443 ,205 ,15);
		panel.add(labelJobTotal);
		
		/**
		 * display Jobtable
		 */
		JPanel CDFJobTable = new CAF_CDFJobTable();
		CDFJobTable.setBounds(12, 468, 569, 206);
		panel.add(CDFJobTable);
		
		/**
		 * display number of total jobs 
		 */
		CAF_JobSubmitionGraph jobSubmitionGraph = new CAF_JobSubmitionGraph();
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
		panel.setBackground(Color.gray);
		panel.setLayout(null);
		panel.setName("panelCDF02");
//		
//		JPanel p = new JPanel();
//		p.setSize(100, 100);
//		p.setBounds(12, 12, 100,100);
//		p.setBackground(Color.red);
//		panel.add(p);

//		/**
//		 * display SAM Station
//		 */
//		JLabel label = new JLabel("SAM Station");
//		label.setBounds(12,12, 100, 30);
//		panel.add(label);
//		
		/**
		 * display cpu & memory panel
		 */
		JPanel displayCpuMon = new SAM_CpuMonitoring();
		//displayCpuMon.setBackground(Color.red);
		displayCpuMon.setBounds(12, 12, 445, 500);
		panel.add(displayCpuMon);
//		
//		/**
//		 * display Seperator1 panel
//		 */
//		JPanel panelSeperator1 = new JPanel();
//		panelSeperator1.setBackground(new Color(153, 153, 153));
//		panelSeperator1.setBounds(0, 226, 1095, 10);
//		panel.add(panelSeperator1);
		
//		/**
//		 * display Seperator2 panel
//		 */
//		JPanel panelSeperator2 = new JPanel();
//		panelSeperator2.setBackground(new Color(153, 153, 153));
//		panelSeperator2.setBounds(0, 426, 1095, 10);
//		panel.add(panelSeperator2);
//		
//		/**
//		 * display Seperator3 panel
//		 */
//		JPanel panelSeperator3 = new JPanel();
//		panelSeperator3.setBackground(new Color(153, 153, 153));
//		panelSeperator3.setBounds(0, 626, 1095, 10);
//		panel.add(panelSeperator3);
		
		
		
		
		
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
		panel.setName("panelAlice01");
		
		
		JLabel l = new JLabel("Alice");
		l.setBounds(12, 12, 445, 330);		
		panel.add(l);
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
		JPanel panel = new KistiCI();		
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
		panel.add(new JLabel("Kisti System Manager (KissMan) V 1.0                      Copylight \u24D2 2011 KISTI, Org. All rights reserved.  ",SwingConstants.RIGHT));		
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
