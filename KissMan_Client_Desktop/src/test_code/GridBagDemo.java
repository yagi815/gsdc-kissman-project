package test_code;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * <pre>
 * test_code
 *   |_ GridBagDemo.java
 *
 * </pre>
 *
 * Desc :
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 6. 30. ���� 2:41:04
 * @Version:
 *
 */
public class GridBagDemo extends JFrame{

	
	private Container container;
	private GridBagLayout gbLayout;
	private GridBagConstraints gbConstraints;
	
	
	/**
	 * 
	 * Desc : Constructor of GridBagDemo.java class
	 */
	public GridBagDemo() {
		// TODO Auto-generated constructor stub
		container = getContentPane();
		gbLayout = new GridBagLayout();
		container.setLayout(gbLayout);
		
		//gridbag ���� ����
		gbConstraints = new GridBagConstraints();
		
		JTextArea ta = new JTextArea("TextArea1",5,10);
		JTextArea tx = new JTextArea("TextArea2",2,2);
		String names[] = {"Iron","Steel","Brass"};
		JComboBox cb = new JComboBox(names);
		JTextField tf = new JTextField("TextField");
		JButton b1 = new JButton("Button 1");
		JButton b2 = new JButton("Button 2");
		JButton b3 = new JButton("Button 3");
		
		//�ؽ�Ʈ ����
		//weightx, weighty=0
		//anchor �� ��� components���� CENTER: the default
		gbConstraints.fill = GridBagConstraints.BOTH;
		addComponent(ta, 0,0,1,3);
		
		//b1��ư
		//weightx,weithgy�Ѵ� 0 : ����Ʈ
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		addComponent(b1,0,1,2,1);
		
		//combo box
		//wightx, weighty �Ѵ� 0: ����Ʈ
		//fill�� HORIZONTAL
		addComponent(cb,2,1,2,1);
		
		//button b2
		gbConstraints.weightx = 1000;
		gbConstraints.weighty=1;
		gbConstraints.fill = GridBagConstraints.BOTH;
		addComponent(b2,1,1,1,1);
		
		//button b3
		//fill��BOTH
		gbConstraints.weightx = 0;
		gbConstraints.weighty = 0;
		addComponent(b3,1,2,1,1);
		
		//textfield
		//wightsx,weighty �Ѵ� 0: fill�� BOTH
		addComponent(tf,3,0,2,1);
		
		//textarea
		//weightx,weighty �Ѵ� 0 : fill�� BOTH
		
		setSize (300, 150);
		show();	
	}
	
	
	
	/**
	 * 
	 * Desc :
	 * @Method Name : addComponent
	 * @param c
	 * @param row
	 * @param column
	 * @param width
	 * @param height
	 *
	 */
	private void addComponent (Component c, int row, int column, int width, int height){
		//gridx, gridy ����
		gbConstraints.gridx = column;
		gbConstraints.gridy = row;
		
		//gridwidth, gridheight  ����
		gbConstraints.gridwidth = width;
		gbConstraints.gridheight = height;
		
		//���� ���� ����
		gbLayout.setConstraints(c, gbConstraints);
		container.add(c);	//add component
	}
	
	
	
	/**
	 * 
	 * Desc :
	 * @Method Name : main
	 * @param args
	 *
	 */
	public static void main(String[] args){
		GridBagDemo app = new GridBagDemo();
		app.addWindowListener(
				new WindowAdapter() {
					public void aaaaaaaa(WindowEvent e){System.exit(0);}
				}
		);
		
	}
}
