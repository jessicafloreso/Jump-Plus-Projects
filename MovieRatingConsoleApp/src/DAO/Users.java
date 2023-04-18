package DAO;

public class Users {
	private int id;
	private String email;
	private String password;

	public Users(int id, String password, String email) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
	}
	public Users() {
		super();
		
	}
	
	public Users(int id, String email) {
		super();
		this.id = id;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", password=" + password + ", email=" + email + "]";
	}
	
	
}