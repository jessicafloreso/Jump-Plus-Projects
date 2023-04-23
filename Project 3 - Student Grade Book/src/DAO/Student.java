package DAO;

public class Student {
	private int id;
	private String firstName;
	private String lastName;
	private double grade;
	public Student(int id, String firstName, String lastName, double grade) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.grade = grade;
	}
	public Student() {
		super();
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", grade=" + grade + "]";
	}
	
	
	

}
