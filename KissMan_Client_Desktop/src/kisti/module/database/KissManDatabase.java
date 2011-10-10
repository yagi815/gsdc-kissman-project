package kisti.module.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import kisti.module.log.ErrorPopup;

public class KissManDatabase {

	private static final String ipAddressForDatabase  = "150.183.234.168";
	private static final String dbName = "kissman";
	private static final String dbUser= "kissman";
	private static final String dbPass= "kissman";
	private static final String url= "jdbc:mysql://"+ ipAddressForDatabase +":3306/kissman";
	private Connection conn ;
	private Statement stmt;
	private String selectQuery;
	//private Vector vector;
	private String resultTuple;
	
		
	/**
	 * 
	 * Desc :
	 * @Method Name : ConnectToKissManDB
	 *
	 */	
	public void KissManDatabase() {
		// TODO Auto-generated constructor stub
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
			ErrorPopup popup = new ErrorPopup("Can't open database");
			System.exit(-1);
		}	
		return stmt;
	}
	
	/**
	 * 
	 * Desc : close connection $ statment stream
	 * @Method Name : CloseDatabase
	 *
	 */
	public void CloseDatabase(){
		try {
			conn.close();
			stmt.close();
			System.out.println("-------------- DB connected.---------------");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());		
			}	
	}
	
	/**
	 * 
	 * Desc : retrieve data from database  
	 * @Method Name : requestDataFromDataBase
	 * @param serviceName
	 * @return obj 
	 *
	 */
	public Object requestDataToDataBase(String serviceName){
		//요청한 서비스 선택하여 해당 펑션 호출
		Object obj = null;
		
		if (serviceName.equals("jobsubmition")) 
		{
			obj = retriveJobsubmitionData();
		} 
		else if (serviceName.equals("CDFJobTable"))
		{
			obj = retriveCDFJob();
		}
		else 
		{
			System.out.println("잘못된 요청");
		}
		return obj ;
	}
	
	/**
	 * 
	 * Desc : execute query for JobsubmitionData
	 * @Method Name : retriveJobsumitionData
	 * @return obj
	 *
	 */
	public Object retriveJobsubmitionData(){
		Object obj = null;
		Statement stmt = OpenDatabase();	
		String selectQuery = "select * from cdf_job";
		
		try {
			ResultSet rs = stmt.executeQuery(selectQuery);
			
			int i=0;
			int month[] = new int[12];
			while (rs.next()) {
				month[rs.getInt(1)-1] += rs.getInt(5);				
			}
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
	
	/**
	 * 
	 * Desc : execute query for CDFJobTable
	 * @Method Name : retriveCDFJob
	 * @return
	 *
	 */
	public Object  retriveCDFJob(){
		Object obj = null;
		Statement stmt = OpenDatabase();	
		String selectQuery = "select * from cdf_job";
		Vector vector = new Vector();
		
		try {
			ResultSet rs = stmt.executeQuery(selectQuery);
			
			int i=0;
			//String[] row = new String[9]; 
			while (rs.next()) {
				String[] row = new String[8]; 
				row[0] = rs.getString(1);
				row[1] = rs.getString(2)+" ~ "+rs.getString(3);
				row[2] = rs.getString(4);
				row[3] = rs.getString(5);
				row[4] = rs.getString(6);
				row[5] = rs.getString(7)+" %";
				row[6] = rs.getString(8);
				row[7] = rs.getString(9);
				//row[8] = rs.getString(9);
				vector.addElement(row);					
			}
			obj = vector;		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//System.out.println("[retriveWorkerNodeStatus()]"+e.toString());
		}	finally{
			CloseDatabase();
		}
		
		return obj;
	}
	
	/**
	 * 
	 * Desc :
	 * @Method Name : main
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KissManDatabase c = new KissManDatabase();
		c.requestDataToDataBase("jobsubmition");
	}
}





