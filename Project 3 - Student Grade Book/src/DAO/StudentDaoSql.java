package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentDaoSql implements StudentDao{
	private Connection conn;
	
	public StudentDaoSql(Connection conn) {
		super();
		this.conn = conn;
	}
	
	@Override
	public List<Student> getStudentsInDb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createStudent(ApplicationDb db, Scanner sc, int teacherId) {
		System.out.println("------------------------------------------");
		System.out.println("Hello Teacher,");
		System.out.println("Let's add student to your course!");
		System.out.println("------------------------------------------");
		
		if (teacherId == -1) {
			System.out.println("Must login to add students to course...");
			return false;
		}
		
		String success = null;
		boolean loop = true;
		boolean created = false;
		
		while(loop) {
			
			//Ask for course name
			System.out.println("Enter coursename: ");
			String course = sc.nextLine();
			int courseId = db.getCourses().getCourseId(course, teacherId);
			
			if (courseId == -1) {
				System.out.println("Course does not exists, Cannnot add student...");
				return created;
			}
			
			System.out.println("Enter student first name: ");
			String fname = sc.nextLine();
			
			System.out.println("Enter student last name: ");
			String lname = sc.nextLine();
			
			if(studentExists(fname, lname, courseId)){
				return created;
			}
			
	    	String stmtStr = "INSERT INTO student (course_id, student_fname, student_lname) VALUES (?, ?, ?)";
			try(PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
				pstmt.setInt(1, courseId);
				pstmt.setString(2, fname);
				pstmt.setString(3, lname);
				pstmt.executeUpdate();
				
				System.out.println("Student: " + fname + " " + lname + " added to course: " + course +  " succesffuly!");
				created = true;
			}catch (SQLException e) {
				System.out.println("Student could not be created");
			}
			
			loop = false;
			
		}
		
		return created;
	}

	private boolean studentExists(String fname, String lname, int courseId) {
		boolean exists = true;
    	String stmtStr = "SELECT * FROM student WHERE course_id = ? AND student_fname = ? AND student_lname = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
			pstmt.setInt(1, courseId);
			pstmt.setString(2, fname);
			pstmt.setString(3, lname);
			ResultSet rs = pstmt.executeQuery();
			
			if( !rs.next()) {
				exists = false;
			}
			rs.next();
		}catch (SQLException e) {
			System.out.println("Student Exists Already, sql error");
		}
    	
		if(exists) {
			System.out.println("Student Already Exists in selected course...");
		}
		
    	return exists;
	}

	@Override
	public Optional<Student> getStudentById(int idArg) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Student> getStudentId(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Student> updateStudent(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean deleteStudent() {
		// TODO Auto-generated method stub
		return false;
	}

}
