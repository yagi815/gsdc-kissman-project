package test_code;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class JTableTest extends JFrame{
	
	JTable table;
	
	public JTableTest() {
		// TODO Auto-generated constructor stub
		String header[] ={"One","Two","Three"};
		String cells[][] = {
				{"111","222","333"},
				{"444","555","666"},
				{"777","888","999"},
				{"111","222","333"},
				{"444","555","666"},
				{"111","222","333"},
				{"444","555","666"},
				{"111","222","333"},
				{"444","555","666"},
				{"111","222","333"},
				{"444","555","666"},
				{"111","222","333"},
				{"444","555","666"},
				{"777","888","999"}
				
		};
		
		table = new JTable(cells,header);
		getContentPane().add(new JScrollPane(table));
		setSize(300,200);
		setVisible(true);
	}
	
	
	
	public static void main(String argsp[]){
		JTableTest t = new JTableTest();
	}	

	
}
