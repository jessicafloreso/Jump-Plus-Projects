package DAO;

public class Rating {
	private int id;
	private int movie_id;
	private int user_id;
	private double rating;
	
	//for future joins
	double average_rating;
	int number_of_ratings;
	
	public Rating(int id, int movie_id, int user_id, double rating) {
		super();
		this.id = id;
		this.movie_id = movie_id;
		this.user_id = user_id;
		this.rating = rating;
	}
	public Rating(int movie_id, int user_id) {
		super();
		this.movie_id = movie_id;
		this.user_id = user_id;
	}
	
	public Rating(int movie_id) {
		super();
		this.movie_id = movie_id;
	}
	public Rating() {
		super();
	}
	public Rating(int movieId, String movieName, double averageRating, int numberOfRatings) {
		super();
		//this.id = id;
		this.movie_id = movieId;
		this.average_rating = averageRating;
		this.number_of_ratings = numberOfRatings;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "Rating [id=" + id + ", movie_id=" + movie_id + ", user_id=" + user_id + ", rating=" + rating + "]";
	}
	
	
	

}
