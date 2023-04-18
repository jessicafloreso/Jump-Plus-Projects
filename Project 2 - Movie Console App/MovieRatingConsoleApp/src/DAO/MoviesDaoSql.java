package DAO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MoviesDaoSql implements MoviesDao{
	private Connection conn;
	private String movie;
	
	public MoviesDaoSql(Connection conn) {
		super();
		this.conn = conn;
	}
	
	@Override
	public List<Movies> getMoviesInDb() { 
		List<Movies> allMovies = new ArrayList<Movies>();
		String stmtStr = "SELECT * FROM movies";
		try(PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
                Movies movie = new Movies();
                movie.setId(rs.getInt("id"));
                movie.setName(rs.getString("name"));
                movie.setDescription(rs.getString("description"));
                allMovies.add(movie);
            }
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("invalid sql command");
			e.printStackTrace();
			return null;
		}
		
		if (!allMovies.isEmpty()) {
			System.out.println("id	name            	Description");
			System.out.println("-------------------------------------------------------");
			for(Movies movie : allMovies) {
				int nameLen = movie.getName().length();
				String spaces = " ".repeat(24 - nameLen );
				System.out.println( movie.getId() + "	"+  movie.getName() + spaces + movie.getDescription());
				}
		}else {
			System.out.println("**No Movies Here yet**");
		}
		
		return allMovies;
	}

	@Override
	public String addMovie(Scanner sc, String cookiePath) { //seession could vary in future based on admin, guest, user
		System.out.println(" 	 --------------------------------------------");
		System.out.println("	 Add a Movie to DB");
		System.out.println(" 	 --------------------------------------------");
		
		boolean loop = true;
        boolean success = false;
  
		while(loop) {
		System.out.println("Enter Movie Name: ");
		String name = sc.nextLine();
		while(movieExists(name)) {
			//try again or exit
			System.out.println("Try again? (y/n): ");
			name = sc.nextLine();
			if(name.equals("n")) {
				System.out.println("Heading back to common console");
				return null;
			}
			
			System.out.println("Enter Movie Name: ");
			name = sc.nextLine();
		}
		movie = name;
		System.out.println("Enter Movie Description: ");
		String description = sc.nextLine();
	        
        //add a movie
        String sql = "INSERT INTO movies (name, description) VALUES (?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, description);
                // Execute SQL statement
                statement.executeUpdate();
                System.out.println("Movie registered successfully: " + name);
                success = true;
            } catch (SQLException e) {
				System.out.println("Could not add movie");
				e.printStackTrace();
			}
		loop = false;
		}
		
		return movie + "Added!";
	}

	@Override
	public Boolean updateMovie(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return null;
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
			System.out.println("Movie Exists Already");
		}
		
    	return exists;
    }

	@Override
	public List<Movies> getMoviesWithAverageRatings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movies getMovieById(int idArg) {
		try(PreparedStatement pstmt = conn.prepareStatement("select * from movies where id = ?")) {
			pstmt.setInt(1, idArg);
			ResultSet rs = pstmt.executeQuery();
			
			if( rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Movies movie = new Movies(id, name, description);
				
				rs.close();
				return movie;
			} else {
				rs.close();
				return null;
			}
		} catch(SQLException e) {
			System.out.println("sql error");
			return null;
		}
	}
	//@Override
		public int getMovieId(String title) {
			try(PreparedStatement pstmt = conn.prepareStatement("select * from movies where name = ?")) {
				pstmt.setString(1, title);
				ResultSet rs = pstmt.executeQuery();
				
				if( rs.next()) {
					int id = rs.getInt("id");
					//String name = rs.getString("name");
//					
					rs.close();
					return id;
				} else {
					rs.close();
					return -1; //movie doesnt exist
				}
			} catch(SQLException e) {
				System.out.println("sql error");
				//return Optional.empty();
			}
			return -1;
		}
		
		public String getMovieTitle(int id) {
			String title = "";
			try(PreparedStatement pstmt = conn.prepareStatement("select * from movies where id = ?")) {
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				
				if( rs.next()) {
					title = rs.getString("name");
					//String name = rs.getString("name");
//					
					rs.close();
					return title;
				} else {
					rs.close();
					return title; //movie doesnt exist
				}
			} catch(SQLException e) {
				System.out.println("sql error");
				//return Optional.empty();
			}
			return title;
		}
}