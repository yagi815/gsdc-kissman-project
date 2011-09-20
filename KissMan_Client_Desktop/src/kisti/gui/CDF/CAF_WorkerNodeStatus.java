package kisti.gui.CDF;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import kisti.server.connectToServer;




/**
 * <pre>
 * kisti.gui.CDF
 *   |_ WorkerNodeStatus.java
 *
 * </pre>
 *
 * Desc : Display status of workernodes 
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 7. 12. 오후 1:56:58
 * @Version:
 *
 */
public class CAF_WorkerNodeStatus extends JPanel{

	
	
    private JTable table;
    private Object obj;
    
    /**
     * 
     * Desc : Constructor of WorkerNodeStatus.java class
     */
    public CAF_WorkerNodeStatus() {
    	
    	
    	connectToServer sc = new connectToServer();
    	obj = sc.requestDataToServer("WorkerNodeStatus");
    	
    	
            // TODO Auto-generated constructor stub
            String header[] = {"Name","state","np","properties","status"};
            String cells[][] = (String[][])obj;

            table = new JTable(cells,header);            
            table.setBackground(new Color(255, 255, 204));

            
            
            /**
             * 셀 간격 조정             * 
             */
    		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
    		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
    		DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
    		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);
            
            
            /**
             * 각 셀의 넓이와 정렬
             */
            table.getColumn("Name").setPreferredWidth(7);
            table.getColumn("Name").setCellRenderer(celAlignCenter);
            table.getColumn("state").setPreferredWidth(5);
            table.getColumn("state").setCellRenderer(celAlignCenter);
            table.getColumn("np").setPreferredWidth(5);
            table.getColumn("np").setCellRenderer(celAlignCenter);
            table.getColumn("properties").setPreferredWidth(10);
            table.getColumn("properties").setCellRenderer(celAlignCenter);
//            table.getColumn("ntype").setPreferredWidth(10);
//            table.getColumn("ntype").setCellRenderer(celAlignCenter);
//            table.getColumn("Name").setPreferredWidth(10);
//            table.getColumn("Name").setCellRenderer(celAlignCenter);
            
            
            
            this.setBackground(Color.WHITE);
            this.setLayout(null);
            JScrollPane jspWN = new JScrollPane(table);
            jspWN.setBounds(0, 0, 599,353);
            this.add(jspWN);//JScrollPanel을 사용하지 않으면 헤더가 보이지 않는다!!
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
            // TODO Auto-generated method stub
            JFrame f = new JFrame();
            CAF_WorkerNodeStatus w = new CAF_WorkerNodeStatus();
            f.add(w);
            f.setSize(650,400);
            f.setVisible(true);
            
    }
}
