package test_code;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class layoutTest extends JFrame implements ActionListener {

	/**
	 * 
	 */
	
	private JButton buttons[];
	private final String names[]={"one","two","three","four","five","six"};
	private boolean toggle = true;
	private Container container;
	private GridLayout grid1, grid2;
	
	
	public layoutTest() {
//		// TODO Auto-generated constructor stub

//		//레이아웃 구성
		grid1 = new GridLayout(2,3,5,5);
		grid2 = new GridLayout(1,5);
		
		//get content panel and set the layout
		container = getContentPane();
		
		buttons = new JButton[5];
		
		
		container.setLayout(grid1);
		
		//버튼을 생성하여 추가한다.
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(names[i]);
			buttons[i].addActionListener(this);
			container.add(buttons[i]);
		}
//		
		setSize(300,150);
		setVisible(true);
//		
		
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (toggle) {
			container.setLayout(grid2);
		} else {
			container.setLayout(grid1);
		}
		toggle = !toggle;
		container.validate();
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			layoutTest app = new layoutTest();
			app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	

}
