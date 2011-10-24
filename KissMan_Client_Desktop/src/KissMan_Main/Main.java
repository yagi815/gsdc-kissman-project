
package KissMan_Main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kisti.gui.main.*;

/**
 * <pre>
 * KissMan_Main
 *   |_ Main.java
 *
 * </pre>
 *
 * Desc :
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 6. 29. AM 11:37:14
 * @Version:
 *
 */
public class Main {	
	
	/**
	 * 
	 * Desc :
	 * @Method Name : main
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GuiMain m = new GuiMain();		
		m.addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent e){System.exit(0);}
					public void windowDeiconified(WindowEvent e){ System.out.println("windowDeiconified");}
					  public void windowIconified(WindowEvent e){ System.out.println("windowIconified"); }
				}
		);
	
	}
}
