package kisti.gui.main;

import java.awt.Color;
import java.awt.MenuBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import kisti.gui.CDF.CAF_JobMon;
import kisti.gui.CDF.CAF_CE03Mon;
import kisti.gui.CDF.CAF_JobGraph;
import kisti.gui.CDF.CAF_QueueStatusMon;
import kisti.gui.CDF.CAF_serverMon;
import kisti.gui.CDF.KistiCI;
import kisti.gui.CDF.SAM_CacheMon;
import kisti.gui.CDF.SAM_DstatMon;
import kisti.gui.CDF.SAM_InfoMon;




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
//	private Jpanel[] = {currentMainPanel,panelCDF01};
	
	private JTable tableCDFWorkernodes = null;
	private JTable tableCDFJobTotal = null;
	private CAF_JobGraph jobSubmitionGraph = null;
	
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
		panelCDF01 = makeCDFPanel01();		
		panelCDF01.setBackground(Color.WHITE);
		panelCDF01.setBounds(234, 12, 1095, 676);		
		panelCDF02 = makeCDFPanel02();
		panelCDF02.setBackground(Color.WHITE);
		panelCDF02.setBounds(234, 12, 1095, 676);
		panelBelle01 = makeBellePanel01();
		panelBelle01.setBackground(Color.WHITE);
		panelBelle01.setBounds(234, 12, 1095, 676);
		panelAlice01 = makeAlicePanel01();
		panelAlice01.setBackground(Color.WHITE);
		panelAlice01.setBounds(234, 12, 1095, 676);
		
		
		this.add(panelCDF01);
		this.add(panelCDF02);
		this.add(panelBelle01);
		this.add(panelAlice01);
		panelCDF01.setVisible(true);		
		panelCDF02.setVisible(false);
		panelBelle01.setVisible(false);
		panelAlice01.setVisible(false);
		
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
		
		
//		for (int i = 0; i < mainPanel.length; i++) {
//			//this.add(mainPanel[i]);
//			System.out.println(mainPanel[i].toString());
//			
//		}
		
		
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
//		System.out.println("tree:"+sourceName);
		
//		System.out.println( "no of component="+this.getComponentCount() );		
//		//System.out.println("current Panel name "+ currentMainPanel.getName() );
		panelCDF01.setVisible(false);
		panelCDF02.setVisible(false);
		panelCDF01.setVisible(false);
		panelAlice01.setVisible(false);
//		panelAlice02.setVisible(false);
		panelBelle01.setVisible(false);
//		panelBelle02.setVisible(false);
		
		
		
		if (sourceName.equals("CDF_CAF")) {
			panelCDF01.setVisible(true);
			System.out.println("CDF_CAF " + sourceName);
		} else if (sourceName.equals("CDF_SAM")) {
			panelCDF02.setVisible(true);
			System.out.println("CDF_SAM " + sourceName);
		} else if (sourceName.equals("Alice")) {
			panelAlice01.setVisible(true);
			System.out.println("Alice " + sourceName);
		} else if (sourceName.equals("Belle")) {
			panelBelle01.setVisible(true);
			System.out.println("Belle " + sourceName);
		} else {
			System.out.println("else");
		}
		
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
					public void mouseClicked (MouseEvent e){		
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
	private JPanel makeCDFPanel01(){
		JPanel panel = new JPanel();		
		panel.setLayout(null);
		panel.setName("panelCDF01");
		
		/** 
		 * display CE03 activity
		 */
		CAF_CE03Mon displayCE03 = new CAF_CE03Mon();
		displayCE03.setBounds(12, 12, 445, 330);
		panel.add(displayCE03);
		
		/**
		 *  display ce03 queue jobs		  
		 */	
		JPanel panelQueueStatus = new CAF_QueueStatusMon();
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
		JPanel wnStatus = new CAF_serverMon();
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
		JPanel CDFJobTable = new CAF_JobMon();
		CDFJobTable.setBounds(12, 468, 569, 206);
		panel.add(CDFJobTable);
		
		/**
		 * display number of total jobs 
		 */
		CAF_JobGraph jobSubmitionGraph = new CAF_JobGraph();
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
	private JPanel makeCDFPanel02(){
		JPanel panel = new JPanel();
		panel.setBackground(Color.gray);
		panel.setLayout(null);
		panel.setName("panelCDF02");

		/**
		 * display SAM mon
		 */		
		JPanel displayCpuMon = new SAM_InfoMon();
		//displayCpuMon.setBackground(Color.red);
		displayCpuMon.setBounds(12, 12, 500, 400);
		panel.add(displayCpuMon);		
		
		
		/**
		 *dispaly SAM Dstat_header 
		 */
//		JPanel sam_dstat_header = new SAM_Dstat_head();
//		sam_dstat_header.setBounds(550,50,500,50);		
//		panel.add(sam_dstat_header);
		/**
		 * display SAM Dstat
		 */		
		
		JPanel sam_dstat = new SAM_DstatMon();
//		sam_dstat.setBackground(Color.RED);
		sam_dstat.setBounds(550,50,500,350);		
		panel.add(sam_dstat);
		
		/**
		 * display Seperator panel
		 */
		JPanel panelSeperator = new JPanel();
		panelSeperator.setBackground(new Color(153, 153, 153));
		panelSeperator.setBounds(0, 426, 1095, 10);
		panel.add(panelSeperator);
		
		
		/**
		 * display SAM cache
		 */		
		JPanel samdisk = new SAM_CacheMon();
//		samdisk.setBackground(Color.RED);
		samdisk.setBounds(12, 460 ,569+300, 206);

		panel.add(samdisk);
		
		
		return panel;		
	}
	
	/**
	 * 
	 * Desc : make Belle01 panel
	 * @Method Name : MakeAlice01Panel
	 * @return panel
	 *
	 */
	private JPanel makeBellePanel01(){
		JPanel panel = new JPanel();		
		panel.setLayout(null);
		panel.setName("panelBelle01");
		
		
		JLabel l = new JLabel("Belle");
		l.setBounds(12, 12, 445, 330);		
		panel.add(l);
		return panel;
	}
	
	/**
	 * 
	 * Desc : make alice01 panel
	 * @Method Name : MakeAlice01Panel
	 * @return panel
	 *
	 */
	private JPanel makeAlicePanel01(){
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
		m.addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent e){System.exit(0);}
					public void windowDeiconified(WindowEvent e){ System.out.println("windowDeiconified");}
					  public void windowIconified(WindowEvent e){ System.out.println("windowIconified"); }
				}
		);
	}
}
