package kisti.gui.CDF;

// JeditorPane �� �̿��Ͽ� �� ���� ������
// �ܾ�ٰ� ǥ�� 

import java.awt.Container;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DisplayCE03 extends JPanel {
	// private JTextField enter;
	private JEditorPane editorPane;
	private String url;
	private Container c;

	
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
		
		this.setBorder(new TitledBorder(new EtchedBorder(),"CE03"));
		this.add(editorPane);
		this.setSize(550,400);				
	}
	
	
	public static void main(String args[]) {
		//DisplayCE03 app = new DisplayCE03();
		// app.setVisible(true);
	}
}
