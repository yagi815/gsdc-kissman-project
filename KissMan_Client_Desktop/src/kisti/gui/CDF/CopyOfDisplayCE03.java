package kisti.gui.CDF;

// JeditorPane �� �̿��Ͽ� �� ���� ������
// �ܾ�ٰ� ǥ�� 

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class CopyOfDisplayCE03 extends JFrame {
	// private JTextField enter;
	private JEditorPane contents;
	private String url;
	private Container c;

	public CopyOfDisplayCE03 () {
		super("Simple Web Browser");
		 c = getContentPane();
		//c = this.getRootPane();

		url = "http://vobox02.sdfarm.kr/pbswebmon/pbswebmon-ce03/cgi-bin/graph.py?start=-24h"; // /

		// URL �� ������ �ѷ��� JEditor �г� ����
		contents = new JEditorPane();

		// edit ����
		contents.setEditable(false);
		//getThePage(url);
		// url ������ ������Ʈ�� ���� �Ҹ��� HyperlinkListener ������ ��� 


		getThePage(url);
		// URL�� ������ �ѷ��� ������ �гο� ��ũ���� �߰�
		// this.add(new JLabel("Webpage"));
		c.add(new JScrollPane(contents), BorderLayout.CENTER);

		// this.add(c);
		// title border
		//this.setBorder(new TitledBorder(new EtchedBorder(), "CE03"));
		// ������ ������
		setSize(550, 400);
		//setVisible(true);
		// show();
	}

	// URL �� ���ڷ� �޾Ƽ� ���� ������ �����´�.
	private void getThePage(String location) {
		// setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //������ ���ǵ�
		// Ŀ���� ������ ���� Ŀ���� ����
		try {
			contents.setPage(location);// ���� URL�� ������ display�ϱ� ���� �Ѵ�.
			System.out.println("setPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			e.printStackTrace();
		}
		// setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));//
	}

	public static void main(String args[]) {
		//CopyOfDisplayCE03 app = new CopyOfDisplayCE03();
		// app.setVisible(true);
	}
}
