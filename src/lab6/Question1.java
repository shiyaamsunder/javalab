package lab6;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Question1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Testing connection
	
		 Statement statement = null;
		 ResultSet resultSet = null;
		 Connection conn = null;
		 
		 
		// Statement for querying a database;
		try {
			conn = PostgresConnection.connect(); 
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * from EMPLOYEE");
			
			//Process query results
			ResultSetMetaData metaData = resultSet.getMetaData();
			int colCount = metaData.getColumnCount();
			for(int i=1; i<=colCount; i++) {
				System.out.printf("%-8s\t", metaData.getColumnName(i));
				
			}
			
			System.out.println();
			
			while(resultSet.next()) {
				for(int i=1; i<=colCount; i++) {
					System.out.printf("%-8s\t", resultSet.getObject(i));
				}
				System.out.println();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}

}