
package test_code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kisti.gui.CDF.DisplayCE03;



/**
 * @author grkim
 *
 */
/**
 * test_code
 * CDF_Main_Panel.java
 *
 *
 *
 * @Author :grkim
 * @Date   :2011. 6. 17.
 * @Version:
 *
 */
public class CDF_Main_Panel extends JFrame{

	//layout °ü¸®ÀÚ
	private GridLayout gridLayout;

	
	//main container
	private Container container;
	private Container containerForContents;
	
	//main component
	public static JCheckBox cbPBS,cbWN,cbOSG,cbMem,cbCPU;
	JPanel chPanel, contentsPanel;
	
	//for each item
	JFrame fPBS ;
	JPanel pMem ;
	
	
	
	public CDF_Main_Panel() {
		// TODO Auto-generated constructor stub
		super("JCeckBox Test");
		
		this.setBackground(Color.red);
		// layout
		gridLayout		= new GridLayout(5,1,10,10);
		
		chPanel 		= new JPanel();
		contentsPanel 	= new JPanel();
		
		//Returns the contentPane object for this frame. 
		container = getContentPane();
		container.setLayout(gridLayout); //setting layout to container
		//containerForCB.setLayout(new BorderLayout());
		
		
		//create components
		cbPBS = new JCheckBox("PBS_CE03");
		cbWN  = new JCheckBox("WN");
		cbOSG = new JCheckBox("OSG");
		cbMem = new JCheckBox("Memory");
		cbCPU = new JCheckBox("CPU");
		
		
		// make the user class to handle the checkbox
		CheckBoxHandler cbHandler = new CheckBoxHandler();
		cbPBS.addItemListener(cbHandler);
		cbWN.addItemListener(cbHandler); 
		cbOSG.addItemListener(cbHandler);
		cbMem.addItemListener(cbHandler);
		cbCPU.addItemListener(cbHandler);
		
		
		//change background color
		cbPBS.setBackground(Color.BLUE);		
		cbWN.setBackground(Color.green);		
		cbOSG.setBackground(Color.cyan);
		cbMem.setBackground(Color.gray);
		cbCPU.setBackground(Color.white);
		
		
		// add components to container		
		chPanel.add(cbPBS);
		chPanel.add(cbWN);
		chPanel.add(cbOSG);		
		chPanel.add(cbMem);		
		chPanel.add(cbCPU);
		container.add(chPanel);
		
		
		contentsPanel.add(new JLabel("test----"));
		container.add(contentsPanel);
		
		
		fPBS = new DisplayCE03();
		pMem = new MemoryMonitor_org();

	}
	
	
	
	/**
	 * 
	 * TODO
	 * @param a
	 * @param b
	 * @return
	 *
	 */
	public int multiply( int a, int b ){
		return a* b;
	}
	
	
	
	private class CheckBoxHandler implements ItemListener{
		public static final int nPBS = 1;
		public static final int nWN = 2;
		public static final int nOSG = 3;
		public static final int nMem = 4;
		public static final int nCPU = 5;
		
		
		public void itemStateChanged(ItemEvent e)
		{
			if ( e.getSource() == cbPBS ){
				if(e.getStateChange() == ItemEvent.SELECTED){
System.out.println("PBS Checked.");
					//container.add(new JLabel("cb PBS"));
					container.add(fPBS);
				}else{
System.out.println("PBS unChecked");
					//container.remove(comp)
				}
			}else if (e.getSource() == cbWN) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					container.add(pMem);
System.out.println("WN Checked");
				}else{
System.out.println("WN Unchecked.");
				}
			}else if (e.getSource() == cbOSG) {
				if(e.getStateChange() == ItemEvent.SELECTED){
System.out.println("OSG Checked.");
				}else{
System.out.println("OSG Unchecked.");
				}
			}else if (e.getSource() == cbMem) {
				if(e.getStateChange() == ItemEvent.SELECTED){
System.out.println("Mem Checked.");
				}else{
System.out.println("Mem Unchecked.");
				}
			}else if (e.getSource() == cbCPU) {
				if(e.getStateChange() == ItemEvent.SELECTED){
System.out.println("CPU Checked.");
				}else{
System.out.println("CPU Unchecked.");
				}
			}
System.out.println("repaint");
			container.validate();
			repaint();		

		}		
	}
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CDF_Main_Panel t = new CDF_Main_Panel();
		
		
		t.setSize(400,350);
		t.setVisible(true);
		
		t.addWindowListener(
				new WindowAdapter(){
					public void WindowClosing(WindowEvent e){System.exit(0);}
				}
				);
	}
}
