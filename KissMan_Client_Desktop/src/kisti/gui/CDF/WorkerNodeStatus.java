package kisti.gui.CDF;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import kisti.module.database.KissManDatabase;




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
public class WorkerNodeStatus extends JPanel{

    private JTable table;
    
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
            table.setBackground(new Color(255, 255, 204));

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
            WorkerNodeStatus w = new WorkerNodeStatus();
            f.add(w);
            f.setSize(650,400);
            f.setVisible(true);
            
    }
}
