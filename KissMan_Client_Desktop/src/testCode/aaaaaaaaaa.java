package testCode;

import javax.swing.JPanel;

public class aaaaaaaaaa extends JPanel{

	
	public aaaaaaaaaa() {
		// TODO Auto-generated constructor stub


		String str = "0";

		
		
		if(str.contains("B")){
			System.out.println("It has B");
			str = str.replace("B", "");
			System.out.println(str);
		}else if(str.contains("k")){
			System.out.println("it has k");
			str = str.replace("k", "000");
			System.out.println(str);
		}else {
			System.out.println(str);
		}

		
		
	}
	/**
	 * Desc :
	 * @Method Name : main
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		aaaaaaaaaa a = new aaaaaaaaaa();
//		JFrame f = new JFrame
	}
}
