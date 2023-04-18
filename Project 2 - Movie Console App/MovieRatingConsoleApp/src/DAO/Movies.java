package DAO;

public class Movies {
	private int id;
	private String name;
	///private double rating;
	private String description;
	
	
	public Movies(int id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		//this.rating = rating;
		this.description = description;
	}
	public Movies() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//	public double getRating() {
//		return rating;
//	}
//	public void setRating(double rating) {
//		this.rating = rating;
//	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Movies [id=" + id + ", name=" + name + "]";
	}
	
	

}