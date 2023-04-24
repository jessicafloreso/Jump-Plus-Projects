package DAO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

/*
 * Here we will make calls to the database for teacher related methods like adding a teacher
 * or logging in as a teacher 
 */

public class TeacherDaoSql implements TeacherDao{

	private Connection conn;
	private String teacher;
	private String username;
	
	public TeacherDaoSql(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public String createTeacher(Scanner sc, String cookiePath) throws SQLException {
		System.out.println("------------------------------------------");
		System.out.println("Hello Teacher, Welcome to the Gradebook!");
		System.out.println("Let's get your account set up!");
		System.out.println("------------------------------------------");
		//Set up booleans fo the while and the success of creating a teacher to the db
		boolean loop = true;
		boolean success = false;
		
		while(loop) {
			
			boolean notUnique = true;
			while(notUnique){
				//ask teacher for user name:
				System.out.println("Enter your username: ");
				username = sc.nextLine();
				if (userExists(username)) {
					System.out.println("Please Use a different username for this new account...");
					//sc.nextLine();
				}else{
					notUnique = false;
				};
			}
			//Ask for password
			
			System.out.println("Enter your password: ");
			String password = sc.nextLine();
			
			//Add user to the db using credentials
			String sql = "INSERT INTO teacher (username, password) VALUES (?, ?)";
			try(PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				
				pstmt.executeUpdate();
				System.out.println("Teacher registered successfully: " + username + "!");
				success = true;
				
				teacher = username;
				//make optional to return
				//Teacher newTeach = new Teacher(username, password);
				//created = Optional.of(newTeach);
				
			}
			loop = false;
			
			//add teacher to the cookie path and allow access to the db
			if(success) {
				//update the cookies
				try {
					Files.lines(Paths.get(cookiePath)).forEach(currTeacher -> {
					    if (currTeacher != null) {
					    	teacher = currTeacher;
					    }
					});
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return teacher;

	}

	@Override
	public String login(Scanner sc, String cookiePath) {
		teacher = null;
		boolean loop = true;
        
		while(loop) {
			System.out.println("Enter Username: ");
			String username = sc.nextLine();
			
			System.out.println("Enter password: ");
			String password = sc.nextLine();
			
			//check db
			String sql = "SELECT * FROM teacher WHERE username = ? AND password = ?";
			try(PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, username);
				pstmt.setString(2,  password);
				ResultSet rs = pstmt.executeQuery();
				
				if(!rs.next()) {
					System.out.println("Please enter valid login credentials");
					loop = false;
					return null;
				}
				rs.next();
				teacher = username;
				
			}catch (SQLException e) {
				System.out.println("invalid sql command");
				return null;
			}
			
			//If we get here, the exception is not thrown and we are able to update the cookies to allow teacher access to db
			try(BufferedWriter writer = new BufferedWriter (new FileWriter(new File(cookiePath), false))) {
				writer.write(teacher);
				System.out.println("Logged in as " + teacher);

			} catch (Exception e) {
				//System.out.println("User Exists Already");
				e.printStackTrace();
			}
			loop = false;
		}
		return teacher;
	}

	@Override
	public Optional<Teacher> getTeacherById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public int getTeacherId(String username) {
		if(username == null) {
			System.out.println("Please login to continue using our db services...");
			return -1;
		}
		
		int teacherId = -1;
		
		//db 
		String sql = "SELECT * FROM teacher WHERE username = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				System.out.println("Please enter valid teacher username");
				return teacherId;
			}
			teacherId = rs.getInt("teacher_id");
			
		}catch (SQLException e) {
			System.out.println("invalid sql command");
			return teacherId;
		}
		
		
		return teacherId;
	}
	
	/*
	 * Below is not needed to have mvp... implement later
	 */

	@Override
	public Optional<Teacher> updateTeacher() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean deleteTeacher() {
		// TODO Auto-generated method stub
		return false;
	}

	
	/*
	 * Helper Methods
	 */
	
	//
	
	private boolean userExists(String username) {
    	boolean exists = true;
    	String stmtStr = "SELECT * FROM teacher WHERE username = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			if( !rs.next()) {
				exists = false;
			}
			rs.next();
		}catch (SQLException e) {
			System.out.println("User Exists Already");
		}
    	
		if(exists) {
			System.out.println("Username Already Exists, try a different username");
		}
		
    	return exists;
    }
}
