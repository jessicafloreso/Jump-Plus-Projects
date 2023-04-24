import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Scanner;

import DAO.ApplicationDb;

public class StudentGradebookRunner {
	
	private static String teacher;
	private static String course;
	private static String cookiePath = "resources/cookies.txt";
	private static ApplicationDb db;
	private static Scanner sc;

	public static void main(String[] args) {
		
		db = new ApplicationDb();
		
		sc = new Scanner(System.in);
		
		//Lambda used to read function, will implicitly open and close the reader
		try {
			Files.lines(Paths.get(cookiePath)).forEach(currTeacher -> {
			    if (currTeacher != null) {
			    	teacher = currTeacher;
			        System.out.println("Welcome back teacher, " + teacher);
			    } 
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(teacher == null) {
	        System.out.println("Welcome, Please Login or Register for a Teacher Account!");
	    }
	    
		//System.out.println( db.getTeachers().getTeacherId(teacher));
		
		boolean running = true;
		while (running) {
			if (teacher != null) {
				System.out.print("* "+ teacher + " * > ");
			} else {
				System.out.print("* Unregistered User * > ");
			}
			String command = sc.nextLine(); 
			
			switch(command.toLowerCase()) {
				//user based commands
				case "add teacher":
				case "1":
					try {
						
						//will need to login in order to access account
						db.getTeachers().createTeacher(sc, cookiePath);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					break;
				case "login": 
				case "2":
					try {
						teacher = db.getTeachers().login(sc, cookiePath);
					} catch (Exception e1) {
						System.out.println("Please enter valid login credentials");
					}
					break;
					
				case "logout": 
				case "3":
					logout();
					break;
					
				case "add course":
				case "4":
					try {
						int teacherId = db.getTeachers().getTeacherId(teacher);
						course = db.getCourses().createCourse(sc, cookiePath, teacherId);
						System.out.println(course);
					} catch (Exception e1) {
						System.out.println("Please enter valid login credentials");
					}
					break;
					
				case "my courses":
				case "5":
					try {
						int teacherId = db.getTeachers().getTeacherId(teacher);
						if (teacherId < 0) {
							System.out.println("Please login...");
							break;
						}
						db.getCourses().getTeacherCourses(teacherId);
					} catch (Exception e1) {
						System.out.println("Please enter valid login credentials");
					}
					break;
					
				case "add student":
				case "6":
					try {
						int teacherId = db.getTeachers().getTeacherId(teacher);
						if (teacherId < 0) {
							System.out.println("Please login...");
							break;
						}
						//db.getCourses().getTeacherCourses(teacherId);
						db.getStudents().createStudent(db, sc, teacherId);
						
					} catch (Exception e1) {
						System.out.println("Please enter valid login credentials");
					}
					break;
				case "update grade":
				case "7":
					try {
						int teacherId = db.getTeachers().getTeacherId(teacher);
						if (teacherId < 0) {
							System.out.println("Please login...");
							break;
						}
						//db.getCourses().getTeacherCourses(teacherId);
						db.getStudents().createStudent(db, sc, teacherId);
						
					} catch (Exception e1) {
						System.out.println("Please enter valid login credentials");
					}
					break;
		
			}
		
		}
	}
	
	public static void logout() {
		teacher = null;
		//TODO: ask if they would like to be remembered or not (clear cookies if not)
		//update cookies
		try(BufferedWriter writer = new BufferedWriter( new FileWriter(new File(cookiePath), false))) {
			writer.write("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Logged out");
	}

}
