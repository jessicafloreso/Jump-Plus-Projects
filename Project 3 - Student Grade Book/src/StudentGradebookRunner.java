import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Scanner;

import DAO.ApplicationDb;

public class StudentGradebookRunner {
	
	private static String teacher;
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
			        System.out.println("Welcome back " + teacher);
			    } 
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(teacher == null) {
	        System.out.println("Welcome, Please Login or Register for a Teacher Account!");
	    }
	    
		System.out.println( db.getTeachers().getTeacherId(teacher));
		
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
		
			}
		
		}
	}

}
