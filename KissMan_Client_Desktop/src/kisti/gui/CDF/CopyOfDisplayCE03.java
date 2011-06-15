package kisti.gui.CDF;

// JeditorPane 을 이용하여 웹 서버 내용을
// 긁어다가 표시 

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

		// URL 의 내용을 뿌려줄 JEditor 패널 생성
		contents = new JEditorPane();

		// edit 금지
		contents.setEditable(false);
		//getThePage(url);
		// url 내용이 업데이트때 만다 불리는 HyperlinkListener 리스너 등록 


		getThePage(url);
		// URL의 내용을 뿌려줄 에디터 패널에 스크롤을 추가
		// this.add(new JLabel("Webpage"));
		c.add(new JScrollPane(contents), BorderLayout.CENTER);

		// this.add(c);
		// title border
		//this.setBorder(new TitledBorder(new EtchedBorder(), "CE03"));
		// 프레임 사이즈
		setSize(550, 400);
		//setVisible(true);
		// show();
	}

	// URL 을 인자로 받아서 웹의 내용을 가져온다.
	private void getThePage(String location) {
		// setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //이전에 정의된
		// 커서를 가져와 현재 커서로 세팅
		try {
			contents.setPage(location);// 현재 URL의 내용을 display하기 시작 한다.
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
