package kisti.module.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class ConnectToKissManDB {

	private String ipAddressForDatabase;
	private String dbName;
	private String dbUser;
	private String dbPass;
	private String url;
	Connection conn ;
	Statement stmt;
	private String selectQuery;
	private Vector vector;
	private String resultTuple;
	
	
	
	
	public ConnectToKissManDB() {
		// TODO Auto-generated constructor stub
		//1. connet to kissman DB
		//2. retrieve data & save to the array(integer)
		//3. return array ;
		
		ipAddressForDatabase = "150.183.234.168";
		dbName = "kissman";
		dbUser = "kissman";
		dbPass = "kissman";
		url = "jdbc:mysql://"+ipAddressForDatabase+"/kissman";
		
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://150.183.234.168:3306/kissman",dbUser,dbPass);
			stmt = conn.createStatement();
			
			selectQuery="select * from job";			
			ResultSet rs = stmt.executeQuery(selectQuery);
			vector = new Vector();
			
			int i=0;
			while (rs.next()) {
				resultTuple = "nWeek= "+rs.getString(1) +" nJob= "+ rs.getShort(2) +" nFailJob= "+ rs.getByte(3);
				System.out.println(resultTuple);
				vector.add(resultTuple);
				resultTuple="";
			}
		

			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			e.printStackTrace();
		}		
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectToKissManDB c = new ConnectToKissManDB();

	}
}




