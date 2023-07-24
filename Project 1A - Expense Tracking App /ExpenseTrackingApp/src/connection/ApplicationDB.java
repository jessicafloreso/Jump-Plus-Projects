package connection;

import java.sql.Connection;

import DAOSql.UsersDaoSql;

public class ApplicationDB {

	private UsersDaoSql users;
	private Connection conn;
	
	public ApplicationDB() {
		super();
		try {
			this.conn = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.users = new UsersDaoSql(conn);
	}
	
	public UsersDaoSql getUsers() {
		return users;
	}
	
}
