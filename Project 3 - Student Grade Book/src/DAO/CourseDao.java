package DAO;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public interface CourseDao {
	
	//CRUD
	//Create
	public String createCourse(Scanner sc, String cookiePath, int teacherId);
	
	
	//Read
	public Optional<Course> getCourseById(Scanner sc, String cookiePath);
	public List<Course> getAllCourses(Scanner sc, String cookiePath);
	public List<Course> getTeacherCourses(int teacherId);
	
	//unorganized
	public List<Student> getStudentsInCourse(Scanner sc, String cookiePath);
	
	//Update - not needed for MVP
	public Optional<Course> updateCourse(Scanner sc, String cookiePath);
	
	//Delete - not needed for MVP
	public boolean deleteCourse(Scanner sc, String cookiePath);
	
	//Students in course
	//Add student to course
	public Optional<Student> addStudent(Scanner sc, String cookiePath);
	//update student
	public Optional<Student> updateStudentGrade();
	//sort students by name and grades
	public List<Student> sortStudents(List<Student> courseStudents);
	
	
	//average across courses
	public List<Optional> getAverageInCourse();
	//average across courses
	public List<Optional> getMedianInCourse();
	
	
	
	
	

}
