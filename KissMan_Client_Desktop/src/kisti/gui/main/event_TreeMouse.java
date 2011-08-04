package kisti.gui.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;

public class event_TreeMouse implements MouseListener{

	private String displayName="CDF_CAF";
	
	
	
	
	public String getDisplayName(){
		return displayName;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		JTree t = (JTree)e.getSource();
		String sourceName = t.getLastSelectedPathComponent().toString();
		System.out.println(sourceName);
		if (sourceName.equalsIgnoreCase("CDF_CAF")) {
			displayName = "CDF_CAF";
		} else if(sourceName.equalsIgnoreCase("CDF_SAM")){
			displayName = "CDF_SAM";
		}else {
			System.out.println("잘못된 입력 입니다. ");
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
