package test_code;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
/**
 *
 * @author grkim
 */
public class frameTest extends Frame{

    public frameTest() {
        

// ���� �����Ѵ�.
Label label1 = new Label("ù��°");
Label label2 = new Label("�ι�°");
Label label3 = new Label("����°");

// �󺧿� ������ �����Ѵ�.
label1.setBackground(Color.red);
label2.setBackground(Color.yellow);
label3.setBackground(Color.green);

// ���� ���δ�.
this.add(label1);
this.add(label2);
this.add(label3);

// �ݵ�� ũ������� ȭ������� ������Ѵ�.
// ������ ���� ������ ����� ȭ���� ������ �ʴ´�.
this.setSize(300,300);
this.show();

    
    }

    public  static void main(String[] args){
        frameTest f = new frameTest();
        
    }
}
