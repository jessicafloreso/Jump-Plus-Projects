package DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import customExceptions.InvalidEmailException;
import customExceptions.InvalidLoginException;

public interface UsersDao {
	//public List<Users> getAllUsers(ApplicationDB db);
	public Optional<Users> getUserById(int idArg);
	public String login(Scanner sc, String cookiePath) throws InvalidLoginException;
	public String addUser(Scanner sc, String cookiePath) throws SQLException;	
}