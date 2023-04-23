package DAO;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

//import customExceptions.InvalidLoginException;

public interface TeacherDao {
	
	//these need cookie paths to read and write to file...
	//Create teacher
	public String createTeacher(Scanner sc, String cookiePath) throws SQLException;	
	public String login(Scanner sc, String cookiePath); //throws InvalidLoginException; 
	
	//Read
	public Optional<Teacher> getTeacherById(int id);
	public int getTeacherId(String username);
	
	/*
	 * Not needed for full functionality of program MVP so implement later
	 */
	//Update
	public Optional<Teacher> updateTeacher();
	
	//Delete
	public boolean deleteTeacher();
	

}
