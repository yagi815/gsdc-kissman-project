package test_code;
import java.awt.*;

/**
 * test_code
 * MenuTest.java
 *
 *
 *
 * @Author :grkim
 * @Date   :2011. 6. 29.
 * @Version:
 *
 */
public class MenuTest extends Frame {

	// 메뉴를 구성하는 생성자
	
	public MenuTest() {

		super("메뉴 테스트 프로그램");
		// 메뉴바 객체 생성
		MenuBar mb = new MenuBar();

		// 파일 메뉴 만들기
		Menu fileMenu = new Menu("파일");
		// 파일 메뉴에 아이템 붙이기
		fileMenu.add("새 파일");
		fileMenu.add("열기");
		fileMenu.addSeparator();
		fileMenu.add("닫기");
		fileMenu.add("저장");
		fileMenu.add("새 이름으로");
		fileMenu.addSeparator();
		fileMenu.add("출력");
		fileMenu.addSeparator();
		fileMenu.add("끝");

		// 편집 메뉴 만들기
		Menu editMenu = new Menu("편집");
		// 편집 메뉴에 아이템 붙이기
		editMenu.add("취소");
		editMenu.addSeparator();
		editMenu.add("잘라내기");
		editMenu.add("복사하기");
		editMenu.add("붙이기");

		// 메뉴를 메뉴바에 추가
		mb.add(fileMenu);
		mb.add(editMenu);

		// 메뉴바를 Frame에 추가.
		setMenuBar(mb);

	}

	public static void main(String args[]) {

		MenuTest mt = new MenuTest();
		mt.setSize(300, 300);
		mt.setVisible(true);

	}
}