package lab6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {
	private static String url = "jdbc:postgresql://localhost:5432/";
	private static String username ="postgres";
	private static String password = "postgres";
	
	
	public static Connection connect() {
		return connect("postgres");
	}
	public static Connection connect(String dbname) {
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(String.format("%s%s", url,dbname), username, password);
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return conn;
	}
}
