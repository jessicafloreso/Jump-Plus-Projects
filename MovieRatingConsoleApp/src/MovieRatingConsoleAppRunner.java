import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
//import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import DAO.ApplicationDB;
//import DAO.UsersDaoSql;
import customExceptions.InvalidLoginException;

public class MovieRatingConsoleAppRunner {
	
	private static ApplicationDB db;
	private static Scanner sc;
	private static String user;
	private static String cookiePath;
	private static int userId;

	public static void main(String[] args) {
		db = new ApplicationDB();
		
		sc = new Scanner(System.in);
		
		cookiePath = "resources/cookies.txt";
		
		
		
		// read cookies
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(cookiePath)))) {
			user = reader.readLine();
			if (user != null) {
				System.out.println("	Welcome back " + user);
				userId = db.getUsers().getUserId(user);
				
			} else {
				System.out.println("	Welcome, You are Currently a Guest User!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		menu();
		boolean running = true;
		while (running) {
			if (user != null) {
				System.out.print("* "+ user + " * > ");
			} else {
				System.out.print("* Guest User * > ");
			}
			String command = sc.nextLine(); 
			
			switch(command.toLowerCase()) {
				//user based commands
				case "add user":
				case "1":
					try {
						user = db.getUsers().addUser(sc, cookiePath);
						
					} catch (SQLException e) {
						System.out.println("User Exists Already");
						e.printStackTrace();
					}
					break;
				case "login": 
				case "2":
					try {
						user = db.getUsers().login(sc, cookiePath);
					} catch (Exception e1) {
						System.out.println("Please enter valid login credentials");
					}
					break;
				case "logout":
				case "3":
					logout();
					break;
				case "commands":
				case "4": 
					menu();
					break;
				
				//movie rating commands
				case "list all movies": 
				case "5": 
					//db.getMovies().getMoviesInDb();
					db.getRatings().getAllRatings();
					break;
				case "view my ratings": 
				case "6":
					db.getRatings().getRatingsByUserId(db, cookiePath);
					break;
				case "add rating": //to existing or new movie
				case "7":
					db.getMovies().getMoviesInDb();
					db.getRatings().addRating(db, sc, cookiePath);
					break;

				case "exit":
				case "8":
					running = false;
					break;
				default:
					System.out.println("	** Please type a valid input/command. **");
					System.out.println("	**  To see commands, type 'commands'  **");
					
					break;
			}
			
		}
		
		sc.close();
		System.out.println("	---------------------------------------");
		System.out.println("	    	Now Exiting Program...");
		System.out.println("	---------------------------------------");
		System.out.println("	---------------------------------------");
		System.out.println("	    	     GOODBYE! :)");
		System.out.println("	---------------------------------------");

	}
	
	public static void menu() {
		System.out.println("	---------------------------------------");
		System.out.println("			COMMANDS				"				);
		System.out.println("	---------------------------------------");
		System.out.println("  	  ** Please type command OR number **");
		System.out.println("	---------------------------------------");
		System.out.println("	    1. Add User	  5. List All Movies");
		System.out.println("	    2. Login	  6. View My Ratings");
		System.out.println("	    3. Logout	  7. Add Rating");
		System.out.println("	    4. Commands   8. Exit");
		System.out.println("	---------------------------------------");

	}
	
	public static void logout() {
		user = null;
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
