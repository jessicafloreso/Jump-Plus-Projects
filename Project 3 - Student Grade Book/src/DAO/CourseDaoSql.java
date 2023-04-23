package DAO;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CourseDaoSql implements CourseDao{
//  each course has:
//	private int id;
//	private int teacherId;
//	private String subject;
	@Override
	public Optional<Course> createCourse(Scanner sc, String cookiePath, String username) {
		boolean loop = true;
		Optional<Course> created = Optional.empty();
		
		while(loop) {
			
			//Ask for course name
			System.out.println("Enter coursename: ");
			
			
			
			
			
			
		}
		
		
		
		return created;
	}

	@Override
	public Optional<Course> getCourseById(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Course> getAllCourses(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> getTeacherCourses(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
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
