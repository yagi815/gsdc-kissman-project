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
        

// 라벨을 생성한다.
Label label1 = new Label("첫번째");
Label label2 = new Label("두번째");
Label label3 = new Label("세번째");

// 라벨에 배경색을 설정한다.
label1.setBackground(Color.red);
label2.setBackground(Color.yellow);
label3.setBackground(Color.green);

// 라벨을 붙인다.
this.add(label1);
this.add(label2);
this.add(label3);

// 반드시 크기결정과 화면출력을 해줘야한다.
// 에러는 나지 않지만 실행시 화면이 보이지 않는다.
this.setSize(300,300);
this.show();

    
    }

    public  static void main(String[] args){
        frameTest f = new frameTest();
        
    }
}
