import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import kisti.gui.CDF.DisplayCE03;
import javax.swing.JTextArea;
import kisti.gui.CDF.JobSubmitionGraph;
import java.awt.Panel;


public class GUI_Main extends JFrame {

	private static int KISSMAN_WIDTH = 1680;
	private static int KISSMAN_HEIGHT = 1050;
	private final JPanel panel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JTree tree = new JTree();
	private final JTable table = new JTable();
	private final JTextArea txtrQueueMaxTot = new JTextArea();
	private final JobSubmitionGraph jobSubmitionGraph = new JobSubmitionGraph();
	private final JTable table_1 = new JTable();
	private final Panel panel_3 = new Panel();
	
	/**
	 * Launch the application.
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

	/**
	 * Create the frame.
	 */
	public GUI_Main() {
		setTitle("KissMan");
		setSize(KISSMAN_WIDTH, KISSMAN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 1491, 772);
		getContentPane().setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(12, 12, 277, 958);
		getContentPane().add(panel);
		panel.setLayout(null);
		tree.setBounds(12, 12, 255, 666);
		tree.addSelectionRow(1);
		tree.expandRow(1);
		tree.setDragEnabled(false);
		tree.setToggleClickCount(2);
		
		panel.add(tree);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(301, 12, 1351, 958);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		table.setBackground(new Color(255, 255, 204));
		table.setBounds(new Rectangle(502, 644, 377, 302));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
			}
		));
	
		//panel_1.add(new JScrollPane(table));
		panel_1.add(table);
		
		DisplayCE03 displayCE03 = new DisplayCE03();
		displayCE03.setBounds(860, 63, 445, 330);
		panel_1.add(displayCE03);
		txtrQueueMaxTot.setText("Queue              Max   Tot   Ena   Str   Que   Run   Hld   Wat   Trn   Ext T \n"
						+ "----------------   ---   ---   ---   ---   ---   ---   ---   ---   ---   --- - \n"
						+ "belle                0     0   yes   yes     0     0     0     0     0     0 E \n"
						+ "osgcdf               0   235   yes   yes     0   234     0     0     0     1 E \n");
		
		txtrQueueMaxTot.setBackground(new Color(255, 204, 204));
		txtrQueueMaxTot.setBounds(860, 423, 445, 117);
		panel_1.add(txtrQueueMaxTot);
		jobSubmitionGraph.setBounds(891, 641, 450, 302);
		panel_1.add(jobSubmitionGraph);
		table_1.setBounds(new Rectangle(12, 22, 801, 490));
		table_1.setBackground(new Color(255, 255, 204));
		table_1.setBounds(12, 63, 801, 490);
		
		panel_1.add(table_1);
		panel_3.setBackground(new Color(153, 0, 0));
		panel_3.setBounds(12, 589, 1329, 10);
		panel_1.add(panel_3);
		
		JLabel lblSamStation = new JLabel("SAM Station");
		lblSamStation.setBounds(22, 605, 82, 18);
		panel_1.add(lblSamStation);
		
		JLabel lblCdfJobs = new JLabel("CDF Jobs");
		lblCdfJobs.setBounds(497, 605, 82, 18);
		panel_1.add(lblCdfJobs);
		
		JLabel lblKistiWorkernodesStatus = new JLabel("Kisti Workernode's Status for CDF");
		lblKistiWorkernodesStatus.setBounds(12, 22, 205, 18);
		panel_1.add(lblKistiWorkernodesStatus);
		
		JTextArea textArea = new JTextArea();
		textArea.setText("----total-cpu-usage---- -dsk/total- -net/total- ---paging-- ---system--\n"
				+"usr sys idl wai hiq siq| read  writ| recv  send|  in   out | int   csw\n"
				  +"0   0 100   0   0   0|  55B   13k|   0     0 |   0     0 | 157   231\n"
				  +"0   0 100   0   0   0|   0     0 | 824B 1568B|   0     0 |1006   290\n"
				  +"0   0  99   1   0   0|   0    96k| 128B  318B|   0     0 |1030   363\n"				
				+"\n");
		textArea.setBackground(new Color(255, 204, 204));
		textArea.setBounds(23, 654, 445, 117);
		panel_1.add(textArea);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(0, 982, 1664, 30);
		getContentPane().add(panel_2);
		
		JLabel lblKissmanKistiAll = new JLabel("Kisti System Monitoring (KissMan) V 1.0                       KISTI all rights reserverd.");
		lblKissmanKistiAll.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblKissmanKistiAll);
	}
}
