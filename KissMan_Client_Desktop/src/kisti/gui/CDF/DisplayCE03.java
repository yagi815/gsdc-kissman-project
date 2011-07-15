package kisti.gui.CDF;

// JeditorPane 을 이용하여 웹 서버 내용을
// 긁어다가 표시 

import java.awt.Container;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;






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
 * @Date   :2011. 6. 29. 오후 11:30:49
 * @Version:
 *
 */
public class DisplayCE03 extends JPanel {
	// private JTextField enter;
	private JEditorPane editorPane;
	private String url;
	private Container c;
	
	private CpuMemoryMonitor cpuMemMonitor;

	/**
	 * 
	 */
	public DisplayCE03() {
		url = "http://vobox02.sdfarm.kr/pbswebmon/pbswebmon-ce03/cgi-bin/graph.py?start=-24h";
		
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
		//DisplayCE03 app = new DisplayCE03();
		// app.setVisible(true);
	}
}
