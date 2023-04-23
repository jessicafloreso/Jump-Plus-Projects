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

public class ApplicationDb {
	private TeacherDaoSql teachers;

	private Connection conn;
	
	public ApplicationDb() {
		super();
		try {
			this.conn = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.teachers = new TeacherDaoSql(conn);
//		this.movies = new MoviesDaoSql(conn);
//		this.ratings = new RatingDaoSql(conn);
	}
	
	public TeacherDaoSql getTeachers() {
		return teachers;
	}
//	
//	public MoviesDaoSql getMovies() {
//		return movies;
//	}
//	public RatingDaoSql getRatings() {
//		return ratings;
//	}

}