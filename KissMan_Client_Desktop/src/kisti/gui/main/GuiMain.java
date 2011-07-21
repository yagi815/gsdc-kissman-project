package kisti.gui.main;

import java.awt.Color;
import java.awt.MenuBar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import kisti.gui.CDF.CDFJobTable;
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
		 * 5. BottomPanel
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
		 * display label for status of workernodes
		 */
		JLabel labelCDFWokerNodes = new JLabel("Kisti Workernode's Status for CDF");
		labelCDFWokerNodes.setBounds(484, 36 ,205 ,18);
		panel.add(labelCDFWokerNodes);
		
		
		/**
		 * display wokernode table
		 */
		JPanel wnStatus = new WorkerNodeStatus();
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
		JPanel CDFJobTable = new CDFJobTable();
		CDFJobTable.setBounds(12, 468, 569, 206);
		panel.add(CDFJobTable);
		
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
		//E:/Java_workspace/KissMan_Client_Desktop/src/kisti/gui/main/Images
		panel.add(new JLabel(new ImageIcon("E:/Java_workspace/KissMan_Client_Desktop/src/kisti/gui/main/Images/KISTI.gif")));
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
