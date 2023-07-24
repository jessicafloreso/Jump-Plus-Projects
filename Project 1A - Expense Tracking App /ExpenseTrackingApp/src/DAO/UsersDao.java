package DAO;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import customExceptions.InvalidLoginException;
import model.User;

public interface UsersDao {
	//public List<Users> getAllUsers(ApplicationDB db);
	public Optional<User> getUserById(int idArg);
	public String login(Scanner sc, String cookiePath) throws InvalidLoginException;
	public String addUser(Scanner sc, String cookiePath) throws SQLException;	
}