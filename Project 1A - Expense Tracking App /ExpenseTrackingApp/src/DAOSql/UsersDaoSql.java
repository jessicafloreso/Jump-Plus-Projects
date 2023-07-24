package DAOSql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;
import DAO.UsersDao;
import customExceptions.InvalidLoginException;
import model.User;
import utilities.ColorsUtility;

public class UsersDaoSql implements UsersDao {
	private Connection conn;
	private String user;

	public UsersDaoSql(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public Optional<User> getUserById(int idArg) {
		try (PreparedStatement pstmt = conn.prepareStatement("select * from users where id = ?")) {
			pstmt.setInt(1, idArg);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");

				User user = new User(id, username);

				Optional<User> found = Optional.of(user);
				rs.close();
				return found;
			} else {
				rs.close();
				return Optional.empty();
			}
		} catch (SQLException e) {
			System.out.println("sql error");
			return Optional.empty();
		}
	}

	
	public int getUserId(String user) {
		int id = 0;
		try (PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?")) {
			pstmt.setString(1, user);
			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				rs.close();
				id = -1;
				return id;
			}
			// rs.next();
			id = rs.getInt("id");
			// String username = rs.getString("username");

		} catch (SQLException e) {
			System.out.println("sql error");

		}
		return id;
	}

	@Override
	public String login(Scanner sc, String cookiePath) throws InvalidLoginException {
		user = null;
		boolean loop = true;

		while (loop) {
			System.out.println("Enter Username: ");
			String username = sc.nextLine();

			System.out.println("Enter Password: ");
			String password = sc.nextLine();

			// find User to login from database

			String stmtStr = "SELECT * FROM users WHERE username = ? AND password = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				ResultSet rs = pstmt.executeQuery();

				if (!rs.next()) {
					ColorsUtility.startRed();
					System.out.println("Please enter valid login credentials");
					ColorsUtility.startGreen();
				}else {
					rs.next();
					user = username;
					loop = false;
				}
				
			} catch (SQLException e) {
				System.out.println("invalid sql command");
				return null;
			}
		}
		
		// if exception not thrown, then success

		// update cookies
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(cookiePath), false))) {
			writer.write(user);

		} catch (Exception e) {
			System.out.println("There is a problem with the login attempt - unable to update cookies.txt");
			//e.printStackTrace();
		}
		return user;

	}

	public String addUser(Scanner sc, String cookiePath) throws SQLException {
//		ColorsUtility.startCyan();
		System.out.println(" 	 --------------------------------------------");
		System.out.println("	| Welcome new user, let's make your account! |");
		System.out.println(" 	 --------------------------------------------");
		boolean loop = true;
		boolean success = false;
		while (loop) {
			// Prompt user information to create an account
			System.out.println("Enter Your username: ");
			String username = sc.nextLine();

			//  check if empty
			if (!validateString(username)) {
				while(!validateString(username)) {
					ColorsUtility.startRed();
					System.out.println("ERROR: Please enter valid username: ");
					ColorsUtility.startCyan();
					username = sc.nextLine();
				}
			}

			System.out.println("Enter Your Password:");
			String password = sc.nextLine();

			if (!validateString(password)) {
				while(!validateString(password)) {
					ColorsUtility.startRed();
					System.out.println("ERROR: Please enter valid password: ");
					ColorsUtility.startCyan();
					password = sc.nextLine();
				}
			}
			
			System.out.println("Confirm Password:");
			String confirm = sc.nextLine();			
			
			// Confirm password
			if (!confirmPassword(password, confirm)) {
				ColorsUtility.startRed();
				// throw new IllegalArgumentException("Password confirmation failed");
				System.out.println("Passwords do not match, try again");
				ColorsUtility.startCyan();
				System.out.println("Enter Your Password:");
				password = sc.nextLine();

				System.out.println("Confirm Password:");
				confirm = sc.nextLine();
			}

			if (userExists(password, confirm)) {
				return null;
			}

			// add a user with valid credentials
			String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
			try (PreparedStatement statement = conn.prepareStatement(sql)) {
				statement.setString(1, username);
				statement.setString(2, password);
				// Execute SQL statement
				statement.executeUpdate();
				System.out.println("User registered successfully: " + username);
				success = true;
			}

			// add a user to the cookie path to greet user and allow access to own database
			if (success) {
				user = username;
				// update cookies
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(cookiePath), false))) {
					writer.write(user);
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println(" 	 --------------------------------------------");
				System.out.println("		Welcome " + user);
				System.out.println(" 	 --------------------------------------------");
				// get out of while loop
				loop = false;
			} else {
				System.out.println("Could not create User... Lets try again?");
			}
		}
//		ColorsUtility.reset();
		return user;
	}

	// Method to validate user and pass entry - for some reason the not null isn't working on the db end so 
	private boolean validateString(String str) {
		return !str.isEmpty();
	}

	// Method to confirm password
	private boolean confirmPassword(String password, String confirm) {
		return password.equals(confirm);
	}

	private boolean userExists(String username, String password) {
		ColorsUtility.startRed();
		boolean exists = true;
		String stmtStr = "SELECT * FROM users WHERE username = ? AND password = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				exists = false;
			}
			rs.next();
		} catch (SQLException e) {
			System.out.println("User Exists Already");
		}

		if (exists) {
			ColorsUtility.startRed();
			System.out.println("User Already Exists, try another username");
		}
		ColorsUtility.startCyan();
		return exists;
	}
}