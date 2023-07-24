package DAO;

import java.util.List;
import java.util.Scanner;

import model.Expense;

public interface ExpensesDao {
	public List<Expense> getAllExpenses(); //not smart for security, 
	public Expense getExpenseById(int id);
	public String addExpense(Scanner sc, String cookiePath);
	public Boolean updateExpense(Scanner sc, String cookiePath);
	public Boolean deleteExpense(Scanner sc, String cookiePath);
	//public List<Expense> getMoviesWithAverageRatings();
}
