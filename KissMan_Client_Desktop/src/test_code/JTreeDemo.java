package test_code;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
public class JTreeDemo extends JFrame implements MouseListener{
    protected JTree tree;
    public JTreeDemo(){
        super("JTree Demo");
       
        DefaultMutableTreeNode jcomponent=
            new DefaultMutableTreeNode("JComponent");
        DefaultMutableTreeNode jpanel=
            new DefaultMutableTreeNode("JPanel");
        DefaultMutableTreeNode jtextcomponent=
            new DefaultMutableTreeNode("JTextComponent");
        DefaultMutableTreeNode jtree=
            new DefaultMutableTreeNode("JTree");
       
        jcomponent.add(jpanel);
        jcomponent.add(jtextcomponent);
        jcomponent.add(jtree);
       
        DefaultMutableTreeNode jtextarea=
            new DefaultMutableTreeNode("JTextArea");
        DefaultMutableTreeNode jtextfield=
            new DefaultMutableTreeNode("JTextField");
       
        jtextcomponent.add(jtextarea);
        jtextcomponent.add(jtextfield);
       
        tree = new JTree(jcomponent);
        tree.addMouseListener(this);
        JScrollPane sp=new JScrollPane(tree);
        getContentPane().add(sp,BorderLayout.CENTER);
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,200);
        setVisible(true);
       
    }
    /* MouseListener Interface 구현을 위한 부분 */
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mousePressed(MouseEvent e){
        TreePath path = tree.getPathForLocation(e.getX(),e.getY());
        if(path ==null)
            return ;
       
        Object o = path.getLastPathComponent();
        if(o instanceof DefaultMutableTreeNode){
            System.out.println(((DefaultMutableTreeNode) o).getUserObject());
        }
    }
    public static void main(String[] args) {
        JTreeDemo td = new JTreeDemo();
    }
}