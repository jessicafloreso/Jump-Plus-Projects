package DAO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import customExceptions.InvalidLoginException;

public class UsersDaoSql implements UsersDao {
	private Connection conn;
	private String user;

	public UsersDaoSql(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public Optional<Users> getUserById(int idArg) {
		try(PreparedStatement pstmt = conn.prepareStatement("select * from users where id = ?")) {
			pstmt.setInt(1, idArg);
			ResultSet rs = pstmt.executeQuery();
			
			if( rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email");
				
				Users user = new Users(id, email);
				
				Optional<Users> found = Optional.of(user);
				rs.close();
				return found;
			} else {
				rs.close();
				return Optional.empty();
			}
		} catch(SQLException e) {
			System.out.println("sql error");
			return Optional.empty();
		}
	}
	
	//@Override
	public int getUserId(String user) {
		int id = 0;
		try(PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM users WHERE email = ?")) {
			pstmt.setString(1, user);
			ResultSet rs = pstmt.executeQuery();
			
			if( !rs.next()) {
				rs.close();
				id = -1;
				return id;
			} 
			//rs.next();
			id = rs.getInt("id");
			//String email = rs.getString("email");
			
		} catch(SQLException e) {
			System.out.println("sql error");
			
		}
		return id;
	}

	@Override
	public String login(Scanner sc, String cookiePath) throws InvalidLoginException {
		user = null;
		boolean loop = true;
        
		while(loop) {
			System.out.println("Enter Username: ");
			String email = sc.nextLine();
			
			if (!validateEmailFormat(email)) {
			    //("Invalid email format");
				System.out.println("**!! Invalid Email Formatting!!**");
				System.out.println("** To re-attempt logging in, use the " + '"'+ "login" + '"' + " command and use valid credentials ");
				System.out.println("** New Users please make an account with the "+ '"'+ "add user" + '"' + " command");
				return user;
			}
			
			System.out.println("Enter Password: ");
			String password = sc.nextLine();
			
			
			//find User to login with database
			//db.getUsers().login(email, password);
			
			String stmtStr = "SELECT * FROM users WHERE email = ? AND password = ?";
			try(PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
				pstmt.setString(1, email);
				pstmt.setString(2, password);
				ResultSet rs = pstmt.executeQuery();
				//rs.next();
				
				if( !rs.next()) {
					//throw new InvalidLoginException("Invalid login");
					System.out.println("Please enter valid login credentials");
					loop = false;
					return null;
				}
				rs.next();
				user = email;
			} catch (SQLException e) {
				System.out.println("invalid sql command");
				return null;
			}
			
			// if exception not thrown, then success
			
			//update cookies
			try(BufferedWriter writer = new BufferedWriter( new FileWriter(new File(cookiePath), false))) {
				writer.write(user);
				System.out.println("Logged in as " + user);

			} catch (Exception e) {
				//System.out.println("User Exists Already");
				e.printStackTrace();
			}
			loop = false; 
		}
		return user;

		
	}
	
	public String addUser(Scanner sc, String cookiePath) throws SQLException {
		System.out.println(" 	 --------------------------------------------");
		System.out.println("	| Welcome new user, let's make your account! |");
		System.out.println(" 	 --------------------------------------------");
		boolean loop = true;
        boolean success = false;
		while(loop) {
			//Prompt user information to create an account
			System.out.println("Enter Your Email:");
			String email = sc.nextLine();
			
			// Validate email format using regular expression
	        if (!validateEmailFormat(email)) {
	            //throw new IllegalArgumentException("Invalid email format");
	        	System.out.println("Please enter valid email");
	        	email = sc.nextLine();
	        }
			
			System.out.println("Enter Your Password:");
			String password = sc.nextLine();
			
			System.out.println("Confirm Password:");
			String confirm = sc.nextLine();

	        // Confirm password
	        if (!confirmPassword(password, confirm)) {
	            //throw new IllegalArgumentException("Password confirmation failed");
	        	System.out.println("Passwords do not match, try again");
	        	System.out.println("Enter Your Password:");
				password = sc.nextLine();
				
				System.out.println("Confirm Password:");
				confirm = sc.nextLine();
	        }
	        
	        if(userExists(password, confirm)) {
	        	return null;
	        }
	        
	        
	        
	        //add a user with valid credentials
	        String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
	        try (PreparedStatement statement = conn.prepareStatement(sql)) {
	                statement.setString(1, email);
	                statement.setString(2, password);
	                // Execute SQL statement
	                statement.executeUpdate();
	                System.out.println("User registered successfully: " + email);
	                success = true;
	            }
			
			//add a user to the cookie path to greet user and allow access to own database
			if(success) {
				user = email;
				//update cookies
				try(
					BufferedWriter writer = new BufferedWriter( new FileWriter(new File(cookiePath), false))) {
					writer.write(user);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println(" 	 --------------------------------------------");
				System.out.println("		Welcome " + user);
				System.out.println(" 	 --------------------------------------------");
			//get out of while loop
			loop = false;
			}else {
				System.out.println("Could not create User... Lets try again?");
			}
		}
		return user;
	}

    // Method to validate email format using regular expression
    private boolean validateEmailFormat(String email) {
        // Regular expression pattern for email validation
        String emailPattern = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to confirm password
    private boolean confirmPassword(String password, String confirm) {
        return password.equals(confirm);
    }
    
    private boolean userExists(String email, String password) {
    	boolean exists = true;
    	String stmtStr = "SELECT * FROM users WHERE email = ? AND password = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			
			if( !rs.next()) {
				exists = false;
			}
		rs.next();
		}catch (SQLException e) {
			System.out.println("User Exists Already");
		}
    	
		if(exists) {
			System.out.println("User Already Exists");
		}
		
    	return exists;
    }
	
}