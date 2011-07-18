package kisti.gui.CDF;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;




/**
 * <pre>
 * kisti.gui.CDF
 *   |_ WorkerNodeStatus.java
 *
 * </pre>
 *
 * Desc : Display status of worknodes with table
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 7. 12. 오후 1:56:58
 * @Version:
 *
 */
public class WorkerNodeStatus extends JPanel{

	private JTable table;
	private Container container;

	
	/**
	 * 
	 * Desc : Constructor of WorkerNodeStatus.java class
	 */
	public WorkerNodeStatus() {
		// TODO Auto-generated constructor stub
		String header[] = {"Name","Power","running","Server","Etc"};
		String cells[][] ={
				{"WN1017","ON","Running","Blade",".."},				
				{"WN1018","ON","Running","Blade",".."},
				{"WN1019","ON","Null","Blade",".."},
				{"WN1020","ON","Running","Blade",".."},
				{"WN2001","OFF","Null","1U Server",".."},
				{"WN2002","ON","Running","1U Server",".."},
				{"WN2003","ON","Running","1U Server",".."},
				{"WN2004","OFF","Null","1U Server",".."}
		};
		
		
		table = new JTable(cells,header);

		container = new Container();
		this.setBorder(new TitledBorder(new EtchedBorder(),"WN Status"));
		this.add(new JScrollPane(table));//JScrollPanel을 사용하지 않으면 헤더가 보이지 않는다!!

	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame();
		WorkerNodeStatus w = new WorkerNodeStatus();
		f.add(w);
		f.setSize(400,400);
		f.setVisible(true);
		
	}
}
