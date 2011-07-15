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

	// �޴��� �����ϴ� ������
	
	public MenuTest() {

		super("�޴� �׽�Ʈ ���α׷�");
		// �޴��� ��ü ����
		MenuBar mb = new MenuBar();

		// ���� �޴� �����
		Menu fileMenu = new Menu("����");
		// ���� �޴��� ������ ���̱�
		fileMenu.add("�� ����");
		fileMenu.add("����");
		fileMenu.addSeparator();
		fileMenu.add("�ݱ�");
		fileMenu.add("����");
		fileMenu.add("�� �̸�����");
		fileMenu.addSeparator();
		fileMenu.add("���");
		fileMenu.addSeparator();
		fileMenu.add("��");

		// ���� �޴� �����
		Menu editMenu = new Menu("����");
		// ���� �޴��� ������ ���̱�
		editMenu.add("���");
		editMenu.addSeparator();
		editMenu.add("�߶󳻱�");
		editMenu.add("�����ϱ�");
		editMenu.add("���̱�");

		// �޴��� �޴��ٿ� �߰�
		mb.add(fileMenu);
		mb.add(editMenu);

		// �޴��ٸ� Frame�� �߰�.
		setMenuBar(mb);

	}

	public static void main(String args[]) {

		MenuTest mt = new MenuTest();
		mt.setSize(300, 300);
		mt.setVisible(true);

	}
}