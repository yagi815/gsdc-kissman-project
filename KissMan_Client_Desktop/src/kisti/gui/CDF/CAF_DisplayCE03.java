package kisti.gui.CDF;


import java.awt.Container;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;






/**
 * <pre>
 * kisti.gui.CDF
 *   |_ DisplayCE03.java
 *
 * </pre>
 *
 * Desc :
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 6. 29. AM 11:30:49
 * @Version:
 *
 */
public class CAF_DisplayCE03 extends JPanel {
	// private JTextField enter;
	private JEditorPane editorPane;
	private String url;
	private Container c;
	
	private CpuMemoryMonitor cpuMemMonitor;

	/**
	 * 
	 */
	public CAF_DisplayCE03() {
		url = "http://vobox02.sdfarm.kr/pbswebmon/pbswebmon-ce03/cgi-bin/graph.py?start=-24h";
//		url = "http://134.75.123.21/nagios/cgi-bin/trends.cgi?host=ce03&timeperiod=last7days&smon=9&sday=1&syear=2011&shour=0&smin=0&ssec=0&emon=9&eday=15&eyear=2011&ehour=24&emin=0&esec=0&assumeinitialstates=yes&assumestateretention=yes&assumestatesduringnotrunning=yes&includesoftstates=no&initialassumedhoststate=0&backtrack=4";
		
		editorPane = new JEditorPane();
		editorPane.setEditable(false);	
		
		
		
		try {
			editorPane.setPage(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
		//this.setBorder(new TitledBorder(new EtchedBorder(),"CE03"));
		this.add(editorPane);
		this.setSize(550,400);				
	}
	

	public static void main(String args[]) {
		CAF_DisplayCE03 app = new CAF_DisplayCE03();
		JFrame f = new JFrame();
		f.add(app);
		f.setSize(800,700 );
		f.setVisible(true);
	}
}
