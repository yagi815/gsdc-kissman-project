import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import kisti.gui.CDF.DisplayCE03;
import kisti.gui.CDF.JobSubmitionGraph;
import java.awt.Panel;
import kisti.gui.CDF.CpuMonitor;
import kisti.gui.CDF.CpuMemoryMonitor;
import javax.swing.JProgressBar;
import com.jgoodies.forms.factories.DefaultComponentFactory;


/**
 * <pre>
 * 
 *   |_ GUI_Main.java
 *
 * </pre>
 *
 * Desc : GUI main code 
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 7. 13. 오전 10:13:29
 * @Version:
 *
 */
public class GUI_Main extends JFrame {

	
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
	
	private static int KISSMAN_WIDTH = 1366	;
	private static int KISSMAN_HEIGHT = 768;
	private final JPanel panelTree = new JPanel();
	private final JPanel panelCDF01 = new JPanel();
	
	
	private  JTree treeMain = null;
	private  DefaultMutableTreeNode treeTop = new DefaultMutableTreeNode("Kisti System Monitoring");
	private  DefaultMutableTreeNode treeCDF = new DefaultMutableTreeNode("CDF");
	private  DefaultMutableTreeNode treeBelle= new DefaultMutableTreeNode("Belle");
	private  DefaultMutableTreeNode treeAlice = new DefaultMutableTreeNode("Alice");
	private JScrollPane jsp = null;
	private final Panel panelKistiCI = new Panel();
	private final CpuMemoryMonitor cpuMemoryMonitor = new CpuMemoryMonitor();
	
	
	
	/**
	 * 
	 * Desc : Constructor of GUI_Main.java class
	 */
	public GUI_Main() {
		setTitle("KissMan");
		setSize(KISSMAN_WIDTH, KISSMAN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 1491, 772);
		getContentPane().setLayout(null);
		
		JPanel panelBottom = new JPanel();
		panelBottom.setBounds(0, 700, 1350, 30);
		getContentPane().add(panelBottom);
		panelBottom.setBackground(new Color(153, 51, 51));
		
		JLabel labelCopyRight = new JLabel("\t\t\t\t\t\tKisti System Monitoring (KissMan) V 1.0                       Copylight \u24D2 2011 KISTI, Org. All rights reserved.  ");
		labelCopyRight.setHorizontalAlignment(SwingConstants.CENTER);
		panelBottom.add(labelCopyRight);
		panelTree.setBackground(Color.WHITE);
		panelTree.setBounds(12, 12, 219, 553);
		getContentPane().add(panelTree);
		panelTree.setLayout(null);
		
		
		treeTop.add(treeCDF);
		treeTop.add(treeBelle);
		treeTop.add(treeAlice);
		
		
		
		treeMain = new JTree(treeTop);
		treeMain.setBounds(12, 12, 176, 280);
		//jsp = new JScrollPane(treeMain);				
		//panelTree.add(jsp, BorderLayout.CENTER);
		
		panelTree.add(treeMain);
		
		
		panelCDF01.setBackground(Color.WHITE);
		panelCDF01.setBounds(243, 12, 1095, 676);
		getContentPane().add(panelCDF01);
		panelCDF01.setLayout(null);
//		panelCDF01.add(tableCDFWorkernodes.getTableHeader(), BorderLayout.NORTH);
//		panelCDF01.add(tableCDFWorkernodes,BorderLayout.CENTER);
		
		JLabel labelCDFWokerNodes = new JLabel("SAM Station");
		labelCDFWokerNodes.setBounds(484, 36, 105, 18);
		panelCDF01.add(labelCDFWokerNodes);
		cpuMemoryMonitor.monitoring.setBackground(new Color(211, 211, 211));
		cpuMemoryMonitor.setBounds(75, 393, 363, 221);
		panelCDF01.add(cpuMemoryMonitor);
		
		JLabel lblTitleTest = DefaultComponentFactory.getInstance().createTitle("Title test");
		lblTitleTest.setBounds(653, 514, 105, 18);
		panelCDF01.add(lblTitleTest);
		panelKistiCI.setBackground(new Color(255, 255, 255));
		panelKistiCI.setBounds(10, 612, 221, 76);
		
		getContentPane().add(panelKistiCI);
	}
	
	/**
	 * 
	 * Desc :
	 * @Method Name : main
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Main frame = new GUI_Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
