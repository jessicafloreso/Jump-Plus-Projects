package DAO;

import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionManager;

public class ApplicationDB {
	private UsersDaoSql users;
	private MoviesDaoSql movies;
	private RatingDaoSql ratings;
	private Connection conn;
	
	public ApplicationDB() {
		super();
		try {
			this.conn = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.users = new UsersDaoSql(conn);
		this.movies = new MoviesDaoSql(conn);
		this.ratings = new RatingDaoSql(conn);
	}
	
	public UsersDaoSql getUsers() {
		return users;
	}
	
	public MoviesDaoSql getMovies() {
		return movies;
	}
	public RatingDaoSql getRatings() {
		return ratings;
	}

}
