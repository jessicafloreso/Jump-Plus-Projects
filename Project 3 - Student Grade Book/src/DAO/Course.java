package DAO;

public class Course {

	private int id;
	private int teacherId;
	private String subject;
	
	//course with no students
	public Course(int id, int teacherId, String subject) {
		super();
		this.id = id;
		this.teacherId = teacherId;
		this.subject = subject;
	}
	

	public Course() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", teacherId=" + teacherId +  ", subject=" + subject
				+ "]";
	}
	
	
	
	
}
