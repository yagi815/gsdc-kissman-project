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





public class test extends JFrame{
	
	private Container container;
	private GridBagLayout gbLayout;
	private GridBagConstraints gbConstraints;
	
	
	public test() {
		// TODO Auto-generated constructor stub
		super("test");
		
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
		//anchor �� ��� components ���� CENTER: the default
		gbConstraints.fill = GridBagConstraints.BOTH;
		addComponent(ta,0,0,1,3);
		
		
		//combo box
		//weightx, weighty �Ѵ� 0 : ����Ʈ 
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		addComponent(b1,0,1,2,1);
		
		//combo box
		//wightx, wighty �Ѵ� 0: ����Ʈ
		//fill�� HORIZONTAL
		addComponent(cb,2,1,2,1);
		
		//button b2
		gbConstraints.weightx = 1000; //can grow wider
		gbConstraints.weighty = 1;	//can grow taller
		gbConstraints.fill = GridBagConstraints.BOTH;
		addComponent(b3,1,2,1,1);
		
		//textfield
		//wightx, weighty �Ѵ� 0: fill�� BOTH
		addComponent(tf,3,0,2,1);
		
		
		setSize(300,150);
		show();		
	}
	
	// addComponent 
	private void addComponent( Component c, int row, int column, int width, int height){
		//gridx,gridy setting
		gbConstraints.gridx = column;
		gbConstraints.gridy = row;
		
		//gridwidth, gridheight setting
		gbConstraints.gridwidth = width;
		gbConstraints.gridheight = height;
		
		//limitation
		gbLayout.setConstraints(c, gbConstraints);
		container.add(c); //add component
	}
	
	
	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		test t = new test();
		t.addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent e){System.exit(0);}
				}
			);

	}

}
