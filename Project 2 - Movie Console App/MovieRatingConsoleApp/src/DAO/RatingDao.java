package DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import customExceptions.InvalidLoginException;

public interface RatingDao {
	public boolean addRating(ApplicationDB db, Scanner sc, String cookiePath);
    //public void updateRating(ApplicationDB db, Scanner sc, String cookiePath);
    //public void deleteRating(ApplicationDB db, Scanner sc, String cookiePath);
    List<Rating> getRatingsByUserId(ApplicationDB db, String cookiePath);
    List<Rating> getRatingsByMovieId(int movieId);
    List<AllRatings> getAllRatings();
}

