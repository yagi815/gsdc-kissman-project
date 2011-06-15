package test_code;
// JeditorPane 을 이용하여 웹 서버 내용을
// 긁어다가 표시 

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;



public class DisplayWebpage extends JFrame{
	private JTextField enter;
	private JEditorPane contents;
	
	
	public DisplayWebpage()
	{
		super("Simple Web Browser");
		Container c = getContentPane();
		
		// http://vobox02.sdfarm.kr/pbswebmon/pbswebmon-ce03/cgi-bin/graph.py?start=-24h <- ce03 24시간 job 상태 
		// URL을 입력 받기 위해 JTextField 생성.
		enter = new JTextField("Enter file URL here");
		
		// URL 입력란에 이벤트 등록하여 url을 넣고 엔터 쳤을경우 getThePage 함수가 실행 되도록 함.
		enter.addActionListener(
					new ActionListener(){						
						public void actionPerformed(ActionEvent e){ getThePage(e.getActionCommand()); }
					}
		);
		
		// URL 입력란을 컨테이너의 상단 가운데 위치.
		c.add(enter, BorderLayout.NORTH	);
		
		// URL 의 내용을 뿌려줄 JEditor 패널 생성
		contents = new JEditorPane();
		
		// edit 금지
		contents.setEditable(false);

		// url 내용이 업데이트때 만다 불리는 HyperlinkListener 리스너 등록 
		contents.addHyperlinkListener(
					new HyperlinkListener(){
						public void hyperlinkUpdate(HyperlinkEvent e)
						{ 
								if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
									getThePage(e.getURL().toString());
						}
					}
		);		
		//  URL의 내용을 뿌려줄 에디터 패널에 스크롤을 추가
		c.add(new JScrollPane(contents), BorderLayout.CENTER);
		
		//프레임 사이즈 
		setSize(400,300);
		show();
		
	}																																				
	
	// URL 을 인자로 받아서 웹의 내용을 가져온다. 
	private void getThePage(String location){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //이전에 정의된 커서를 가져와 현재 커서로 세팅 
			
			try {				
				contents.setPage(location);//현재 URL의 내용을 diplay하기 시작 한다.				
				enter.setText(location); //입력 받은 URL 을 테스트 페널에 세팅				
//			}catch (IOException io) {
//				// TODO: handle exception
//				JOptionPane.showMessageDialog(this, "Error retrieving specified URL", "Bad URL", JOptionPane.ERROR_MESSAGE);				
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error retrieving specified URL", "Bad URL", JOptionPane.ERROR_MESSAGE);
			}
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));//
	}
	
	
	public static void main(String args[])
	{
		DisplayWebpage app = new DisplayWebpage();
		
		app.addWindowListener(
				new WindowAdapter() {
					//윈도가 종료 될때 동작,,, 창을 닫는다.. 
					public void windowClosing(WindowEvent e){System.exit(0);}
				}
		);
	}
}
