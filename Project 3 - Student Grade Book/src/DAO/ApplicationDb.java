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
	private CourseDaoSql courses;
	private StudentDaoSql students;

	private Connection conn;
	
	public ApplicationDb() {
		super();
		try {
			this.conn = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.teachers = new TeacherDaoSql(conn);
		this.courses = new CourseDaoSql(conn);
		this.students = new StudentDaoSql(conn);
	}
	
	public TeacherDaoSql getTeachers() {
		return teachers;
	}
	
	public CourseDaoSql getCourses() {
		return courses;
	}
	public StudentDaoSql getStudents() {
		return students;
	}

}