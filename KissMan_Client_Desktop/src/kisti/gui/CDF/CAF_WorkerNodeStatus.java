package kisti.gui.CDF;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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

//134.75.123.117 : wn1017	
	/* =======================================================================
	 for sv in $(seq -f wn10%02g.sdfarm.kr 17 38) $(seq -f wn20%02g.sdfarm.kr \ 
	 01 12) $(seq -f wn20%02g.sdfarm.kr 25 31);   do echo "### $sv ###";  \
	 ssh $sv 'df -h /tmp' ; done
	 
	 
	 
result : 
	 
### wn2029.sdfarm.kr ###
Filesystem            Size  Used Avail Use% Mounted on
/dev/mapper/rootvg-tmp
                      2.0G  153M  1.7G   9% /tmp
### wn2030.sdfarm.kr ###
Filesystem            Size  Used Avail Use% Mounted on
/dev/mapper/rootvg-tmp
                      2.0G  153M  1.7G   9% /tmp
### wn2031.sdfarm.kr ###
Filesystem            Size  Used Avail Use% Mounted on
/dev/mapper/rootvg-tmp
                      2.0G  153M  1.7G   9% /tmp
	 
	 
	 =======================================================================*/
	
	
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
//            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
            CAF_WorkerNodeStatus w = new CAF_WorkerNodeStatus();
            f.add(w);
            f.setSize(650,400);
            f.setVisible(true);
            
    }
}
