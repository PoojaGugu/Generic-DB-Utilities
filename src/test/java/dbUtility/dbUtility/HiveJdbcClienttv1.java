package dbUtility.dbUtility;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;

public class HiveJdbcClienttv1 {
	
	 private  String driverName ;
	 
	 
	 public HiveJdbcClienttv1(String dbName) throws ClassNotFoundException, SQLException, IOException
	 {
		 this.driverName = getDriver(dbName);
	 }
	 
	 public void setdriverName(String dbName) throws ClassNotFoundException, SQLException, IOException
	 {
		 this.driverName = getDriver(dbName);
	 }
	public String getdriverName()
	{
		return this.driverName;
	}
	
	
	public Connection dbconnect (String dbName) throws SQLException, ClassNotFoundException, IOException {
		
		 Initialize initObj  = new Initialize();
		 String url="",userName="",password="";	
		
		 switch(dbName.toUpperCase()) 
		 {
		 case "ORACLE":
			 url= initObj.getPropertyValue("ORACLE_DB_URL","db.properties");
			 userName= initObj.getPropertyValue("ORACLE_DB_USER","db.properties");
			 password= initObj.getPropertyValue("ORACLE_DB_PWD","db.properties");
			 break;

		 case "SQL" :
			 url= initObj.getPropertyValue("SQL_DB_URL","db.properties");
			 userName= initObj.getPropertyValue("SQL_DB_USER","db.properties");
			 password= initObj.getPropertyValue("SQL_DB_PWD","db.properties");			 
			 break;

		 case "HIVE":
			 url= initObj.getPropertyValue("hive_db_URL","db.properties");
			 userName= initObj.getPropertyValue("hive_User","db.properties");
			 password= initObj.getPropertyValue("hive_Pswd","db.properties");	
			 break;
		 
		 default:
			 break;
			 
		 } 
		 
		 return DriverManager.getConnection(url,userName,password);
	}
	

	public String  getDriver (String dbName) throws SQLException, ClassNotFoundException, IOException {
		
		 Initialize initObj  = new Initialize();
			
	String dbDriverName =null ;
		 switch(dbName.toUpperCase()) 
		 {
		 case "ORACLE":
			 dbDriverName= initObj.getPropertyValue("ORACLE_DB_DRIVER","db.properties");
			 break;

		 case "SQL" :
			 dbDriverName = initObj.getPropertyValue("SQL_DB_DRIVER","db.properties");
			 break;

		 case "HIVE":
			 dbDriverName =initObj.getPropertyValue("hive_db_driver","db.properties");	
			 break;
		 
		 default:
			 dbDriverName=null;
			 break;
			 
		 } 
		 
		 return dbDriverName;
	}
	
	 /**
	   * @param args
	   * @throws SQLException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	   */
	 @SuppressWarnings({ "rawtypes" })
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		 
		 Initialize initObj  = new Initialize();
		 HiveJdbcClienttv1 hivObj = new HiveJdbcClienttv1(args[0]);
		 try{
			 Class.forName(hivObj.getdriverName());
		 } catch (ClassNotFoundException e) 
		 {
			 e.printStackTrace();
			 System.exit(1);
		 }
	      
	      Hashtable<String,HashMap> dataTb = dbFunctions.getDataInHashTable(hivObj.dbconnect(args[0]), args[1]); //query to be passed through command line 
	      initObj.excelOutputforBI(dataTb, args[2]); //Excel path to be specified
	      
	      
//	      String tableName = "testHiveDriverTable";
//	      stmt.execute("drop table if exists " + tableName);
//	      stmt.execute("create table " + tableName + " (key int, value string)");
//	       show tables
//	       String sql = "show tables '" + tableName + "'";
//	       String sql = ("show tables");
//	       ResultSet res = stmt.executeQuery("select * from conversion_stage1.order_tracking limit 10");
	     
	    
	 }
}
