package kisti.module.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class KissManDatabase {

	private static final String ipAddressForDatabase  = "150.183.234.168";
	private static final String dbName = "kissman";
	private static final String dbUser= "kissman";
	private static final String dbPass= "kissman";
	private static final String url= "jdbc:mysql://"+ ipAddressForDatabase +":3306/kissman";
	Connection conn ;
	Statement stmt;
	private String selectQuery;
	private Vector vector;
	private String resultTuple;
	
		
	/**
	 * 
	 * Desc :
	 * @Method Name : ConnectToKissManDB
	 *
	 */	
	public void KissManDatabase() {
//		ipAddressForDatabase = "150.183.234.168";
//		dbName = "kissman";
//		dbUser = "kissman";
//		dbPass = "kissman";
//		url = "jdbc:mysql://"+ ipAddressForDatabase +":3306/kissman";
		//jdbc:mysql://150.183.234.168:3306/kissman
	}
	
	public  Statement OpenDatabase(){
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection( url, dbUser, dbPass);
			stmt = conn.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("[OpenDatabase()] "+e.toString());
			e.printStackTrace();
		}	
		return stmt;
	}
	
	public void CloseDatabase(){
		try {
			conn.close();
			stmt.close();			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	
	

	public Object requestDataFromDataBase(String serviceName){
		//요청한 서비스 선택하여 해당 펑션 호출
		Object obj = null;
		
		if (serviceName.equals("jobsubmition")) 
		{
			obj = retriveJobsumitionData();
		} 
		else if (serviceName.equals(""))
		{
			
		}else {
			System.out.println("잘못된 요청");
		}
		return obj ;
	}
	
	
	public Object retriveJobsumitionData(){
		Object obj = null;
		Statement stmt = OpenDatabase();	
		String selectQuery = "select * from cdf_job";
		
		try {
			ResultSet rs = stmt.executeQuery(selectQuery);
			//vector = new Vector();
			
			int i=0;
			int month[] = new int[12];
			while (rs.next()) {
				month[rs.getInt(1)-1] += rs.getInt(5);				
			}
			
			
//			for (int j = 0; j < month.length; j++) {
//				System.out.println(month[i]);
//			}
			obj = month;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("[retriveJobsumitionData()]"+e.toString());
		}	finally{
			CloseDatabase();
		}
		return obj;
	}
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KissManDatabase c = new KissManDatabase();
		c.requestDataFromDataBase("jobsubmition");
	}
}




