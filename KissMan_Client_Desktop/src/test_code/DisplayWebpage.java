package test_code;
// JeditorPane �� �̿��Ͽ� �� ���� ������
// �ܾ�ٰ� ǥ�� 

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
		
		// http://vobox02.sdfarm.kr/pbswebmon/pbswebmon-ce03/cgi-bin/graph.py?start=-24h <- ce03 24�ð� job ���� 
		// URL�� �Է� �ޱ� ���� JTextField ����.
		enter = new JTextField("Enter file URL here");
		
		// URL �Է¶��� �̺�Ʈ ����Ͽ� url�� �ְ� ���� ������� getThePage �Լ��� ���� �ǵ��� ��.
		enter.addActionListener(
					new ActionListener(){						
						public void actionPerformed(ActionEvent e){ getThePage(e.getActionCommand()); }
					}
		);
		
		// URL �Է¶��� �����̳��� ��� ��� ��ġ.
		c.add(enter, BorderLayout.NORTH	);
		
		// URL �� ������ �ѷ��� JEditor �г� ����
		contents = new JEditorPane();
		
		// edit ����
		contents.setEditable(false);

		// url ������ ������Ʈ�� ���� �Ҹ��� HyperlinkListener ������ ��� 
		contents.addHyperlinkListener(
					new HyperlinkListener(){
						public void hyperlinkUpdate(HyperlinkEvent e)
						{ 
								if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
									getThePage(e.getURL().toString());
						}
					}
		);		
		//  URL�� ������ �ѷ��� ������ �гο� ��ũ���� �߰�
		c.add(new JScrollPane(contents), BorderLayout.CENTER);
		
		//������ ������ 
		setSize(400,300);
		show();
		
	}																																				
	
	// URL �� ���ڷ� �޾Ƽ� ���� ������ �����´�. 
	private void getThePage(String location){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //������ ���ǵ� Ŀ���� ������ ���� Ŀ���� ���� 
			
			try {				
				contents.setPage(location);//���� URL�� ������ diplay�ϱ� ���� �Ѵ�.				
				enter.setText(location); //�Է� ���� URL �� �׽�Ʈ ��ο� ����				
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
					//������ ���� �ɶ� ����,,, â�� �ݴ´�.. 
					public void windowClosing(WindowEvent e){System.exit(0);}
				}
		);
	}
}
