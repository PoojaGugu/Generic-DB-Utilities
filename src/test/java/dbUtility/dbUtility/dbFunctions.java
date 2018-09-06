package dbUtility.dbUtility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class dbFunctions {
	public static Hashtable<String,HashMap>  getDataInHashTable(Connection con,String Query) throws ClassNotFoundException, IOException, SQLException
	{
		 Hashtable<String,HashMap> table = new Hashtable<String,HashMap>();
		HashMap<String, String> dataMap = new HashMap<String, String>();
		Statement stmt = con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet resultset =stmt.executeQuery(Query);		
		ResultSetMetaData rsmd = resultset.getMetaData();
		int row=0;
		while (resultset.next())
		{
		
			for (int i = 1; i <= rsmd.getColumnCount(); i++) 
			{
			
				dataMap.put(rsmd.getColumnName(i), resultset.getString(rsmd.getColumnName(i)));
			}
			table.put(String.valueOf(row), (HashMap)dataMap.clone());
			row++;
		}
		
		return table;
	}

}
