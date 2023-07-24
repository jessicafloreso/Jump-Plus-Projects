package DAOSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import DAO.ExpensesDao;
import model.Expense;

public class ExpensesDaoSql implements ExpensesDao{
	private Connection conn;
	
	public ExpensesDaoSql(Connection conn){
		super();
		this.conn = conn;
	}

	@Override
	public List<Expense> getAllExpenses() {
		List<Expense> allExpenses = new ArrayList<Expense>();
		String stmt = "SELECT * FROM expenses";
		try(PreparedStatement pstmt = conn.prepareStatement(stmt)) {
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
                Expense expense = new Expense();
                expense.setId(rs.getInt("id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setCategory(rs.getString("category"));
                expense.setDescription(rs.getString("description"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDay(rs.getInt("day"));
                expense.setMonth(rs.getInt("month"));
                expense.setYear(rs.getInt("year"));
                allExpenses.add(expense);
            }
			rs.close();
		} catch (SQLException e) {
			System.out.println("invalid sql command");
			e.printStackTrace();
			return null;
		}
		
		
		return null;
	}

	@Override
	public Expense getExpenseById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addExpense(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateExpense(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteExpense(Scanner sc, String cookiePath) {
		// TODO Auto-generated method stub
		return null;
	}

}
