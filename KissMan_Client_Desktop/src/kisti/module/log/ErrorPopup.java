package kisti.module.log;

import java.awt.Frame;

import javax.swing.JOptionPane;

public class ErrorPopup extends Frame{

//	private String errorMessage;
	
	public ErrorPopup() {
		// TODO Auto-generated constructor stub
//		JOptionPane.showMessageDialog(this, "test");
	}
	public ErrorPopup(String message) {
		// TODO Auto-generated constructor stub
		JOptionPane.showMessageDialog(this, message);
	}
	/**
	 * Desc :
	 * @Method Name : main
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ErrorPopup f = new ErrorPopup();
	
//		f.setVisi...
//		JOptionPane.showConfirmDialog(f, "Confirm");
//		JOptionPane.showMessageDialog(f, "Message");

	}
}
