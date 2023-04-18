package DAO;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public interface MoviesDao {
	public List<Movies> getMoviesInDb();
	public Movies getMovieById(int idArg);
	public String addMovie(Scanner sc, String cookiePath);
	public Boolean updateMovie(Scanner sc, String cookiePath);
	public List<Movies> getMoviesWithAverageRatings();
}
