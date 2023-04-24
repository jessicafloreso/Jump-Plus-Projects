package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CourseDaoSql implements CourseDao{
	
	private Connection conn;
	private String id;
	private String course;
	
	public CourseDaoSql(Connection conn) {
		super();
		this.conn = conn;
	}
//  each course has:
//	private int id;
//	private int teacherId;
//	private String subject;
	@Override
	public String createCourse(Scanner sc, String cookiePath, int teacherId) {
		String success = null;
		boolean loop = true;
		
		//String course;
		
		if (teacherId == -1)
			return "course cannot be created, please login as a teacher";
		
		while(loop) {
			
			//Ask for course name
			System.out.println("Enter coursename: ");
			course = sc.nextLine();
			
			System.out.println("Creating course " + course +"...");
			
			if(courseExists(course, teacherId)) {
				System.out.println("Course exists...");
				return "course cannot be created since it exists!";
				
			}
			
	    	String stmtStr = "INSERT INTO course (teacher_id, course_subject) VALUES (?, ?)";
			try(PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
				pstmt.setInt(1, teacherId);
				pstmt.setString(2, course);
				pstmt.executeUpdate();
				
				System.out.println("Course: " + course + " created succesffuly!");
				loop = false;
			}catch (SQLException e) {
				System.out.println("Course could not be created");
			}
			
			return "Course: " + course + " created!";
		}
		return success;
	}

	private boolean courseExists(String course, int teacherId) {
		
		
		boolean exists = true;
    	String stmtStr = "SELECT * FROM course WHERE teacher_id = ? AND course_subject = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
			pstmt.setInt(1, teacherId);
			pstmt.setString(2, course);
			ResultSet rs = pstmt.executeQuery();
			
			if( !rs.next()) {
				exists = false;
			}
			//rs.next();
		}catch (SQLException e) {
			System.out.println("User Exists Already");
		}
    	
		if(exists) {
			System.out.println("Course has been found!");
		}
		
    	return exists;
		
}
	
	public int getCourseId(String course, int teacherId) {
		
		int courseId = -1;
		
		if(!courseExists(course, teacherId)) {
			System.out.println("This course does not exists given the name and your current teacher id");
			return courseId;
		}
		
		//db 
		String sql = "SELECT * FROM course WHERE teacher_id = ? AND course_subject = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, teacherId);
			pstmt.setString(2, course);
			ResultSet rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				System.out.println("Unable to find the course...");
				return teacherId;
			}
			courseId = rs.getInt("course_id");
			
		}catch (SQLException e) {
			System.out.println("invalid sql command @ get course Id");
			return courseId;
		}
		
		
		return courseId;
	}

	@Override
	public Optional<Course> getCourseById(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override //not needed for MVP
	public List<Course> getAllCourses(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> getTeacherCourses(int teacherId) {
		
		List<Course> courses = new ArrayList<Course>();
		//List<Student> students = new ArrayList<Student>();
		
		String stmtStr = "SELECT c.subject AS course_name, AVG(s.grade) AS avg_grade, MEDIAN(s.grade) AS median_grade "
                + "FROM course c "
                + "JOIN student s ON c.course_id = s.course_id "
                + "JOIN teacher t ON c.teacher_id = t.teacher_id "
                + "WHERE t.teacher_id = ? "
                + "GROUP BY c.subject";
		try(PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
			pstmt.setInt(1, teacherId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String courseName = rs.getString("course_name");
                double avgGrade = rs.getDouble("avg_grade");
                double medianGrade = rs.getDouble("median_grade");

                // Print the course statistics
                System.out.println("Course: " + courseName);
                System.out.println("Average Grade: " + avgGrade);
                System.out.println("Median Grade: " + medianGrade);
                System.out.println("------------");
			}
		}catch (SQLException e) {
			System.out.println("User Exists Already");
		}
		
		
		
		return null;
	}

	@Override
	public List<Student> getStudentsInCourse(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * Not needed for MVP
	 */
	@Override
	public Optional<Course> updateCourse(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	/* 
	 * Not needed for MVP
	 */
	@Override
	public boolean deleteCourse(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<Student> addStudent(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Student> updateStudentGrade() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Student> sortStudents(List<Student> courseStudents) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Optional> getAverageInCourse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Optional> getMedianInCourse() {
		// TODO Auto-generated method stub
		return null;
	}

}
