package kisti.module.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import kisti.module.log.ErrorPopup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConnectToKissmanDB {

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
	public Object requestDataToDataBase(String doNotUse){
		//요청한 서비스 선택하여 해당 펑션 호출
		Object obj = null;		
			obj = retriveCDFJob();		
		return obj ;
	}
	
	/**
	 * 
	 * Desc : execute query for JobsubmitionData
	 * @Method Name : retriveJobsumitionData
	 * @return obj
	 *
	 */
	public Object retriveCDFJob(){
		Object obj = null;
		Statement stmt = OpenDatabase();	
		String selectQuery = "select * from cdf_job";
		
		try {
			ResultSet rs = stmt.executeQuery(selectQuery);
			

			/**
			 * XML 생성 
			 */
			
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				
				//root element
				
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("Data");
				doc.appendChild(rootElement);
				
				
				
				while(rs.next()){
					
					Element node = doc.createElement("Node");
					rootElement.appendChild(node);
					

					Element month = doc.createElement("month");
					month.appendChild(doc.createTextNode(rs.getString(1)));
					node.appendChild(month);
					Element period1 = doc.createElement("period1");
					period1.appendChild(doc.createTextNode(rs.getString(2)));
					node.appendChild(period1);
					Element period2 = doc.createElement("period2");
					period2.appendChild(doc.createTextNode(rs.getString(3)));
					node.appendChild(period2);
					Element user = doc.createElement("user");
					user.appendChild(doc.createTextNode(rs.getString(4)));
					node.appendChild(user);
					Element sucessJob = doc.createElement("sucessJob");
					sucessJob.appendChild(doc.createTextNode(rs.getString(6)));
					node.appendChild(sucessJob);
					Element ratioSucessJob = doc.createElement("ratioSucessJob");
					ratioSucessJob.appendChild(doc.createTextNode(rs.getString(7)));
					node.appendChild(ratioSucessJob);
					Element wallTime = doc.createElement("wallTime");
					wallTime.appendChild(doc.createTextNode(rs.getString(8)));
					node.appendChild(wallTime);
					Element status = doc.createElement("status");
					status.appendChild(doc.createTextNode(rs.getString(9)));
					node.appendChild(status);
				
				}

				
				 /**
				  *  XML 파일로 쓰기
				  */
		        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		        Transformer transformer = transformerFactory.newTransformer();
		        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		        transformer.setOutputProperty(OutputKeys.INDENT, "No");       
		        DOMSource source = new DOMSource(doc);
		        StreamResult result = new StreamResult(new FileOutputStream(new File("cdfJobs.xml")));
// 		         파일로 쓰지 않고 콘솔에 찍어보고 싶을 경우 다음을 사용+ (디버깅용)
//		        StreamResult result = new StreamResult(System.out);	        
		        transformer.transform(source, result);
		        System.out.println("File saved!");
		        
		        
		        /**
		         * Stringm 으로 축출... 
		         */
		        File xmlFile = new File("cdfJobs.xml");
		        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(xmlFile.getPath())));
		        String xmlString="";
		        String str="";
		        while ( (str = br.readLine()) != null) {
					xmlString += str;					
				}
				
		        obj = xmlString;				
		        br.close();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println(e.toString());
			}
			
			
			
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
	 * Desc :
	 * @Method Name : main
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectToKissmanDB c = new ConnectToKissmanDB();
		c.requestDataToDataBase("jobGraph");		
//		c.requestDataToDataBase("jobsubmition");
	}
}





