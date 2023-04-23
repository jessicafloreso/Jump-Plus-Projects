package DAO;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public interface StudentDao {
	
	public List<Student> getStudentsInDb();
	
	//CRUD implementation needs to happen
	
	//CREATE
	public Optional<Student> createStudent(Scanner sc, String cookiePath);
	
	//READ
	public Optional<Student> getStudentById(int idArg);
	public Optional<Student> getStudentId(Scanner sc, String cookiePath);
	
	//UPDATE
	public Optional<Student> updateStudent(Scanner sc, String cookiePath);
	
	//DELETE
	public boolean deleteStudent();
	
	public List<Student> getMoviesWithAverageRatings();
}
