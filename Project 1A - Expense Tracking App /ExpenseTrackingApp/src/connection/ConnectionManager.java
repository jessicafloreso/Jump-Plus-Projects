package connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

	
	private static Connection connection;
	
	private static void makeConnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
		Properties props = new Properties(); // used to access our properties file
		props.load( new FileInputStream("resources/config.properties") );
		
		String url = props.getProperty("url");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(url, username, password);
	}
	
	public static Connection getConnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		if (connection == null) {
			makeConnection();
		}
		return connection;
	}
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to our Program!");
		System.out.println("Establishing db connection...");
		try {
			Connection connection = ConnectionManager.getConnection();
			System.out.println("connection made!");
			connection.close();

		} catch (FileNotFoundException e) {
			System.out.println("cant load details for connection");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("cant driver for connection");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("cant load details for connection");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("cant connect to db");
			e.printStackTrace();
		}
		
	}
}
