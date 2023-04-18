package DAO;

public class AllRatings {
	int movieId;
	String movieTitle;
	double avgRating;
	int numRatings;
	public AllRatings(int movieId, String movieTitle, double avgRating, int numRatings) {
		super();
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.avgRating = avgRating;
		this.numRatings = numRatings;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
	public int getNumRatings() {
		return numRatings;
	}
	public void setNumRatings(int numRatings) {
		this.numRatings = numRatings;
	}
	@Override
	public String toString() {
		return "AllRatings [movieId=" + movieId + ", movieTitle=" + movieTitle + ", avgRating=" + avgRating
				+ ", numRatings=" + numRatings + "]";
	}
	
	
}
