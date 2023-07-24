package model;

public class Expense {
    private int id;
    private int userId;
    private String category;
    private String description;
    private double amount;
    private int day;
    private int month;
    private int year;

    public Expense(int id, int userId, String category, String description, double amount, int day, int month, int year) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    public Expense() {
    	super();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", userId=" + userId + ", category=" + category + ", description=" + description
				+ ", amount=" + amount + ", day=" + day + ", month=" + month + ", year=" + year + "]";
	}

	
	
	
}
