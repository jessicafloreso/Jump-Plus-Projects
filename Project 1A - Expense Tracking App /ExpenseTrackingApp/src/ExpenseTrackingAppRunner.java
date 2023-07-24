import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import connection.ApplicationDB;
import utilities.ColorsUtility;

public class ExpenseTrackingAppRunner {
	
	private static ApplicationDB db;
	private static Scanner sc;
	private static String user;
	private static String cookiePath;
	
	public static void main(String[] args) {
		db = new ApplicationDB();
		
		sc = new Scanner(System.in);
		
		cookiePath = "resources/cookies.txt";
		
		// read cookies
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(cookiePath)))) {
			user = reader.readLine();
			if (user != null) {
				System.out.println("	Welcome back " + user);
				db.getUsers().getUserId(user);
				
			} else {
				System.out.println("	Welcome, You are Currently a Guest User!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
						ColorsUtility.startCyan();
						user = db.getUsers().addUser(sc, cookiePath);
						ColorsUtility.reset();
						
					} catch (SQLException e) {
						ColorsUtility.startRed();
						System.out.println("ERROR: User Exists Already, Try Again");
						//e.printStackTrace();
						ColorsUtility.reset();
					}
					break;
				case "login": 
				case "2":
					try {
						ColorsUtility.startGreen();
						user = db.getUsers().login(sc, cookiePath);
						System.out.println("Logged in as " + user);
						ColorsUtility.reset();
					} catch (Exception e1) {
						ColorsUtility.startRed();
						System.out.println("Please enter valid login credentials");
						ColorsUtility.reset();
					}
					break;
				case "logout":
				case "3":
					logout();
					break;
				default:
					System.out.println("	** Please type a valid input/command. **");
					System.out.println("	**  To see commands, type 'commands'  **");
					break;
				}
			}
			
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
