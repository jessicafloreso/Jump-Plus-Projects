package DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class RatingDaoSql implements RatingDao{
	private Connection conn;
	private Double rating;
	
	
	public RatingDaoSql(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		// TODO
		
	}

	@Override
	public boolean addRating(ApplicationDB db, Scanner sc, String cookiePath) {
		
		
		System.out.println(" 	 --------------------------------------------");
		System.out.println("	 	*** Adding a Rating... ***");
		System.out.println(" 	 --------------------------------------------");
		
		
		boolean loop = true;
        boolean success = false;
        int movieId = -1;
        int userId = -1;
  
		while(loop) {
		System.out.println("Is there a movie from above that you would like to rate? (y/n): ");
		String ans = sc.nextLine();
		if(!ans.equals("y")) {
			//Let Users know they can add movie
			System.out.println("Would you like to add a movie to rate it? (y/n)");
			String addMov = sc.nextLine();
			if(addMov.equals("y")) {
				db.getMovies().addMovie(sc, cookiePath);
				db.getMovies().getMoviesInDb();
			}else {
				System.out.println("Heading back to common console");
				return false;
			}
		}
		
		loop = false;
		}
		
		//else we go to the rating
		
		boolean intLoop = true;
		while (intLoop ) { 
			try {
				System.out.println("Enter Movie Id: ");
				movieId = sc.nextInt();
				intLoop = false;
			}catch (InputMismatchException e) {
		        System.out.println("Not a valid input. Please enter an integer.");
		        sc.nextLine(); // Clear the scanner buffer
		        intLoop = true;
		    }catch (Exception e){
				System.out.println("not a valid input, please type an int");
				intLoop = true;
			}
		}
		
		boolean dLoop = true; //valid doubles
		while (dLoop) {
			try {
				String title = db.getMovies().getMovieTitle(movieId);
				int titleLen = title.length();
				int spaces = (34 - titleLen)/2;
				String movieDisplay = " ".repeat(spaces) + title + " ".repeat(spaces) ;
				
				System.out.println(" 	 -------------------------------");
				System.out.println("	"+ movieDisplay);
				System.out.println(" 	 -------------------------------");
				System.out.println("	|	Rating:			|\n"
								+ "	|	 0. Really Bad		|\n"
								+ "	|	 1. Bad			|\n"
								+ "	|	 2. Not Good		|\n"
								+ "	|	 3. Okay		|\n"
								+ "	|	 4. Good		|\n"
								+ "	|	 5. Great		|");
				System.out.println(" 	 -------------------------------");
				
				System.out.println("Rating for this movie 0-5 out of 5: ");
				double response = sc.nextDouble();
				
				if (!(response < 0 || response > 5)) {
					rating = response;
					dLoop = false;
				}else {
					System.out.println("Not a valid input. Please enter an valid double.");
			        sc.nextLine();
				}
			}catch (InputMismatchException e) {
		        System.out.println("Not a valid input. Please enter an valid double.");
		        sc.nextLine(); // Clear the scanner buffer
		    }catch (Exception e){
				System.out.println("not a valid input, please type a valid input");
			}
			
		}
		
		//read cookies to get user id
		// read cookies
		String userEmail = null;
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(cookiePath)))) {
			userEmail = reader.readLine();
			}catch (Exception e) {
				e.printStackTrace();
			}
		if (userEmail != null && movieId > 0) {
				userId = db.getUsers().getUserId(userEmail);
				//add a rating to db as user
				insertRatingAsUser(userId, movieId);
				success = true;
				
			} else if (userEmail == null && movieId > 0){
				insertRatingAsGuest(movieId);
				success = true;
			}else {
				System.out.println("We are just as confused as you are!");
				success = false;
			}
		
		return success; 
	}

	@Override
	public List<Rating> getRatingsByUserId(ApplicationDB db, String cookiePath) {
		List<Rating> movieRatingsList = new ArrayList<Rating>();
		List<Movies> movieList = new ArrayList<Movies>();
		
		String userEmail = null;
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(cookiePath)))) {
			userEmail = reader.readLine();
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		int userId = db.getUsers().getUserId(userEmail);
		
		if(userId < 0) {
			System.out.println("Sorry, cannot see ratings as guest...");
			return null;
		}
		
		String stmtStr = 
						"SELECT m.id AS movie_id, m.name AS movie_name, m.description, r.rating " +
	                    "FROM movies m " +
	                    "LEFT JOIN ratings r ON m.id = r.movie_id " +
	                    "WHERE r.user_id = ?";
			
		try (PreparedStatement pstmt = conn.prepareStatement(stmtStr)){
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				//int ratingId = rs.getInt("id");
				int movieId = rs.getInt("movie_id");
				Double rating = rs.getDouble("rating");
				String title = rs.getString("movie_name");
				String description = rs.getString("description");

				Rating ratings = new Rating(1, movieId, userId, rating); 
				Movies movies = new Movies(movieId, title, description );
				movieRatingsList.add(ratings);
				movieList.add(movies);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//print the movies in a neat table
		
		int maxTitle = 0;
		if(!movieRatingsList.isEmpty()) {
			//first calculate spacing for table formatting with max title
			for(Movies movie : movieList) {
				int movieTitleLen = movie.getName().length();
				if (movieTitleLen > maxTitle) {
					maxTitle = movieTitleLen;
			}
		}
			String spaces = " ".repeat(maxTitle);
			String spacesTitle = " ".repeat(8);
			System.out.println("MovieId" + spacesTitle +"Title" + spaces + "Your Rating");
			System.out.println("-------------------------------------------");
			
			
			for(int i = 0; i < movieList.size(); i++) {
				Movies m = movieList.get(i);
				Rating r = movieRatingsList.get(i);
				
				String currTitle = m.getName();
				int currTitleLen = m.getName().length(); //title length
				int currId = m.getId();
				int currIdLen  = Integer.toString(currId).length(); //id length
				
				double numRating = r.getRating();
				String currNumRatStr = Integer.toString((int)numRating); //convert to string to find len
				int numRatingLen = currNumRatStr.length(); 
				
				spacesTitle = " ".repeat(9 - currIdLen); //between id and title
				String spacesTitleR = " ".repeat( 30 - (maxTitle + currTitleLen + currIdLen) + currIdLen/2); //Between title and av rating
				String ratingSpace = " ".repeat(19 - (2 - numRatingLen));
				
				String listMovie = "   " + currId + spacesTitle + currTitle + spacesTitleR + numRating;
				System.out.println(listMovie);
			}
			
		}
		return movieRatingsList;
	}
	

	@Override
	public List<Rating> getRatingsByMovieId(int movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AllRatings> getAllRatings() {
		List<AllRatings> movieRatingsList = new ArrayList<AllRatings>();
		
		String stmtStr = 
				"SELECT m.id AS movie_id, m.name AS movie_name, AVG(r.rating) AS average_rating, COUNT(r.id) AS number_of_ratings\n"
				+ "FROM movies m\n"
				+ "LEFT JOIN ratings r ON m.id = r.movie_id\n"
				+ "GROUP BY m.id, m.name;\n"
				+ "";
			
		try (PreparedStatement pstmt = conn.prepareStatement(stmtStr)){
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int movieId = rs.getInt("movie_id");
				String movieName = rs.getString("movie_name");
				double averageRating = rs.getDouble("average_rating");
				int numberOfRatings = rs.getInt("number_of_ratings");

				AllRatings ratings = new AllRatings(movieId, movieName, averageRating, numberOfRatings); 
				movieRatingsList.add(ratings);
			}
			rs.close();
			pstmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//print the movies in a neat table
		
		int maxTitle = 0;
		if(!movieRatingsList.isEmpty()) {
			//first calculate spacing for table formatting with max title
			for(AllRatings movie : movieRatingsList) {
				int nameLen = movie.getMovieTitle().length();
				if (nameLen > maxTitle) {
					maxTitle = nameLen;
				}
			}
			String spaces = " ".repeat(maxTitle);
			//Movie to title: 16len 63-29
			String spacesTitle = " ".repeat(8);
			System.out.println("MovieId" + spacesTitle +"Title" + spaces + "Average Rating"+ spacesTitle+ "# of Ratings");
			System.out.println("----------------------------------------------------------------------------");
			
			
			for(AllRatings movie : movieRatingsList) {
				
				int currTitle = movie.getMovieTitle().length();
				int currId = movie.getMovieId();
				String currIdLenStr  = Integer.toString(currId); //convert to string to find len
				int currIdLen = currIdLenStr.length(); 
				//System.out.println(currIdLen);
				
				int numRating = movie.getNumRatings();
				String currNumRatStr = Integer.toString(numRating); //convert to string to find len
				int numRatingLen = currNumRatStr.length(); 
				
				spacesTitle = " ".repeat(9 - currIdLen); //between id and title
				String spacesTitleAvg = " ".repeat( 49 - (maxTitle + currTitle + currIdLen) + currIdLen/2); //Between title and av rating
				String AvgRatingSpace = " ".repeat(19 - (2 - numRatingLen));
				
				String listMovie = "   " + currId + spacesTitle + movie.getMovieTitle() + spacesTitleAvg + movie.getAvgRating() + AvgRatingSpace + numRating;
				System.out.println(listMovie);
			}
			
		}
		return movieRatingsList;
	}
	
	private boolean movieExists(String name) {
    	boolean exists = true;
    	String stmtStr = "SELECT * FROM movies WHERE name = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if( !rs.next()) {
				exists = false;
			}
		rs.next();
		}catch (SQLException e) {
			System.out.println("Rating Exists Already");
		}
		
    	return exists;
    }

	private void insertRatingAsUser(int userId, int movieId) {
		//add a rating to db as user
        String sql = "INSERT INTO ratings (movie_id, user_id, rating) VALUES (?, ?, ?)";
        try (PreparedStatement  pstmt = conn.prepareStatement(sql)) {
        	 pstmt.setInt(1, movieId);
        	 pstmt.setInt(2, userId);
        	 pstmt.setDouble(3, rating);
        	// Execute SQL statement
        	 pstmt.executeUpdate();
               
            System.out.println("Movie rated successfully! "  );
            } catch (SQLException e) {
				System.out.println("Could not add movie to db (from insertRatingAsUser)");
				e.printStackTrace();
			}
	}
	private void insertRatingAsGuest(int movieId) {
		//add a rating to db as user
        String sql = "INSERT INTO ratings (movie_id, rating) VALUES (?, ?)";
        try (PreparedStatement  pstmt = conn.prepareStatement(sql)) {
        	 pstmt.setInt(1, movieId);
        	 pstmt.setDouble(2, rating);
        	// Execute SQL statement
        	 pstmt.executeUpdate();
               
            System.out.println("Movie rated successfully!" );
            } catch (SQLException e) {
				System.out.println("Could not add movie to db (from InsertRatingAsGuest)");
				e.printStackTrace();
			}
	}
}