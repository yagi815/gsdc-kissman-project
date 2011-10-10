package testCode;

import java.util.StringTokenizer;




public class stringParsing {
	

	public stringParsing() {
		// TODO Auto-generated constructor stub
	}	
	
	public static String[] tokenizer(String input) {
		String str[] = new String[14];
		StringTokenizer st = new StringTokenizer(input, " ");
											
		for (int i = 0; i < 14; i++) { 
			String temp = st.nextToken();
			if (!temp.equals("|")) {
				str[i] = temp;
			}
		}
		return str;
	}
	
	/**
	 * Desc :
	 * @Method Name : main
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "str:0   0 100   0   0   0|   0     0 |2486B 5424B|   0     0 |1055   350";
		stringParsing p = new stringParsing();
		
		String str[] = new String[14];
		str = p.tokenizer(s);
		
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}
		
	}
}
